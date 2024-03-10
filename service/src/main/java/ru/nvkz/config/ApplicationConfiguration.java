package ru.nvkz.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.nvkz.util.HibernateUtil;

import java.lang.reflect.Proxy;

@Configuration
@ComponentScan(basePackages = "ru.nvkz")
public class ApplicationConfiguration {

    @Bean(destroyMethod = "close")
    public SessionFactory sessionFactory() {
        return HibernateUtil.buildSessionFactory();
    }

    @Bean
    public Session session(SessionFactory sessionFactory) {
        return (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(),
                new Class[]{Session.class},
                ((proxy, method, args) -> method.invoke(sessionFactory.getCurrentSession(), args)));
    }
}
