package utils;

import java.util.List;

public interface Variable <T> {
	public boolean hasNext ();
	public T getNext ();
	public List <T> getNext(int inNoOfElements);
	public void setPosition (int pPosition);
	public int getTotal (); 
	public int getPosition ();
	public String toString ();
}
