package com.techartworks.helloworld.repository.entities;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;

public record Event(@Id Integer event_id, String title, String description,
                    DateTime start_datetime,
                    DateTime end_datetime,
                    Integer venue_id,
                    Integer organizer_id,
                    Venue venue,
                    Organizer organizer) {
}
