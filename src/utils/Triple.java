package utils;

public class Triple <F, S, T> {
	private F mFirst; // primary key
	private S mSecond;
	private T mThird;
	
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

	public T getThird() {
		return mThird;
	}

	public void setThird(T pThird) {
		mThird = pThird;
	}

	public Triple (F pFirst, S pSecond, T pThird) {
		mFirst = pFirst;
		mSecond = pSecond;
		mThird = pThird;
	}
}
