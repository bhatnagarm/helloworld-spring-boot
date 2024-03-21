package com.techartworks.helloworld.repository.entities;

public record EventEventCategory(Integer event_id,
                                 Integer category_id,
//                                 PRIMARY KEY (event_id, category_id),
        Event event, EventCategory eventCategory) {
}
