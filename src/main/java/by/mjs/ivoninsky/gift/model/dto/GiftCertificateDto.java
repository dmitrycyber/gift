package by.mjs.ivoninsky.gift.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Integer duration;
    private String createDate;
    private String lastUpdateDate;
    private Set<TagDto> tags;
}
