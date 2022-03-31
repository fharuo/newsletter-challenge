package com.service.serviceImpl;

import com.model.SubscriberModel;
import com.port.SubscriberPort;
import com.service.SubscriberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriberServiceImpl implements SubscriberService {

    private final SubscriberPort port;

    public SubscriberServiceImpl(SubscriberPort port) {
        this.port = port;
    }

    @Override
    public List<SubscriberModel> getAll() {
        return port.getAll();
    }
}
