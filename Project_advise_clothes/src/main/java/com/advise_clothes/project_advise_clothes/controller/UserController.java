package com.advise_clothes.project_advise_clothes.controller;

import com.advise_clothes.project_advise_clothes.entity.User;
import com.advise_clothes.project_advise_clothes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * 로그인
     * 이후 휴대폰 번호나 이메일로 로그인 하게 할 건지 확인
     * @param user 로그인할 유저 정보, account, password
     * @return
     */
    @GetMapping("")
    public ResponseEntity<User> login(@RequestBody User user) {
        return userService.findUserByIdAndPassword(user).map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).body(user));
    }

    @GetMapping("/{account}")
    public ResponseEntity<User> getUserByAccount(@PathVariable String account) {
        User userToFind = User.builder().account(account).build();
        return userService.findByUserForNotDelete(userToFind).map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userToFind));
    }

    @PostMapping("")
    public ResponseEntity<User> joinUs(@RequestBody User user) {
        return userService.findByUserForNotDelete(user).map(value -> ResponseEntity.status(HttpStatus.NO_CONTENT).body(value))
                .orElseGet(() -> (user.getAccount() != null || user.getPassword() != null || user.getNickname() != null || user.getEmail() != null || user.getPhoneNumber() != null )?
                        ResponseEntity.status(HttpStatus.OK).body(userService.createUser(user)) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user));
    }

    /**
     * 회원정보 변경
     * @param account 변경할 계정
     * @param user 변경할 값
     * @return
     */
    @PutMapping("{account}")
    public ResponseEntity<User> updateUser(@PathVariable String account, @RequestBody User user) {
        User userToFind = User.builder().account(account).build();
        return userService.findByUserForNotDelete(userToFind).map(value -> {
                    if (user.getPassword() != null) { value.setPassword(user.getPassword()); }
                    if (user.getNickname() != null) { value.setNickname(user.getNickname()); }
                    if (user.getEmail() != null) { value.setEmail(user.getEmail()); }
                    if (user.getPhoneNumber() != null) { value.setPhoneNumber(user.getPhoneNumber()); }       // 이후 휴대폰 본인인증으로 변경
                    if (user.getArea() != null) { value.setArea(user.getArea()); }
                    if (user.getHeight() != null) { value.setHeight(user.getHeight()); }
                    if (user.getWeight() != null) { value.setWeight(user.getWeight()); }
                    return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(value));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).body(user));

    }
}