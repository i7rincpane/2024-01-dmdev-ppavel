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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.server.ResponseStatusException;
import ru.nvkz.dto.BasketProductCreateEditDto;
import ru.nvkz.dto.BasketProductReadDto;
import ru.nvkz.dto.BasketReadDto;
import ru.nvkz.service.BasketProductService;

@Slf4j
@Controller
@RequestMapping("/basket-products")
@RequiredArgsConstructor
@SessionAttributes({"basket"})
public class BasketProductController {

    private final BasketProductService basketProductService;

    @PostMapping
    public String create(Model model, @RequestParam Long productId, @SessionAttribute("basket") BasketReadDto sessionBasket) {
        BasketProductReadDto newBasketProduct = basketProductService.createOrCountUpdate(new BasketProductCreateEditDto(
                productId,
                sessionBasket.getId(),
                1,
                true
        ));
        model.addAttribute("basket", newBasketProduct.getBasket());
        return "redirect:/products/" + newBasketProduct.getProduct().getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute BasketProductCreateEditDto basketProduct, @SessionAttribute("basket") BasketReadDto sessionBasket) {
        checkAffiliation(id, sessionBasket);
        return basketProductService.update(id, basketProduct)
                .map(it -> "redirect:/baskets/" + it.getBasket().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, @SessionAttribute("basket") BasketReadDto sessionBasket) {
        checkAffiliation(id, sessionBasket);
        if (!basketProductService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/baskets/" + sessionBasket.getId();
    }

    private void checkAffiliation(Long id, BasketReadDto sessionBasket) {
        basketProductService.findById(id)
                .ifPresent(foundBasket -> {
                    if (sessionBasket.getId() != foundBasket.getBasket().getId()) {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
                    }
                });
    }
}
