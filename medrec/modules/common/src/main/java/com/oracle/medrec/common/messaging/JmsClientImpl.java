package com.oracle.medrec.common.messaging;

import jakarta.ejb.EJB;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.jms.DeliveryMode;
import jakarta.jms.Destination;
import jakarta.jms.JMSConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.JMSProducer;
import jakarta.jms.JMSRuntimeException;
import jakarta.jms.Message;

/**
 * @author Copyright (c) 2007, 2019, Oracle and/or its
 *         affiliates. All rights reserved.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Local(JmsClient.class)
public class JmsClientImpl implements JmsClient {

  @EJB
  private MessageComposer messageComposer;

  @Inject
  @JMSConnectionFactory("com.oracle.medrec.jms.connectionFactory")
  private JMSContext context;

  private int defaultDeliveryMode = Message.DEFAULT_DELIVERY_MODE;
  private int defaultPriority = Message.DEFAULT_PRIORITY;
  private long defaultTimeToLive = Message.DEFAULT_TIME_TO_LIVE;

  @Override
  public boolean isDefaultPersistent() {
    return defaultDeliveryMode == DeliveryMode.PERSISTENT;
  }

  @Override
  public void setDefaultPersistent(boolean persistent) {
    this.defaultDeliveryMode = persistent ? DeliveryMode.PERSISTENT : DeliveryMode.NON_PERSISTENT;
  }

  @Override
  public int getDefaultPriority() {
    return defaultPriority;
  }

  @Override
  public void setDefaultPriority(int priority) {
    this.defaultPriority = priority;
  }

  @Override
  public long getDefaultTimeToLive() {
    return defaultTimeToLive;
  }

  @Override
  public void setDefaultTimeToLive(long timeToLive) {
    this.defaultTimeToLive = timeToLive;
  }

  @Override
  public String send(Destination destination, Object payload) {
    return send(destination, payload, defaultDeliveryMode, defaultPriority, defaultTimeToLive);
  }

  @Override
  public String send(Destination destination, Object payload, int deliveryMode, int priority, long timeToLive) {
    return send(destination, createOutgoingMessage(payload), defaultDeliveryMode, defaultPriority, defaultTimeToLive);
  }

  @Override
  public String send(Destination destination, OutgoingMessage outgoingMessage) {
    return send(destination, outgoingMessage, defaultDeliveryMode, defaultPriority, defaultTimeToLive);
  }

  public String send(Destination destination, OutgoingMessage outgoingMessage, int deliveryMode, int priority,
                     long timeToLive) {
    try {
      Message message = convertMessage(context, outgoingMessage);
      JMSProducer producer = context.createProducer();
      producer.setDeliveryMode(deliveryMode);
      producer.setPriority(priority);
      producer.setTimeToLive(timeToLive);
      producer.send(destination, message);
      return message.getJMSMessageID();
    } catch (JMSRuntimeException | JMSException e) {
      throw new MessageException("Cannot send JMS message", e);
    }
  }

  @Override
  public OutgoingMessage createOutgoingMessage(Object payload) {
    OutgoingMessage outgoingMessage = new OutgoingMessageImpl();
    outgoingMessage.setPayload(payload);
    return outgoingMessage;
  }

  protected Message convertMessage(JMSContext context, OutgoingMessage outgoingMessage) throws JMSException {
    Message message = messageComposer.composeMessage(context, outgoingMessage.getPayload());

    if (outgoingMessage.getJmsCorrelationId() != null) {
      message.setJMSCorrelationID(outgoingMessage.getJmsCorrelationId());
    }
    if (outgoingMessage.getJmsCorrelationIdAsBytes() != null) {
      message.setJMSCorrelationIDAsBytes(outgoingMessage.getJmsCorrelationIdAsBytes());
    }
    if (outgoingMessage.getJmsReplyTo() != null) {
      message.setJMSReplyTo(outgoingMessage.getJmsReplyTo());
    }
    for (String propertyName : outgoingMessage.getPropertyNames()) {
      message.setObjectProperty(propertyName, outgoingMessage.getProperty(propertyName));
    }
    return message;
  }

}
