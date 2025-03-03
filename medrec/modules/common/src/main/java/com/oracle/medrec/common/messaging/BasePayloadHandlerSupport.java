package com.oracle.medrec.common.messaging;

import com.oracle.medrec.common.core.ThrowableLogged;

import jakarta.ejb.EJB;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;

/**
 * @author Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 */
public abstract class BasePayloadHandlerSupport<T> implements MessageListener {

  @EJB
  private MessageComposer messageComposer;

  public void setMessageConverter(MessageComposer messageComposer) {
    this.messageComposer = messageComposer;
  }

  @ThrowableLogged
  public void onMessage(Message message) {
    try {
      doOnMessage(message);
    } catch (JMSException e) {
      // runtime exception to trigger redelivery
      throw new MessageException(e);
    }
  }

  @SuppressWarnings("unchecked")
  protected T extractIncomingPayload(Message message) throws JMSException {
    return (T) messageComposer.extractPayload(message);
  }

  protected abstract void doOnMessage(Message message) throws JMSException;
}
