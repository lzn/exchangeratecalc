package com.lzn.exchangerates;

import com.lzn.exchangerates.service.NBPRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableCaching
@Configuration
public class ExchangeratesApplication  extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeratesApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ExchangeratesApplication.class);
	}

}
