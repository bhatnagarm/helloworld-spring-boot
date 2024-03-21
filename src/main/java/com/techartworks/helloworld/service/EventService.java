package com.techartworks.helloworld.service;

import com.techartworks.helloworld.domain.model.EventModel;
import com.techartworks.helloworld.domain.model.OrganizerModel;
import com.techartworks.helloworld.domain.model.VenueModel;
import com.techartworks.helloworld.repository.EventRepository;
import com.techartworks.helloworld.repository.entities.Event;
import com.techartworks.helloworld.repository.entities.Organizer;
import com.techartworks.helloworld.repository.entities.Venue;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<EventModel> getEventService() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(EventService::eventTransformer).collect(Collectors.toList());
    }

    private static EventModel eventTransformer(Event event) {
        return new EventModel (event.event_id(), event.title(), event.description(), event.start_datetime(),
                event.end_datetime(), event.venue_id(), event.organizer_id(),venueTransformer(event.venue()),
                organizerTransformer(event.organizer()));
    }

    private static VenueModel venueTransformer(Venue venue) {
        return new VenueModel(venue.venue_id(), venue.name(), venue.address(), venue.capacity(), venue.contact_person(),
                venue.contact_email(), venue.contact_phone());
    }

    private static OrganizerModel organizerTransformer(Organizer organizer) {
        return new OrganizerModel(organizer.organizer_id(), organizer.name(), organizer.email(), organizer.phone());
    }
}
