package com.umbum.pos.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.umbum.pos.model.Account;
import com.umbum.pos.repository.AccountRepo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountService implements UserDetailsService {
    private final AccountRepo accountRepo;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepo accountRepo,
        PasswordEncoder passwordEncoder) {
        this.accountRepo = accountRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return accountRepo.read(username);
        } catch (Exception e) {
            log.warn("{} 계정이 없음.", username);
            throw new UsernameNotFoundException(username);
        }
    }

    public int registerAccount(Account account) {
        Account newAccount = new Account(
            account.getUsername(),
            passwordEncoder.encode(account.getPassword()),
            account.getBranchId(),
            account.getAuthorities()
            );
        return accountRepo.create(newAccount);
    }
}
