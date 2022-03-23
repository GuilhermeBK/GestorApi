package com.br.gestor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.br.gestor.config.property.GestorFinanceiroApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(GestorFinanceiroApiProperty.class)
public class GestorFinanceiroApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestorFinanceiroApiApplication.class, args);
	}

}
