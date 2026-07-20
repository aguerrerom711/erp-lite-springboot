package com.andresgm.erp_lite;

import com.andresgm.erp_lite.infrastructure.persistence.jpa.entity.ProductEntity;
import com.andresgm.erp_lite.infrastructure.persistence.jpa.repositories.ProductRepository;
import com.andresgm.erp_lite.infrastructure.persistence.mongo.document.CatalogDocument;
import com.andresgm.erp_lite.infrastructure.persistence.mongo.repositories.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ErpLiteApplication implements CommandLineRunner {

	@Autowired
	private ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(ErpLiteApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		this.productRepository.findAll().stream()
				.map(ProductEntity::getName)
				.forEach(System.out::println);
	}
}
