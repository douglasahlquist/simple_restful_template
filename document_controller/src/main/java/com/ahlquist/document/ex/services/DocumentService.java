package com.ahlquist.document.ex.services;

import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ahlquist.document.model.Document;
import com.ahlquist.document.repositories.DocumentRepository;
import com.ahlquist.document.services.BaseService;
import com.ahlquist.document.services.IBaseService;
import com.ahlquist.document.utils.RandomStr;
import com.ahlquist.document.utils.WebUtils;

@Service
public class DocumentService extends BaseService<DocumentRepository, Document, String>
		implements IBaseService<Document, String> {

	final static Logger logger = Logger.getLogger(DocumentService.class);

	@Autowired
	public DocumentService(@Qualifier("documentRepository") final DocumentRepository repository) {
		super(repository);
	}

	public final static int UUID_LENGTH = 20;

	/**
	 * Utility method used to generate a unique Id;
	 * 
	 * @return
	 */
	protected String generateUUID() {
		return RandomStr.generateUUID(UUID_LENGTH);
	}

	/**
	 * Creates and causes to persists a new Document
	 * 
	 * @param documentId - the documentId
	 * @param map        - a map<String, String> of the HttpHeaders
	 * @return - String 'id' of the newly presisted Document
	 */
	public Optional<Document> create(final String content, Map<String, Object> header) {

		if (content == null) {
			return Optional.empty();
		}

		String contentType = null;
		Long contentLength = null;
		if (header != null) {
			logger.debug("headers: " + header.toString());
			contentType = (String) header.get(WebUtils.CONTENT_TYPE);
			contentLength = Long.getLong((String) header.get(WebUtils.CONTENT_LENGTH));
		}

		Document d = new Document();
		d.setId(this.generateUUID());
		// TODO (dahlquist): use the method in WebUtil to retrieve the metadata
		d.setMetadata(contentType);
		if (contentLength == null) {
			d.setLength((long) content.length());
		} else {
			d.setLength(contentLength);
		}
		d.setContent(content);

		logger.debug("Document to save: " + d.toString());
		this.getRepository().save(d);

		return Optional.of(d);
	}

	/**
	 * Retrieves an Optional<Document> from a 'documentId'. If not match is found an
	 * Optional with null is is returned.
	 * 
	 * @param documentId
	 * @return The Optional wrapper
	 */
	public Optional<Document> get(final String documentId) {
		return this.getRepository().findById(documentId);
	}

	/**
	 * Updates the 'Document' matching the documentId fields
	 * 
	 * @param documentId - the unique documentId used to query for the document to
	 *                   update
	 * @param content    - the body of the Http request from which the document will
	 *                   be updated
	 * @param header     - the Http Header (as a Map) from which the metadata is
	 *                   retrieved
	 * @return - int count of the number of documents updated. Since the ids are unique, the count should 
	 *           be either 1 or 0
	 */
	public int update(final String documentId, final String content, final Map<String, Object> header) {

		String contentType = (String) header.get(WebUtils.CONTENT_TYPE);
		String lengthStr = (String)header.get(WebUtils.CONTENT_LENGTH);
		logger.debug("Content-Type: " + contentType + " COntent-Length: " + lengthStr);
		Long contentLength = Long.valueOf(lengthStr);

		return this.getRepository().updateDocument(documentId, contentType, content, contentLength);
	}

	/**
	 * Deletes the 'Document' from the data store if exists
	 * 
	 * @param documentId
	 * @return true if found and delete, false if not found
	 */
	public int delete(final String documentId) {
		return this.getRepository().deleteByDocumentId(documentId);
	}

}
