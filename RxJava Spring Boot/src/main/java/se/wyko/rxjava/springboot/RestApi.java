package se.wyko.rxjava.springboot;


import org.glassfish.jersey.client.rx.Rx;
import org.glassfish.jersey.client.rx.rxjava.RxObservableInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import rx.Observable;

import javax.ws.rs.client.ClientBuilder;


@Controller
@ComponentScan
@EnableAutoConfiguration
public class RestApi {

    private final static Logger logger = LoggerFactory.getLogger(RestApi.class);

    @Autowired
    HardWorkingService service;

    @RequestMapping("/async")
    @ResponseBody
    Observable asyncHello() throws InterruptedException {
        logger.info("In");
        final Observable observable = service.calculateItAsync();
        logger.info("Out");
        return observable;
    }

    @RequestMapping("/sync")
    @ResponseBody
    String syncHello() throws InterruptedException {
        logger.info("In");
        String result = service.calculateIt();
        logger.info("Out");
        return result;
    }

    @RequestMapping("/asyncProxy")
    @ResponseBody
    Observable<String> asyncRestCall() {
        logger.info("In");
        Observable<String> response = Rx.newClient(RxObservableInvoker.class)
                .target("http://localhost:7070")
                .request()
                .rx()
                .get(String.class);
        logger.info("Out");
        return response;
    }

    @RequestMapping("/syncProxy")
    @ResponseBody
    String syncRestCall() {
        logger.info("In");
        String response = ClientBuilder.newClient()
                .target("http://localhost:7070")
                .request()
                .get(String.class);
        logger.info("Out");
        return response;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RestApi.class, args);
    }
}