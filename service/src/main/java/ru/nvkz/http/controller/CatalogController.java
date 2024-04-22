package ru.nvkz.http.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import ru.nvkz.dto.BasketReadDto;
import ru.nvkz.dto.ProductCreateEditDto;
import ru.nvkz.dto.ProductReadDto;
import ru.nvkz.dto.CategoryReadDto;
import ru.nvkz.dto.PropertyCreateEditDto;
import ru.nvkz.dto.PropertyFilterReadDto;
import ru.nvkz.entity.TypeValue;
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


    @GetMapping
    public String findAll(Model model, @SessionAttribute("basket") BasketReadDto basket) {
        model.addAttribute("catalogs", categoryService.findAllByParentRoot());
        model.addAttribute("pathElements", categoryService.findAllPathElementByParentRoot());
        return "catalog/catalogs";
    }

    @GetMapping("/{parentId}")
    public String findAllByParentId(Model model, @PathVariable("parentId") Long parentId, ProductFilter productFilter) {
        List<CategoryReadDto> catalogs = categoryService.findAllByParentId(parentId);

        List<PropertyFilterReadDto> a = propertyService.findAllWithCountProductProperty(parentId);

        if (catalogs.isEmpty()) {
            model.addAttribute("producers", producerRepository.findAllByCategoryId(parentId));
            model.addAttribute("properties", a);
            model.addAttribute("productFilter", productFilter);

            List<ProductReadDto> products = productService.findAllDistinctByProductFilter(productFilter, parentId);
            model.addAttribute("products", products);
        }

        model.addAttribute("catalogs", catalogs);
        model.addAttribute("parentId", parentId);
        model.addAttribute("pathElements", categoryService.findAllPathElementByParentId(parentId));
        return "catalog/catalogs";
    }

    @GetMapping("/{parentId}/properties/create-form")
    public String showPropertyCreateForm(@PathVariable Long parentId, Model model, PropertyCreateEditDto property) {
        model.addAttribute("property", property);
        model.addAttribute("categoryId", parentId);
        model.addAttribute("types", TypeValue.values());
        return "property/property-create";
    }

    @GetMapping("/{parentId}/products/create-form")
    public String showProductCreateForm(@PathVariable Long parentId, Model model, ProductCreateEditDto product) {
        model.addAttribute("product", product);
        model.addAttribute("categoryId", parentId);
        model.addAttribute("producers", producerRepository.findAll());
        return "product/product-create";
    }


}
