package com.vendas.gestaovendas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.vendas.gestaovendas.entities"})
@EnableJpaRepositories(basePackages = {"com.vendas.gestaovendas.repository"})
@ComponentScan(basePackages = {"com.vendas.gestaovendas.service", "com.vendas.gestaovendas.controller"})
@SpringBootApplication
public class GestaoVendasApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoVendasApplication.class, args);
	}

}
