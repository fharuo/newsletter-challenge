package com.service;

import com.model.SubscriberModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubscriberService {
    List<SubscriberModel> getAll();
}
