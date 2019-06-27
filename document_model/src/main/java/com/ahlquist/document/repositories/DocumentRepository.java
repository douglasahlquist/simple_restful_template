package com.ahlquist.document.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ahlquist.document.model.Document;

@Repository ("documentRepository")
public interface DocumentRepository extends CrudRepository<Document, String> {
	
	@Modifying
	@Transactional 
    @Query("UPDATE Document d SET d.id = :id, d.metadata = :metadata, d.content = :content, d.length = :length WHERE d.id = :id")
    int updateDocument(@Param("id") String documentId, @Param("metadata") String metadata, 
    		@Param("content") String content, @Param("length") Long length);
	
	
	@Modifying
    @Query("delete from Document d where d.id = :id")
	int deleteByDocumentId(@Param("id") String documentId);

}
