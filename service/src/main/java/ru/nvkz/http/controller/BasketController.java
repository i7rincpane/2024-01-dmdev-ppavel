package ru.nvkz.http.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import ru.nvkz.service.BasketProductService;
import ru.nvkz.service.BasketService;

@Slf4j
@Controller
@RequestMapping("/baskets")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;
    private final BasketProductService basketProductService;

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return basketService.findById(id)
                .map(basket -> {
                    model.addAttribute("basket", basket);
                    model.addAttribute("basketProducts", basketProductService.findAllByBasketId(id));
                    return "basket/basket";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
