package com.webflux.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webflux.userservice.dto.TransactionRequestDto;
import com.webflux.userservice.dto.TransactionResponseDto;
import com.webflux.userservice.dto.TransactionStatus;
import com.webflux.userservice.entity.UserTransaction;
import com.webflux.userservice.repository.UserRepository;
import com.webflux.userservice.repository.UserTransactionRepository;
import com.webflux.userservice.util.EntityDtoUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserTransactionRepository transactionRepository;

	public Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto requestDto) {
		return this.userRepository.updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
						.filter(Boolean::booleanValue)
						.map(b -> EntityDtoUtil.toEntity(requestDto))
						.flatMap(this.transactionRepository::save)
						.map(ut -> EntityDtoUtil.toDto(requestDto, TransactionStatus.APROVED))
						.defaultIfEmpty(EntityDtoUtil.toDto(requestDto, TransactionStatus.DECLINED));
	}
	
	public Flux<UserTransaction> getByUserId(int userId) {
		return this.transactionRepository.findByUserId(userId);
	}

}
