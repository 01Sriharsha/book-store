package dev.sriharsha.bookstore.backend.service;

import dev.sriharsha.bookstore.backend.common.PageResponse;
import dev.sriharsha.bookstore.backend.dto.BookRequest;
import dev.sriharsha.bookstore.backend.dto.BookResponse;
import dev.sriharsha.bookstore.backend.dto.BorrowedBookResponse;
import dev.sriharsha.bookstore.backend.entity.Book;
import dev.sriharsha.bookstore.backend.entity.TransactionHistory;
import dev.sriharsha.bookstore.backend.entity.User;
import dev.sriharsha.bookstore.backend.exception.OperationNotPermittedException;
import dev.sriharsha.bookstore.backend.exception.ResourceNotFoundException;
import dev.sriharsha.bookstore.backend.mapper.BookMapper;
import dev.sriharsha.bookstore.backend.repository.BookRepository;
import dev.sriharsha.bookstore.backend.repository.TransactionHistoryRepository;
import dev.sriharsha.bookstore.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;
    private final UserRepository userRepository;

    private final FileService fileService;

    public BookResponse save(Authentication authenticatedUser, BookRequest bookRequest) {
        User user = (User) authenticatedUser.getPrincipal();
        log.info("Current logged-in User:- {}, {} , {}", user.getId(),
                user.getFullname(), user.getEmail());
        if (bookRepository.existsByTitle(bookRequest.title())) {
            throw new OperationNotPermittedException("A book with same title already exists");
        }
        Book book = bookMapper.mapToBook(bookRequest);
        book.setOwner(user);
        Book savedBook = bookRepository.save(book);
        return bookMapper.mapToBookResponse(savedBook);
    }

    public BookResponse getBookById(Integer bookId) {
        return bookRepository.findById(bookId)
                .map(bookMapper::mapToBookResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID::" + bookId));
    }

    public PageResponse<BookResponse> getAllBooks(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by("createdAt")
                .descending());
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

    //Get all books that can be displayed and exclude the current logged-in user books
    public PageResponse<BookResponse> getAllDisplayBooks(
            Authentication authenticatedUser, int pageNumber, int size
    ) {
        User currentUser = (User) authenticatedUser.getPrincipal();
        log.info("Current logged-in User:-{}, {} , {}", currentUser.getId(),
                currentUser.getFullname(), currentUser.getEmail());
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by("createdAt")
                .descending());
        Page<Book> books = bookRepository
                .findByOwnerIdNotAndShareableIsTrue(currentUser.getId(), pageable);
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

    //Get all books of current logged-in user
    public PageResponse<BookResponse> getAllBooksOfOwner(
            Authentication authenticatedUser, int pageNumber, int size
    ) {
        User currentUser = (User) authenticatedUser.getPrincipal();
        log.info("Current logged-in User:- {} , {}", currentUser.getFullname(), currentUser.getEmail());
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by("createdAt").descending());
        Page<Book> books = bookRepository.findByOwnerId(currentUser.getId(), pageable);
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

    //Get all borrowed books of current logged-in user
    public PageResponse<BorrowedBookResponse> getAllBorrowedBooksOfOwner(
            Authentication authenticatedUser, int pageNumber, int size
    ) {
        User currentUser = (User) authenticatedUser.getPrincipal();
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by("createdAt")
                .descending());
        Page<TransactionHistory> borrowedBooks = transactionHistoryRepository
                .findByUserId(pageable, currentUser.getId());
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

    //Get all returned books of current logged-in user
    public PageResponse<BorrowedBookResponse> getAllReturnedBooksOfOwner(
            Authentication authenticatedUser, int pageNumber, int size
    ) {
        User currentUser = (User) authenticatedUser.getPrincipal();
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by("createdAt").descending());
        Page<TransactionHistory> borrowedBooks = transactionHistoryRepository
                .findByBookOwnerId(pageable, currentUser.getId());
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

    public Integer updateShareableStatus(Authentication authenticatedUser, Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID::" + bookId));
        User user = (User) authenticatedUser.getPrincipal();
        if (!Objects.equals(user.getId(), book.getOwner().getId())) {
            throw new OperationNotPermittedException("You cannot modify book information!");
        }
        book.setShareable(!book.isShareable());
        bookRepository.save(book);
        return bookId;
    }

    public Integer updateArchiveStatus(Authentication authenticatedUser, Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID::" + bookId));
        User user = (User) authenticatedUser.getPrincipal();
        if (!Objects.equals(user.getId(), book.getOwner().getId())) {
            throw new OperationNotPermittedException("You cannot modify book information!");
        }
        book.setShareable(!book.isArchived());
        bookRepository.save(book);
        return bookId;
    }

    public Integer borrowBook(Authentication authenticatedUser, Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID::" + bookId));
        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("Requested cannot be borrowed! Since it is not shareable");
        }
        User user = (User) authenticatedUser.getPrincipal();
        //if owner is same as user
        if (Objects.equals(user.getId(), book.getOwner().getId())) {
            throw new OperationNotPermittedException("You cannot borrow your own book");
        }
        final boolean isAlreadyBorrowed = transactionHistoryRepository
                .existsByBookIdAndUserId(book.getId(), user.getId());
        if (isAlreadyBorrowed) {
            throw new OperationNotPermittedException("The book is already borrowed");
        }
        TransactionHistory newHistory = TransactionHistory.builder()
                .user(user)
                .book(book)
                .returned(false)
                .returnApproved(false)
                .build();
        return transactionHistoryRepository.save(newHistory).getId();
    }

    public Integer returnBorrowedBook(Authentication authenticatedUser, Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID::" + bookId));
        User user = (User) authenticatedUser.getPrincipal();
        TransactionHistory borrowedHistory = transactionHistoryRepository.findByBookIdAndUserId(book.getId(), user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No book is borrowed to return"));
        if (borrowedHistory.getReturned() && borrowedHistory.getReturnApproved()) {
            throw new OperationNotPermittedException("Book is already returned and approved");
        } else if (borrowedHistory.getReturned()) {
            throw new OperationNotPermittedException("Book is already returned");
        } else {
            borrowedHistory.setReturned(true);
        }
        return transactionHistoryRepository.save(borrowedHistory).getId();
    }

    public Integer approveReturnedBook(Authentication authenticatedUser, Integer bookId, Integer borrowedUserId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID::" + bookId));
        User user = (User) authenticatedUser.getPrincipal();
        //if not owner throw exception
        if (!Objects.equals(user.getId(), book.getOwner().getId())) {
            throw new OperationNotPermittedException("You cannot modify book information");
        }
        User borrowedUser = userRepository.findById(borrowedUserId)
                .orElseThrow(() -> new UsernameNotFoundException("Borrowed user not found"));
        TransactionHistory borrowedHistory = transactionHistoryRepository.findByBookIdAndUserId(book.getId(), borrowedUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No book is borrowed to return"));
        if (!borrowedHistory.getReturned()) {
            throw new OperationNotPermittedException("The Book is not yet returned to approve");
        } else if (borrowedHistory.getReturnApproved()) {
            throw new OperationNotPermittedException("The Book is already approved");
        } else {
            borrowedHistory.setReturnApproved(true);
        }
        return transactionHistoryRepository.save(borrowedHistory).getId();
    }

    public Integer uploadBookCoverImage(Authentication authenticatedUser, Integer bookId, MultipartFile file) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID::" + bookId));
        User user = (User) authenticatedUser.getPrincipal();
        String bookCoverImage = fileService.save(user.getId(), file);
        book.setBookCover(bookCoverImage);
        return bookRepository.save(book).getId();
    }
}
