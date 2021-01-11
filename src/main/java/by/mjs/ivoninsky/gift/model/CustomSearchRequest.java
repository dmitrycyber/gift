package by.mjs.ivoninsky.gift.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomSearchRequest implements Serializable {
    private String name;
    private String type;

    private Integer priceFrom;
    private Integer priceTo;

    private Integer durationFrom;
    private Integer durationTo;

    private String createDateFrom;
    private String createDateTo;
}
