package com.oracle.medrec.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

/**
 * Base class from which every concrete entity requiring version for optimistic
 * concurrency control inherits.
 *
 * @author Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 */
@MappedSuperclass
public abstract class VersionedEntity extends BaseEntity {

  /**
   * Field used by JPA's optimistic concurrency control
   */
  @Version
  private Integer version;

  public Integer getVersion() {
    return version;
  }

  protected void setVersion(Integer version) {
    this.version = version;
  }
}
