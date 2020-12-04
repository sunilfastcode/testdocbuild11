package com.fastcode.testdocbuild11.addons.docmgmt.domain.file;

import com.querydsl.core.types.Predicate;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component("fileManager")
public class FileManager implements IFileManager {

    @Autowired
    @Qualifier("fileRepository")
    protected IFileRepository _fileRepository;

    public FileEntity create(FileEntity file) {
        return _fileRepository.save(file);
    }

    public void delete(FileEntity file) {
        _fileRepository.delete(file);
    }

    public FileEntity update(FileEntity file) {
        return _fileRepository.save(file);
    }

    public FileEntity findById(Long fileId) {
        Optional<FileEntity> dbFile = _fileRepository.findById(fileId);
        return dbFile.orElse(null);
    }

    public Page<FileEntity> findAll(Predicate predicate, Pageable pageable) {
        return _fileRepository.findAll(predicate, pageable);
    }
}
