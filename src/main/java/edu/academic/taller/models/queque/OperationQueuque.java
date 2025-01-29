package edu.academic.taller.models.queque;

import edu.academic.taller.exceptions.MyListException;
import edu.academic.taller.models.list.MyLinkedList;

public class OperationQueuque<E> extends MyLinkedList<E> {
    private int top;

    public OperationQueuque(int top) {
        this.top = top;
    }

    public int getTop() {
        return top;
    }

    public Boolean verify() {
        return getLength() >= top; 
    }

    public Boolean isEmpty() {
        return getLength() == 0;
    }

    public void queuque(E data) throws MyListException {
        if (!verify()) {
            add(data); 
        } else {
            throw new MyListException("La cola está llena");
        }
    }
    
    public E dequeuque() throws Exception, MyListException {
        if (isEmpty()) {
            throw new MyListException("La cola está vacía");
        } else {
//            removeLast();
        	remove(getLength()-1);
            return null; //top ojo aqui
        }
    }
    
    public void reset() {
    	this.reset();
    }
}
