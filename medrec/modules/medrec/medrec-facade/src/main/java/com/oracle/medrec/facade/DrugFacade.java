package com.oracle.medrec.facade;

import com.oracle.medrec.facade.model.DrugInfo;

import jakarta.ejb.Local;
import java.util.List;

/**
 * @author Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 */
@Local
public interface DrugFacade {

  List<DrugInfo> getAllDrugsInfo();
}
