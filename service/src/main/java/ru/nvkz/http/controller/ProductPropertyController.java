package ru.nvkz.http.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import ru.nvkz.dto.ProductPropertyCreateEditDto;
import ru.nvkz.entity.TypeValue;
import ru.nvkz.service.ProductPropertyService;
import ru.nvkz.service.StringClassifierService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/product-properties")
@RequiredArgsConstructor
public class ProductPropertyController {

    private final ProductPropertyService productPropertyService;
    private final StringClassifierService stringClassifierService;

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return productPropertyService.findById(id).map(productProperty -> {
            model.addAttribute("productProperty", productProperty);

            if (productProperty.getProperty().getDtype().equals(TypeValue.STRING_CLASSIFIER)) {
                model.addAttribute("stringClassifiers", stringClassifierService.findByPropertyId(productProperty.getProperty().getId()));
            }

            return "product-property/product-property";
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String create(@RequestParam List<Long> properties, @RequestParam Long productId) {
        List<ProductPropertyCreateEditDto> productPropertyCreateEditDtos = properties.stream().map((propertyId) -> ProductPropertyCreateEditDto.builder()
                        .propertyId(propertyId)
                        .productId(productId)
                        .build())
                .toList();
        return "redirect:/products/" + productPropertyService.createAll(productPropertyCreateEditDtos).stream().findFirst().get().getProduct().getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @ModelAttribute ProductPropertyCreateEditDto productProperty) {
        return productPropertyService.update(id, productProperty)
                .map((it) -> {
                    return "redirect:/products/" + it.getProduct().getId();
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, @RequestParam Long productId) {
        if (!productPropertyService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/products/" + productId;
    }

}
