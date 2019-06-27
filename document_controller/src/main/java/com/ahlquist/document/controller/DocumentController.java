package com.ahlquist.document.controller;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ahlquist.document.ex.services.DocumentService;
import com.ahlquist.document.model.Document;
import com.ahlquist.document.utils.WebUtils;
import com.google.common.net.MediaType;

@RestController("documentController")
@RequestMapping(value = "/storage/documents/")
public class DocumentController {

	final static Logger logger = Logger.getLogger(DocumentController.class);

	@Autowired
	@Qualifier("documentService")
	DocumentService documentService;

	private Map<String, Object> getHeaders(HttpServletRequest request) {
		return WebUtils.getHeadersInfo(request);
	}

	/**
	 * Query - GET /storage/documents/{documentId} <code>
	Request URL:
	  GET /storage/documents/ONWZ4UUVV8S31JCB662P
	
	Response Code:
	  200 OK
	Response Header:
	  Content-Length: 11
	Response Body
	  hello world
	</code>
	 * 
	 * @param documentId
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping(value = "{documentId}", method = RequestMethod.GET, produces = { "*/*" })
	@ResponseBody
	ResponseEntity<String> get(@PathVariable("documentId") final String documentId) {

		logger.debug("documentId: " + documentId);
		Optional<Document> oDocument = this.documentService.get(documentId);

		if (oDocument.isPresent()) {
			Document d = oDocument.get();
			// HttpHeaders headers = WebUtils.getResponseHeaders(d);
			return new ResponseEntity<String>(d.getContent(), /* headers, */ HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Create - POST /storage/documents <code>
	Request URL:
	  POST /storage/documents
	Reqest Header:
	  Content-Length: 11
	Request Body:
	  hello world
	
	Response Code:
	  201 Created
	Response Header:
	  Content-Type: text/plain; charset=us-ascii
	  Content-Length: 20
	Response Body:
	  ONWZ4UUVV8S31JCB662P
	</code>
	 * 
	 * @param map
	 * @param response
	 * @return
	 */

	@RequestMapping(method = { RequestMethod.POST }, consumes = { "*/*" }, // text/plain; UTF-8", "text/plain;
																			// charset=us-ascii" },
			produces = { "text/plain; charset=us-ascii" })
	@ResponseBody
	public ResponseEntity<String> create(@RequestBody String content, HttpServletRequest request) {

		logger.debug("content: " + content);

		Map<String, Object> headers = this.getHeaders(request);

		Optional<Document> oDocument = documentService.create(content, headers);

		if (oDocument.isPresent()) {
			Document d = oDocument.get();
			logger.debug("created document: " + d.toString());
			return new ResponseEntity<String>(d.getId(), /* WebUtils.getResponseHeaders(d), */ HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Update - PUT /storage/documents/{docId} <code>
	Request URL:
	  POST /storage/documents
	Request Header:
	  Content-Length: 11
	Request Body:
	  hello world
	
	Response Code:
	  201 Created
	Response Header:
	  Content-Type: text/plain; charset=us-ascii
	  Content-Length: 20
	Response Body:
	  ONWZ4UUVV8S31JCB662P
	</code>
	 * 
	 * @param map
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "{documentId}", method = { RequestMethod.PUT }, consumes = { "*/*" }, produces = { "*/*" })
	@ResponseBody
	public ResponseEntity<String> update(@PathVariable("documentId") final String documentId,
			@RequestBody String content, HttpServletRequest request) {

		Map<String, Object> headers = this.getHeaders(request);
		logger.debug("documentId: " + documentId + " content: " + content + " headers: " + headers.toString());

		int count = documentService.update(documentId, content, headers);
		if (count > 0) {
			HttpHeaders httpHeader = new HttpHeaders();
			httpHeader.add("content-type", (String) headers.get("content-type"));
			httpHeader.add("content-length", Integer.toString(content.length()));

			logger.debug("document update count: " + count);
			return new ResponseEntity<String>(documentId, httpHeader, HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Delete - DELETE /storage/documents/{documentId}
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "{documentId}", method = { RequestMethod.DELETE })
	@ResponseBody
	public ResponseEntity<String> delete(@PathVariable("documentId") final String documentId) {

		logger.debug("documentId: " + documentId);
		
		if (this.documentService.delete(documentId)>0) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		} else {
			// This is not documented in the requirements, but if on success NO_CONTENT is
			// return,
			// on failure should return BAD_REQUEST
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
}
