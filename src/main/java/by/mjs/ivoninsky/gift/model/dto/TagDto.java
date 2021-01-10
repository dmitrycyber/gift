package by.mjs.ivoninsky.gift.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDto implements Serializable {
    private Long id;
    private String name;
    private Set<GiftCertificateDto> gifts;
}
