package com.oracle.medrec.model;

import java.io.Serial;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 */
@Entity
@Table(name = "physicians")
public class Physician extends RegularUser {

  @Serial
  private static final long serialVersionUID = 2298019356529544739L;

}
