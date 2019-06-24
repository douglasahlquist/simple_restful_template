package com.ahlquist.document.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.ahlquist.document.utils.EntityToJsonUtil;

@MappedSuperclass
public class GeneratedIdEntity<K> implements IBaseEntity<K> {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		GeneratedIdEntity other = (GeneratedIdEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * @param id
	 */
	public GeneratedIdEntity(K id) {
		super();
		this.id = id;
	}

	public GeneratedIdEntity() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public K id;

	@Override
	public String toString() {
		return new EntityToJsonUtil<GeneratedIdEntity<K>>().toString(this);
	}

	/*
	 * @see com.ahlquist.document.model.IBaseEntity#getId()
	 */
	@Override
	public K getId() {
		return id;
	}

	/*
	 * @see com.ahlquist.document.model.IBaseEntity#setId(java.lang.Object)
	 */
	@Override
	public void setId(K id) {
		this.id = id;
	}
}