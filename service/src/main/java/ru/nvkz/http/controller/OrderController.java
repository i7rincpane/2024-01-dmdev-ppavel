package ru.nvkz.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.nvkz.dto.CustomUserDetails;
import ru.nvkz.entity.OrderStatus;
import ru.nvkz.exeption.ValidationException;
import ru.nvkz.service.OrderProductService;
import ru.nvkz.service.OrderService;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
@SessionAttributes({"basket"})
public class OrderController {

    private final OrderService orderService;
    private final OrderProductService orderProductService;

    @PostMapping
    public String create(@RequestParam Long basketId, RedirectAttributes redirectAttributes) {
        try {
            return "redirect:/orders/"+ orderService.create(basketId).getId();
        } catch (ValidationException e) {
            redirectAttributes.addFlashAttribute("errors", e.getErrors());
            return "redirect:/baskets/" + basketId;
        }
    }

    @GetMapping
    public String findAllByAuthenticationUser(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("orders", orderService.findAllByUserId(customUserDetails.getId()));
        return "order/orders";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return orderService.findById(id)
                .map(order -> {
                    model.addAttribute("basket", order.getBasket());
                    model.addAttribute("order", order);
                    model.addAttribute("orderProducts", orderProductService.findAllByOrderId(order.getId()));
                    model.addAttribute("orderStatuses", OrderStatus.values());
                    return "order/order";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}