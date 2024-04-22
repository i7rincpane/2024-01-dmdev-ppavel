package ru.nvkz.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nvkz.dto.ProductCreateEditDto;
import ru.nvkz.entity.Category;
import ru.nvkz.entity.Producer;
import ru.nvkz.entity.Product;
import ru.nvkz.repository.CategoryRepository;
import ru.nvkz.repository.ProducerRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductCreateEditMapper implements Mapper<ProductCreateEditDto, Product> {

    private final ProducerRepository producerRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product map(ProductCreateEditDto object) {
        Product product = new Product();
        copy(object, product);
        return product;
    }

    @Override
    public Product map(ProductCreateEditDto fromObject, Product toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(ProductCreateEditDto object, Product toObject) {
        toObject.setCategory(getCategory(object.getCategoryId()));
        toObject.setCode(object.getCode());
        toObject.setCount(object.getCount());
        toObject.setPrice(object.getPrice());
        toObject.setProducer(getProducer(object.getProducerId()));
        toObject.setModel(object.getModel());
    }

    private Producer getProducer(Long producerId) {
        return Optional.ofNullable(producerId)
                .flatMap(producerRepository::findById)
                .orElse(null);
    }

    private Category getCategory(Long categoryId) {
        return Optional.ofNullable(categoryId)
                .flatMap(categoryRepository::findById)
                .orElse(null);
    }
}
