package dev.sriharsha.bookstore.backend.controllers;

import dev.sriharsha.bookstore.backend.common.PageResponse;
import dev.sriharsha.bookstore.backend.dto.BookRequest;
import dev.sriharsha.bookstore.backend.dto.BookResponse;
import dev.sriharsha.bookstore.backend.dto.BorrowedBookResponse;
import dev.sriharsha.bookstore.backend.dto.Response;
import dev.sriharsha.bookstore.backend.service.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Tag(name = "Book")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookRequest bookRequest, Authentication authenticatedUser) {
        BookResponse bookResponse = bookService.save(authenticatedUser, bookRequest);
        Response response = Response.builder()
                .message("Book Saved Successfully")
                .data(bookResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable Integer bookId) {
        BookResponse bookResponse = bookService.getBookById(bookId);
        Response response = Response.builder()
                .message("Book fetched Successfully")
                .data(bookResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        PageResponse<BookResponse> books = bookService.getAllBooks(pageNumber, size);
        Response response = Response.builder()
                .message("Book fetched Successfully")
                .data(books)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/owner")
    public ResponseEntity<?> getAllBooksOfOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        PageResponse<BookResponse> books = bookService.getAllBooks(pageNumber, size);
        Response response = Response.builder()
                .message("Book fetched Successfully")
                .data(books)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/borrowed")
    public ResponseEntity<?> getAllBorrowedBooksOfUser(
            @RequestParam(name = "page", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        PageResponse<BorrowedBookResponse> allBorrowedBooksOfUser = bookService
                .getAllBorrowedBooksOfUser(pageNumber, size);
        Response response = Response.builder()
                .message("Borrowed Book fetched Successfully")
                .data(allBorrowedBooksOfUser)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/returned")
    public ResponseEntity<?> getAllReturnedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        PageResponse<BorrowedBookResponse> allReturnedBooks = bookService
                .getAllReturnedBooks(pageNumber, size);
        Response response = Response.builder()
                .message("Returned Book fetched Successfully")
                .data(allReturnedBooks)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK); 
    }

}
