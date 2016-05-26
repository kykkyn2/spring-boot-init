package com.qkrwjdgus.controller;

import com.nhncorp.lucy.security.xss.XssPreventer;
import com.qkrwjdgus.model.Account;
import com.qkrwjdgus.model.AccountDto;
import com.qkrwjdgus.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author PJH
 * @since 2016-04-29.
 */
@RestController
public class AccountController {

    @Autowired
    private AccountService service;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
    public String setImage(@PathVariable String id) throws Exception {
        System.out.println(id);

        System.out.println(XssPreventer.escape(id));


        return "adad";
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public ResponseEntity createAccount(@RequestBody @Valid AccountDto.Create create, BindingResult result) {

        if (result.hasErrors()) {
            // TODO: 2016-05-25 에러 응답 본문 추가 하기
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Account newAccount = service.createAccount(create);

        //return new ResponseEntity<>(newAccount, HttpStatus.OK);
        return new ResponseEntity<>(modelMapper.map(newAccount, AccountDto.Response.class), HttpStatus.CREATED);

    }


}
