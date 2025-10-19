package com.shafigag.event_reg.service.impl;

import com.shafigag.event_reg.data.entity.Registration;
import com.shafigag.event_reg.data.repo.RegistrationRepository;
import com.shafigag.event_reg.service.RegistrationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private RegistrationRepository registrationRepository;

    @Override
    public Registration createRegistration(Registration registration) {
        return registrationRepository.save(registration);
    }

    @Override
    @Transactional(readOnly = true)
    public Registration getRegistrationById(Long registrationId) {
        return registrationRepository.findById(registrationId)
                .orElseThrow(() -> new EntityNotFoundException("Registration not found with id: " + registrationId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    @Override
    public Registration updateRegistration(Long registrationId, Registration registration) {
        Registration foundRegistration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new EntityNotFoundException("Registration not found with id: " + registrationId));

        foundRegistration.setUser(registration.getUser());
        foundRegistration.setEvent(registration.getEvent());

        return registrationRepository.save(foundRegistration);
    }

    @Override
    public void deleteRegistration(Long registrationId) {
        Registration foundRegistration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new EntityNotFoundException("Registration not found with id: " + registrationId));

        registrationRepository.delete(foundRegistration);
    }
}
