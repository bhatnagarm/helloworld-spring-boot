package com.techartworks.helloworld.domain.model;

import org.springframework.data.annotation.Id;

public record OrganizerModel(@Id Integer organizer_id, String name, String email, String phone) {
}
