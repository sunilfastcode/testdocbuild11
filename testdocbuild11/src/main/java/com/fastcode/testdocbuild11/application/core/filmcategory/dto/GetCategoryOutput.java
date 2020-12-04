package com.fastcode.testdocbuild11.application.core.filmcategory.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCategoryOutput {

    private Integer categoryId;
    private LocalDateTime lastUpdate;
    private String name;
    private Short filmCategoryCategoryId;
    private Short filmCategoryFilmId;
}
