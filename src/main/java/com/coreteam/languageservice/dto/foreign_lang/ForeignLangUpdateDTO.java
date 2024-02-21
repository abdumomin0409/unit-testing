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
public class ForeignLangUpdateDTO implements BaseDTO {

    private Integer foreign_language_id;
    private Integer modul_id;
    @NotBlank(message = "Constanta should not be null or empty!")
    private String t_const;
    @NotBlank(message = "Language Name should not be null or empty!")
    private String lang;
    @NotBlank(message = "Text should not be null or empty!")
    private String text;
    private String description;
    private Integer created_user_id;

}

