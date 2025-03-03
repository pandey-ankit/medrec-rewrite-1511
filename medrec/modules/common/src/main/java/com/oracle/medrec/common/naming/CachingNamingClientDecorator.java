package com.oracle.medrec.common.naming;

import com.oracle.medrec.common.core.MethodInvocationCached;

import jakarta.annotation.Priority;
import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;

import static jakarta.interceptor.Interceptor.Priority.APPLICATION;

/**
 * A CDI Decorator. Cache any implementations of {@link NamingClient} to improve performance.
 *
 * @author : xiaojwu.
 *         Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 * @see MethodInvocationCached
 */
@Decorator
public class CachingNamingClientDecorator implements NamingClient {

  @Inject
  @Delegate
  @Any
  private NamingClient namingClient;

  public void setNamingClient(NamingClient namingClient) {
    this.namingClient = namingClient;
  }

  @MethodInvocationCached
  public <T> T lookup(Class<T> clazz, String name) {
    return namingClient.lookup(clazz, name);
  }
}
