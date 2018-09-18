package com.github.jflc.spring.cloud.service.s3.connector

import org.assertj.core.api.BDDAssertions.then
import org.springframework.cloud.service.BaseServiceInfo
import org.testng.annotations.Test

class AmazonS3ServiceInfoCreatorTest {

    private val serviceInfoCreator = AmazonS3ServiceInfoCreator()

    @Test
    fun `given a valid service data content, when create service info, then an AmazonS3ServiceInfo is created`() {
        // GIVEN
        val name = "name"
        val accessKey = "access key"
        val secretKey = "secret key"
        val region = "region"
        val bucket = "bucket"
        val credentials = mapOf(
                "region" to region,
                "access_key_id" to accessKey,
                "secret_access_key" to secretKey,
                "bucket" to bucket
        )
        val serviceData = mapOf("name" to name, "credentials" to credentials)

        // WHEN
        val result = serviceInfoCreator.createServiceInfo(serviceData)

        // THEN
        then(result).isNotNull
                .hasNoNullFieldsOrProperties()
                .returns(name, BaseServiceInfo::getId)
                .returns(region, AmazonS3ServiceInfo::region)
                .returns(accessKey, AmazonS3ServiceInfo::accessKeyId)
                .returns(secretKey, AmazonS3ServiceInfo::secretAccessKey)
                .returns(bucket, AmazonS3ServiceInfo::bucket)
    }

    @Test
    fun `given a service data containing a aws-s3 label, when accept, then the result is true`() {
        // GIVEN
        val label = "aws-s3"
        val serviceData = mapOf("label" to label)

        // WHEN
        val result = serviceInfoCreator.accept(serviceData)

        // THEN
        then(result).isTrue()
    }

    @Test
    fun `given a service data containing a s3 label, when accept, then the result is true`() {
        // GIVEN
        val label = "s3"
        val serviceData = mapOf("label" to label)

        // WHEN
        val result = serviceInfoCreator.accept(serviceData)

        // THEN
        then(result).isTrue()
    }

    @Test
    fun `given a service data containing a aws-ec2 label, when accept, then the result is false`() {
        // GIVEN
        val label = "aws-ec2"
        val serviceData = mapOf("label" to label)

        // WHEN
        val result = serviceInfoCreator.accept(serviceData)

        // THEN
        then(result).isFalse()
    }
}
