package org.example.aws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sns.SnsClient;

@Component("MyS3Service")
public class MyS3Service {

    private static Logger logger = LoggerFactory.getLogger(MyS3Service.class);

    public static void listS3Buckets() {
        logger.info("Starting S3 Service");

        Region region = Region.US_EAST_1;
        S3Client s3Client = S3Client.builder().region(region)
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .build();

        logger.info("Successfully established connection to S3 client");
    }

}
