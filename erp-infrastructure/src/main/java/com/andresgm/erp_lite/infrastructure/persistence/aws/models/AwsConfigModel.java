package com.andresgm.erp_lite.infrastructure.persistence.aws.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "aws.s3")
@Validated
public record AwsConfigModel(
    @NotBlank(message = "aws.s3.endpoint must not be blank")
    String endpoint,
    @NotBlank(message = "aws.s3.region must not be blank")
    String region,
    @NotBlank(message = "aws.s3.access-key must not be blank")
    String accessKey,
    @NotBlank(message = "aws.s3.secret-key must not be blank")
    String secretKey,
    @NotBlank(message = "aws.s3.bucket-name must not be blank")
    String bucketName,
    @NotNull(message = "aws.s3.path-style-enabled must not be null")
    Boolean pathStyleEnabled) {

        public String getBucketUrl(){
            return String.format("%s/%s", endpoint, bucketName);
        }

}
