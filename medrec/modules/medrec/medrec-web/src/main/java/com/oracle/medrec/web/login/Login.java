/**
 * @author Copyright (c) 2008, 2019, Oracle and/or its affiliates. All rights reserved.
 *
 */
package com.oracle.medrec.web.login;

import jakarta.enterprise.inject.Model;
import jakarta.enterprise.inject.Produces;
import jakarta.validation.constraints.NotNull;

/**
 * CDI Model Bean Class.
 */
@Model
public class Login {

  @NotNull
  private String username;

  @NotNull
  private String password;

  @Produces
  @Username
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Produces
  @Password
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
