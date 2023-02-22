package com.example.demo.domain.user.query;

import com.example.demo.core.generic.AbstractCommandServiceImpl;
import com.example.demo.core.generic.AbstractQueryServiceImpl;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserDetailsImpl;
import com.example.demo.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UserQueryServiceImpl extends AbstractQueryServiceImpl<User> implements UserQueryService {

    @Autowired
    public UserQueryServiceImpl(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return ((UserRepository) repository).findByEmail(email)
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

}
