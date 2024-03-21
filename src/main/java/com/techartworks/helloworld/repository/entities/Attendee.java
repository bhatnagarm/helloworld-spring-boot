package com.techartworks.helloworld.repository.entities;

import org.springframework.data.annotation.Id;

public record Attendee(@Id Integer attendee_id, String first_name, String last_name,
                       String relationship, String email, String phone) {
}
