package com.sparta.currency_user.entity;

import com.sparta.currency_user.dto.ExchangeRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Table(name = "exchange")
public class Exchange extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "to_currency_id")
    private Currency currency;

    // 환전 전 금액 (원화 기준)
    private BigDecimal amount_in_krw;
    // 환전 후 금액
    private BigDecimal amount_after_exchange;
    // 상태
    private String status;

    public Exchange() {}

    public Exchange(User user, Currency currency, BigDecimal amount_in_krw, BigDecimal amount_after_exchange, String status) {
        this.user = user;
        this.currency = currency;
        this.amount_in_krw = amount_in_krw;
        this.amount_after_exchange = amount_after_exchange;
        this.status = status;
    }

}
