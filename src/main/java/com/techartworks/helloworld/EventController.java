package com.techartworks.helloworld;

import com.techartworks.helloworld.domain.model.EventModel;
import com.techartworks.helloworld.repository.EventRepository;
import com.techartworks.helloworld.repository.entities.Event;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventController {

    private EventRepository eventRepository;

    @GetMapping("/events")
    List<Event> getEvents() {
        return eventRepository.findAll();

    }
}
