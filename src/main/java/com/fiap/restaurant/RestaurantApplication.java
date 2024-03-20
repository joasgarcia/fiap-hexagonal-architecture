package com.fiap.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class RestaurantApplication {

	public static void main(String[] args) {
		System.out.println("Serviço de pedido");
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));

		SpringApplication.run(RestaurantApplication.class, args);
	}

}
