package com.techartworks.helloworld.repository;

import com.techartworks.helloworld.repository.entities.Event;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, Integer> {
    @NotNull
    List<Event> findAll();
}
