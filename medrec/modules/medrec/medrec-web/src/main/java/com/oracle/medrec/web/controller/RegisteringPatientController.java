package com.oracle.medrec.web.controller;

import com.oracle.medrec.model.Patient;
import com.oracle.medrec.service.DuplicateSsnException;
import com.oracle.medrec.service.DuplicateUsernameException;
import com.oracle.medrec.service.PatientService;
import com.oracle.medrec.web.Constants;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 */
@Model
public class RegisteringPatientController extends BaseMedRecPageController {

  @Inject
  private PatientService patientService;

  private Patient patient = new Patient();

  @NotNull
  @Size(min = 6, max = 20)
  private String password;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Patient getPatient() {
    return patient;
  }

  public String registerPatient() {
    if (password != null && !password.equals(patient.getPassword())) {
      getPageContext().addGlobalOnlyErrorMessageWithKey("message.password.notConsistent");
    } else {
      try {
        patientService.createPatient(patient);
        return "viewPatientRegistrationResult";
      } catch (DuplicateUsernameException e) {
        getPageContext().addGlobalOnlyErrorMessageWithKey("message.duplicateUsername");
      } catch (DuplicateSsnException e) {
        getPageContext().addGlobalOnlyErrorMessageWithKey("message.duplicateSsn");
      }
    }
    return Constants.REGISTER_PATIENT;
  }

}
