package com.coreteam.languageservice.model.foreign_lang;

import com.coreteam.languageservice.model.BaseEntity;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForeignLangDB implements BaseEntity {
    private Integer id;
    private String tConst;
    private Integer modulID;
    private String lang;
    private String langShort;
    private String text;
    private String description;
    private LocalDateTime createdDatetime;
    private Integer createdUserId;
    private Integer status;
}
