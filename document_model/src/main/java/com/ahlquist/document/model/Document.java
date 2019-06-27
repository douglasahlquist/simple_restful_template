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
@Entity
@Table(name = "Document")
public class Document extends ProvidedIdEntity<String> implements Serializable, IBaseEntity<String> {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((metadata == null) ? 0 : metadata.hashCode());
		result = prime * result + (int) (length ^ (length >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Document other = (Document) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (metadata == null) {
			if (other.metadata != null)
				return false;
		} else if (!metadata.equals(other.metadata))
			return false;
		if (length != other.length)
			return false;
		return true;
	}

	private static final long serialVersionUID = -5772272607969605327L;

	public String toString() {
		return new EntityToJsonUtil<Document>().toString(this);
	}

	private String content;
	private Long length;
	private String metadata;

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getLength() {
		return this.length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public String getMetadata() {
		return this.metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
}
