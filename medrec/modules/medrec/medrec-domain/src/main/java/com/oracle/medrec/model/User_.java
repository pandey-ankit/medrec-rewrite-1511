package com.oracle.medrec.model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * User entity's MetaModel.
 *
 * @author Xiaojun Wu. <br>
 *         Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 */
@StaticMetamodel(User.class)
public class User_ {

  public static volatile SingularAttribute<User, String> username;

  public static volatile SingularAttribute<User, String> password;

  public static volatile SingularAttribute<User, String> email;

}
