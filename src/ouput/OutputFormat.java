package ouput;



public class OutputFormat {
	
	private String mDelimiter = "\t";
	private String mLineSeperator = "\n";
	private String mEncoding = "UTF-8";
	public final static String UTF8 = "UTF-8";
	public final static String UTF16 = "UTF-16";
	
	OutputFormat () {	
	}

	public String getDelimiter() {
		return mDelimiter;
	}

	public String getLineSeperator() {
		return mLineSeperator;
	}

	public String getEncoding() {
		return mEncoding;
	}

	public void setDelimiter(String mDelimiter) {
		this.mDelimiter = mDelimiter;
	}

	public void setLineSeperator(String mLineSeperator) {
		this.mLineSeperator = mLineSeperator;
	}

	public void setEncoding(String mEncoding) {
		this.mEncoding = mEncoding;
	}
}
