package com.qkrwjdgus.model;

import lombok.Getter;

/**
 * @author PJH
 * @since 2016-06-01.
 */
public class AccountNotFoundException extends RuntimeException {


    public AccountNotFoundException(Long id) {
        this.id = id;
    }

    @Getter
    Long id;
}
