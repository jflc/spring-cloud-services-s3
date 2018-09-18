package com.github.jflc.spring.cloud.service.s3.configuration

import com.amazonaws.services.s3.AmazonS3
import com.github.jflc.spring.cloud.service.s3.connector.AmazonS3ServiceInfo
import com.github.jflc.spring.cloud.service.s3.test.mock.MockCloudConnector
import org.assertj.core.api.BDDAssertions.then
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.annotations.BeforeTest
import org.testng.annotations.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, properties = ["spring.cloud.aws.s3.enabled=true"])
class AmazonS3ConfigurationIT: AbstractTestNGSpringContextTests() {

    @SpringBootApplication
    private class MockSpringBootApplication

    @Autowired
    private lateinit var context: ApplicationContext

    @BeforeTest
    fun setUp() {
        val id = "dummy id"
        val accessKey = "dummy access key"
        val secretKey = "dummy secret key"
        val region = "eu-central-1"
        val bucket = "dummy bucket"

        val serviceInfo = AmazonS3ServiceInfo(
                id = id,
                accessKeyId = accessKey,
                secretAccessKey = secretKey,
                region = region,
                bucket = bucket
        )

        val connector = MockCloudConnector.INSTANCE

        given(connector.isInMatchingCloud).willReturn(true)
        given(connector.serviceInfos).willReturn(listOf(serviceInfo))
    }

    @Test
    fun `given AmazonS3 class, when getBean by it, then an instance should be returned`() {
        // GIVEN
        val cls = AmazonS3::class.java

        // WHEN
        val amazonS3 = context.getBean(cls)

        // THEN
        then(amazonS3).isNotNull
    }

    @Test
    fun `given AmazonS3Properties class, when getBean by it, then an instance should be returned`() {
        // GIVEN
        val cls = AmazonS3Properties::class.java

        // WHEN
        val properties = context.getBean(cls)

        // THEN
        then(properties).isNotNull
                .hasNoNullFieldsOrProperties()
                .returns("dummy bucket", AmazonS3Properties::bucket)
                .returns("eu-central-1", AmazonS3Properties::region)
        then(properties.credentials).hasNoNullFieldsOrProperties()
                .returns("dummy access key", AWSCredentialsProperties::accessKey)
                .returns("dummy access key", AWSCredentialsProperties::getAWSAccessKeyId)
                .returns("dummy secret key", AWSCredentialsProperties::secretKey)
                .returns("dummy secret key", AWSCredentialsProperties::getAWSSecretKey)
    }
}


