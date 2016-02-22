package se.wyko.rxjava.springboot;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;


@Controller
@ComponentScan
@EnableAutoConfiguration
public class RestApi {

    private final static Logger logger = LoggerFactory.getLogger(RestApi.class);

    @Autowired
    GreeterService service;


    @RequestMapping("/{input}")
    @ResponseBody
    String syncRestCall(@PathVariable("input") String input) {
        logger.info("In");
        String response = service.greetMe(input);
        logger.info("Out");
        return response;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RestApi.class, args);
    }
}