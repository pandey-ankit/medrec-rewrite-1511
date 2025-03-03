package com.oracle.medrec.service.impl.notification;

import com.oracle.medrec.common.mail.MailClient;
import com.oracle.medrec.common.mail.MailClientImpl;
import com.oracle.medrec.model.Patient;
import com.oracle.medrec.model.PersonName;
import org.junit.jupiter.api.Test;

import static org.easymock.EasyMock.*;

/**
 * {@link PatientNotifierImpl} test case.
 *
 * @author Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 * @since Jul 16, 2007
 */
public class PatientNotifierImplTestCase {
  
  @Test
  public void notifyPatient() {
    MailClient mg = new MailClientImpl();
    Patient patient = new Patient();
    patient.setEmail("foo@foo.com");
    patient.setStatus(Patient.Status.APPROVED);
    patient.setName(new PersonName());
    PatientNotifierImpl impl = new PatientNotifierImpl();
    impl.setMailGateway(mg);
    impl.notifyPatient(patient);
  }
}
