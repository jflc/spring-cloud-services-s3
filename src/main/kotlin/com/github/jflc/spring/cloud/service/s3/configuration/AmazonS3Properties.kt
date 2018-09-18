package com.github.jflc.spring.cloud.service.s3.configuration

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSCredentialsProvider
import org.hibernate.validator.constraints.NotEmpty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@ConfigurationProperties(prefix = "spring.cloud.aws.s3")
@Validated
class AmazonS3Properties : AWSCredentialsProvider {

    @NotEmpty
    lateinit var region: String

    lateinit var bucket: String

    val credentials: AWSCredentialsProperties = AWSCredentialsProperties()

    override fun refresh() {}

    override fun getCredentials(): AWSCredentials = this.getCredentials()

}

class AWSCredentialsProperties : AWSCredentials {

    @NotEmpty
    lateinit var accessKey: String

    @NotEmpty
    lateinit var secretKey: String

    override fun getAWSAccessKeyId(): String = this.accessKey

    override fun getAWSSecretKey(): String = this.secretKey
}