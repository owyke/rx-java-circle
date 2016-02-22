package se.wyko.rxjava.springboot;

import org.glassfish.jersey.client.rx.Rx;
import org.glassfish.jersey.client.rx.rxjava.RxObservableInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import rx.Observable;

import javax.ws.rs.client.ClientBuilder;


@Component
public class GreeterService {
    private final static Logger logger = LoggerFactory.getLogger(GreeterService.class);

    public Observable<String> greetMe(String input) {
        logger.info("Service In");
        Observable<String> response = Rx.newClient(RxObservableInvoker.class)
                .target("http://localhost:7070/" + input)
                .request()
                .rx()
                .get(String.class);
        logger.info("Service Out");
        return Observable.just("Hello ").zipWith(response,(o1,o2) -> o1 + o2);
    }

    public Observable<String> ayncWorkIt() {
        logger.info("Service In");
        Observable<String> response = Rx.newClient(RxObservableInvoker.class)
                .target("http://localhost:7070")
                .request()
                .rx()
                .get(String.class);
        logger.info("Service Out");
        return response;
    }

}
