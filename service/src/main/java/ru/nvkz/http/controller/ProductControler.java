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

import ru.nvkz.dto.ProductCreateEditDto;
import ru.nvkz.dto.ProductPropertyCreateEditDto;
import ru.nvkz.dto.ProductPropertyReadDto;

import ru.nvkz.dto.PropertyReadDto;
import ru.nvkz.dto.StringClassifierReadDto;

import ru.nvkz.entity.TypeValue;
import ru.nvkz.repository.ProducerRepository;
import ru.nvkz.service.ProductPropertyService;
import ru.nvkz.service.ProductService;
import ru.nvkz.service.PropertyService;
import ru.nvkz.service.StringClassifierService;

import java.util.List;
import java.util.Map;

import java.util.Optional;
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
    private final PropertyService propertyService;

    @GetMapping("/{id}/product-properties/create-form")
    public String showProductProperties(@PathVariable Long id, Model model) {
        model.addAttribute("productId", id);
        model.addAttribute("properties", propertyService.findMissingPropertiesByProductId(id));
        return "product-property/product-properties";
    }

    @GetMapping("/{id}")
    public String findByid(@PathVariable("id") Long id, Model model) {

        List<ProductPropertyReadDto> productProperties = productPropertyService.findByProductId(id);

        return productService.findById(id)
                .map(product -> {
                    model.addAttribute("productRead", product);
                    model.addAttribute("productCreateEdit", getProductCreateEdit(productProperties));
                    model.addAttribute("productProperties", productProperties);
                    model.addAttribute("propertyIdStringClassifiers", getStringClassifiers(productProperties));
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

    @PostMapping
    public String create(@ModelAttribute ProductCreateEditDto product) {
        return "redirect:/catalogs/" + productService.create(product).getCategory().getId();
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable("id") Long id, @RequestParam(required = false) Long categoryId) {
        if (!productService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/catalogs/" + categoryId;
    }

    private static ProductCreateEditDto getProductCreateEdit(List<ProductPropertyReadDto> productProperties) {
        ProductCreateEditDto productCreateEdit = ProductCreateEditDto.builder().build();
        productCreateEdit.getProductProperties().addAll(productProperties.stream()
                .map((productProperty) -> ProductPropertyCreateEditDto.builder()
                        .id(productProperty.getId())
                        .productId(productProperty.getProduct().getId())
                        .propertyId(productProperty.getProperty().getId())
                        .textValue(productProperty.getTextValue())
                        .numberValue(productProperty.getNumberValue())
                        .floatValue(productProperty.getFloatValue())
                        .dateValue(productProperty.getDateValue())
                        .booleanValue(productProperty.getBooleanValue())
                        .stringClassifierId(Optional.ofNullable(productProperty.getStringClassifier()).map(StringClassifierReadDto::getId).orElse(null))
                        .build()
                ).toList());
        return productCreateEdit;
    }

    private Map<Long, List<StringClassifierReadDto>> getStringClassifiers(List<ProductPropertyReadDto> productPropertis) {
        Map<Long, List<StringClassifierReadDto>> propertyIdStringClassifiers = productPropertis.stream()
                .map(ProductPropertyReadDto::getProperty)
                .filter((property) -> property.getDtype().equals(TypeValue.STRING_CLASSIFIER))
                .map(PropertyReadDto::getId)
                .map(stringClassifierService::findByPropertyId)
                .collect(Collectors.toMap((classifiers) -> classifiers.get(0).getProperty().getId(), (classifiers) -> classifiers));
        return propertyIdStringClassifiers;
    }


}
