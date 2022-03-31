package com.service.serviceImpl;

import com.exception.InvalidPasswordException;
import com.model.SubscriberModel;
import com.port.SubscriberPort;
import com.service.SubscriberService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriberServiceImpl implements SubscriberService, UserDetailsService {

    private final SubscriberPort port;

    public SubscriberServiceImpl(SubscriberPort port) {
        this.port = port;
    }

    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Override
    public SubscriberModel create(SubscriberModel subscriberModel) {
        String encryptedPassword = passwordEncoder().encode(subscriberModel.getPassword());
        subscriberModel.setPassword(encryptedPassword);

        return port.save(subscriberModel);
    }

    @Override
    public UserDetails authenticate(SubscriberModel subscriberModel) {
        UserDetails userDetails = loadUserByUsername(subscriberModel.getUsername());
        boolean matches = passwordEncoder().matches(subscriberModel.getPassword(), userDetails.getPassword());

        if(matches) {
            return userDetails;
        }

        throw new InvalidPasswordException();
    }

    @Override
    public List<SubscriberModel> getAll() {
        return port.getAll();
    }

    @Override
    public UserDetails loadUserByUsername(String appUserLogin) throws UsernameNotFoundException {
        SubscriberModel subscriberModel = port.findByUsername(appUserLogin);

        String[] roles = subscriberModel.isAdmin() ?
                new String[] {"ADMIN", "USER"} : new String[] {"USER"};

        return User.builder()
                .username(subscriberModel.getUsername())
                .password(subscriberModel.getPassword())
                .roles(roles)
                .build();
    }

}
