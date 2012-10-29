package com.mdgeorge.algebra.properties.meta;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import com.mdgeorge.algebra.properties.meta.annotation.MethodDup;
import com.mdgeorge.algebra.properties.meta.annotation.MethodExt;
import com.mdgeorge.algebra.properties.meta.annotation.MethodName;
import com.mdgeorge.algebra.properties.meta.annotation.MethodRef;

class MethodDefn {
	public enum Type {
		DUP,
		EXT,
		NAME,
		REF,
		PRIMARY,
	}

	public final Type              type;
	public final ExecutableElement decl;
	public final ExecutableElement ap;
	
	public MethodDefn ( ExecutableElement    propertyDecl
	                  , VariableElement      checkArg
	                  , ProcessingUtils      utils
	                  , BadPropertyException parentLog
	                  )
	throws BadPropertyException
	{
		BadPropertyException log = new BadPropertyException(parentLog);
		this.decl = propertyDecl;
		
		//
		// figure out what type or property this is.
		//
		if (propertyDecl == null)
		{
			this.type = Type.PRIMARY;
		}
		else
		{
			if (!propertyDecl.getSimpleName().equals(checkArg.getSimpleName()))
				log.warn ( "The check method argument corresponding to the " + 
				           propertyDecl.getSimpleName() + " property is "    +
				           "named '" + checkArg.getSimpleName() + "'.  The " +
				           "recommended name is '"                           +
				           propertyDecl.getSimpleName() + "'."
				         , checkArg
				         );

			//
			// Properties must be strings
			//
			TypeMirror stringType = utils.eu.getTypeElement("java.lang.String").asType();
			if (!propertyDecl.getReturnType().equals(stringType))
				log.error( "@MagicProperty values must be Strings", propertyDecl );
			
			//
			// Properties must have (exactly one) type annotation
			//
			List<Type> types = new ArrayList<Type> ();
			
			if (propertyDecl.getAnnotation(MethodName.class) != null)
				types.add(Type.NAME);
			if (propertyDecl.getAnnotation(MethodRef.class)  != null)
				types.add(Type.REF);
			if (propertyDecl.getAnnotation(MethodExt.class)  != null)
				types.add(Type.EXT);
			if (propertyDecl.getAnnotation(MethodDup.class)  != null)
				types.add(Type.DUP);

			if (types.size() != 1)
				log.error ( "@MagicProperty values must be annotated "  +
                            "with exactly of @MethodName, @MethodRef, " +
                            "@MethodDup or @MethodExt."
				          , propertyDecl
				          );

			log.dumpAndThrow();

			this.type = types.get(0);
		}
			
		//
		// find return type and parameters for corresponding method
		//
		
		String badArg = "The type of the first argument of Definition.check " +
	                    "must have a method named 'ap'.";
		
		TypeMirror checkArgType = checkArg.asType();
		if (checkArgType.getKind() != TypeKind.DECLARED)
			log.error (badArg + " (type not a declared type)", checkArg);
		
		log.dumpAndThrow();
		
		Element type = ((DeclaredType) checkArgType).asElement();
		if (!(type instanceof TypeElement))
			log.error(badArg + " (something strange going on)", checkArg);
		
		log.dumpAndThrow();
		

		List<Element> aps = utils.findBySimpleName((TypeElement) type, "ap");
		if (aps.size() != 1)
			log.error(badArg + " (wrong number of 'ap's)", checkArg);
		
		log.dumpAndThrow();
		
		Element ap = aps.get(0);
		if (!(ap instanceof ExecutableElement))
			log.error(badArg + " ('ap' not a method)", checkArg);
		
		log.dumpAndThrow();
		
		this.ap = (ExecutableElement) ap;
	}
	
}
