package ru.nvkz.http.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nvkz.dto.ProductTypePathElement;
import ru.nvkz.dto.ProductTypeReadDto;
import ru.nvkz.service.ProductTypeService;
import ru.nvkz.service.PropertyService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/catalogs")
@RequiredArgsConstructor
public class CatalogController {

    private final ProductTypeService productTypeService;
    private final PropertyService propertyService;

    @GetMapping
    public String findAll(Model model) {
        List<ProductTypeReadDto> catalogs = productTypeService.findAllByParentId(null);
        List<ProductTypePathElement> pathElements = productTypeService.findAllPathElementByParentId(null);
        model.addAttribute("catalogs", catalogs);
        model.addAttribute("pathElements", pathElements);
        return "catalog/catalogs";
    }

    @GetMapping("/{parentId}")
    public String findAllByParentId(Model model, @PathVariable("parentId") Integer parentId) {
        List<ProductTypeReadDto> catalogs = productTypeService.findAllByParentId(parentId);

        if (catalogs.isEmpty()) {
            model.addAttribute("propertys", propertyService.findAllWithCountProductPropertyValue(parentId));
        }

        model.addAttribute("catalogs", catalogs);
        model.addAttribute("parentId", parentId);
        model.addAttribute("pathElements", productTypeService.findAllPathElementByParentId(parentId));
        return "catalog/catalogs";
    }

}
