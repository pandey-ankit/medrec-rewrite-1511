package com.oracle.physician.web.controller;

import com.oracle.physician.service.RecordService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * CreatingRecordController is a JSF ManagedBean that is responsible for
 * creating diagnostic record of a patient by physician.
 *
 * @author Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 */
@Named
@RequestScoped
public class SavingRecordController {

  @Inject
  private RecordService recordService;

  @Inject
  private CreatingRecordController creatingRecordController;

  public String saveRecord() {
    return creatingRecordController.saveRecord(recordService);
  }
}
