package com.qkrwjdgus.controller;

import com.nhncorp.lucy.security.xss.XssPreventer;
import com.qkrwjdgus.commons.ErrorResponse;
import com.qkrwjdgus.model.Account;
import com.qkrwjdgus.model.AccountDto;
import com.qkrwjdgus.model.AccountNotFoundException;
import com.qkrwjdgus.model.UserDuplicatedException;
import com.qkrwjdgus.repository.AccountRepository;
import com.qkrwjdgus.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private AccountRepository repository;

    @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
    public String setImage(@PathVariable String id) throws Exception {
        System.out.println(id);

        System.out.println(XssPreventer.escape(id));


        return "adad";
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public ResponseEntity createAccount(@RequestBody @Valid AccountDto.Create create, BindingResult result) {

        if (result.hasErrors()) {

            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(result.getFieldError().getField() + " error !! " + result.getFieldError().getDefaultMessage());
            errorResponse.setCode("bad.request");

            //return new ResponseEntity(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        Account newAccount = service.createAccount(create);

        //  서비스에 값이 잘 넘어왔는지 확인 하는 방법
        //  1. 리턴 타입으로 판단.  직관적이기는 함
        //  2. 파라미터 이용 (callback 처리) 직관적이기는 함
        //  3. Exception 처리

        //return new ResponseEntity<>(newAccount, HttpStatus.OK);
        return new ResponseEntity<>(modelMapper.map(newAccount, AccountDto.Response.class), HttpStatus.CREATED);

    }

    //  /accounts?page=0&size=20&sort=username,desc&sort=joined,desc
    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public ResponseEntity getAccounts(Pageable pageable) {

        Page<Account> page = repository.findAll(pageable);

        //  비밀번호 같은 내용이 들어가기 때문에 response 로 변경 해서 내보내자
        List<AccountDto.Response> content = page.getContent().parallelStream()
                .map(account -> modelMapper.map(account, AccountDto.Response.class))
                .collect(Collectors.toList());

        PageImpl<AccountDto.Response> result = new PageImpl<>(content, pageable, page.getTotalElements());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.GET)
    public ResponseEntity getAccount(@PathVariable Long id) {

        Account account = service.getAccount(id);
        AccountDto.Response result = modelMapper.map(account, AccountDto.Response.class);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    //  전체 업데이트 PUT
    //
    //  부분 업데이트 PATCH
    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateAccount(@PathVariable Long id, @RequestBody @Valid AccountDto.Update updateDto, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Account updateAccount = service.updateAccount(id, updateDto);
        return new ResponseEntity<>(modelMapper.map(updateAccount, AccountDto.Response.class), HttpStatus.OK);

    }


    //  삭제 API
    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAccount(@PathVariable Long id) {
        service.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity handleAccountNotFoundException(AccountNotFoundException e) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("[" + e.getId() + "] 에 해당하는 계정이 없습니다.");
        errorResponse.setCode("account.not.found.exception");

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(UserDuplicatedException.class)
    public ResponseEntity handleUserDuplicatedException(UserDuplicatedException e) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("[" + e.getUsername() + "] 중복된 username 입니다.");
        errorResponse.setCode("duplicated.username.exception");

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    // TODO: 2016-05-26  streme() vs parallelStream()
    // TODO: 2016-05-26 HATETOS
    // TODO: 2016-06-02 SPA vs NSPA
    //  목록 구현 및 페이징 처리 api 에서는 Hateoas api 가 유리함
    // SinglePageApp(SPA)  앵귤러 , 리엑트
    // NoneSinglePageApp(NSPA) JSP , Thtmeleaf

}
