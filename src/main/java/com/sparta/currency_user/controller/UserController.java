package com.sparta.currency_user.controller;

import com.sparta.currency_user.dto.UserRequestDto;
import com.sparta.currency_user.dto.UserResponseDto;
import com.sparta.currency_user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 전체 유저 조회
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findUsers() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    // 특정 유저 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUser(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    // 유저 생성
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok().body(userService.save(userRequestDto));
    }

    // 유저 삭제
    // D: 고객이 삭제될 때 해당 고객이 수행한 모든 환전 요청도 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().body("정상적으로 삭제되었습니다.");
    }
}
