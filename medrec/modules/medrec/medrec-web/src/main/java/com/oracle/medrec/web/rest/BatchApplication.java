package com.oracle.medrec.web.rest;

import org.glassfish.jersey.media.sse.SseFeature;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * RESTFul Application
 *
 * @author : xiaojwu.
 *         Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved
 */
@ApplicationPath("admin/batch")
public class BatchApplication extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    final Set<Class<?>> classes = new HashSet();
    classes.add(SseFeature.class);
    classes.add(BatchResource.class);
    classes.add(BatchSSEHandler.class);
    return classes;
  }

}
