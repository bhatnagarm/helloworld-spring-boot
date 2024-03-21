package com.techartworks.helloworld.repository.entities;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;

public record HotelRooms(@Id Integer room_number,
                         Integer attendee_id,
                         DateTime staying_on_date,
                         Event event,
                         Attendee attendee) {
}
