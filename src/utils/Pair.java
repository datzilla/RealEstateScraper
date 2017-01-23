package utils;

public class Pair <F,S> {
	
	private F mFirst;
	private S mSecond;
	
	public F getFirst() {
		return mFirst;
	}

	public void setFirst(F pFirst) {
		mFirst = pFirst;
	}

	public S getSecond() {
		return mSecond;
	}

	public void setSecond(S pSecond) {
		mSecond = pSecond;
	}

	public Pair(F pFirst, S pSecond) {
		mFirst = pFirst;
		mSecond = pSecond;
	}
	
	public Pair () {
	}

}