package by.mjs.ivoninsky.gift.dao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryArgModel implements Serializable {
    private String query;
    private Object[] args;
}
