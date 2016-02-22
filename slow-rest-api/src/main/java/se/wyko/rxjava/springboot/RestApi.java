package se.wyko.rxjava.springboot;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import rx.Single;
import rx.schedulers.Schedulers;
import rx.util.async.Async;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@ComponentScan
@EnableAutoConfiguration
public class RestApi {
    private final static Logger logger = LoggerFactory.getLogger(RestApi.class);

    @RequestMapping("/{input}")
    @ResponseBody
    Single<String> revertIt(@PathVariable("input") String input) throws InterruptedException {
        logger.info("in");
        return Async.start(() -> {
            logger.info("working");
            sleep(1000);
            return new StringBuilder(input).reverse().toString();
        } , Schedulers.newThread()).toSingle();
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        SpringApplication.run(RestApi.class, args);
    }
}