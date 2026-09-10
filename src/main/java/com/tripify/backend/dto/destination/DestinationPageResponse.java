package com.tripify.backend.dto.destination;

import java.util.List;

public class DestinationPageResponse {

    private List<DestinationResponse> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public DestinationPageResponse(
            List<DestinationResponse> content,
            int page,
            int size,
            long totalElements,
            int totalPages) {

        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<DestinationResponse> getContent() {
        return content;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }
}