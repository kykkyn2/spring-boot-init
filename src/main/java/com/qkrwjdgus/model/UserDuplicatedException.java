package com.qkrwjdgus.model;

import lombok.Getter;

/**
 * @author PJH
 * @since 2016-05-26.
 */
public class UserDuplicatedException extends RuntimeException {

    @Getter
    String username;

    public UserDuplicatedException(String username) {
        this.username = username;
    }


}
