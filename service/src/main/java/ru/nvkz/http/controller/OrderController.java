package ru.nvkz.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import ru.nvkz.dto.OrderCreateEditDto;
import ru.nvkz.dto.OrderReadDto;
import ru.nvkz.dto.UserDetails;
import ru.nvkz.entity.OrderStatus;
import ru.nvkz.service.OrderService;

import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/user-orders")
    public String findAllByAuthenticationUser(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("orders", orderService.findAllByUserId(userDetails.getId()));
        return "order/user-orders";
    }

    @GetMapping
    public String findAll(Model model) {
        List<OrderReadDto> orders = orderService.findAll();
        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("orders", orders);
        return "order/orders";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return orderService.findById(id)
                .map(order -> {
                    model.addAttribute("order", order);
                    model.addAttribute("orderStatuses", OrderStatus.values());
                    return "order/order";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/create")
    public String showCreate(Model model, @ModelAttribute("order") OrderCreateEditDto order) {
        model.addAttribute("order", order);
        model.addAttribute("orderStatuses", OrderStatus.values());
        return "order/order-create";
    }

    @PostMapping
    public String create(@ModelAttribute OrderCreateEditDto order, @RequestParam List<Long> productIds) {
        return "redirect:/orders" + orderService.create(order, productIds).getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute OrderCreateEditDto order) {
        return orderService.update(id, order)
                .map(it -> "redirect:/orders/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        if (!orderService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/orders";
    }
}