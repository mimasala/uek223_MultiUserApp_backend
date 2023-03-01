package com.example.demo.domain.event;

import com.example.demo.core.generic.AbstractRepository;
import com.example.demo.domain.user.User;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends AbstractRepository<Event> {
    void deleteByEventOwner(User eventOwner);

}
