package com.fastcode.testdocbuild11.application.core.film.dto;

import java.math.BigDecimal;
import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UpdateFilmInput {

    private String description;

    @NotNull(message = "filmId Should not be null")
    private Integer filmId;

    @NotNull(message = "lastUpdate Should not be null")
    private LocalDateTime lastUpdate;

    private Short length;

    private String rating;

    private Integer releaseYear;

    @NotNull(message = "rentalDuration Should not be null")
    private Short rentalDuration;

    @NotNull(message = "rentalRate Should not be null")
    private BigDecimal rentalRate;

    @NotNull(message = "replacementCost Should not be null")
    private BigDecimal replacementCost;

    @NotNull(message = "title Should not be null")
    @Length(max = 255, message = "title must be less than 255 characters")
    private String title;

    private Short languageId;
    private Long versiono;
}
