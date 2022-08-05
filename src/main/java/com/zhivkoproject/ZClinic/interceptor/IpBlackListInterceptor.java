package com.zhivkoproject.ZClinic.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IpBlackListInterceptor implements HandlerInterceptor {
    private final Map<String, Integer> ipActivity = new HashMap<>();
    private final Map<String, LocalTime> startActivityTime = new HashMap<>();

    private final LocalTime localTime = LocalTime.now();
    private final List<String> blacklistedIpAddresses = new ArrayList<>();

    public IpBlackListInterceptor() {
        //this.blacklistedIpAddresses.add("0:0:0:0:0:0:0:1");
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String ipAddress = request.getRemoteAddr();
        if (ipActivity.containsKey(ipAddress)){
            boolean isUpdate = false;
            int currentActivity = ipActivity.get(ipAddress);
//time not working
            //начална страница - 28 пъти в рамките на една минута
            //останалите страници - от 40 до 60 пъти в рамките на минута
            if (currentActivity > 250){

                LocalTime userStartActivityTime = startActivityTime.get(ipAddress);
                Duration duration = Duration.between(userStartActivityTime, LocalTime.now());

                if (duration.getSeconds() <= 60){
                    this.blacklistedIpAddresses.add(ipAddress);
                    System.out.println(duration.getSeconds());
                } else {
                    ipActivity.put(ipAddress, 1);
                    startActivityTime.put(ipAddress, LocalTime.now());
                    isUpdate = true;
                    System.out.println(LocalTime.now());
                }
            }

            if (!isUpdate){
                currentActivity++;
                ipActivity.put(ipAddress, currentActivity);
            }

        } else {
            ipActivity.put(ipAddress, 1);
            startActivityTime.put(ipAddress, LocalTime.now());
        }
        System.out.println(ipAddress);

        if (blacklistedIpAddresses.contains(ipAddress)) {
            response.sendError(400);

        }
        return true;
    }
}
