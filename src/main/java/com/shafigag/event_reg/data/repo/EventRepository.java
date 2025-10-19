package com.shafigag.event_reg.data.repo;

import com.shafigag.event_reg.data.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
