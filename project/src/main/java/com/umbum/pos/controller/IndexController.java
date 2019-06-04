package com.umbum.pos.controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.umbum.pos.model.Account;
import com.umbum.pos.service.AccountService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IndexController {
    private final AccountService accountService;

    public IndexController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "signin";
    }

    @GetMapping("/join")
    public String join() { return "/error/404";}

    @ResponseBody
    @PostMapping("/join")
//    public String postJoin() {
    public String postJoin(@RequestBody Map<String, String> accountInfo) {
        // RequestBody를 못쓴다. Authorities 때문에... 그래서 그냥 메뉴얼하게 처리해주자.
        HashSet<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(accountInfo.get("role")));
        int result = accountService.registerAccount(new Account(
            accountInfo.get("username"),
            accountInfo.get("password"),
            accountInfo.get("branchId"),
            authorities
        ));
        return String.valueOf(result);
    }
}

