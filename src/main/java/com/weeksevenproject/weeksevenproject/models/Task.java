package com.weeksevenproject.weeksevenproject.models;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.extensions.annotations.DynamoDbAutoGeneratedTimestampAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@DynamoDbBean
public class Task {
    
    private UUID id;
    private String title;
    private String summary;
    private String content;
    private boolean status;
    private Instant createdAt;
    private Instant updatedAt;

    @DynamoDbAutoGeneratedTimestampAttribute
    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @DynamoDbAutoGeneratedTimestampAttribute
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    

    @DynamoDbPartitionKey
    public UUID getId() {
        return id;
    }
}
