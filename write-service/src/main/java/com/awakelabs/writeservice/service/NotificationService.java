package com.awakelabs.writeservice.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    /**
     * Demo method to simulate asynchronous messaging or functionality
     * @param patientId
     * @throws InterruptedException
     */

    @Async
    public void send(Integer patientId) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("Notification sent to :" + patientId);
    }
}
