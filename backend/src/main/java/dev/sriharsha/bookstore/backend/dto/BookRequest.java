package dev.sriharsha.bookstore.backend.dto;

import jakarta.validation.constraints.NotEmpty;

public record BookRequest(
        @NotEmpty(message = "Book id cannot be empty")
        Integer id,
        @NotEmpty(message = "Book title cannot be empty")
        String title,

        @NotEmpty(message = "Book author cannot be empty")
        String author,

        @NotEmpty(message = "Book synopsis cannot be empty")
        String synopsis,

        @NotEmpty(message = "Book cover cannot be empty")
        String bookCover,

        @NotEmpty(message = "Book shareable cannot be empty")
        boolean shareable
) {
}
