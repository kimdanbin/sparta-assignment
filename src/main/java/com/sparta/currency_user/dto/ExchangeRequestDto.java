package com.sparta.currency_user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ExchangeRequestDto {

    private String currencyName;
    private BigDecimal amount_in_krw;

}
