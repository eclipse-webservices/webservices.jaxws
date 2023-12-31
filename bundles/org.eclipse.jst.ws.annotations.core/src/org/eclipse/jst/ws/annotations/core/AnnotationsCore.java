/*******************************************************************************
 * Copyright (c) 2009 Shane Clarke.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Shane Clarke - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.ws.annotations.core;

import java.util.List;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeLiteral;
/**
 * Utility class for creating annotations and member value pairs.
 * <p>
 * <strong>Provisional API:</strong> This class/interface is part of an interim API that is still under
 * development and expected to change significantly before reaching stability. It is being made available at
 * this early stage to solicit feedback from pioneering adopters on the understanding that any code that uses
 * this API will almost certainly be broken (repeatedly) as the API evolves.
 * </p>
 */
public final class AnnotationsCore {

    private AnnotationsCore() {
    }

    /**
     * Creates a new {@link NormalAnnotation}.
     *
     * @param ast the {@link AST} that will be used to create the annotation.
     * @param annotationName the name of the annotation.
     * @param memberValuePairs a list of {@link MemberValuePair} to add to the normal annotation.
     * @return a normal annotation with the given member value pairs.
     */
    public static NormalAnnotation createNormalAnnotation(AST ast, String annotationName, List<MemberValuePair> memberValuePairs) {
        NormalAnnotation annotation = ast.newNormalAnnotation();

        Name annotationTypeName = ast.newName(annotationName);

        annotation.setTypeName(annotationTypeName);

        if (memberValuePairs != null) {
            for (MemberValuePair memberValuePair : memberValuePairs) {
                @SuppressWarnings("unchecked")
                List<MemberValuePair> annotationValues = annotation.values();
                annotationValues.add(memberValuePair);
            }
        }
        return annotation;
    }

    /**
     * Creates a new {@link SingleMemberAnnotation}.
     *
     * @param ast the {@link AST} that will be used to create the annotation.
     * @param annotationName the name of the annotation.
     * @param value the {@link Expression} to set as the single member annotation value.
     * @return a single member annotation with the given value.
     */
    public static SingleMemberAnnotation createSingleMemberAnnotation(AST ast, String annotationName, Expression value) {
        SingleMemberAnnotation annotation = ast.newSingleMemberAnnotation();

        Name annotationTypeName = ast.newName(annotationName);

        annotation.setTypeName(annotationTypeName);

        if (value != null) {
            value = (Expression)Expression.copySubtree(ast, value);
            annotation.setValue(value);
        }

        return annotation;
    }

    /**
     * Creates a new {@link MarkerAnnotation}.
     *
     * @param ast the {@link AST} that will be used to create the annotation.
     * @param annotationName the name of the annotation.
     * @return a marker annotation.
     */
    public static MarkerAnnotation createMarkerAnnotation(AST ast, String annotationName) {
        MarkerAnnotation annotation = ast.newMarkerAnnotation();

        Name annotationTypeName = ast.newName(annotationName);

        annotation.setTypeName(annotationTypeName);

        return annotation;
    }

    /**
     * Creates a new {@link MemberValuePair}.
     *
     * @param ast the {@link AST} that will be used to create the member value pair.
     * @param name the {@link MemberValuePair} "name".
     * @param expression the {@link Expression} "value" of the member value pair.
     * @return a new member value pair with the given name and value.
     */
    public static MemberValuePair createMemberValuePair(AST ast, String name, Expression expression) {
        MemberValuePair memberValuePair = ast.newMemberValuePair();
        memberValuePair.setName(ast.newSimpleName(name));
        memberValuePair.setValue(expression);
        return memberValuePair;
    }

    /**
     * Creates a new {@link MemberValuePair} with a {@link StringLiteral} value.
     *
     * @param ast the {@link AST} that will be used to create the member value pair.
     * @param name the member value pair "name".
     * @param value the <code>String</code> "value".
     * @return a new member value pair with the given name and value.
     */
    public static MemberValuePair createStringMemberValuePair(AST ast, String name, String value) {
        MemberValuePair stringMemberValuePair = AnnotationsCore.createMemberValuePair(ast, name,
                AnnotationsCore.createStringLiteral(ast, value.toString()));

        return stringMemberValuePair;
    }

    /**
     * Creates a new {@link MemberValuePair} with a {@link BooleanLiteral} value.
     *
     * @param ast the {@link AST} that will be used to create the member value pair.
     * @param name the member value pair "name".
     * @param value the <code>Boolean</code> "value".
     * @return a new member value pair with the given name and value.
     */
    public static MemberValuePair createBooleanMemberValuePair(AST ast, String name, Boolean value) {
        MemberValuePair booleanValuePair = AnnotationsCore.createMemberValuePair(ast, name, AnnotationsCore
                .createBooleanLiteral(ast, value.booleanValue()));

        return booleanValuePair;
    }

    /**
     * Creates a new {@link MemberValuePair} with a {@link NumberLiteral} value.
     *
     * @param ast the {@link AST} that will be used to create the member value pair.
     * @param name the member value pair "name".
     * @param value the <code>String</code> "value" representing the number.
     * @return a new member value pair with the given name and value.
     */
    public static MemberValuePair createNumberMemberValuePair(AST ast, String name, String value) {
        MemberValuePair primitiveValuePair = AnnotationsCore.createMemberValuePair(ast, name,
                AnnotationsCore.createNumberLiteral(ast, value.toString()));
        return primitiveValuePair;
    }

    /**
     * Creates a new {@link MemberValuePair} with a {@link Name} value.
     *
     * @param ast the {@link AST} that will be used to create the member value pair.
     * @param className the enclosing class name.
     * @param name the member value pair "name".
     * @param value an object representing the <code>enum</code> value.
     * @return a new member value pair with the given name and value.
     */
    public static MemberValuePair createEnumMemberValuePair(AST ast, String className, String name, Object value) {
        if (value instanceof IField) {
            return AnnotationsCore.createMemberValuePair(ast, name, createEnumLiteral(ast, className, (IField) value));
        }
        return AnnotationsCore.createMemberValuePair(ast, name, createEnumLiteral(ast, className, value));
    }

    /**
     * Creates a new {@link MemberValuePair} with a {@link TypeLiteral} value.
     *
     * @param ast the {@link AST} that will be used to create the member value pair.
     * @param name the member value pair "name".
     * @param value an object representing the type value.
     * @return a new member value pair with the given name and value.
     */
    public static MemberValuePair createTypeMemberValuePair(AST ast, String name, Object value) {
        return AnnotationsCore.createMemberValuePair(ast, name,
                createTypeLiteral(ast, value));
    }

    /**
     * Creates a new {@link MemberValuePair} with an {@link ArrayInitializer} value.
     *
     * @param ast the {@link AST} that will be used to create the member value pair.
     * @param name the member value pair "name".
     * @param values an array of <code>Object</code> values. Supports  {@link java.lang.String}, {@link java.lang.Boolean}, {@link java.lang.Class} values.
     * @return a new member value pair with the given name and array of values.
     */
    public static MemberValuePair createArrayMemberValuePair(AST ast, String name, Object[] values) {
        return AnnotationsCore.createMemberValuePair(ast, name, createArrayValueLiteral(ast, values));
    }

    /**
     * Creates a new {@link ArrayInitializer}.
     * @param ast the {@link AST} that will be used to create the {@link ArrayInitializer}.
     * @param values an array of <code>Object</code> values. Supports  {@link java.lang.String}, {@link java.lang.Boolean}, {@link java.lang.Class} values.
     * @return a new srray initializer.
     */
    @SuppressWarnings("unchecked")
    public static ArrayInitializer createArrayValueLiteral(AST ast, Object[] values) {
        ArrayInitializer arrayInitializer = ast.newArrayInitializer();
        for (Object value : values) {
            if (value.equals(Class.class)) {
                arrayInitializer.expressions().add(createTypeLiteral(ast, value.toString()));
            }

            if (value instanceof String) {
                String stringValue = value.toString();
                if (stringValue.endsWith(".class")) { //$NON-NLS-1$
                    arrayInitializer.expressions().add(createTypeLiteral(ast, stringValue.substring(0,
                            stringValue.lastIndexOf(".")))); //$NON-NLS-1$
                } else {
                    arrayInitializer.expressions().add(createStringLiteral(ast, stringValue));
                }
            }

            if (value.equals(Boolean.TYPE)) {
                arrayInitializer.expressions().add(createBooleanLiteral(ast, (Boolean) value));
            }

        }
        return arrayInitializer;
    }

    /**
     * Creates a new {@link QualifiedName} to represent an <code>enum</code> literal value.
     *
     * @param ast the {@link AST} that will be used to create the qualified name.
     * @param className the fully qualified name of the enclosing class.
     * @param value an object representing the <code>enum</code> value.
     * @return a new qualified name.
     */
    public static QualifiedName createEnumLiteral(AST ast, String className, Object value) {
        QualifiedName enumName = null;
        SimpleName enumClassName = ast.newSimpleName(value.getClass().getSimpleName());
        SimpleName enumLiteral = ast.newSimpleName(value.toString());
        if (value.getClass().isMemberClass()) {
            Name enumEnclosingClassName = null;
            String enclosingClassName = value.getClass().getEnclosingClass().getCanonicalName();
            if (enclosingClassName.equals(className)) {
                enumEnclosingClassName = ast.newSimpleName(value.getClass().getEnclosingClass()
                        .getSimpleName());
            } else {
                enumEnclosingClassName = ast.newName(enclosingClassName);
            }
            QualifiedName qualifiedName = ast.newQualifiedName(enumEnclosingClassName, enumClassName);
            enumName = ast.newQualifiedName(qualifiedName, enumLiteral);
        } else {
            Name qualifiedName = ast.newName(value.getClass().getCanonicalName());
            enumName = ast.newQualifiedName(qualifiedName, enumLiteral);
        }
        return enumName;
    }

    /**
     * Creates a new {@link QualifiedName} to represent an <code>enum</code> literal value.
     *
     * @param ast the {@link AST} that will be used to create the qualified name.
     * @param className the fully qualified name of the enclosing class.
     * @param enumConstant a {@link org.eclipse.jdt.core.IField} where <code>isEnumConstant()</code> is <code>true</code>.
     * @return a new qualified name.
     * @throws JavaModelException
     * @since 1.1
     */
    public static QualifiedName createEnumLiteral(AST ast, String className, IField enumConstant) {
        QualifiedName enumName = null;
        SimpleName enumClassName = ast.newSimpleName(enumConstant.getDeclaringType().getElementName());
        SimpleName enumLiteral = ast.newSimpleName(enumConstant.getElementName());
        try {
            if (enumConstant.getDeclaringType().isMember()) {
                Name enumEnclosingClassName = null;
                String enclosingClassName = enumConstant.getDeclaringType().getDeclaringType().getFullyQualifiedName();
                if (enclosingClassName.equals(className)) {
                    enumEnclosingClassName = ast.newSimpleName(enumConstant.getDeclaringType().getDeclaringType().getElementName());
                } else {
                    enumEnclosingClassName = ast.newName(enclosingClassName);
                }
                QualifiedName qualifiedName = ast.newQualifiedName(enumEnclosingClassName, enumClassName);
                enumName = ast.newQualifiedName(qualifiedName, enumLiteral);
            } else {
                Name qualifiedName = ast.newName(enumConstant.getDeclaringType().getFullyQualifiedName());
                enumName = ast.newQualifiedName(qualifiedName, enumLiteral);
            }
        } catch (JavaModelException jme) {
            AnnotationsCorePlugin.log(jme.getStatus());
        }
        return enumName;
    }

    /**
     * Creates a new {@link TypeLiteral}.
     *
     * @param ast the {@link AST} that will be used to create the {@link TypeLiteral}.
     * @param value a <code>Class</code> or a <code>String</code> from which to create the type literal.
     * @return a new type literal or null it the value is not of type <code>Class</code> or <code>String</code>.
     */
    public static TypeLiteral createTypeLiteral(AST ast, Object value) {
        TypeLiteral typeLiteral = null;
        if (value instanceof Class<?>) {
            typeLiteral = createTypeLiteral(ast, (Class<?>) value);
        }
        if (value instanceof String) {
            typeLiteral = createTypeLiteral(ast, (String) value);
        }
        return typeLiteral;
    }

    /**
     * Creates a new {@link TypeLiteral}.
     *
     * @param ast the {@link AST} that will be used to create the type literal.
     * @param value the class value.
     * @return a new type literal.
     */
    public static TypeLiteral createTypeLiteral(AST ast, Class<?> value) {
        TypeLiteral typeLiteral = ast.newTypeLiteral();

        Class<?> aClass = value;
        SimpleName className = ast.newSimpleName(aClass.getSimpleName());

        if (aClass.isMemberClass()) {
            Name enclosingClassName = ast.newName(aClass.getEnclosingClass().getCanonicalName());
            QualifiedType qualifiedType = ast.newQualifiedType(ast.newSimpleType(enclosingClassName), className);
            typeLiteral.setType(qualifiedType);
            return typeLiteral;
        }
        return createTypeLiteral(ast, value.getCanonicalName());
    }

    /**
     * Creates a new {@link TypeLiteral}
     *
     * @param ast the {@link AST} that will be used to create the type literal.
     * @param value the name of the class.
     * @return a new type literal.
     */
    public static TypeLiteral createTypeLiteral(AST ast, String value) {
        TypeLiteral typeLiteral = ast.newTypeLiteral();

        if (value.indexOf(".") == -1) { //$NON-NLS-1$
            typeLiteral.setType(ast.newSimpleType(ast.newSimpleName(value)));
        } else {
            String qualifier = value.substring(0, value.lastIndexOf(".")); //$NON-NLS-1$
            String identifier = value.substring(value.lastIndexOf(".") + 1); //$NON-NLS-1$
            if (qualifier.equals("java.lang")) { //$NON-NLS-1$
                typeLiteral.setType(ast.newSimpleType(ast.newSimpleName(identifier)));
            } else {
                typeLiteral.setType(ast.newQualifiedType(ast.newSimpleType(ast.newName(qualifier)), ast
                        .newSimpleName(identifier)));
            }
        }
        return typeLiteral;
    }

    /**
     * Creates a new {@link StringLiteral}.
     * @param ast the {@link AST} that will be used to create the string literal.
     * @param literalValue the string value.
     * @return a new string literal.
     */
    public static StringLiteral createStringLiteral(AST ast, String literalValue) {
        StringLiteral stringLiteral = ast.newStringLiteral();
        stringLiteral.setLiteralValue(literalValue);
        return stringLiteral;
    }

    /**
     * Creates a new {@link BooleanLiteral}.
     * @param ast the {@link AST} that will be used to create the boolean literal.
     * @param value the boolean value.
     * @return a new boolean literal.
     */
    public static BooleanLiteral createBooleanLiteral(AST ast, boolean value) {
        BooleanLiteral booleanLiteral = ast.newBooleanLiteral(value);
        return booleanLiteral;
    }

    /**
     * Creates a new {@link NumberLiteral}.
     * @param ast the {@link AST} that will be used to create the {@link NumberLiteral}.
     * @param value the number value.
     * @return a new number literal.
     */
    public static NumberLiteral createNumberLiteral(AST ast, String value) {
        NumberLiteral primitiveLiteral = ast.newNumberLiteral();
        primitiveLiteral.setToken(value);
        return primitiveLiteral;
    }
}
