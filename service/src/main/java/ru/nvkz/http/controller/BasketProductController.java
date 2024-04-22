package ru.nvkz.http.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.server.ResponseStatusException;
import ru.nvkz.dto.BasketProductCreateEditDto;
import ru.nvkz.dto.BasketProductReadDto;
import ru.nvkz.service.BasketProductService;

@Slf4j
@Controller
@RequestMapping("/basket-products")
@RequiredArgsConstructor
@SessionAttributes({"basket"})
public class BasketProductController {

    private final BasketProductService basketProductService;

    @PostMapping
    public String create(@ModelAttribute BasketProductCreateEditDto basketProduct, Model model) {
        BasketProductReadDto newBasketProduct = basketProductService.create(basketProduct);
        model.addAttribute("basket", newBasketProduct.getBasket());
        return "redirect:/products/" + newBasketProduct.getProduct().getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute BasketProductCreateEditDto basketProduct, Model model) {
        return basketProductService.update(id, basketProduct)
                .map(it -> {
                    model.addAttribute("basket", it.getBasket());
                    return "redirect:/baskets/" + it.getBasket().getId();
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
