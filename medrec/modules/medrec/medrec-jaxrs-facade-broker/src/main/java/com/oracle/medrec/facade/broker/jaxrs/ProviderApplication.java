package com.oracle.medrec.facade.broker.jaxrs;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Date: 2014-2-11
 *
 * @author Xiaojun Wu. <br>
 *         Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 */
@ApplicationPath("resources")
public class ProviderApplication extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    final Set<Class<?>> classes = new HashSet<Class<?>>();
    classes.add(JaxRsPhysicianFacadeBroker.class);
    classes.add(JaxRsRecordFacadeBroker.class);
    classes.add(JaxRsDrugFacadeBroker.class);
    classes.add(ProviderExceptionMapper.class);
    return classes;
  }

}
