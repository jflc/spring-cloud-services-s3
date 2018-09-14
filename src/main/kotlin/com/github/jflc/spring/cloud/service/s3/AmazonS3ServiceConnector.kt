package com.github.jflc.spring.cloud.service.s3

import io.pivotal.spring.cloud.config.java.ServiceInfoPropertySourceAdapter
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.MapPropertySource
import org.springframework.core.env.PropertySource

private const val PROPERTY_SOURCE_NAME = "springCloudServicesAmazonS3"

private const val SPRING_CLOUD_AWS_CREDENTIALS_ACCESSKEY = "spring.cloud.aws.s3.credentials.accessKey"
private const val SPRING_CLOUD_AWS_CREDENTIALS_SECRETKEY = "spring.cloud.aws.s3.credentials.secretKey"
private const val SPRING_CLOUD_AWS_AWS_REGION = "spring.cloud.aws.s3.region"
private const val SPRING_CLOUD_AWS_S3_BUCKET = "spring.cloud.aws.s3.bucket"

@Configuration
open class AmazonS3ServiceConnector: ServiceInfoPropertySourceAdapter<AmazonS3ServiceInfo>() {

    override fun toPropertySource(serviceInfo: AmazonS3ServiceInfo): PropertySource<*> {
        val properties = mapOf(
                SPRING_CLOUD_AWS_CREDENTIALS_ACCESSKEY to serviceInfo.accessKeyId,
                SPRING_CLOUD_AWS_CREDENTIALS_SECRETKEY to serviceInfo.secretAccessKey,
                SPRING_CLOUD_AWS_AWS_REGION to serviceInfo.region,
                SPRING_CLOUD_AWS_S3_BUCKET to serviceInfo.bucket
        )
        return MapPropertySource(PROPERTY_SOURCE_NAME, properties)
    }

}