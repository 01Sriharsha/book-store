package dev.sriharsha.bookstore.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BookRequest(

        Integer id,
        @NotEmpty(message = "Book title cannot be empty")
        String title,
        @NotEmpty(message = "Book author cannot be empty")
        String author,
        @NotEmpty(message = "Book synopsis cannot be empty")
        String synopsis,
        String bookCover,
        @NotNull(message = "Book shareable status cannot be empty")
        boolean shareable
) {
}
