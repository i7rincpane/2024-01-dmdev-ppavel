package ru.nvkz;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.nvkz.config.ApplicationConfiguration;
import ru.nvkz.repository.OrderRepository;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        var rep = context.getBean(OrderRepository.class);
        rep.getEntityManager().getTransaction().begin();
        rep.findAll().forEach(System.out::println);
        rep.getEntityManager().getTransaction().commit();
    }
}
