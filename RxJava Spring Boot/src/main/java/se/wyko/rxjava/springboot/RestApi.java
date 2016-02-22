package se.wyko.rxjava.springboot;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rx.Observable;


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
        Observable<String> response = service.ayncWorkIt();
        logger.info("Out");
        return response;
    }

    @RequestMapping("/syncProxy")
    @ResponseBody
    String syncRestCall() {
        logger.info("In");
        String response = service.workIt();
        logger.info("Out");
        return response;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RestApi.class, args);
    }
}