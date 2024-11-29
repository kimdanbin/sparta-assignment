package com.sparta.currency_user.dto;

import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.entity.Exchange;
import com.sparta.currency_user.entity.User;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ExchangeResponseDto {
    private Long id;

    private Long userId;
    private String currencyName;
    private BigDecimal amount_in_krw;
    private BigDecimal amount_after_exchange;
    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ExchangeResponseDto(Long id, User user, Currency currency, BigDecimal amount_in_krw, BigDecimal amount_after_exchange, String status, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.userId = user.getId();
        this.currencyName = currency.getCurrencyName();
        this.amount_in_krw = amount_in_krw;
        this.amount_after_exchange = amount_after_exchange;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public ExchangeResponseDto(Exchange exchange) {
        this.id = exchange.getId();
        this.userId = exchange.getUser().getId();
        this.currencyName = exchange.getCurrency().getCurrencyName();
        this.amount_in_krw = exchange.getAmount_in_krw();
        this.amount_after_exchange = exchange.getAmount_after_exchange();
        this.status = exchange.getStatus();
        this.createdAt = exchange.getCreatedAt();
        this.modifiedAt = exchange.getModifiedAt();
    }

    public static ExchangeResponseDto toDto(Exchange exchange) {
        return new ExchangeResponseDto(exchange);
    }
}
