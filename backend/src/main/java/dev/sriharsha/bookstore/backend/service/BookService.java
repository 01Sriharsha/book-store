package dev.sriharsha.bookstore.backend.service;

import dev.sriharsha.bookstore.backend.common.PageResponse;
import dev.sriharsha.bookstore.backend.dto.BookRequest;
import dev.sriharsha.bookstore.backend.dto.BookResponse;
import dev.sriharsha.bookstore.backend.dto.BorrowedBookResponse;
import dev.sriharsha.bookstore.backend.entity.Book;
import dev.sriharsha.bookstore.backend.entity.TransactionHistory;
import dev.sriharsha.bookstore.backend.entity.User;
import dev.sriharsha.bookstore.backend.mapper.BookMapper;
import dev.sriharsha.bookstore.backend.repository.BookRepository;
import dev.sriharsha.bookstore.backend.repository.TransactionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;

    public BookResponse save(Authentication authenticatedUser, BookRequest bookRequest) {
        User user = (User) authenticatedUser.getPrincipal();
        Book book = bookMapper.mapToBook(bookRequest);
        book.setOwner(user);
        Book savedBook = bookRepository.save(book);
        return bookMapper.mapToBookResponse(savedBook);
    }

    public BookResponse getBookById(Integer bookId) {
        return bookRepository.findById(bookId)
                .map(bookMapper::mapToBookResponse)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public PageResponse<BookResponse> getAllBooks(int pageNumber, int size) {
//        User user = (User) authenticatedUser.getPrincipal();
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by("createdAt").descending());
        Page<Book> books = bookRepository.findAll(pageable);
        List<BookResponse> bookResponse = books.stream()
                .map(bookMapper::mapToBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponse,
                books.getNumber(),
                books.getSize(),
                books.getNumberOfElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    public PageResponse<BookResponse> getAllBooksOfOwner(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by("createdAt").descending());
        Page<Book> books = bookRepository.findByOwnerId(0, pageable);
        List<BookResponse> bookResponse = books.stream()
                .map(bookMapper::mapToBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponse,
                books.getNumber(),
                books.getSize(),
                books.getNumberOfElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    public PageResponse<BorrowedBookResponse> getAllBorrowedBooksOfUser(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by("createdAt").descending());
        Page<TransactionHistory> borrowedBooks = transactionHistoryRepository.findByUserId(pageable, 0);
        List<BorrowedBookResponse> borrowedBookResponse = borrowedBooks.stream()
                .map(bookMapper::mapToBorrowedBookResponse)
                .toList();
        return new PageResponse<>(
                borrowedBookResponse,
                borrowedBooks.getNumber(),
                borrowedBooks.getSize(),
                borrowedBooks.getNumberOfElements(),
                borrowedBooks.getTotalPages(),
                borrowedBooks.isFirst(),
                borrowedBooks.isLast()
        );
    }

    public PageResponse<BorrowedBookResponse> getAllReturnedBooks(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by("createdAt").descending());
        Page<TransactionHistory> borrowedBooks = transactionHistoryRepository.findByBookOwnerId(pageable, 0);
        List<BorrowedBookResponse> borrowedBookResponse = borrowedBooks.stream()
                .map(bookMapper::mapToBorrowedBookResponse)
                .toList();
        return new PageResponse<>(
                borrowedBookResponse,
                borrowedBooks.getNumber(),
                borrowedBooks.getSize(),
                borrowedBooks.getNumberOfElements(),
                borrowedBooks.getTotalPages(),
                borrowedBooks.isFirst(),
                borrowedBooks.isLast()
        );
    }
}
