package dev.sriharsha.bookstore.backend.mapper;

import dev.sriharsha.bookstore.backend.dto.FeedbackRequest;
import dev.sriharsha.bookstore.backend.dto.FeedbackResponse;
import dev.sriharsha.bookstore.backend.entity.Feedback;
import org.springframework.stereotype.Service;

@Service
public class FeedbackMapper {

    public Feedback mapToFeedback(FeedbackRequest feedbackRequest) {
        return Feedback.builder()
                .message(feedbackRequest.message())
                .rating(feedbackRequest.rating())
                .build();
    }

    public FeedbackResponse mapToFeedbackResponse(Feedback feedback) {
        return FeedbackResponse.builder()
                .id(feedback.getId())
                .message(feedback.getMessage())
                .rating(feedback.getRating())
                .bookId(feedback.getBook().getId())
                .bookTitle(feedback.getBook().getTitle())
                .build();
    }
}
