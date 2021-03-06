/*************************************************************************************
 * Copyright (c) 2012 Red Hat, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     JBoss by Red Hat - Initial implementation.
 ************************************************************************************/
package org.switchyard.tools.ui.validation;

import org.eclipse.core.runtime.IStatus;

/**
 * ValidationProblem
 * 
 * <p/>
 * Problem codes associated with validation errors/warnings.
 */
public enum ValidationProblem {

    /** OK. Ensures ordinal 0 (i.e. status code 0) reflects OK. */
    OK("No problem.", IStatus.OK, false),
    /** The name used for the service or reference is not unique. */
    DuplicateName("Duplicate Name: The name \"{0}\" must be unique among services, references and component services.",
            false),
    /**
     * The promoted component reference's name must match the reference
     * promoting it.
     */
    PromotedReferenceNameMismatch(
            "Promoted Reference Name Mismatch: The component reference must have the same name as the reference promoting it ({0}).",
            true),
    /** The component reference resolves to multiple services/references. */
    AmbiguousReference(
            "Ambiguous Reference: Cannot resolve a service for component reference \"{0}\" (i.e. resolves to multiple services).",
            false),
    /** The component reference cannot be resolved to a service or reference. */
    UnresolvedReference("Unresolved Reference: The component reference \"{0}\" cannot be resolved to a service.", true),
    /** The reference is not used by any component references. */
    UnusedReference("Unused Reference: The reference \"{0}\" is unused (i.e. it promotes no component references).",
            IStatus.WARNING, false),
    /** The service is not bound to any component service. */
    UnboundService(
            "Unbound Service: The service \"{0}\" is not bound to any component service (i.e. it promotes no component service).",
            IStatus.WARNING, false),
    /** The source operation does not existing in the target service/reference. */
    UnboundOperation("Unbound Operation: Cannot resolve operation \"{0}\" on target.", true),
    /** The return value from the target service is ignored. */
    UnusedReturnValue("Unused Return Value: The source operation \"{0}\" is in-only, but the target is in-out.",
            IStatus.WARNING, true),
    /**
     * The message exchange patterns of the source and target service are
     * incompatible.
     */
    IncomptaibleMEP(
            "Incompatible Message Exchange Pattern: The source operation \"{0}\" is in-out, but the target is in-only.",
            true),
    /** The target service throws a fault which is not handled by the source. */
    UnhandledFault("Unhandled fault: The source operation \"{0}\" must declare a fault.", true),
    /** A transformation is missing for the required from/to types. */
    MissingTransformation("Required transformation missing: from=\"{0}\" to=\"{1}\".", true),
    /** The service interface could not be resolved. */
    UnresolvableServiceInterface(
            "Unresolvable Service Interface: The specified interface does not exist on the project classpath.", false),
    /** A failure occurred while trying to load the available transformers. */
    UnableToValidateTransformers(
            "Unable to Validate Tranformers: Could not load declared transformers from output switchyard.xml to validate whether or not required transforms are missing from service interactions.",
            IStatus.WARNING, false);

    /** Used to identify the problem code attribute in IMarker objects. */
    public static final String PROBLEM_CODE = "ValidationProblem.code";

    /**
     * @param statusCode the status code (i.e. ordinal).
     * 
     * @return true if the enum value associated with the status code applies to
     *         connections.
     */
    public static boolean isConnectionProblem(int statusCode) {
        if (statusCode > 0 && statusCode < values().length) {
            return values()[statusCode].isConnectionProblem();
        }
        return false;
    }

    private String _message;
    private int _severity;
    private boolean _connectionProblem;

    private ValidationProblem(String message, int severity, boolean connectionProblem) {
        _message = message;
        _severity = severity;
        _connectionProblem = connectionProblem;
    }

    private ValidationProblem(String message, boolean connectionProblem) {
        this(message, IStatus.ERROR, connectionProblem);
    }

    /**
     * @return true if this problem relates to a connection.
     */
    public boolean isConnectionProblem() {
        return _connectionProblem;
    }

    /**
     * @return the message associated with this problem.
     */
    public String getMessage() {
        return _message;
    }

    /**
     * @return the problem severity
     * @see IStatus#getSeverity()
     */
    public int getSeverity() {
        return _severity;
    }
}
