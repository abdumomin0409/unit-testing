package com.coreteam.languageservice.dto.modul;

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
public class ModulUpdateDTO implements BaseDTO {
    private Integer modul_id;
    @NotBlank(message = "Modul Name should not be null or empty!")
    private String name;
}
