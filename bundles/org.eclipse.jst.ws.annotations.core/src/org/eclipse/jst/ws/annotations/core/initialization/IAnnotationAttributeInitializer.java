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
package org.eclipse.jst.ws.annotations.core.initialization;

import java.util.List;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

/**
 * Base interface for annotation attribute initializers.
 * <p>
 * <strong>Provisional API:</strong> This class/interface is part of an interim API that is still under
 * development and expected to change significantly before reaching stability. It is being made available at
 * this early stage to solicit feedback from pioneering adopters on the understanding that any code that uses
 * this API will almost certainly be broken (repeatedly) as the API evolves.
 * </p>
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IAnnotationAttributeInitializer {

    /**
     * Returns a list of {@link MemberValuePair} that may be added to a {@link org.eclipse.jdt.core.dom.NormalAnnotation}.
     * @param javaElement the java element on which the annotation is declared.
     * @param ast the <code>AST</code> with which to create the member value pairs.
     * @param annotationClass the {@link java.lang.annotation.Annotation} class which may be
     * used to query the declared members of the annotation type and the members default values.
     * @return a list of member value pairs.
     * @deprecated as of 1.1 replaced by {@link #getMemberValuePairs(IJavaElement, AST, IType)}
     */
    @Deprecated
    public List<MemberValuePair> getMemberValuePairs(IJavaElement javaElement, AST ast,
            Class<? extends java.lang.annotation.Annotation> annotationClass);

    /**
     * Returns a list of {@link MemberValuePair} that may be added to a {@link org.eclipse.jdt.core.dom.NormalAnnotation}.
     * @param javaElement the java element on which the annotation is declared.
     * @param ast the <code>AST</code> with which to create the member value pairs.
     * @param type the {@link org.eclipse.jdt.core.IType} which represents an annotation type,.
     * @return a list of member value pairs.
     * @since 1.1
     */
    public List<MemberValuePair> getMemberValuePairs(IJavaElement javaElement, AST ast, IType type);

    /**
     * Used to provide a list of {@link ICompletionProposal} for a {@link MemberValuePair} value.
     * @param javaElement the java element on which the annotation is declared.
     * @param memberValuePair the member value pair in which content assist was invoked.
     * @return a list of completion proposals.
     */
    public List<ICompletionProposal> getCompletionProposalsForMemberValuePair(IJavaElement javaElement,
            MemberValuePair memberValuePair);

    /**
     * Used to provide a list of {@link ICompletionProposal} for a {@link SingleMemberAnnotation} value.
     * @param javaElement the java element on which the annotation is declared.
     * @param singleMemberAnnotation the single member annotation in which content assist was invoked.
     * @return a list of completion proposals.
     */
    public List<ICompletionProposal> getCompletionProposalsForSingleMemberAnnotation(IJavaElement javaElement,
            SingleMemberAnnotation singleMemberAnnotation);


}
