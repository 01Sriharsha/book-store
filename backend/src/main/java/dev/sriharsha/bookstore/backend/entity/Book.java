package dev.sriharsha.bookstore.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String author;

    private String synopsis;

    private String bookCover;

    private boolean archived;

    private boolean shareable;

    @ManyToOne
    private User owner;

    @OneToMany(mappedBy = "book")
    private List<Feedback> feedbacks;

    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<TransactionHistory> histories;

    @Transient
    public double getRating() {
        if (feedbacks.size() == 0) {
            return 0.0;
        } else {
            double rate = this.feedbacks.stream()
                    .mapToDouble(Feedback::getRating)
                    .average()
                    .orElse(0.0);
            //5.23 --> 5.0
            return Math.round(rate * 10.0) / 10.0;
        }
    }
}
