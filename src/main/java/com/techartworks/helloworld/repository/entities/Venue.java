package com.techartworks.helloworld.repository.entities;

import org.springframework.data.annotation.Id;

public record Venue(@Id Integer venue_id, String name, String address,
                    Integer capacity, String contact_person, String contact_email,
                    String contact_phone) {
}
