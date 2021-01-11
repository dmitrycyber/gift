package by.mjs.ivoninsky.gift.model;

import by.mjs.ivoninsky.gift.util.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse<T> implements Serializable {
    private T message;
    private String comment;
    private int code;
}
