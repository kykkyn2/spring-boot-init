package com.qkrwjdgus.controller;

import com.nhncorp.lucy.security.xss.XssPreventer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PJH
 * @since 2016-04-29.
 */
@RestController
public class TestController {

    @RequestMapping(value = "/test/{id}",method = RequestMethod.GET)
    public String setImage(@PathVariable String id) throws Exception{
        System.out.println(id);

        System.out.println(XssPreventer.escape(id));


        return "adad";
    }

}
