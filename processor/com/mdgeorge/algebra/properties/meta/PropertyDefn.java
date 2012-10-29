package com.mdgeorge.algebra.properties.meta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;

/**
 * @author mdgeorge
 *
 */
class PropertyDefn
{
	public final List<TypeParameterElement> checkTypeParams;
	public final MethodDefn                 annotatedMethod;
	public final List<MethodDefn>           parameters;
	public final List<TypeMirror>           checkArgs;
	
	public PropertyDefn ( ProcessingUtils      utils
	                    , TypeElement          property
	                    , BadPropertyException parentLog
	                    )
	throws BadPropertyException
	{
		BadPropertyException log = new BadPropertyException(parentLog);

		this.checkTypeParams = new ArrayList<TypeParameterElement> ();
		this.parameters      = new ArrayList<MethodDefn>           ();
		this.checkArgs       = new ArrayList<TypeMirror>           ();
		
		//
		// check that magicProperty is an annotation
		//
		if (!property.getKind().equals(ElementKind.ANNOTATION_TYPE))
			log.error ( "@MagicProperties must be annotations"
			          , property
			          );
		
		//
		// find and check Definition inner class
		//
		List<TypeElement> definitions = ElementFilter.typesIn (utils.findBySimpleName(property, "Definition"));
		
		if (definitions.isEmpty())
			log.error ( "@MagicProperty annotation "  +
			            property.getSimpleName() +
			            " requires a static inner class named 'Definition'"
			          , property
			          );
		
		if (definitions.size() > 1)
			for (TypeElement e : definitions)
				log.error ( "@MagicProperty annotation "  +
				            property.getSimpleName() +
			                " cannot have multiple Definitions"
				          , e
				          );

		log.dumpAndThrow();
		
		TypeElement definition = definitions.get(0);
		
		//
		// ensure that we found a suitable Definition inner class
		//
		if (!definition.getModifiers().contains(Modifier.PUBLIC))
			log.error ( property.getSimpleName() +
			            ".Definition must be public"
			          , definition
			          );
		
		if (!definition.getModifiers().contains(Modifier.STATIC))
			log.error ( property.getSimpleName() + ".Definition must " +
			            "be static"
			          , definition
			          );
		
		//
		// walk over the contents of definition to find a method called "check"
		//
		List<ExecutableElement> checks = ElementFilter.methodsIn(utils.findBySimpleName(definition, "check"));
		
		if (checks.isEmpty())
			log.error ( "@MagicProperty annotation " +
			            property.getSimpleName() +
			            " requires a 'check' method in its 'Definition' class."
			          , definition
			          );

		else if (checks.size() > 1)
			for (Element e : checks)
				log.error ( "@MagicProperty Definitions should only have a " +
				            "single 'check' method"
				          , e
				          );

		
		log.dumpAndThrow();
		ExecutableElement check = checks.get(0);

		//
		// ensure we found one appropriate "check" method
		//
		if (!check.getModifiers().contains(Modifier.PUBLIC))
			log.error ( property.getSimpleName() + ".Definition.check" +
	                    " must be public"
			          , check
			          );
		
		if (!check.getModifiers().contains(Modifier.STATIC))
			log.error ( property.getSimpleName() + ".Definition.check" +
	                    " must be static"
			          , check
			          );
		
		//
		// check must return boolean
		//
		if (!check.getReturnType().getKind().equals(TypeKind.BOOLEAN))
			log.error ( "@MagicProperty 'check' function must return a boolean"
			          , check
			          );
		
		this.checkTypeParams.addAll(check.getTypeParameters());
		
		//
		// verify check parameter types
		//
		
		Iterator<? extends VariableElement> args = check.getParameters().iterator();

		//
		// first parameter should correspond to the annotated method
		//
		if (!args.hasNext())
		{
			log.error ( "@MagicProperty 'check' function must have at least " +
			            "one argument."
			          , check
			          );
		}
		
		log.dumpAndThrow();

		MethodDefn annotatedMethod;
		try {
			annotatedMethod = new MethodDefn(null, args.next(), utils, log);
		}
		catch (final BadPropertyException e) {
			annotatedMethod = null;
		}
		this.annotatedMethod = annotatedMethod;
		
		//
		// next parameters should correspond to the annotation values
		//
		for (ExecutableElement e : ElementFilter.methodsIn(property.getEnclosedElements()))
		{
			if (!args.hasNext()) {
				log.error ( "@MagicProperty 'check' method must have an " +
				            "argument corresponding to the " +
				            e.getSimpleName() + " property."
				          , check
				          );
				log.dumpAndThrow();
			}
			else {
				try {
					this.parameters.add(new MethodDefn(e, args.next(), utils, log));
				}
				catch (final BadPropertyException exc) {
					continue;
				}
			}
		}
		
		log.dumpAndThrow();
		
		//
		// final parameters are the generated arguments to the test
		//
		
		while (args.hasNext())
			this.checkArgs.add(args.next().asType());
	}
	
}
