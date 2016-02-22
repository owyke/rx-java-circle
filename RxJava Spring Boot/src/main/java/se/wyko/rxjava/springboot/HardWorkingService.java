package se.wyko.rxjava.springboot;

import org.glassfish.jersey.client.rx.Rx;
import org.glassfish.jersey.client.rx.rxjava.RxObservableInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import rx.Observable;
import rx.schedulers.Schedulers;
import rx.util.async.Async;

import javax.ws.rs.client.ClientBuilder;


@Component
public class HardWorkingService {
    private final static Logger logger = LoggerFactory.getLogger(HardWorkingService.class);

    public Observable<String> calculateItAsync() {
        logger.info("Service start");
        final Observable result = Async.start(this::doHardWork, Schedulers.newThread());
        logger.info("Service done");
        return result;
    }

    public String calculateIt() {
        logger.info("Service start");
        final String result = doHardWork();
        logger.info("Service done");
        return result;
    }

    public String workIt() {
        logger.info("Service In");
        String response = ClientBuilder.newClient()
                .target("http://localhost:7070")
                .request()
                .get(String.class);
        logger.info("Service Out");
        return response;
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

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String doHardWork() {
        logger.info("Starting work");
        sleep(1000);
        logger.info("Done with work");
        return "Hello world!";
    }
}
