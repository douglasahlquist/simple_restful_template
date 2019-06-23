package com.ahlquist.document.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.ahlquist.document.utils.EntityToJsonUtil;

@MappedSuperclass
public class GeneratedIdEntity<K> implements IBaseEntity<K> {

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