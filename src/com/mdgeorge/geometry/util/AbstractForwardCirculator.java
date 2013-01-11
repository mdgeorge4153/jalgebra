package com.mdgeorge.geometry.util;

import java.util.NoSuchElementException;

public abstract class AbstractForwardCirculator<E>
           implements ForwardCirculator<E>
{
	protected E current;
	
	protected AbstractForwardCirculator(E current) throws NoSuchElementException
	{
		if (current == null)
			throw new NoSuchElementException();
		this.current = current;
	}
	
	public final E get() {
		return current;
	}
}
