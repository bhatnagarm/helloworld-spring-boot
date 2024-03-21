package com.techartworks.helloworld.repository.entities;

import org.springframework.data.annotation.Id;

public record EventCoordinator(@Id Integer coordinator_id,
                               String coordinator_name,
                               String coordinator_email,
                               String coordinator_phoneno,
                               String coordinator_area) {
}
