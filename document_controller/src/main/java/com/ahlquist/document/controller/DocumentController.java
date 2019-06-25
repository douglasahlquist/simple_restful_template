package com.ahlquist.document.controller;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ahlquist.document.ex.services.DocumentExService;
import com.ahlquist.document.model.Document;


@RestController("documentController")
@RequestMapping(value = "/storage/documents/")
public class DocumentController {

    final static Logger logger = Logger.getLogger(DocumentController.class);
    
	@Autowired
	private HttpServletRequest request;

    @Autowired
    @Qualifier("documentService")
    DocumentExService documentExService;
    
    private Map<String, Object> getHeaders(){
    	return WebUtils.getHeadersInfo(request);
    }

    /** Query  - GET    /storage/documents/{documentId} 
<code>
Request URL:
  GET /storage/documents/ONWZ4UUVV8S31JCB662P

Response Code:
  200 OK
Response Header:
  Content-Length: 11
Response Body
  hello world
</code>
     * @param documentId
     * @return
     * @throws JSONException
     */
    @RequestMapping(value = "{documentId}", method = RequestMethod.GET, produces= {"*/*"})
    @ResponseBody
    ResponseEntity<String> get(@PathVariable final String documentId) throws JSONException {

    	Optional<Document> oDocument = this.documentExService.get(documentId);
    	if(oDocument.isPresent()) {
    		Document d = oDocument.get();
    		HttpHeaders headers = WebUtils.getResponseHeaders(d);
    		return new ResponseEntity<String>(d.getContent(), headers, HttpStatus.OK);
    	}
        return new ResponseEntity<String>("result",  HttpStatus.NOT_FOUND);
    }

    /** Create - POST   /storage/documents
<code>
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
     * @param map
     * @param response
     * @return
     */

    @RequestMapping(method = { RequestMethod.POST}, consumes= {"*/*"}, produces= {"*/*"})
    @ResponseBody
    public ResponseEntity<String> create(@RequestBody String content) {

    	Map<String, Object> headers = getHeaders();

        Optional<Document> oDocument = documentExService.create(content, headers);
        if(oDocument.isPresent()) {
        	Document d = oDocument.get();
        	return new ResponseEntity<String>(d.getId(), WebUtils.getResponseHeaders(d), HttpStatus.CREATED);
        }else {
        	return new ResponseEntity<String>("Error: NO_CONTENT", HttpStatus.NO_CONTENT);
        }
    }
    
    /** Update - PUT    /storage/documents/{docId} 
<code>
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
     * @param map
     * @param response
     * @return
     */
    @RequestMapping(value="{documentId}", method = { RequestMethod.PUT },consumes= {"*/*"}, produces= {"*/*"})
    @ResponseBody
    public ResponseEntity<String> update(@PathVariable final String documentId, @RequestBody String content) {

    	Map<String, Object> headers = getHeaders();
    	Optional<Document> oDocument = this.documentExService.findById(documentId);
        if(oDocument.isPresent()) {
            Optional<Document> oNewDocument = documentExService.update(documentId, content, headers); 
            if(oNewDocument.isPresent()) {
            	
            	MultiValueMap<String, String> mvMap = null;
            	return new ResponseEntity<String>(oNewDocument.get().getId(), mvMap, HttpStatus.OK);
            }
        }
        return new ResponseEntity<String>("Error: NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    /** Delete - DELETE /storage/documents/{documentId}
     * 
     * @param map
     * @return
     */
    @RequestMapping(value="{documentId}", method = { RequestMethod.DELETE })
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable final String documentId) {
    	
        boolean result = this.documentExService.delete(documentId);
        if(result) {
            return new ResponseEntity<String>("success", HttpStatus.NO_CONTENT);
        }else {
        	//This is not documented in the requirements, but if on success NO_CONTENT is return, 
        	//   on failure should return BAD_REQUEST
            return new ResponseEntity<String>("failure", HttpStatus.BAD_REQUEST);
        }
    }
}
