package com.test.my.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "cloud.aws.s3")
@ConstructorBinding // @ConfigurationPropertiesScan
public class S3Config {

	private final String bucket;
}
