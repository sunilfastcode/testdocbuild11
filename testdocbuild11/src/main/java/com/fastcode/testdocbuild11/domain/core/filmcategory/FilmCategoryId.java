package com.fastcode.testdocbuild11.domain.core.filmcategory;

import java.io.Serializable;
import java.time.*;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FilmCategoryId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Short categoryId;
    private Short filmId;

    public FilmCategoryId(Short categoryId, Short filmId) {
        this.categoryId = categoryId;
        this.filmId = filmId;
    }
}
