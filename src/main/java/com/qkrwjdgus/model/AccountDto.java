package com.qkrwjdgus.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author PJH
 * @since 2016-05-24.
 */
public class AccountDto {

    @Data
    public static class Create {

        @NotBlank
        @Size(min = 5)
        private String username;

        @NotBlank
        @Size(min = 5)
        private String password;

    }


    @Data
    public static class Response {

        private Long id;
        private String username;
        private String fullName;
        private Date joined;
        private Date updated;

    }

    @Data
    public static class Update {

        private String password;
        private String fullName;
        private Date updated;

    }

}
