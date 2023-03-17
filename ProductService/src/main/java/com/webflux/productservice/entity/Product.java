package com.webflux.productservice.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Product {
	
	@Id
	private String id;
	private String description;
	private Integer price;


}
