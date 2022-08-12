package com.zhivkoproject.ZClinic.service;

import com.zhivkoproject.ZClinic.repository.*;
import com.zhivkoproject.ZClinic.service.impl.ZClinicUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private CartRepository mockCart;

    @Mock
    private TestRepository mockTest;

    @Mock
    private ResultRepository mockResult;

    @Mock
    private OrderRepository mockOrder;

    @Mock
    private UserRepository mockUser;

    private ZClinicUserDetailsService userDetails;

    @BeforeEach
    void setUp() {
        userDetails = new ZClinicUserDetailsService(mockUser);
    }


}
