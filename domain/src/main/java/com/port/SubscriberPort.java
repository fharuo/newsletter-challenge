package com.port;

import com.model.SubscriberModel;

import java.util.List;

public interface SubscriberPort {
    List<SubscriberModel> getAll();

    SubscriberModel findByUsername(String username);

    SubscriberModel save(SubscriberModel subscriberModel);
}
