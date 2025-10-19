package com.shafigag.event_reg.data.repo;

import com.shafigag.event_reg.data.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
}
