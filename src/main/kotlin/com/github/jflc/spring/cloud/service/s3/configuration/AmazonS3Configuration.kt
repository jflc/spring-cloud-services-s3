package com.github.jflc.spring.cloud.service.s3.configuration

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = ["com.github.jflc.spring.cloud.service.s3"])
@ConditionalOnProperty(prefix = "spring.cloud.aws.s3", name = ["enabled"])
@EnableConfigurationProperties(AmazonS3Properties::class)
class AmazonS3Configuration(private val amazonS3Properties: AmazonS3Properties) {

    @Bean
    fun amazonS3(): AmazonS3 {
        val credentialsProvider = this.amazonS3Properties
        val region = this.amazonS3Properties.region
        return AmazonS3ClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(region)
                .build()
    }

}