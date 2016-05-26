package com.qkrwjdgus.service;

import com.qkrwjdgus.model.Account;
import com.qkrwjdgus.model.AccountDto;
import com.qkrwjdgus.repository.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author PJH
 * @since 2016-05-24.
 */
@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public Account createAccount(AccountDto.Create dto) {


        //  대체 가능 -> modelMapper
        /*
        Account account = new Account();
        account.setUsername(dto.getUsername());
        account.setPassword(dto.getPassword());
        */

        //  위 소스와 대체 가능
        Account account = modelMapper.map(dto, Account.class);

        // TODO: 2016-05-26 유효한 username 인지 판단 하기
        // TODO: 2016-05-26 password 암호화

        Date nowDate = new Date();

        account.setJoined(nowDate);
        account.setUpdated(nowDate);

        //  JPA 처리 후 리턴
        return repository.save(account);

    }
}
