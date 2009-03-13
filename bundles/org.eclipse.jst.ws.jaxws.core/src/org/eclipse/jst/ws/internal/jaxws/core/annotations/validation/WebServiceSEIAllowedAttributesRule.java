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
package org.eclipse.jst.ws.internal.jaxws.core.annotations.validation;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.eclipse.jst.ws.annotations.core.processor.AbstractAnnotationProcessor;
import org.eclipse.jst.ws.internal.jaxws.core.JAXWSCoreMessages;

import com.sun.mirror.apt.Messager;
import com.sun.mirror.declaration.AnnotationMirror;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;
import com.sun.mirror.declaration.AnnotationTypeElementDeclaration;
import com.sun.mirror.declaration.AnnotationValue;
import com.sun.mirror.declaration.Declaration;
import com.sun.mirror.declaration.InterfaceDeclaration;

/**
 * 
 * @author sclarke
 *
 */
public class WebServiceSEIAllowedAttributesRule extends AbstractAnnotationProcessor {

    public WebServiceSEIAllowedAttributesRule() {
    }
    
    @Override
    public void process() {
        Messager messager = environment.getMessager();

        AnnotationTypeDeclaration annotationDeclaration = (AnnotationTypeDeclaration) environment
                .getTypeDeclaration("javax.jws.WebService"); //$NON-NLS-1$

        Collection<Declaration> annotatedTypes = environment
                .getDeclarationsAnnotatedWith(annotationDeclaration);

        for (Declaration declaration : annotatedTypes) {
            Collection<AnnotationMirror> annotationMirrors = declaration.getAnnotationMirrors();

            for (AnnotationMirror mirror : annotationMirrors) {
                Map<AnnotationTypeElementDeclaration, AnnotationValue> valueMap = mirror.getElementValues();
                Set<Map.Entry<AnnotationTypeElementDeclaration, AnnotationValue>> valueSet = valueMap
                        .entrySet();
                for (Map.Entry<AnnotationTypeElementDeclaration, AnnotationValue> annotationKeyValue : valueSet) {
                    if (declaration instanceof InterfaceDeclaration) {
                        if (annotationKeyValue.getKey().getSimpleName().equals("serviceName")) { //$NON-NLS-1$
                            messager.printError(mirror.getPosition(), JAXWSCoreMessages
                                    .WEBSERVICE_ANNOTATION_PROCESSOR_SERVICENAME_SEI_ERROR_MESSAGE);
                        } else if (annotationKeyValue.getKey().getSimpleName().equals("endpointInterface")) { //$NON-NLS-1$
                            messager.printError(mirror.getPosition(), JAXWSCoreMessages
                                    .WEBSERVICE_ANNOTATION_PROCESSOR_ENDPOINTINTERFACE_SEI_ERROR_MESSAGE);
                        } else if (annotationKeyValue.getKey().getSimpleName().equals("portName")) { //$NON-NLS-1$
                            messager.printError(mirror.getPosition(), JAXWSCoreMessages
                                    .WEBSERVICE_ANNOTATION_PROCESSOR_PORTNAME_SEI_ERROR_MESSAGE);
                        }
                    }
                }
            }
        }
    }
    
}