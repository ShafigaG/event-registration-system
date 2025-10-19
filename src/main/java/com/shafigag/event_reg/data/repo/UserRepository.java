package com.shafigag.event_reg.data.repo;

import com.shafigag.event_reg.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
