package com.company.schedulars;

import com.company.entity.Developer;
import com.company.helper.DeveloperIDGenerator;
import com.company.repository.DeveloperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeveloperIdScheduler {

    private final DeveloperRepository developerRepository;

    // Runs every 1 minute (cron: sec min hour day month weekday)
    @Scheduled(cron = "0 */1 * * * *")
    @Transactional
    public void checkAndGenerateMissingIds() {
        List<Developer> missingDevelopers = developerRepository.findDevelopersWithoutId();

        if (!missingDevelopers.isEmpty()) {
            for (Developer dev : missingDevelopers) {
                String generatedId = DeveloperIDGenerator.generateDeveloperId(dev);
                developerRepository.updateDeveloperId(dev.getId(), generatedId);
                System.err.println("Fixed missing developer_id for developer: " + dev.getfName() + " -> " + generatedId);
            }
        }
    }

}
