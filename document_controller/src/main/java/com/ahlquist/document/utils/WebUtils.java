package com.ahlquist.document.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.*;

import com.ahlquist.document.controller.DocumentController;
import com.ahlquist.document.model.Document;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebUtils {
	
	final static Logger logger = Logger.getLogger(WebUtils.class);

	public final static String CONTENT_TYPE = "content-type";
	public final static String CONTENT_LENGTH = "content-length";
	public final static String DEFAULT_CONTENT_TYPE = "text/plain; charset=us-ascii";

	public static Map<String, Object> getHeadersInfo(HttpServletRequest request) {

		Map<String, Object> map = new HashMap<>();

		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}

		return map;
	}

	/**
	 * Populates an HttpHeader with metadata related to the Document
	 * 
	 * @param d - the Document entity
	 * @return
	 */
	public static HttpHeaders getResponseHeaders(final Document d) {
		HttpHeaders headers = new HttpHeaders();
		String contentType = d.getMetadata();
		if (contentType != null) {
            //TODO(dahlquist) metadata should be brokenup into COntent-Type, Filename and other in key value pairs
			headers.add(CONTENT_TYPE, d.getMetadata());
		} else {
			headers.add(CONTENT_TYPE, DEFAULT_CONTENT_TYPE);
		}
		Long length = d.getLength();
		if (length != null) {
			headers.add(CONTENT_LENGTH, contentType);
		}
		return headers;
	}
	
	public static String getContentTypeAsString(HttpServletRequest request) {
		return getContentType(request).toString();
	}
	
	public static MediaType getContentType(HttpServletRequest request) {
	    String contentType = request.getContentType();
	    if (contentType == null) {
	        if (logger.isTraceEnabled()) {
	            logger.trace("No Content-Type header found, defaulting to application/octet-stream");
	        }
	        return MediaType.APPLICATION_OCTET_STREAM;
	    }
	    return MediaType.parseMediaType(contentType);
	}
	 

}