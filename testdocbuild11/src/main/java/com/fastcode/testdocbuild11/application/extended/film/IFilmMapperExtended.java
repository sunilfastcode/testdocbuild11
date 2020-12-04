package com.fastcode.testdocbuild11.application.extended.film;

import com.fastcode.testdocbuild11.application.core.film.IFilmMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IFilmMapperExtended extends IFilmMapper {}
