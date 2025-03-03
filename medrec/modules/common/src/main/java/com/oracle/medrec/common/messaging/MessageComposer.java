package com.oracle.medrec.common.messaging;

import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.Message;

/**
 * A util composing and extracting JMS message.
 *
 * @author Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 */
public interface MessageComposer {

  Message composeMessage(JMSContext context, Object payload) throws JMSException;

  Object extractPayload(Message message) throws JMSException;
}
