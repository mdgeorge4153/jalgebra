package com.mdgeorge.algebra.properties.meta;

import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;

import com.mdgeorge.util.NotImplementedException;

@SupportedAnnotationTypes("com.mdgeorge.algebra.properties.meta.MagicCheck")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class TestGenerator
     extends AbstractProcessor
{
	private ProcessingUtils util;
	
	@Override
	public void init(ProcessingEnvironment pe) {
		super.init(pe);
		this.util = new ProcessingUtils(processingEnv);
	}

	@Override
	public boolean process ( Set<? extends TypeElement> _
	                       , RoundEnvironment roundEnv
	                       )
	{
		for (Element e : roundEnv.getElementsAnnotatedWith(MagicCheck.class))
		{
			assert e instanceof TypeElement;
			checkClass((TypeElement) e);
		}

		return true;
	}
	
	private void checkClass(TypeElement e)
	{
		for (ExecutableElement m : ElementFilter.methodsIn(util.eu.getAllMembers(e)))
		{
			for (AnnotationMirror a : util.findAllAnnotations(m))
				if (isMagic(a))
					checkMethodProperty(m,a);
		}
	}
	
	private void checkMethodProperty(ExecutableElement method, AnnotationMirror property)
	{
		util.note( util.methodName(method)
			     + ": " + property.getAnnotationType().asElement().getSimpleName()
			     );
		
		Elements eu = processingEnv.getElementUtils();
		
		for (Map.Entry<? extends ExecutableElement,? extends AnnotationValue> e : eu.getElementValuesWithDefaults(property).entrySet())
		{
			util.note( "\t" + e.getKey().getSimpleName() + ": " + e.getValue().toString());
		}
	}

	
	
	/**
	 * Convenience method to determine if a given annotation is a @MagicProperty.
	 */
	private boolean isMagic(AnnotationMirror a){
		return a.getAnnotationType().asElement()
				.getAnnotation(MagicProperty.class) != null;
	}
	
}
