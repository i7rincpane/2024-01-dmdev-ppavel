package ru.nvkz.http.controller;

import lombok.RequiredArgsConstructor;
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
import ru.nvkz.dto.StringClassifierCreateEditDto;
import ru.nvkz.service.StringClassifierService;

@Controller
@RequestMapping("/string-classifiers")
@RequiredArgsConstructor
public class StringClassifierController {
    private final StringClassifierService stringClassifierService;

    @GetMapping("create-form")
    public String showStringClassifiersCreateForm(Model model, StringClassifierCreateEditDto stringClassifier) {
        model.addAttribute("stringClassifier", stringClassifier);
        return "stringClassifier/string-classifier-create";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return stringClassifierService.findById(id).map(stringClassifier -> {
            model.addAttribute("stringClassifier", stringClassifier);
            return "stringClassifier/string-classifier";
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String create(@ModelAttribute StringClassifierCreateEditDto stringClassifier) {
        return getRedirectPath(stringClassifierService.create(stringClassifier).getProperty().getId());
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute StringClassifierCreateEditDto stringClassifier) {
        return stringClassifierService.update(id, stringClassifier)
                .map(it -> getRedirectPath(stringClassifier.getPropertyId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable("id") Long id, @RequestParam Long propertyId) {
        if (!stringClassifierService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return getRedirectPath(propertyId);
    }

    private static String getRedirectPath(Long propertyId) {
        return String.format("redirect:/properties/%s/string-classifiers", propertyId);
    }

}
