package org.example;

import org.example.algorithm.ETCAlgorithm;
import org.example.aws.CloudWatchLogEntry;
import org.example.aws.MyCloudwatchService;
import org.example.strategy.ExperimentRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private ExperimentRunner runner;

    @Autowired
    private MyCloudwatchService cloudwatchService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        CloudWatchLogEntry entry = new CloudWatchLogEntry(CloudWatchLogEntry.CloudWatchLogType.STARTUP,
                CloudWatchLogEntry.Level.INFO,
                "Starting up backend server", "");
        cloudwatchService.sendLogEvent(entry);
    }
}