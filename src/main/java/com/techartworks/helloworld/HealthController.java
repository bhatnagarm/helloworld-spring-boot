package com.techartworks.helloworld;

import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    private final ApplicationEventPublisher publish;

    public HealthController(final ApplicationEventPublisher publish) {
        this.publish = publish;
    }

    @RequestMapping("/down")
    public String down() {

        AvailabilityChangeEvent.publish(publish, this, ReadinessState.REFUSING_TRAFFIC);
        return "Down";
    }

    @RequestMapping("/up")
    public String up() {
        AvailabilityChangeEvent.publish(publish, this, ReadinessState.ACCEPTING_TRAFFIC);
        return "Up";
    }
}
