package com.oracle.medrec.common.core;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import java.util.logging.Logger;

/**
 * A CDI Interceptor for validating method's parameters legal or not.
 *
 * @author : xiaojwu.
 *         Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 */
@Interceptor
@MethodParameterValidated
public class MethodParameterValidatingInterceptor {

  private static final Logger LOGGER = Logger.getLogger(MethodParameterValidatingInterceptor.class.getName());

  @Inject
  private MethodParameterValidator methodParameterValidator;

  public void setMethodParameterValidator(MethodParameterValidator methodParameterValidator) {
    this.methodParameterValidator = methodParameterValidator;
  }

  @AroundInvoke
  public Object validateParameters(InvocationContext invocationContext) throws Exception {
    LOGGER.info("Validating method " + invocationContext.getMethod().getName() + " parameters...");
    methodParameterValidator.validateParameters(invocationContext.getMethod(), invocationContext.getParameters());
    return invocationContext.proceed();
  }
}
