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
public class LanguageCreateDTO implements BaseDTO {
    @NotBlank(message = "Language name should not be null or empty!")
    private String name;
    @NotBlank(message = "Language short name should not be null or empty!")
    private String short_name;
}
