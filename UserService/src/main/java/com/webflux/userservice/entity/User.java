package com.webflux.userservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Table("users")
public class User {

	@Id
	private Integer id;
	private String name;
	private Integer balance;

}
