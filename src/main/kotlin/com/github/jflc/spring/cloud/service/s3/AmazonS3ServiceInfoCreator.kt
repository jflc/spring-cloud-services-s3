package com.github.jflc.spring.cloud.service.s3

import org.springframework.cloud.cloudfoundry.CloudFoundryServiceInfoCreator
import org.springframework.cloud.cloudfoundry.Tags


class AmazonS3ServiceInfoCreator : CloudFoundryServiceInfoCreator<AmazonS3ServiceInfo>(Tags("s3", "aws-s3")) {

    override fun createServiceInfo(serviceData: Map<String, Any>): AmazonS3ServiceInfo {
        val credentials = serviceData["credentials"] as Map<String, Any>
        return AmazonS3ServiceInfo(
                id = serviceData["name"] as String,
                region = credentials["region"] as String,
                accessKeyId = credentials["access_key_id"] as String,
                secretAccessKey = credentials["secret_access_key"] as String,
                bucket = credentials["bucket"] as String
        )
    }

}