package com.oracle.medrec.common.messaging;

/**
 * @author Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 */
public interface OutgoingMessage extends JmsMessage {

  void setPayload(Object payload);

  void clearPayload();

}
