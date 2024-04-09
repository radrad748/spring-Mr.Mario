package com.radik.my.project.services.schedules;

import com.radik.my.project.services.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ScheduleShare {

    private final ShareService shareService;

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void deleteExpiredShare() {
        shareService.deleteExpiredShare();
    }

}
