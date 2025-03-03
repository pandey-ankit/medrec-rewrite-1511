package com.oracle.medrec.facade;

import com.oracle.medrec.facade.model.RecordDetail;
import com.oracle.medrec.facade.model.RecordSummary;
import com.oracle.medrec.facade.model.RecordToCreate;

import jakarta.ejb.Local;

/**
 * @author Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 */
@Local
public interface RecordFacade {

  void createRecord(RecordToCreate record);

  RecordSummary getRecordSummaryByPatientId(Long patientId);

  RecordDetail getRecordDetail(Long id);
}
