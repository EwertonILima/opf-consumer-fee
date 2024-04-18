package com.ewertonilima.consumerfee.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import java.net.URI

@Configuration
class DynamoDbConfig(
    @Value("\${cloud.aws.region.static:}")
    private val awsRegion: String,
    @Value("\${cloud.aws.dynamodb.endpoint:}")
    private val endpoint: String,
    @Value("\${cloud.aws.credentials.accessKey:}")
    private val accessKeyId: String,
    @Value("\${cloud.aws.credentials.secretKey:}")
    private val secretKey: String,
    @Value("\${spring.profiles.active:}")
    private val env: String
) {

    @Bean
    fun dynamoDbAsyncClient(): DynamoDbAsyncClient {
        if (env == "local") {
            return DynamoDbAsyncClient.builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.of(awsRegion))
                .credentialsProvider(
                    StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                            accessKeyId,
                            secretKey
                        )
                    )
                )
                .build()
        }
        return DynamoDbAsyncClient.builder().build()
    }

    @Bean
    @Primary
    fun dynamoDbEnhancedAsyncClient(dynamoDbAsyncClient: DynamoDbAsyncClient): DynamoDbEnhancedAsyncClient {
        return DynamoDbEnhancedAsyncClient.builder()
            .dynamoDbClient(dynamoDbAsyncClient)
            .build()
    }
}