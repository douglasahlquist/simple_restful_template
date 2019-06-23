package com.ahlquist.document.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ahlquist.document.utils.*;


/**
 * Document Entity
 * 
 * @author Douglas Ahlquist
 *
 */
@Table(name = "document")
public class Document extends GeneratedIdEntity<Long> implements Serializable, IBaseEntity<Long> {

    private static final long serialVersionUID = -5772272607969605327L;

    @Override
    public String toString() {
        return new EntityToJsonUtil<Document>().toString(this);
    }

    private String content;
    private long length;
    private String filename;
    
    public String getContent() {
    	return this.content;
    }
    public void setContent(String content) {
    	this.content = content;
    }
    public long getLength() {
    	return this.length;
    }
    public void setLength(long length) {
    	this.length = length;
    }
    public String getFilename() {
    	return this.filename;
    }
    public void setFilename(String filename) {
    	this.filename = filename;
    }
}
