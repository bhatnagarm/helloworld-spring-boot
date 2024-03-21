package com.techartworks.helloworld.domain.model;

public record VenueModel(Integer venue_id, String name, String address,
                         Integer capacity, String contact_person, String contact_email,
                         String contact_phone) {
}
