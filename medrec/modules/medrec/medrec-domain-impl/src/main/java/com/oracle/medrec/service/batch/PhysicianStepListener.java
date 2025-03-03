package com.oracle.medrec.service.batch;

import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Physician step listener.
 *
 * @author Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 *         Created by xiaojwu on 26/11/14.
 */
@Named("PhysicianStepListener")
public class PhysicianStepListener extends AbstractReportStepListener {

  @Inject
  private PhysicianSumCache physicianSumCache;

  @Override
  public void initExtraUserData() throws Exception {
    stepContext.setTransientUserData(physicianSumCache.getCache(seq));
  }

  @Override
  public void cleanExtraUserData() throws Exception {
    physicianSumCache.release(seq);
  }

  @Override
  protected BatchFinishedUpEvent.Type getType() {
    return BatchFinishedUpEvent.Type.PHYSICIAN;
  }
}
