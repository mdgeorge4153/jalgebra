package com.mdgeorge.algebra.properties.meta;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic.Kind;


class BadPropertyException extends Exception {
	private interface Entry { <R> R visit(EntryVisitor<R> v); }
	private interface EntryVisitor<R> {
		R visitWarning(Warning w);
		R visitError(Error e);
	}
	
	private class Warning implements Entry {
		public final String  message;
		public final Element element;
		
		public Warning (String message, Element element) {
			this.message = message;
			this.element = element;
		}
		
		public <R> R visit(EntryVisitor<R> v) {
			return v.visitWarning(this);
		}
	}
	
	private class Error   implements Entry {
		public final String  message;
		public final Element element;
		
		public Error (String message, Element element) {
			this.message = message;
			this.element = element;
		}
		
		public <R> R visit(EntryVisitor<R> v) {
			return v.visitError(this);
		}
	}
	
	private final List<Entry>          log;
	private final BadPropertyException parent;
	
	public BadPropertyException(BadPropertyException parent) {
		super("Invalid property");
		
		this.log      = new ArrayList<Entry> ();
		this.parent   = parent;
	}
	
	public void warn(String message, Element e) {
		this.log.add(new Warning(message, e));
	}
	
	public void error(String message, Element e) {
		this.log.add(new Error(message, e));
	}
	
	public void dumpAndThrow()
	     throws BadPropertyException
	{
		boolean hasError = false;
		
		for (Entry e : log)
			hasError |= e.visit(new EntryVisitor<Boolean>() {
				public Boolean visitWarning(Warning w) {
					return false;
				}
				
				public Boolean visitError(Error e) {
					return true;
				}
			});
		
		parent.log.addAll(log);
		log.clear();
		
		if (hasError)
			throw this;
	}
	
	public void dumpInto (final Messager messager) {
		for (Entry e : log)
			e.visit(new EntryVisitor<Void>() {
				public Void visitWarning(Warning w) {
					messager.printMessage(Kind.WARNING, w.message, w.element);
					return null;
				}
				public Void visitError(Error e) {
					messager.printMessage(Kind.ERROR, e.message, e.element);
					return null;
				}
			});
	}
}
