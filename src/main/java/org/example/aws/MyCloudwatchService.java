package org.example.aws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;
import software.amazon.awssdk.services.cloudwatchlogs.model.*;

import java.time.Instant;

@Component("Cloudwatch")
public class MyCloudwatchService {

    private static Logger logger = LoggerFactory.getLogger(MyCloudwatchService.class);

    private static final String LOG_GROUP_NAME = "Bandit-App";
    private static final String LOG_STREAM_NAME = "Backend";
    private final ObjectMapper objectMapper;
    CloudWatchLogsClient logsClient;

    public MyCloudwatchService() {
        objectMapper = new ObjectMapper();
        logsClient = CloudWatchLogsClient.create();
        createLogGroupIfNotExists();
        createLogStreamIfNotExists();
    }

    public static void main(String[] args) {
        MyCloudwatchService service = new MyCloudwatchService();
        service.sendTestMessage();
    }

    public void sendTestMessage() {
        try {
            CloudWatchLogEntry entry = new CloudWatchLogEntry(CloudWatchLogEntry.CloudWatchLogType.STARTUP,
                    CloudWatchLogEntry.Level.INFO, "Sample message", "Debugging some stuff");
            sendLogEvent(entry);
        } catch (JsonProcessingException e) {
            System.err.println("Could not send the log");
            e.printStackTrace();
        }
    }

    private void createLogGroupIfNotExists() {
        try {
            logsClient.createLogGroup(CreateLogGroupRequest.builder()
                    .logGroupName(LOG_GROUP_NAME)
                    .build());
            logger.info("Log group created: " + LOG_GROUP_NAME);
        } catch (ResourceAlreadyExistsException e) {
            logger.info("Log group already exists: " + LOG_GROUP_NAME);
        }
    }

    private void createLogStreamIfNotExists() {
        try {
            logsClient.createLogStream(CreateLogStreamRequest.builder()
                    .logGroupName(LOG_GROUP_NAME)
                    .logStreamName(LOG_STREAM_NAME)
                    .build());
            logger.info("Log stream created: " + LOG_STREAM_NAME);
        } catch (ResourceAlreadyExistsException e) {
            logger.info("Log stream already exists: " + LOG_STREAM_NAME);
        }
    }



    public void sendLogEvent(CloudWatchLogEntry entry) throws JsonProcessingException {

        String jsonLog = objectMapper.writeValueAsString(entry);
        logger.info("Logging to CloudWatch: " + entry.toString());

        try {
            // Prepare a log event
            InputLogEvent logEvent = InputLogEvent.builder()
                    .message(jsonLog)
                    .timestamp(Instant.now().toEpochMilli())
                    .build();

            // Retrieve the sequence token if required
            DescribeLogStreamsResponse logStreamsResponse = logsClient.describeLogStreams(DescribeLogStreamsRequest.builder()
                    .logGroupName(LOG_GROUP_NAME)
                    .logStreamNamePrefix(LOG_STREAM_NAME)
                    .build());

            String sequenceToken = logStreamsResponse.logStreams().isEmpty() ? null
                    : logStreamsResponse.logStreams().get(0).uploadSequenceToken();

            // Send the log event
            PutLogEventsRequest.Builder putLogEventsRequestBuilder = PutLogEventsRequest.builder()
                    .logGroupName(LOG_GROUP_NAME)
                    .logStreamName(LOG_STREAM_NAME)
                    .logEvents(logEvent);

            if (sequenceToken != null) {
                putLogEventsRequestBuilder.sequenceToken(sequenceToken);
            }

            logsClient.putLogEvents(putLogEventsRequestBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
