package dev.sriharsha.bookstore.backend.dto;

import lombok.Builder;

@Builder
public record FeedbackResponse(
        Integer id,
        String message,
        Double rating,
        String bookTitle,
        Integer bookId
) {
}
