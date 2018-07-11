package com.etnetera.hr;

import java.util.ArrayList;

/**
 * Extended ArrayList with unique values
 * 
 * @author Bogdan
 *
 */
public class UniqueList<E> extends ArrayList<E> {

	@Override
	public boolean add(E arg0) {
		if (this.contains(arg0))
			return false;
		return super.add(arg0);
	}

}
