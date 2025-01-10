package edu.academic.taller.DAOs;

import edu.academic.taller.models.list.MyLinkedList;

public interface InterfazDAO<T> {
	void persist (T object) throws Exception;
	void merge(T object, Integer index) throws Exception;
    MyLinkedList listAll();
    T get(Integer id) throws Exception;
    void delete(int index) throws Exception;
}
