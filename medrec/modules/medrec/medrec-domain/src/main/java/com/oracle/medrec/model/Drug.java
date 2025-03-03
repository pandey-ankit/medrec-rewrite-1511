package com.oracle.medrec.model;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author : xiaojwu.
 *         Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 */
@Entity
@Table(name = "drugs")
@NamedQuery(name = "Drug.findAllDrugs", query = "SELECT d FROM Drug d ORDER BY d.id")
public class Drug extends VersionedEntity {

  @Serial
  private static final long serialVersionUID = 3610658213331393425L;

  @NotNull
  private String name;

  @NotNull
  private BigDecimal price;

  public void setID(long id) {
    super.setId(id);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  // todo producer mock. It needs to add Producer column and table
  public String getProducer() {
    return "Oracle";
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

}
