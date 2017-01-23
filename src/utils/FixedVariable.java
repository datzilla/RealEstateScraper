package utils;

import java.util.List;

public class FixedVariable <T> implements Variable<T> {
	
	private List <T> mList;
	private int mPosition;
	
	public FixedVariable(List <T> pList) {
		mList = pList;
		mPosition = 0;
	}
	
	@Override
	public boolean hasNext() {
		if (mList != null) {
			if (mPosition < mList.size() - 1) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	
	}

	@Override
	public T getNext() {
		if (mList != null) {
			mPosition++;
			if (mPosition < mList.size()) {
				return mList.get(mPosition);
			}
			else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public void setPosition(int pPosition) {
		mPosition = pPosition;
	}

	@Override
	public int getTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<T> getNext(int inNoOfElements) {
		// TODO Auto-generated method stub
		return null;
	}

}
