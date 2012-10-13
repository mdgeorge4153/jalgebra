package com.mdgeorge.algebra.properties.meta;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.tools.Diagnostic.Kind;

import com.mdgeorge.util.NotImplementedException;

@SupportedAnnotationTypes("com.mdgeorge.algebra.properties.meta.MagicProperty")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class PropertyChecker
     extends AbstractProcessor
{
	public PropertyChecker() {
		super();
	}
	
	@Override
	public boolean process ( Set<? extends TypeElement> _
	                       , RoundEnvironment           env
	                       )
	{
		note("foo");
		

		for (Element e : env.getElementsAnnotatedWith(MagicProperty.class)) {
			assert e instanceof TypeElement;
			checkWellFormed((TypeElement) e);
		}
		
		return true;
	}

	/**
	 * This method checks whether a type is a well-formed @MagicProperty type. 
	 * A type I is well-formed if: <ol>
	 * 	<li>I is an annotation with @Target(METHOD)</li>
	 *  <li>I contains a public static inner class named <code>Definition</code></li>
	 *  <li>I.Definition contains a public static method named <code>check</code></li>
	 *  <li>I.Definition.check's arguments must be as follows:<ol>
	 *  	<li>The first argument corresponds to the annotated method</li>
	 *  	<li>The following arguments correspond to the other annotation parameters of I</li>
	 *  	<li>The remaining arguments correspond to arbitrary elements of their domains.</li>
	 *  	</ol>
	 *  </ol>  
	 * @param magicProperty
	 */
	private void checkWellFormed(TypeElement magicProperty)
	{
		//
		// walk over the contents of magicType to find a type called "Definition"
		//
		TypeElement definition = null;
		for (final Element e : magicProperty.getEnclosedElements())
		{
			definition = e.accept
				( new SimpleElementVisitor6<TypeElement,Void>()
					{
						@Override
						public TypeElement visitType(TypeElement te, Void _)
						{
							if (te.getSimpleName().contentEquals("Definition"))
								return te;
							else
								return null;
						}
					}
				, null
				);
			
			if (definition != null)
				break;
		}
		
		//
		// ensure that we found a suitable Definition inner class
		//
		if (definition == null)
		{
			error ( "@MagicProperty annotation "
			        + magicProperty.getSimpleName()
			        + " requires a static inner class named 'Definition'"
			      , magicProperty
			      );
			return;
		}

		if (!definition.getModifiers().contains(Modifier.PUBLIC))
			error ( magicProperty.getSimpleName() + ".Definition must be public"
			      , definition
			      );
		
		if (!definition.getModifiers().contains(Modifier.STATIC))
			error ( magicProperty.getSimpleName() + ".Definition must be static"
			      , definition
			      );
		
		//
		// walk over the contents of definition to find a method called "check"
		//
		List<ExecutableElement> checks = new ArrayList<ExecutableElement>();
		for (Element e : definition.getEnclosedElements())
		{
			ExecutableElement check = e.accept
				( new SimpleElementVisitor6<ExecutableElement,Void> ()
					{
						@Override
						public ExecutableElement visitExecutable(ExecutableElement e, Void _)
						{
							if (e.getSimpleName().contentEquals("check"))
								return e;
							else
								return null;
						}
					}
				, null
				);
			
			if (check != null)
				checks.add(check);
		}
		
		//
		// ensure we found one appropriate "check" method
		//
		if (checks.isEmpty())
		{
			error ( "@MagicProperty annotation " +
			        magicProperty.getSimpleName() +
			        " requires a 'check' method in its 'Definition' class."
			      , definition
			      );
			return;
		}
		else if (checks.size() > 1)
		{
			for (Element e : checks)
				error ( "@MagicProperty Definitions should only have a " +
				        "single 'check' method"
				      , e
				      );
			return;
		}

		ExecutableElement check = checks.get(0);
		
		if (!check.getModifiers().contains(Modifier.PUBLIC))
		{
			String message = magicProperty.getSimpleName() + ".Definition.check"
			               + " must be public";
			error(message, check);
		}
		
		if (!check.getModifiers().contains(Modifier.STATIC))
		{
			String message = magicProperty.getSimpleName() + ".Definition.check"
		                   + " must be static";
			error(message, check);
		}
		
		//
		// check must return boolean
		//
		if (!check.getReturnType().getKind().equals(TypeKind.BOOLEAN))
			error ( "@MagicProperty 'check' function must return a boolean"
			      , check
			      );
		
		//
		// verify check parameter types
		//
		
		Iterator<? extends VariableElement> args = check.getParameters().iterator();

		//
		// first parameter should correspond to the annotated method
		//
		if (!args.hasNext())
		{
			error ( "@MagicProperty 'check' function must have at least one "
			      + "argument"
			      , check
			      );
			return;
		}
		
		VariableElement methodArg = args.next();
		// TODO: typecheck methodArg
		
		//
		// next parameters should correspond to the annotation values
		//
		for (Element e : magicProperty.getEnclosedElements())
			if (e.getKind().equals(ElementKind.METHOD))
			{
				if (!args.hasNext()) {
					error ( "@MagicProperty 'check' method must have an "
					      + "argument corresponding to the " + e.getSimpleName()
					      + " property." 
					      , check
					      );
					return;
				}
				else
					// TODO: check argument type
					args.next();
			}
			
		//
		// final parameters are the generated arguments to the test
		//
		
		// TODO: checking?
		
	}
	
	
	/*
	 ** Private helper methods *************************************************
	 */
	
	@SuppressWarnings("unused")
	private void note(String message) {
		processingEnv.getMessager().printMessage(Kind.NOTE, message);
	}

	private void error(String message, Element location) {
		processingEnv.getMessager().printMessage(Kind.ERROR, message, location);
	}
	
	@SuppressWarnings("unused")
	private void warning(String message, Element location) {
		processingEnv.getMessager().printMessage(Kind.WARNING, message, location);
	}
}
