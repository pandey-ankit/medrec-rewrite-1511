package com.oracle.medrec.facade.broker.jaxrs;

import com.oracle.medrec.facade.DrugFacade;
import com.oracle.medrec.facade.model.DrugInfo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Logger;

/**
 * Physician Facade's jax-rs broker.
 *
 * @author Xiaojun Wu. <br>
 *         Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 */
@ApplicationScoped
@Path("/drug")
public class JaxRsDrugFacadeBroker {

  private static final Logger LOGGER = Logger.getLogger(JaxRsDrugFacadeBroker.class.getName());

  @Inject
  private DrugFacade drugFacade;

  @GET
  @Produces(MediaType.APPLICATION_XML)
  public List<DrugInfo> authenticateAndReturnPhysician() {
    LOGGER.finer("Get drugs.");
    return drugFacade.getAllDrugsInfo();
  }

}
