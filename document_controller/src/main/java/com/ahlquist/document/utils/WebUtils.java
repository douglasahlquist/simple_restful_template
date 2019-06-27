package com.ahlquist.document.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.http.HttpHeaders;
import org.springframework.util.*;

import com.ahlquist.document.model.Document;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebUtils {

	public final static String CONTENT_TYPE = "Content-Type";
	public final static String CONTENT_LENGTH = "Content-Length";
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

	public Map<String, String> convertFile(HttpServletRequest request, HttpSession session) {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			System.out.println("File Not Uploaded");
			return null;
		} else {
			Map<String, String> map = new HashMap<>();

			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			@SuppressWarnings("rawtypes")
			List items = null;
			try {
				items = upload.parseRequest((RequestContext) request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			try {
				FileItem file = (FileItem) items.get(0);
				System.out.print("Field Name :" + file.getFieldName()); // Display the field name
				System.out.print("Content Type :" + file.getContentType()); // Display Type of File
				System.out.print("File Name :" + file.getName()); // Display File Name
			} catch (Exception e) {
				System.err.print(e);
			}
			return map;

		}
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

}