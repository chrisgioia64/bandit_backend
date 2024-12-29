package org.example.aws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.SnsException;

@Component("PublishTextSMS")
public class PublishTextSMS {

    private static Logger logger = LoggerFactory.getLogger(PublishTextSMS.class);

    public static void sendMessage(int numExperiments) {
        Region region = Region.US_EAST_1;
        SnsClient snsClient = SnsClient.builder()
                .region(region)
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .build();


        System.out.println("Sending message");
        logger.info("Sending text message");
        String message = "An API request was made to getExperiments/. There were " + numExperiments + " experiments";
        String phoneNumber = "5104999454"; // Replace with a mobile phone number.

        try {
            PublishRequest request = PublishRequest.builder()
                    .message(message)
                    .phoneNumber(phoneNumber)
                    .build();

            snsClient.publish(request);

        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}