package com.mdgeorge.algebra.properties.meta;

import java.lang.reflect.Modifier;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.AbstractElementVisitor6;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.tools.Diagnostic.Kind;

import com.mdgeorge.util.NotImplementedException;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class PropertyChecker
     extends AbstractProcessor
{
	public PropertyChecker() {
		super();
	}
	
	@Override
	public boolean process ( Set<? extends TypeElement> annotations
	                       , RoundEnvironment           env
	                       )
	{
		for (TypeElement e : annotations) {
			MagicCheck mc = e.getAnnotation(MagicCheck.class); 
			if (mc != null)
			{
				checkWellFormed(e);
			}
		}
		
		return true;
	}

	/**
	 * This method checks whether a type is a well-formed @MagicCheck type. 
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
	 * @param magicType
	 */
	private void checkWellFormed(TypeElement magicType)
	{
		//
		// walk over the contents of magicType to find a type called "Definition"
		//
		TypeElement definition = null;
		for (Element e : magicType.getEnclosedElements())
		{
			definition = e.accept
				( new SimpleElementVisitor6<TypeElement,Void>()
					{
						@Override
						public TypeElement visitType(TypeElement e, Void _)
						{
							processingEnv.getMessager().printMessage(Kind.NOTE, "checking " + e.getSimpleName());
							if (e.getSimpleName().equals("Definition"))
								return e;
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
			String message = "@MagicCheck annotation "
			               + magicType.getSimpleName()
			               + " requires a static inner class named 'Definition'"
			               ;
			processingEnv.getMessager().printMessage(Kind.ERROR, message, magicType);
			return;
		}
		
		if (!definition.getModifiers().contains(Modifier.PUBLIC))
		{
			String message = magicType.getSimpleName() + ".Definition"
			               + " must be public";
			processingEnv.getMessager().printMessage(Kind.ERROR, message, definition);
		}
		
		if (!definition.getModifiers().contains(Modifier.STATIC))
		{
			String message = magicType.getSimpleName() + ".Definition"
		                   + " must be static";
			processingEnv.getMessager().printMessage(Kind.ERROR, message, definition);
		}
		
		//
		// walk over the contents of definition to find a method called "check"
		//
		ExecutableElement check      = null;
		for (Element e : definition.getEnclosedElements())
		{
			check = e.accept
				( new SimpleElementVisitor6<ExecutableElement,Void> ()
					{
						@Override
						public ExecutableElement visitExecutable(ExecutableElement e, Void _)
						{
							if (e.getSimpleName().equals("check"))
								return e;
							else
								return null;
						}
					}
				, null
				);
			
			if (check != null)
				break;
		}
		
		//
		// ensure we found an appropriate "check" method
		//
		if (check == null)
		{
			String message = "@MagicCheck annotation "
			               + magicType.getSimpleName()
			               + " requires a 'check' method in its 'Definition'"
			               + " class."
			               ;
			processingEnv.getMessager().printMessage(Kind.ERROR, message, definition);
			return;
		}

		if (!check.getModifiers().contains(Modifier.PUBLIC))
		{
			String message = magicType.getSimpleName() + ".Definition.check"
			               + " must be public";
			processingEnv.getMessager().printMessage(Kind.ERROR, message, check);
		}
		
		if (!definition.getModifiers().contains(Modifier.STATIC))
		{
			String message = magicType.getSimpleName() + ".Definition.check"
		                   + " must be static";
			processingEnv.getMessager().printMessage(Kind.ERROR, message, check);
		}
	}
	
}
