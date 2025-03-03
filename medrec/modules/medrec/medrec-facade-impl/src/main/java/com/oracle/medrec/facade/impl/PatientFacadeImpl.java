package com.oracle.medrec.facade.impl;

import com.oracle.medrec.common.core.MethodParameterValidated;
import com.oracle.medrec.common.core.ThrowableLogged;
import com.oracle.medrec.facade.PatientFacade;
import com.oracle.medrec.facade.model.FoundPatient;
import com.oracle.medrec.model.Patient;
import com.oracle.medrec.service.PatientService;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@MethodParameterValidated
@ThrowableLogged
public class PatientFacadeImpl implements PatientFacade {

  @Inject
  private PatientService patientService;

  public void setPatientService(PatientService patientService) {
    this.patientService = patientService;
  }

  public Patient getPatient(Long patientId) {
    return patientService.getPatient(patientId);
  }

  public FoundPatient findApprovedPatientBySsn(String ssn) {
    Patient patient = patientService.findApprovedPatientBySsn(ssn);
    if (patient == null) {
      return null;
    }
    return new FoundPatient(patient);
  }

  public List<FoundPatient> findApprovedPatientsByLastName(String lastName) {
    List<Patient> patients = patientService.findApprovedPatientsByLastName(lastName);
    List<FoundPatient> foundPatients = new LinkedList<>();
    for (Patient patient : patients) {
      foundPatients.add(new FoundPatient(patient));
    }
    return foundPatients;
  }

  public List<FoundPatient> fuzzyFindApprovedPatientsByLastNameAndSsn(String lastName, String ssn) {
    List<Patient> patients = patientService.fuzzyFindApprovedPatientsByLastNameAndSsn(lastName, ssn);
    List<FoundPatient> foundPatients = new LinkedList<>();
    for (Patient patient : patients) {
      foundPatients.add(new FoundPatient(patient));
    }
    return foundPatients;
  }
}
