package com.shafigag.event_reg.service;

import com.shafigag.event_reg.data.entity.Event;
import com.shafigag.event_reg.data.repo.EventRepository;
import com.shafigag.event_reg.service.impl.EventServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    private Event event;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        event = new Event();
        event.setId(1L);
        event.setName("Test Event");
        event.setDescription("This is a test event.");
        event.setLocation("Test City");
        event.setStartingAt(Instant.now());
        event.setEndingAt(Instant.now().plusSeconds(3600));
        event.setCapacity(100);
        event.setAttendance(10);
        event.setTicketPrice(50.0);
    }

    @Test
    void createEventSavesAndReturnsEvent() {
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event result = eventService.createEvent(event);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Test Event");
        verify(eventRepository).save(event);
    }

    @Test
    void getEventByIdReturnsEvent() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        Event result = eventService.getEventById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        verify(eventRepository).findById(1L);
    }

    @Test
    void getEventByIdThrowsException() {
        when(eventRepository.findById(13L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> eventService.getEventById(13L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Event not found with id: 13");

        verify(eventRepository).findById(13L);
    }

    @Test
    void getAllEventsReturnsList() {
        when(eventRepository.findAll()).thenReturn(Arrays.asList(event, new Event()));

        List<Event> result = eventService.getAllEvents();

        assertThat(result).hasSize(2);
        verify(eventRepository).findAll();
    }

    @Test
    void updateEventUpdatesAndReturnsEvent() {
        Event updated = new Event();
        updated.setName("Updated Event");
        updated.setDescription("Updated desc");
        updated.setLocation("Updated City");
        updated.setStartingAt(Instant.now());
        updated.setEndingAt(Instant.now().plusSeconds(7200));
        updated.setCapacity(200);
        updated.setAttendance(20);
        updated.setTicketPrice(100.0);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event result = eventService.updateEvent(1L, updated);

        assertThat(result.getName()).isEqualTo("Updated Event");
        assertThat(result.getDescription()).isEqualTo("Updated desc");
        assertThat(result.getCapacity()).isEqualTo(200);
        verify(eventRepository).findById(1L);
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void updateEventThrowsException() {
        when(eventRepository.findById(13L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> eventService.updateEvent(13L, event))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Event not found with id: 13");

        verify(eventRepository).findById(13L);
        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    void deleteEvent() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        eventService.deleteEvent(1L);

        verify(eventRepository).findById(1L);
        verify(eventRepository).delete(event);
    }

    @Test
    void deleteEventThrowsException() {
        when(eventRepository.findById(13L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> eventService.deleteEvent(13L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Event not found with id: 13");

        verify(eventRepository).findById(13L);
        verify(eventRepository, never()).delete(any(Event.class));
    }

}
