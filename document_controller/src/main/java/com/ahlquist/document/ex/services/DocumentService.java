package com.ahlquist.document.ex.services;

import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ahlquist.document.controller.WebUtils;
import com.ahlquist.document.model.Document;
import com.ahlquist.document.repositories.DocumentRepository;
import com.ahlquist.document.services.BaseService;
import com.ahlquist.document.services.IBaseService;
import com.ahlquist.document.utils.RandomStr;

@Service
public class DocumentService extends BaseService<DocumentRepository, Document, String>
		implements IBaseService<Document, String> {

	final static Logger logger = Logger.getLogger(DocumentService.class);

	@Autowired
	public DocumentService( @Qualifier("documentRepository") final DocumentRepository repository) {
		super(repository);
	}
	
	public final static int UUID_LENGTH = 20;

	/**
	 * Utility method used to generate a unique Id;
	 * @return
	 */
	protected String generateUUID() {
		return RandomStr.generateUUID(UUID_LENGTH);
	}

	/**
	 * Creates and causes to persists a new Document
	 * @param documentId - the documentId
	 * @param map        - a map<String, String> of the HttpHeaders
	 * @return - String 'id' of the newly presisted Document
	 */
	public Optional<Document> create(final String content, Map<String, Object> header) {

		if (content == null) {
			return Optional.empty();
		}

		Document d = new Document();
		String contentType = (String) header.get(WebUtils.CONTENT_TYPE);
		Long contentLength = Long.getLong((String)header.get(WebUtils.CONTENT_LENGTH));

		d.setId(this.generateUUID());
		if (contentType != null) {
			// TODO (dahlquist): use the method in WebUtil to retrieve the metadata
			d.setMetadata(contentType);
		} else {
			d.setLength(contentLength);
			d.setContent(content);
		}
		this.getRepository().save(d);

		return Optional.of(d);
	}

	/**
	 * Retrieves an Optional<Document> from a 'documentId'.  If not match is found an Optional with null is
	 *     is returned.
	 * @param documentId
	 * @return The Optional wrapper
	 */
	public Optional<Document> get(final String documentId) {
		return this.getRepository().findById(documentId);
	}

	
	/**
	 * Updates the 'Document' matching the documentId fields
	 * @param documentId - the unique documentId used to query for the document to update 
	 * @param content - the body of the Http request from which the document will be updated
	 * @param header - the Http Header (as a Map) from which the metadata is retrieved
	 * @return - The Optional containing a possible 'Document'
	 */
	public Optional<Document> update(final String documentId, final String content, final Map<String, Object> header) {

		String contentType = (String) header.get(WebUtils.CONTENT_TYPE);
		Long contentLength = (Long) header.get(WebUtils.CONTENT_LENGTH);

		Optional<Document> oDocument = this.getRepository().findById(documentId);
		if (oDocument.isPresent()) {
			Document d = oDocument.get();
			d.setLength(contentLength);
			d.setContent(content);
			d.setMetadata(contentType);

			getRepository().save(d);
			return Optional.of(d);
		}
		return Optional.empty();
	}

	/**
	 * Deletes the 'Document' from the data store if exists
	 * @param documentId
	 * @return true if found and delete, false if not found
	 */
	public boolean delete(final String documentId) {
		Optional<Document> oDocument = this.getRepository().findById(documentId);
		if (oDocument.isPresent()) {
			Document d = oDocument.get();
			getRepository().delete(d);
			return true;
		}
		return false;
	}

}
