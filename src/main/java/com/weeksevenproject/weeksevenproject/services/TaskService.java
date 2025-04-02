package com.weeksevenproject.weeksevenproject.services;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weeksevenproject.weeksevenproject.dto.TaskDTO;
import com.weeksevenproject.weeksevenproject.models.Task;
import com.weeksevenproject.weeksevenproject.utils.PaginatedResult;

import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;

@Service
public class TaskService {
    
    @Autowired
    DynamoDbTemplate dynamoDbTemplate;

    public boolean addtask(TaskDTO taskDTO) {

        Task task = new Task();
        task.setId(UUID.randomUUID());
        task.setTitle(taskDTO.getTitle());
        task.setStatus(false);
        task.setContent(taskDTO.getContent());
        task.setSummary(taskDTO.getSummary());
        task.setCreatedAt(Instant.now());
        task.setUpdatedAt(Instant.now());

        dynamoDbTemplate.save(task);

        return true;
    }


    public Task findTaskById(String id) {
        Key primaryKey = Key.builder().partitionValue(id).build();
        Task task = dynamoDbTemplate.load(primaryKey, Task.class);
        return task;
    }


    public boolean deleteTask(String id) {
        Key primaryKey = Key.builder().partitionValue(id).build();
        Task task = dynamoDbTemplate.delete(primaryKey, Task.class);
        return task != null;
    }

    public boolean updateTask(TaskDTO taskDTO, String id) {
        try {
            Task existingTask = findTaskById(id);
            if (existingTask == null) {
                return false;
            }
            existingTask.setTitle(taskDTO.getTitle());
            existingTask.setContent(taskDTO.getContent());
            existingTask.setSummary(taskDTO.getSummary());
            existingTask.setUpdatedAt(Instant.now());
            dynamoDbTemplate.update(existingTask);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean completeTask(String id) {
        try {
            Task existingTask = findTaskById(id);
            if (existingTask == null) {
                return false;
            }
            existingTask.setStatus(true);
            dynamoDbTemplate.update(existingTask);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public PaginatedResult<Task> getTasks(int pageSize, String nextPageToken) {
        ScanEnhancedRequest.Builder scanEnhancedRequest = ScanEnhancedRequest.builder()
                .limit(pageSize);
        // if (nextPageToken != null && !nextPageToken.isEmpty()) {
        //     try {
        //         // Decode the base64 token to get the exclusive start key
        //         Map<String, AttributeValue> exclusiveStartKey = decodeNextPageToken(nextPageToken);
        //         scanEnhancedRequest.exclusiveStartKey(exclusiveStartKey);
        //     } catch (Exception e) {
        //         throw new IllegalArgumentException("Invalid pagination token", e);
        //     }
        // }
        PageIterable<Task> tasks = dynamoDbTemplate.scan(scanEnhancedRequest.build(), Task.class);
        // Get the first page
        Page<Task> page = tasks.iterator().next();
        
        // Create result with items and next token
        List<Task> items = page.items().stream().collect(Collectors.toList());
        String newNextPageToken = null;
        
        // Generate next token if there are more pages
        // if (page.lastEvaluatedKey() != null && !page.lastEvaluatedKey().isEmpty()) {
        //     newNextPageToken = encodeNextPageToken(page.lastEvaluatedKey());
        // }
        
        return new PaginatedResult<>(items, newNextPageToken);
    }

     // Helper methods for token handling
    // private String encodeNextPageToken(Map<String, AttributeValue> lastEvaluatedKey) {
    //     try {
    //         // Convert the map to JSON and then Base64 encode
    //         ObjectMapper objectMapper = new ObjectMapper();
    //         String json = objectMapper.writeValueAsString(lastEvaluatedKey);
    //         return Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
    //     } catch (Exception e) {
    //         throw new RuntimeException("Error encoding pagination token", e);
    //     }
    // }
    
    // private Map<String, AttributeValue> decodeNextPageToken(String token) {
    //     try {
    //         // Decode Base64 token and convert from JSON
    //         byte[] decodedBytes = Base64.getDecoder().decode(token);
    //         String json = new String(decodedBytes, StandardCharsets.UTF_8);
            
    //         ObjectMapper objectMapper = new ObjectMapper();
    //         return objectMapper.readValue(json, new TypeReference<Map<String, AttributeValue>>() {});
    //     } catch (Exception e) {
    //         throw new RuntimeException("Error decoding pagination token", e);
    //     }
    // }
}
