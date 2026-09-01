package com.andresgm.erp_lite.infrastructure.persistence.aws.configs;


import com.andresgm.erp_lite.infrastructure.persistence.aws.models.AwsConfigModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

@Configuration
@Slf4j
public class S3BucketConfig {

    @Bean
    public S3Client s3Client(AwsConfigModel awsConfig) {
        log.info("Configuring ASW S3 Bucket");
        var credentials = AwsBasicCredentials.create(
                awsConfig.accessKey(),
                awsConfig.secretKey()
        );
        var s3Config = S3Configuration.builder()
                .pathStyleAccessEnabled(awsConfig.pathStyleEnabled())
                .build();

        var s3ClientBuilder = S3Client.builder()
                .region(Region.of(awsConfig.region()))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .serviceConfiguration(s3Config);

        return s3ClientBuilder.build();
    }
}
