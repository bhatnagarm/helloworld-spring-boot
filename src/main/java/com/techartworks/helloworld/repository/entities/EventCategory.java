package com.techartworks.helloworld.repository.entities;

import org.springframework.data.annotation.Id;

public record EventCategory(@Id Integer category_id, String category_name) {
}
