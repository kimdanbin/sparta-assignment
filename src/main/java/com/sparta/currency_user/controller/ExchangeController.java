package com.sparta.currency_user.controller;

import com.sparta.currency_user.dto.ExchangeRequestDto;
import com.sparta.currency_user.dto.ExchangeResponseDto;
import com.sparta.currency_user.dto.PatchExchangeRequestDto;
import com.sparta.currency_user.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeController {
    private final ExchangeService exchangeService;

    // C: 환전 요청 수행
    @PostMapping("/{userId}")
    public ResponseEntity<ExchangeResponseDto> createExchange(@PathVariable Long userId, @RequestBody ExchangeRequestDto exchangeRequestDto) {
        return ResponseEntity.ok().body(exchangeService.save(userId, exchangeRequestDto));
    }

    // R: 환전 요청 전체 조회
    @GetMapping
    public ResponseEntity<List<ExchangeResponseDto>> findAllExchanges() {
        return ResponseEntity.ok().body(exchangeService.findAll());
    }

    // R: 고객 고유 식별자를 기반으로 특정 고객이 수행한 환전 요청 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<ExchangeResponseDto>> findExchangesById(@PathVariable Long userId) {
        return ResponseEntity.ok().body(exchangeService.findExchangesById(userId));
    }

    // U: 특정 환전 요청 상태를 취소로 변경
    @PatchMapping("/{userId}/{exchangeId}")
    public ResponseEntity<ExchangeResponseDto> updateExchange(@PathVariable Long userId, @PathVariable Long exchangeId, @RequestBody PatchExchangeRequestDto patchExchangeRequestDto) {
        return ResponseEntity.ok().body(exchangeService.updateStatus(userId, exchangeId, patchExchangeRequestDto));
    }

    // D: 고객이 삭제될 때 해당 고객이 수행한 모든 환전 요청도 삭제
    @DeleteMapping("/{userId}/{exchangeId}")
    public ResponseEntity<String> deleteExchange(@PathVariable Long exchangeId) {
        exchangeService.deleteExchangeById(exchangeId);
        return ResponseEntity.ok().body("정상적으로 삭제되었습니다.");
    }

}
