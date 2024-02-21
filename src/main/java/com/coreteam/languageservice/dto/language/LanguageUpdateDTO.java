package com.coreteam.languageservice.dto.language;

import com.coreteam.languageservice.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class LanguageUpdateDTO implements BaseDTO {
    private Integer language_id;
    @NotBlank(message = "Language Name should not be null or empty!")
    private String name;
    @NotBlank(message = "Language Short Name should not be null or empty!")
    private String short_name;
}
