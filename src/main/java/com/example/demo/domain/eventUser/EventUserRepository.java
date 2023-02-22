package com.example.demo.domain.eventUser;

import com.example.demo.core.generic.AbstractRepository;
import com.example.demo.domain.event.Event;
import org.springframework.stereotype.Repository;

@Repository
public interface EventUserRepository extends AbstractRepository<Event> {
}
