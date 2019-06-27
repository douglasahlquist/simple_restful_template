package com.ahlquist.document.services;

import java.util.Optional;

/**
 * Base Service interface for all base methods used to access the all repositories with a Key named 'id'
 * 
 * @author Douglas Ahlquist
 *
 * @param <K> - Key used in the CrudRepository
 * @param <T> - Type used in the CrudRepository
 */
public interface IBaseService<T, K> {

	public T create(T t) throws IllegalArgumentException;

	public Optional<T> findById(K id);

	//public void deleteById(T l) throws IllegalArgumentException;

	public T save(T t);

	//public T merge(T t);
}
