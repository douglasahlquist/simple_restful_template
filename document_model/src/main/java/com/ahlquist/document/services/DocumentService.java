package com.ahlquist.document.services;

import java.util.Optional;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;

import com.ahlquist.document.model.Document;
import com.ahlquist.document.repositories.DocumentRepository;

public class DocumentService extends BaseService<DocumentRepository, Document, Long>
		implements IBaseService<Document, Long> {

	final static Logger logger = Logger.getLogger(DocumentService.class);

	@Autowired
	public DocumentService(@Qualifier("documentRepository") final DocumentRepository repository) {
		super(repository);
		// TODO Auto-generated constructor stub
	}

}
