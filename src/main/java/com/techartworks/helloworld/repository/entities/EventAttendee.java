package com.techartworks.helloworld.repository.entities;

import org.joda.time.DateTime;

public record EventAttendee(Integer event_id, Integer attendee_id, DateTime registration_date,
                            PRIMARY KEY (event_id, attendee_id), Event event, Attendee attendee) {
}
