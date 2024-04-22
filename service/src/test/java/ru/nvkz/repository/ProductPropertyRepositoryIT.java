package ru.nvkz.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.nvkz.IntegrationTestBase;

@RequiredArgsConstructor
class ProductPropertyRepositoryIT extends IntegrationTestBase {

    private final ProductPropertyRepository repository;

    @Test
    void findByProductId() {
        var a = repository.findByProductId(1L);
        System.out.println(a.size());
        a.forEach(System.out::println);
    }
}