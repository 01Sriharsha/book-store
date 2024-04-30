package dev.sriharsha.bookstore.backend.dto;

import lombok.Builder;

@Builder
public record BorrowedBookResponse(
        Integer id,
        String title,
        String author,
        Double rating,
        boolean returned,
        boolean returnApproved
) {
}
