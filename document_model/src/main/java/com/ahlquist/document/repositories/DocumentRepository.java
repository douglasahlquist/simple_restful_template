package com.ahlquist.document.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ahlquist.document.model.Document;

@Repository ("documentRepository")
public interface DocumentRepository extends CrudRepository<Document, String> {

}
