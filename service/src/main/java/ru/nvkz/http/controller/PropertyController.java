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
import ru.nvkz.dto.PropertyCreateEditDto;
import ru.nvkz.entity.TypeValue;
import ru.nvkz.service.PropertyService;
import ru.nvkz.service.StringClassifierService;

@Slf4j
@Controller
@RequestMapping("/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;
    private final StringClassifierService stringClassifierService;


    @GetMapping("/{id}/string-classifiers")
    public String showStringClassifiers(@PathVariable Long id, Model model) {
        model.addAttribute("property", propertyService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        model.addAttribute("stringClassifiers", stringClassifierService.findByPropertyId(id));
        return "stringClassifier/string-classifiers";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return propertyService.findById(id).map(property -> {
            model.addAttribute("categoryId", property.getCategory().getId());
            model.addAttribute("property", property);
            model.addAttribute("types", TypeValue.values());
            return "property/property";
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String create(@ModelAttribute PropertyCreateEditDto property) {
        return "redirect:/catalogs/" + propertyService.create(property).getCategory().getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute PropertyCreateEditDto property) {
        return propertyService.update(id, property)
                .map(it -> "redirect:/catalogs/" + property.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable("id") Long id, @RequestParam(required = false) Long categoryId) {
        if (!propertyService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/catalogs/" + categoryId;
    }

    @GetMapping("/create-form")
    public String showPropertyCreateForm(Model model, PropertyCreateEditDto property, @RequestParam Long parentId) {
        model.addAttribute("property", property);
        model.addAttribute("categoryId", parentId);
        model.addAttribute("types", TypeValue.values());
        return "property/property-create";
    }

}
