package dev.sriharsha.bookstore.backend.common;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> {

    private List<T> content;
    private int pageNumber;
    private int size;
    private int totalItems;
    private int totalPages;
    private boolean first;
    private boolean last;
}
