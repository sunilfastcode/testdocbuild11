package com.fastcode.testdocbuild11.application.core.filmcategory.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFilmCategoryInput {

    @NotNull(message = "categoryId Should not be null")
    private Short categoryId;

    @NotNull(message = "filmId Should not be null")
    private Short filmId;

    @NotNull(message = "lastUpdate Should not be null")
    private LocalDateTime lastUpdate;
}
