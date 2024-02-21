package com.coreteam.languageservice.model.modul;

import com.coreteam.languageservice.model.BaseEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ModulDB implements BaseEntity {
    private Integer id;
    private String name;
    private Integer status;
}
