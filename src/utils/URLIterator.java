package utils;

import java.net.URL;
import java.util.List;

public interface URLIterator {
	public boolean hasNext ();
	public URL getCurrentURL ();
	public int getCurrentPosition();
	public void setCurrentPosition(int pPosition);
	public List <URL> getNext (int pNumberOfURL);
	public URL getNext ();
	public URL getURL(int pPosition);
	public void clearCache();
}
