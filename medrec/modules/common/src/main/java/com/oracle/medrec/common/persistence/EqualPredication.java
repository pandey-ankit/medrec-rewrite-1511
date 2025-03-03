package com.oracle.medrec.common.persistence;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.metamodel.SingularAttribute;

/**
 * A predicate for testing the arguments for equality, namely '=' SQL.
 *
 * @author Xiaojun Wu. <br>
 *         Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 */
public class EqualPredication extends Predication<Object> {

  public EqualPredication(Object value, String... attribute) {
    super(value, attribute);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public EqualPredication(Object value, SingularAttribute... attribute) {
    super(value, attribute);
  }

  @Override
  protected Predicate createPredicate(CriteriaBuilder cb) {
    return cb.equal(path, value);
  }
}
