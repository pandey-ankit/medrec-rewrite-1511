package com.oracle.medrec.common.messaging;

import jakarta.jms.JMSException;
import jakarta.jms.Message;

/**
 * @author Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 */
public abstract class OneWayPayloadHandlerSupport<T> extends BasePayloadHandlerSupport<T> implements PayloadHandler<T> {

  protected void doOnMessage(Message message) throws JMSException {
    handlePayload(extractIncomingPayload(message));
  }
}
