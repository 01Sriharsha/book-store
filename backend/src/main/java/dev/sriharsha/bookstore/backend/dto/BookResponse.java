package dev.sriharsha.bookstore.backend.dto;

import lombok.Builder;
@Builder
public record BookResponse(
        Integer id,
        String title,
        String author,
        String synopsis,
        byte[] bookCover,
        boolean shareable,
        boolean archived,
        Integer userId,
        String username,
        Double rating
) {
}
