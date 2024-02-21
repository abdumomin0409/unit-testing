package com.coreteam.languageservice.dto.foreign_lang;

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
public class ForeignLangCreateDTO implements BaseDTO {
    @NotBlank(message = "Constanta should not be null or empty!")
    private String t_const;
    private Integer modul_id;
    @NotBlank(message = "Language name should not be null or empty!")
    private String lang;
    @NotBlank(message = "Text should not be null or empty!")
    private String text;
    private String description;
    private Integer created_user_id;
}
