package com.sparta.currency_user.service;

import com.sparta.currency_user.dto.ExchangeRequestDto;
import com.sparta.currency_user.dto.ExchangeResponseDto;
import com.sparta.currency_user.dto.PatchExchangeRequestDto;
import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.entity.Exchange;
import com.sparta.currency_user.entity.User;
import com.sparta.currency_user.repository.CurrencyRepository;
import com.sparta.currency_user.repository.ExchangeRepository;
import com.sparta.currency_user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeService {
    private final CurrencyRepository currencyRepository;
    private final ExchangeRepository exchangeRepository;
    private final UserRepository userRepository;

    /**
     * C: 환전 요청 수행
     * @param exchangeRequestDto requestbody에서 json으로 받은거
     * @return 테이블에 저장하고 반환받은거 그대로 리턴
     */
    public ExchangeResponseDto save(Long userId, ExchangeRequestDto exchangeRequestDto) {

        // 유저, 통화, 한국돈, 환전된돈, 상태 받기
        User findUser = userRepository.findByIdElseThrow(userId);
        Currency findCurrency = currencyRepository.findByCurrencyNameElseThrow(exchangeRequestDto.getCurrencyName());
        BigDecimal amount_in_krw = exchangeRequestDto.getAmount_in_krw();
        BigDecimal amount_after_exchange = exchangeRequestDto.getAmount_in_krw().divide(findCurrency.getExchangeRate(), 2, RoundingMode.HALF_UP);
        String status = "normal";

        Exchange exchange = new Exchange(findUser, findCurrency, amount_in_krw, amount_after_exchange, status);

        Exchange saved = exchangeRepository.save(exchange);
        return new ExchangeResponseDto(saved);
    }

    // R: 환전 요청 전체 조회
    public List<ExchangeResponseDto> findAll() {
        return exchangeRepository.findAll().stream().map(ExchangeResponseDto::toDto).toList();
    }

    // R: 고객 고유 식별자를 기반으로 특정 고객이 수행한 환전 요청 조회
    public List<ExchangeResponseDto> findExchangesById(Long userId) {
        return exchangeRepository.findAllByUserId(userId);
    }

    // U: 특정 환전 요청 상태를 취소로 변경
    public ExchangeResponseDto updateStatus(Long userId, Long exchangeId, PatchExchangeRequestDto patchExchangeRequestDto) {

        int updateRow = exchangeRepository.updateStatus(userId, exchangeId, patchExchangeRequestDto.getStatus());
        if (updateRow == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "환전 내역 없음");
        }

        return ExchangeResponseDto.toDto(exchangeRepository.findByIdElseThrow(exchangeId));
    }

    // D: 고객이 삭제될 때 해당 고객이 수행한 모든 환전 요청도 삭제
    @Transactional
    public void deleteExchangeById(Long exchangeId) {
        exchangeRepository.deleteById(exchangeId);
    }

}
