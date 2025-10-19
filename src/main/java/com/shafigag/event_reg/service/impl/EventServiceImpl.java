package com.shafigag.event_reg.service.impl;

import com.shafigag.event_reg.data.entity.Event;
import com.shafigag.event_reg.data.repo.EventRepository;
import com.shafigag.event_reg.service.EventService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    @Transactional(readOnly = true)
    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + eventId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event updateEvent(Long eventId, Event event) {
        Event foundEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + eventId));

        foundEvent.setName(event.getName());
        foundEvent.setDescription(event.getDescription());
        foundEvent.setLocation(event.getLocation());
        foundEvent.setStartingAt(event.getStartingAt());
        foundEvent.setEndingAt(event.getEndingAt());
        foundEvent.setCapacity(event.getCapacity());
        foundEvent.setAttendance(event.getAttendance());
        foundEvent.setTicketPrice(event.getTicketPrice());

        return eventRepository.save(foundEvent);
    }

    @Override
    public void deleteEvent(Long eventId) {
        Event foundEvent = eventRepository.findById(eventId)
                        .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + eventId));

        eventRepository.delete(foundEvent);
    }
}
