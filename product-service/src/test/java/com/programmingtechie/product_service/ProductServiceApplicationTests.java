package com.programmingtechie.product_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmingtechie.product_service.dto.ProductRequest;
import com.programmingtechie.product_service.model.Product;
import com.programmingtechie.product_service.repository.ProductRepository;
import com.programmingtechie.product_service.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ProductServiceApplicationTests {

	@Mock
	private MockMvc mockMvc;

//	@Mock
//	private ObjectMapper objectMapper;

	@Mock  // Mock repository to avoid hitting the real database
	private ProductRepository productRepository;

	@InjectMocks
	private ProductService productService;

	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
//		ObjectMapper objectMapper = new ObjectMapper();
//		String productRequestString = objectMapper.writeValueAsString(productRequest);
//
//		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
//						.contentType(MediaType.APPLICATION_JSON)
//						.content(productRequestString)).andExpect(status().isCreated());
		productService.createProduct(productRequest);
		verify(productRepository).save(any(Product.class));
	}

	private ProductRequest getProductRequest() {
		return new ProductRequest("iPhone 13", "Latest iPhone model", BigDecimal.valueOf(1200));
	}
}
