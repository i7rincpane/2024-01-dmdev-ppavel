package ru.nvkz.http.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import ru.nvkz.dto.ProductCreateEditDto;
import ru.nvkz.dto.ProductPropertyReadDto;

import ru.nvkz.dto.PropertyReadDto;
import ru.nvkz.dto.StringClassifierReadDto;

import ru.nvkz.entity.TypeValue;
import ru.nvkz.repository.ProducerRepository;
import ru.nvkz.service.ProductPropertyService;
import ru.nvkz.service.ProductService;
import ru.nvkz.service.StringClassifierService;

import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductControler {

    private final ProductService productService;
    private final ProductPropertyService productPropertyService;
    private final StringClassifierService stringClassifierService;
    private final ProducerRepository producerRepository;


    @GetMapping("/{id}")
    public String findByid(@PathVariable("id") Long id, Model model, ProductCreateEditDto productCreateEdit) {

        List<ProductPropertyReadDto> productPropertis = productPropertyService.findByProductIdAndCreateNewProperties(id);

        Map<Long, List<StringClassifierReadDto>> propertyIdStringClassifiers = productPropertis.stream()
                .peek(productProperty -> {
                    addPropertyValue(productCreateEdit.getPropertyIdStringClassifierId(), productProperty);
                    addPropertyValue(productCreateEdit.getPropertyIdTextValue(), productProperty);
                    addPropertyValue(productCreateEdit.getPropertyIdFloatValue(), productProperty);
                    addPropertyValue(productCreateEdit.getPropertyIdBooleanValue(), productProperty);
                    addPropertyValue(productCreateEdit.getPropertyIdNumberValue(), productProperty);
                })
                .map(ProductPropertyReadDto::getProperty)
                .filter((property) -> property.getDtype().equals(TypeValue.STRING_CLASSIFIER))
                .map(PropertyReadDto::getId)
                .map(stringClassifierService::findByPropertyId)
                .filter(CollectionUtils::isNotEmpty)
                .collect(Collectors.toMap((classifiers) -> classifiers.get(0).getProperty().getId(), (classifier) -> classifier));

        return productService.findById(id)
                .map(product -> {
                    model.addAttribute("productRead", product);
                    model.addAttribute("productCreateEdit", productCreateEdit);
                    model.addAttribute("productProperties", productPropertis);
                    model.addAttribute("propertyIdStringClassifiers", propertyIdStringClassifiers);
                    model.addAttribute("producers", producerRepository.findAll());
                    return "catalog/product";
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute ProductCreateEditDto product) {
        return productService.update(id, product)
                .map(it -> "redirect:/products/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private static <T> void addPropertyValue(Map<Long, T> propertyIdValues, ProductPropertyReadDto<T> productProperty) {
        propertyIdValues.put(productProperty.getProperty().getId(), productProperty.getValue());
    }
}
