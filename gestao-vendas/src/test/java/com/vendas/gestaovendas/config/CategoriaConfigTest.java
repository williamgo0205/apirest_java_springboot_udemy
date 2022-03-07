package com.vendas.gestaovendas.config;

import com.vendas.gestaovendas.GestaoVendasApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SpringBootTest(classes = GestaoVendasApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CategoriaConfigTest {
}
