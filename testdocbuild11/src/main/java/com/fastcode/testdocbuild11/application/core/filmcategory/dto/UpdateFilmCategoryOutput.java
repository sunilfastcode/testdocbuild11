package com.fastcode.testdocbuild11.application.core.filmcategory.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFilmCategoryOutput {

    private Short categoryId;
    private Short filmId;
    private LocalDateTime lastUpdate;
    private Integer categoryDescriptiveField;
    private Integer filmDescriptiveField;
}
