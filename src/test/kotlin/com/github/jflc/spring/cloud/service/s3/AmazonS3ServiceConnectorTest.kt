package com.github.jflc.spring.cloud.service.s3

import com.github.jflc.spring.cloud.service.s3.test.mock.MockCloudConnector
import org.assertj.core.api.BDDAssertions.then
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.MutablePropertySources
import org.springframework.core.env.PropertySource
import org.testng.annotations.Test

class AmazonS3ServiceConnectorTest {

    private val serviceConnector = AmazonS3ServiceConnector()

    @Test
    fun `given a valid AmazonS3ServiceInfo, when toPropertySource, then a valid PropertySource is created`() {
        // GIVEN
        val id = "dummy id"
        val accessKey = "dummy access key"
        val secretKey = "dummy secret key"
        val region = "dummy region"
        val bucket = "dummy bucket"

        val serviceInfo = AmazonS3ServiceInfo(
                id = id,
                accessKeyId = accessKey,
                secretAccessKey = secretKey,
                region = region,
                bucket = bucket
        )

        val propertySources = MutablePropertySources()

        val event = mock(ApplicationEnvironmentPreparedEvent::class.java)
        val environment = mock(ConfigurableEnvironment::class.java)

        given(MockCloudConnector.INSTANCE.isInMatchingCloud).willReturn(true)
        given(MockCloudConnector.INSTANCE.serviceInfos).willReturn(listOf(serviceInfo))
        given(event.environment).willReturn(environment)
        given(environment.propertySources).willReturn(propertySources)

        // WHEN
        serviceConnector.onApplicationEvent(event)

        // THEN
        then(propertySources.get("springCloudServicesAmazonS3")).isNotNull
                .returns("springCloudServicesAmazonS3", PropertySource<*>::getName)
                .returns(accessKey, { it.getProperty("spring.cloud.aws.s3.credentials.accessKey") })
                .returns(secretKey, { it.getProperty("spring.cloud.aws.s3.credentials.secretKey") })
                .returns(region, { it.getProperty("spring.cloud.aws.s3.region") })
                .returns(bucket, { it.getProperty("spring.cloud.aws.s3.bucket") })
    }
}