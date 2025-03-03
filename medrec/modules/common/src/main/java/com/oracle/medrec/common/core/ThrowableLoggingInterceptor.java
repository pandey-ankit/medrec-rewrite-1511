package com.oracle.medrec.common.core;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import java.util.logging.Logger;

/**
 * A CDI Interceptor that is mainly for application entry points. Before throwing
 * exceptions to outer world, we firstly write them into logs.
 *
 * @author : xiaojwu.
 *         Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 */
@Interceptor
@ThrowableLogged
public class ThrowableLoggingInterceptor {

  private static final Logger LOGGER = Logger.getLogger(ThrowableLoggingInterceptor.class.getName());

  @Inject
  private ThrowableLogger throwableLogger;

  public void setExceptionLogger(ThrowableLogger throwableLogger) {
    this.throwableLogger = throwableLogger;
  }

  @AroundInvoke
  public Object logException(InvocationContext invocationContext) throws Exception {
    LOGGER.fine("Checking throwable...");
    try {
      return invocationContext.proceed();
    } catch (Throwable t) {
      throwableLogger.log(t);
      if (t instanceof Exception exception) {
        throw exception;
      } else {
        throw (Error) t;
      }
    }
  }
}
