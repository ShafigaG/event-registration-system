package com.shafigag.event_reg.service;

import com.shafigag.event_reg.data.entity.Event;
import com.shafigag.event_reg.data.entity.Registration;
import com.shafigag.event_reg.data.entity.User;
import com.shafigag.event_reg.data.repo.RegistrationRepository;
import com.shafigag.event_reg.service.impl.RegistrationServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RegistrationServiceImplTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    private Registration registration;
    private User user;
    private Event event;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");

        event = new Event();
        event.setId(1L);
        event.setName("Test Event");
        event.setDescription("Test desc");
        event.setLocation("Test City");
        event.setCapacity(100);
        event.setAttendance(10);
        event.setTicketPrice(20.0);

        registration = new Registration();
        registration.setId(1L);
        registration.setUser(user);
        registration.setEvent(event);
    }

    @Test
    void createRegistrationSavesAndReturnsRegistration() {
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        Registration result = registrationService.createRegistration(registration);

        assertThat(result).isNotNull();
        assertThat(result.getUser().getName()).isEqualTo("Test User");
        verify(registrationRepository).save(registration);
    }

    @Test
    void getRegistrationByIdReturnsRegistration() {
        when(registrationRepository.findById(1L)).thenReturn(Optional.of(registration));

        Registration result = registrationService.getRegistrationById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEvent().getName()).isEqualTo("Test Event");
        verify(registrationRepository).findById(1L);
    }

    @Test
    void getRegistrationByIdThrowsException() {
        when(registrationRepository.findById(13L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> registrationService.getRegistrationById(13L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Registration not found with id: 13");

        verify(registrationRepository).findById(13L);
    }

    @Test
    void getAllRegistrationsReturnsList() {
        when(registrationRepository.findAll()).thenReturn(Arrays.asList(registration, new Registration()));

        List<Registration> result = registrationService.getAllRegistrations();

        assertThat(result).hasSize(2);
        verify(registrationRepository).findAll();
    }

    @Test
    void updateRegistrationUpdatesAndReturnsRegistration() {
        Registration updated = new Registration();
        User newUser = new User();
        newUser.setId(2L);
        newUser.setName("Another User");
        newUser.setEmail("another@example.com");

        Event newEvent = new Event();
        newEvent.setId(2L);
        newEvent.setName("Another Event");
        newEvent.setDescription("Updated desc");
        newEvent.setLocation("Another City");

        updated.setUser(newUser);
        updated.setEvent(newEvent);

        when(registrationRepository.findById(1L)).thenReturn(Optional.of(registration));
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        Registration result = registrationService.updateRegistration(1L, updated);

        assertThat(result.getUser().getName()).isEqualTo("Another User");
        assertThat(result.getEvent().getName()).isEqualTo("Another Event");
        verify(registrationRepository).findById(1L);
        verify(registrationRepository).save(any(Registration.class));
    }

    @Test
    void updateRegistrationThrowsException() {
        when(registrationRepository.findById(13L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> registrationService.updateRegistration(13L, registration))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Registration not found with id: 13");

        verify(registrationRepository).findById(13L);
        verify(registrationRepository, never()).save(any(Registration.class));
    }

    @Test
    void deleteRegistration() {
        when(registrationRepository.findById(1L)).thenReturn(Optional.of(registration));

        registrationService.deleteRegistration(1L);

        verify(registrationRepository).findById(1L);
        verify(registrationRepository).delete(registration);
    }

    @Test
    void deleteRegistrationThrowsException() {
        when(registrationRepository.findById(13L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> registrationService.deleteRegistration(13L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Registration not found with id: 13");

        verify(registrationRepository).findById(13L);
        verify(registrationRepository, never()).delete(any(Registration.class));
    }

}
