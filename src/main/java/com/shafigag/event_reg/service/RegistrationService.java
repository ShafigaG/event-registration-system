package com.shafigag.event_reg.service;

import com.shafigag.event_reg.data.entity.Registration;

import java.util.List;

public interface RegistrationService {
    Registration createRegistration(Registration registration);
    Registration getRegistrationById(Long registrationId);
    List<Registration> getAllRegistrations();
    Registration updateRegistration(Long registrationId, Registration registration);
    void deleteRegistration(Long registrationId);
}
