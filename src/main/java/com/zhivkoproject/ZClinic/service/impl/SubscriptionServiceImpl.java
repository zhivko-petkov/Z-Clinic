package com.zhivkoproject.ZClinic.service.impl;

import com.zhivkoproject.ZClinic.model.binding.SubscriptionAddModel;
import com.zhivkoproject.ZClinic.model.entity.Subscription;
import com.zhivkoproject.ZClinic.repository.SubscriptionRepository;
import com.zhivkoproject.ZClinic.service.SubscriptionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final ModelMapper modelMapper;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, ModelMapper modelMapper) {
        this.subscriptionRepository = subscriptionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveSubscription(SubscriptionAddModel subscriptionModel) {
        Subscription subscription = modelMapper.map(subscriptionModel, Subscription.class);
        subscriptionRepository.save(subscription);
    }
}
