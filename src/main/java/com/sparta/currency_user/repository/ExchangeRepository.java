package com.sparta.currency_user.repository;

import com.sparta.currency_user.dto.ExchangeResponseDto;
import com.sparta.currency_user.entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

    Optional<Exchange> findById(Long id);

    @Query("SELECT new com.sparta.currency_user.dto.ExchangeResponseDto(e.id, e.user, e.currency, e.amount_in_krw, e.amount_after_exchange, e.status, e.createdAt, e.modifiedAt) " +
            "FROM Exchange e WHERE e.user.id = :userId")
    List<ExchangeResponseDto> findAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Exchange e SET e.status = :status WHERE e.user.id = :uid AND e.id = :eid")
    int updateStatus(@Param("uid") Long uid, @Param("eid") Long eid, @Param("status") String status);

    default Exchange findByIdElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다"));
    }

}
