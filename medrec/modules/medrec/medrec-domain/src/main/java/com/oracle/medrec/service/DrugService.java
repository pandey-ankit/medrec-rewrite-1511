package com.oracle.medrec.service;

import com.oracle.medrec.model.Drug;

import jakarta.ejb.Local;
import java.util.List;

/**
 * Drug business service interface.
 *
 * @author Xiaojun Wu. <br>
 *         Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 */
@Local
public interface DrugService {

  /**
   * Retrieves all drug's information.
   */
  List<Drug> getAllDrugsInfo();

}
