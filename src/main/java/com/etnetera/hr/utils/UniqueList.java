package com.etnetera.hr.utils;

import java.util.ArrayList;

/**
 * Extended ArrayList with unique values
 * 
 * @author Bogdan
 *
 */
public class UniqueList<E> extends ArrayList<E> {

	/**
	 * Appends the specified element to the end of this list. Does nothing If
	 * element is already in list.
	 * 
	 * @param arg0
	 *            - element to be appended to this list
	 * @return true (as specified by Collection.add(E))
	 */
	@Override
	public boolean add(E arg0) {
		if (this.contains(arg0))
			return false;
		return super.add(arg0);
	}

}
