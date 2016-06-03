package com.qkrwjdgus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qkrwjdgus.SpringBootInitApplication;
import com.qkrwjdgus.model.AccountDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author PJH
 * @since 2016-05-25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootInitApplication.class)
@WebAppConfiguration
//@Transactional
//@Rollback       //  롤백 처리를 하게 둘것이냐 말것이냐~
public class AccountControllerTest {

    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void createAccount() throws Exception {
        //MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        AccountDto.Create createDto = new AccountDto.Create();
        createDto.setUsername("kykkyn222");
        createDto.setPassword("password");

        ResultActions result = mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(createDto)));

        result.andDo(print());
        result.andExpect(status().isCreated());

        //  {"username":"kykkyn2","fullName":"adadad"}
        //  JSON 소스에 값이 정확히 일치 하는지 확인 하는 작업
        //  result.andExpect(jsonPath("$.username", is("kykkyn22")));


    }

    @Test
    public void createAccount_BadRequest() throws Exception {

        AccountDto.Create createDto = new AccountDto.Create();
        createDto.setUsername("kykky");
        createDto.setPassword("111");

        ResultActions result = mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(createDto)));

        result.andDo(print());
        result.andExpect(status().isBadRequest());

    }


    @Test
    public void getAccounts() throws Exception {

        ResultActions result = mockMvc.perform(get("/accounts"));

        result.andDo(print());
        result.andExpect(status().isOk());

    }

    private AccountDto.Create accountCreateFixture() {
        AccountDto.Create createDto = new AccountDto.Create();
        createDto.setUsername("kykky");
        createDto.setPassword("111");

        return createDto;
    }

    @Test
    public void getAccount() throws Exception {

        //AccountDto.Create createDto = accountCreateFixture();

        ResultActions result = mockMvc.perform(get("/accounts/1"));

        result.andDo(print());
        result.andExpect(status().isOk());

    }

    @Test
    public void updateAccount() throws Exception {
        //AccountDto.Create createDto = accountCreateFixture();

        AccountDto.Update updateDto = new AccountDto.Update();
        updateDto.setUpdated(new Date());
        updateDto.setFullName("parkjunghyun");
        updateDto.setPassword("passwd");


        ResultActions result = mockMvc.perform(put("/accounts/15").contentType(MediaType.APPLICATION_JSON_UTF8).content(objectMapper.writeValueAsString(updateDto)));

        result.andDo(print());
        result.andExpect(status().isOk());

    }

    @Test
    public void deleteAccount() throws Exception {
        ResultActions result = mockMvc.perform(delete("/accounts/16"));
        result.andDo(print());
        result.andExpect(status().isNoContent());
    }

}