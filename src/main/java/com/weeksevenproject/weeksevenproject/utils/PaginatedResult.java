package com.weeksevenproject.weeksevenproject.utils;

import java.util.List;

public class PaginatedResult<T> {
    private final List<T> items;
    private final String nextPageToken;
    
    public PaginatedResult(List<T> items, String nextPageToken) {
        this.items = items;
        this.nextPageToken = nextPageToken;
    }
    
    public List<T> getItems() {
        return items;
    }
    
    public String getNextPageToken() {
        return nextPageToken;
    }
    
    public boolean hasNextPage() {
        return nextPageToken != null && !nextPageToken.isEmpty();
    }
}
