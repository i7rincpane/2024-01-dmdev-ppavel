package ru.nvkz.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nvkz.dto.BasketProductCreateEditDto;
import ru.nvkz.service.BasketProductService;

@Controller
@RequestMapping("order-products")
@RequiredArgsConstructor
public class ProductOrderController {

    private final BasketProductService basketProductService;

    @PostMapping
    public String create(@ModelAttribute BasketProductCreateEditDto productOrder) {
        return "redirect:/products/" + basketProductService.create(productOrder).getProduct().getId();
    }
}
