package dev.sriharsha.bookstore.backend.service;

import dev.sriharsha.bookstore.backend.common.PageResponse;
import dev.sriharsha.bookstore.backend.dto.FeedbackRequest;
import dev.sriharsha.bookstore.backend.dto.FeedbackResponse;
import dev.sriharsha.bookstore.backend.entity.Book;
import dev.sriharsha.bookstore.backend.entity.Feedback;
import dev.sriharsha.bookstore.backend.entity.User;
import dev.sriharsha.bookstore.backend.exception.ResourceNotFoundException;
import dev.sriharsha.bookstore.backend.mapper.FeedbackMapper;
import dev.sriharsha.bookstore.backend.repository.BookRepository;
import dev.sriharsha.bookstore.backend.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackService {
    private final BookRepository bookRepository;
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    public Integer saveFeedback(Authentication authenticatedUser, Integer bookId, FeedbackRequest feedbackRequest) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID::" + bookId));
        User user = (User) authenticatedUser.getPrincipal();
        Feedback feedback = Feedback.builder()
                .message(feedbackRequest.message())
                .rating(feedbackRequest.rating())
                .book(book)
                .build();
        return feedbackRepository.save(feedback).getId();
    }

    public PageResponse<FeedbackResponse> getAllFeedbacksOfBook(Authentication authenticatedUser, Integer bookId, int pageNumber, int size) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID::" + bookId));
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<Feedback> feedbacks = feedbackRepository.findByBookId(pageable, book.getId());
        List<FeedbackResponse> feedbackResponse = feedbacks.stream()
                .map(feedbackMapper::mapToFeedbackResponse)
                .toList();
        return new PageResponse<>(
                feedbackResponse,
                feedbacks.getNumber(),
                feedbacks.getSize(),
                feedbacks.getNumberOfElements(),
                feedbacks.getTotalPages(),
                feedbacks.isFirst(),
                feedbacks.isLast()
        );
    }
}
