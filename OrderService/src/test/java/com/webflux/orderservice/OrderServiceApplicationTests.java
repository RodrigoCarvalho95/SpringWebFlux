package com.webflux.orderservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.webflux.orderservice.client.ProductClient;
import com.webflux.orderservice.client.UserClient;
import com.webflux.orderservice.dto.ProductDto;
import com.webflux.orderservice.dto.PurchaseOrderRequestDto;
import com.webflux.orderservice.dto.PurchaseOrderResponseDto;
import com.webflux.orderservice.dto.UserDto;
import com.webflux.orderservice.service.OrderFulfillmentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class OrderServiceApplicationTests {

	@Autowired
	private UserClient userClient;

	@Autowired
	private ProductClient productClient;

	@Autowired
	private OrderFulfillmentService fulfillmentService;

	@Test
	void contextLoads() {
		
		Flux<PurchaseOrderResponseDto> dtoFlux = Flux.zip(userClient.getAllUsers(), productClient.getAllProducts())
														.map(t -> buildDto(t.getT1(), t.getT2()))
														.flatMap(dto -> this.fulfillmentService.processOrder(Mono.just(dto)))
														.doOnNext(System.out::println);
		
		StepVerifier.create(dtoFlux)
				.expectNextCount(4)
				.verifyComplete();
	}

	private PurchaseOrderRequestDto buildDto(UserDto userDto, ProductDto productDto) {
		PurchaseOrderRequestDto dto = new PurchaseOrderRequestDto();
		dto.setProductId(productDto.getId());
		dto.setUserId(userDto.getId());
		return dto;
	}

}
