package com.techartworks.helloworld.repository.entities;

import org.springframework.data.annotation.Id;

public record Organizer(@Id Integer organizer_id, String name, String email, String phone) {
}
