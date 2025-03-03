package com.oracle.medrec.common.web;

import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;
import jakarta.servlet.http.HttpServletResponse;
import java.io.Serial;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 */
public class CacheControlPhaseListener implements PhaseListener {

  @Serial
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = Logger.getLogger(CacheControlPhaseListener.class.getName());

  public PhaseId getPhaseId() {
    return PhaseId.RENDER_RESPONSE;
  }

  public void afterPhase(PhaseEvent event) {
  }

  public void beforePhase(PhaseEvent event) {
    FacesContext facesContext = event.getFacesContext();
    HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    response.addHeader("Pragma", "no-cache");
    response.addHeader("Cache-Control", "no-cache");
    response.addHeader("Cache-Control", "must-revalidate");
    response.addHeader("Expires", "Fri, 13 Nov 1981 13:10:00 GMT");
    LOGGER.log(Level.FINER, "Added cache control to HTTP response");
  }
}