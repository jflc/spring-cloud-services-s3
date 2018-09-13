package com.github.jflc.spring.cloud.service.s3

import org.springframework.cloud.service.BaseServiceInfo

class AmazonS3ServiceInfo(id: String,
                          val accessKeyId: String,
                          val secretAccessKey: String,
                          val region: String,
                          val bucket: String) : BaseServiceInfo(id)