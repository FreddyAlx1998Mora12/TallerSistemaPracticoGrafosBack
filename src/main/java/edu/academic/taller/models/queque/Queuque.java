package edu.academic.taller.models.queque;

import edu.academic.taller.exceptions.MyListException;

public class Queuque<E>{
    private OperationQueuque<E> queuqu;

    public Queuque(Integer top) {
        this.queuqu = new OperationQueuque<>(top);
    }

    public Integer getTop(){
        return queuqu.getSize();
    }

    public Integer getSize() {
        return queuqu.getSize();
    }

    public Boolean verify(){
        return queuqu.verify();
    }

    public void queuque(E data) throws MyListException {
        queuqu.queuque(data);
    }

    public void clear(){
        queuqu.reset();
    }
    
    public E dequeuque() throws Exception,MyListException {
        return queuqu.dequeuque();
    }
    
    public boolean isEmpty() {
    	return queuqu.isEmpty();
    }
    
}
