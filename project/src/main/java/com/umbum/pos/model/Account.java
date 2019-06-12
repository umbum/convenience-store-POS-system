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
    private String branchName;

    public Account(String username, String password, long branchId, String branchName,
        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.branchId = branchId;
        this.branchName = branchName;
    }

    public Account(String username, String password, long branchId,
        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.branchId = branchId;
    }
}