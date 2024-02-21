package com.coreteam.languageservice.model.language;

import com.coreteam.languageservice.model.BaseEntity;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanguageDB implements BaseEntity {
    private Integer id;
    private String name;
    private String shortName;
    private Integer status;
}
