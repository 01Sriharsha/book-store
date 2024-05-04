package dev.sriharsha.bookstore.backend.controllers;

import dev.sriharsha.bookstore.backend.common.PageResponse;
import dev.sriharsha.bookstore.backend.dto.FeedbackRequest;
import dev.sriharsha.bookstore.backend.dto.FeedbackResponse;
import dev.sriharsha.bookstore.backend.dto.Response;
import dev.sriharsha.bookstore.backend.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping("/book/{bookId}")
    public ResponseEntity<?> saveFeedback(
            Authentication authenticatedUser,
            @PathVariable Integer bookId,
            @RequestBody FeedbackRequest feedbackRequest
    ) {
        Integer id = feedbackService.saveFeedback(authenticatedUser, bookId, feedbackRequest);
        Response response = Response.builder()
                .message("Feedback saved successfully")
                .data(id)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<?> getAllFeedbacksOfBook(
            Authentication authenticatedUser,
            @PathVariable Integer bookId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        PageResponse<FeedbackResponse> allFeedbacksOfBook = feedbackService
                .getAllFeedbacksOfBook(authenticatedUser, bookId, pageNumber, size);
        Response response = Response.builder()
                .message("Feedback saved successfully")
                .data(allFeedbacksOfBook)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{feedbackId}")
    public ResponseEntity<?> updateFeedback(
            Authentication authenticatedUser,
            @PathVariable Integer feedbackId,
            @RequestBody FeedbackRequest feedbackRequest
    ) {
        Integer id = feedbackService.updateFeedback(authenticatedUser, feedbackId, feedbackRequest);
        Response response = Response.builder()
                .message("Feedback updated successfully")
                .data(id)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<?> deleteFeedback(
            Authentication authenticatedUser,
            @PathVariable Integer feedbackId
    ) {
        feedbackService.deleteFeedback(authenticatedUser, feedbackId);
        Response response = Response.builder()
                .message("Feedback deleted successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
