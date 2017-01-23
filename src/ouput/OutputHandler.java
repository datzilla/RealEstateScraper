package ouput;



public interface OutputHandler {
	public void close () throws Exception;
	public void reset () throws Exception;
	public void writeOutput (Outputable pOutputable) throws Exception;
}
