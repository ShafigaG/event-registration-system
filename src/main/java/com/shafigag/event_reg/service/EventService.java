package com.shafigag.event_reg.service;

import com.shafigag.event_reg.data.entity.Event;

import java.util.List;

public interface EventService {
    Event createEvent(Event event);
    Event getEventById(Long eventId);
    List<Event> getAllEvents();
    Event updateEvent(Long eventId, Event event);
    void deleteEvent(Long eventId);
}
