package com.zhivkoproject.ZClinic.scheduler;

import com.zhivkoproject.ZClinic.service.TestService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NewTestsScheduler {
    private final TestService testService;

    public NewTestsScheduler(TestService testService) {
        this.testService = testService;
    }

    //“At 00:00 on every day-of-week from Monday through Friday.”
    @Scheduled(cron = "0 0 0 * * *", zone = "Europe/Sofia")
    public void addNewTests() {
        System.out.println("schedule refresh");
        testService.changeStatusTests();

    }
}
