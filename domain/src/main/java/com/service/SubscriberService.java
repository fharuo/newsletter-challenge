package com.service;

import com.model.SubscriberModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SubscriberService {
    List<SubscriberModel> getAll();

    UserDetails loadUserByUsername(String appUserLogin);

    SubscriberModel create(SubscriberModel subscriberModel);

    UserDetails authenticate(SubscriberModel subscriberModel);
}
