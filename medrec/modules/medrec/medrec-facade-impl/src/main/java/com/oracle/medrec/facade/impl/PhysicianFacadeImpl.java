package com.oracle.medrec.facade.impl;

import com.oracle.medrec.common.core.MethodParameterValidated;
import com.oracle.medrec.common.core.ThrowableLogged;
import com.oracle.medrec.facade.PhysicianFacade;
import com.oracle.medrec.facade.model.AuthenticatedPhysician;
import com.oracle.medrec.model.Physician;
import com.oracle.medrec.service.PhysicianService;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

/**
 * @author Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@MethodParameterValidated
@ThrowableLogged
public class PhysicianFacadeImpl implements PhysicianFacade {

  @Inject
  private PhysicianService physicianService;

  public void setPhysicianService(PhysicianService physicianService) {
    this.physicianService = physicianService;
  }

  public boolean authenticatePhysician(String username, String password) {
    return physicianService.authenticatePhysician(username, password);
  }

  public AuthenticatedPhysician authenticateAndReturnPhysician(String username, String password) {
    Physician physician = physicianService.authenticateAndReturnPhysician(username, password);
    if (physician == null) {
      return null;
    }
    return new AuthenticatedPhysician(physician);
  }
}
