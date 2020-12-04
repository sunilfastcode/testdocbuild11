package com.fastcode.testdocbuild11.addons.docmgmt.domain.file;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFileManager {
    FileEntity create(FileEntity file);

    void delete(FileEntity file);

    FileEntity update(FileEntity file);

    FileEntity findById(Long id);

    Page<FileEntity> findAll(Predicate predicate, Pageable pageable);
}
