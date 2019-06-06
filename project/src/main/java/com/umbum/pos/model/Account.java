package com.umbum.pos.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper=true)
@Getter
@Setter
public class Account extends User {
    private long branchId;

    public Account(String username, String password, long branchId,
        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.branchId = branchId;
    }
}