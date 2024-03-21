package com.techartworks.helloworld.domain.model;

import org.joda.time.DateTime;

public record EventModel(Integer event_id, String title, String description,
                         DateTime start_datetime,
                         DateTime end_datetime,
                         Integer venue_id,
                         Integer organizer_id,
                         VenueModel venue,
                         OrganizerModel organizer) {
}
