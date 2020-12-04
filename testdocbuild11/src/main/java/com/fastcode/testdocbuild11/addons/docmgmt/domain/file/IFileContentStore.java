package com.fastcode.testdocbuild11.addons.docmgmt.domain.file;

import org.springframework.content.commons.repository.ContentStore;
import org.springframework.content.rest.StoreRestResource;

@StoreRestResource(path = "file")
public interface IFileContentStore extends ContentStore<FileEntity, String> {}
