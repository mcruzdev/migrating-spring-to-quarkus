package dev.matheuscruz.quarkusclub.product;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class RunInTransaction {

    @Transactional
    public void run(Runnable runnable) {
        runnable.run();
    }
}