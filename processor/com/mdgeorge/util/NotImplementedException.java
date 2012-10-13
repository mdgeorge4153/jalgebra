package com.mdgeorge.util;

/**
 * Used to indicate a portion of the program that is not yet implemented.
 * Throwing this Error is preferable to returning a dummy value, because
 * unimplemented functionality can be easily identified that way.
 * 
 * @author mdgeorge
 */
public class NotImplementedException extends Error {
	private static final long serialVersionUID = 1L;

	public NotImplementedException(String msg) {
		super(msg);
	}
}
