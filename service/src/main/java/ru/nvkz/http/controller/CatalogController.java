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
import ru.nvkz.dto.ProductReadDto;
import ru.nvkz.dto.CategoryReadDto;
import ru.nvkz.filter.ProductFilter;
import ru.nvkz.repository.ProducerRepository;
import ru.nvkz.service.ProductService;
import ru.nvkz.service.CategoryService;
import ru.nvkz.service.PropertyService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/catalogs")
@RequiredArgsConstructor
public class CatalogController {

    private final CategoryService categoryService;
    private final ProducerRepository producerRepository;
    private final PropertyService propertyService;
    private final ProductService productService;

    @GetMapping("/product/{id}")
    public String findByid(@PathVariable("id") Long id, Model model) {
        return productService.findById(id)
                .map(product -> {
                    model.addAttribute("product", product);
                    return "catalog/product";
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public String findAll(Model model) {
        List<CategoryReadDto> catalogs = categoryService.findAllByParentRoot();

        model.addAttribute("catalogs", categoryService.findAllByParentRoot());
        model.addAttribute("pathElements", categoryService.findAllPathElementByParentRoot());
        return "catalog/catalogs";
    }

    @GetMapping("/{parentId}")
    public String findAllByParentId(Model model, @PathVariable("parentId") Integer parentId, ProductFilter productFilter) {
        List<CategoryReadDto> catalogs = categoryService.findAllByParentId(parentId);

        if (catalogs.isEmpty()) {
            model.addAttribute("producers", producerRepository.findAllByCategoryId(parentId));
            model.addAttribute("properties", propertyService.findAllWithCountProductPropertyValue(parentId));
            model.addAttribute("productFilter", productFilter);
            List<ProductReadDto> products = productService.findAllDistinctByProductFilter(productFilter, parentId);
            model.addAttribute("products", products);
        }

        model.addAttribute("catalogs", catalogs);
        model.addAttribute("parentId", parentId);
        model.addAttribute("pathElements", categoryService.findAllPathElementByParentId(parentId));
        return "catalog/catalogs";
    }

}
