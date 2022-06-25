package com.hackfse.stock.market;

import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StockMarketApplication {

	/**
	 * Main method for starting up the application.
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(StockMarketApplication.class, args);
	}

}
