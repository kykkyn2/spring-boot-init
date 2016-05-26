package com.qkrwjdgus.repository;

import com.qkrwjdgus.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author PJH
 * @since 2016-05-24.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
}
