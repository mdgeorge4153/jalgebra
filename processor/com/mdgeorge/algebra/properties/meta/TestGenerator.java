package com.mdgeorge.algebra.properties.meta;

import java.util.ArrayList;
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
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;

import com.mdgeorge.util.NotImplementedException;

@SupportedAnnotationTypes("com.mdgeorge.algebra.properties.meta.MagicCheck")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class TestGenerator
     extends AbstractProcessor
{

	@Override
	public boolean process ( Set<? extends TypeElement> _
	                       , RoundEnvironment roundEnv
	                       )
	{
		for (Element e : roundEnv.getElementsAnnotatedWith(MagicCheck.class))
		{
			assert e instanceof TypeElement;
			check((TypeElement) e);
		}

		return true;
	}
	
	private void check(TypeElement e)
	{
		Elements util = processingEnv.getElementUtils();
		for (ExecutableElement m : ElementFilter.methodsIn(util.getAllMembers(e)))
		{
			note("in " + m.getSimpleName() + ":");
			for (AnnotationMirror a : findAllAnnotations(m, e))
				note("\t" + a.getAnnotationType());
		}
	}
	
	/**
	 * Find all of the annotations on any method that the given method overrides.
	 */
	private List<AnnotationMirror> findAllAnnotations(ExecutableElement method)
	{
		return findAllAnnotations(method);
	}
	
	/**
	 * Recursive helper method for findAllAnnotations.  Returns all annotations
	 * on methods from container or any supertype of container that are
	 * overridden by the given method. 
	 * 
	 * @param method
	 * @param container
	 * @return
	 */
	private List<AnnotationMirror> findAllAnnotations(ExecutableElement method, TypeElement container)
	{
		final List<AnnotationMirror> result = new ArrayList<AnnotationMirror>();
		final Types                  tu     = processingEnv.getTypeUtils();
		final Elements               eu     = processingEnv.getElementUtils();

		// recursively find annotations in container's parents
		if (container.getSuperclass().getKind().equals(TypeKind.DECLARED))
			result.addAll(findAllAnnotations(method, (TypeElement) tu.asElement(container.getSuperclass())));
		
		for (TypeMirror sup : container.getInterfaces())
			result.addAll(findAllAnnotations(method, (TypeElement) tu.asElement(sup)));
		
		// find annotations in container
		for (ExecutableElement containedMethod : ElementFilter.methodsIn(container.getEnclosedElements()))
			if (  eu.overrides(method, containedMethod, (TypeElement) method.getEnclosingElement())
			   || containedMethod.equals(method)
			   )
				result.addAll(containedMethod.getAnnotationMirrors());

		return result;
	}

	
	
	
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
