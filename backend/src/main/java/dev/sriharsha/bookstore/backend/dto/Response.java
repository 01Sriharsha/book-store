package dev.sriharsha.bookstore.backend.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Response {
    private String message;
    private Object data;
    private Map<String, Object> properties;

    public void setProperty(String property, Object value) {
        properties.put(property, value);
    }
}
