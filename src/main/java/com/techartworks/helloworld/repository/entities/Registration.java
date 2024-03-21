package com.techartworks.helloworld.repository.entities;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;

public record Registration(@Id Integer registration_id, Integer event_id, Integer attendee_id,
                           DateTime registration_date, Event event, Attendee attendee) {
}
