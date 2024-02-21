package com.coreteam.languageservice.model.status;

import com.coreteam.languageservice.model.BaseEntity;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusDB implements BaseEntity {
    private Integer id;
    private String name;
}
