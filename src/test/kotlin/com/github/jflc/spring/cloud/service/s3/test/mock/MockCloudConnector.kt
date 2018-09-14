package com.github.jflc.spring.cloud.service.s3.test.mock

import org.mockito.Mockito
import org.springframework.cloud.CloudConnector
import org.springframework.cloud.app.ApplicationInstanceInfo
import org.springframework.cloud.service.ServiceInfo

class MockCloudConnector: CloudConnector {
    companion object {
        @JvmField val INSTANCE: CloudConnector = Mockito.mock(CloudConnector::class.java)

        fun reset() {
            Mockito.reset(INSTANCE)
        }

    }

    override fun getApplicationInstanceInfo(): ApplicationInstanceInfo = INSTANCE.applicationInstanceInfo

    override fun getServiceInfos(): MutableList<ServiceInfo> = INSTANCE.serviceInfos

    override fun isInMatchingCloud(): Boolean = INSTANCE.isInMatchingCloud
}