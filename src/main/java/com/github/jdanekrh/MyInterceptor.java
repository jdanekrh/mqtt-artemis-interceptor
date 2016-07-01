package com.github.jdanekrh;

import org.apache.activemq.artemis.api.core.ActiveMQException;
import org.apache.activemq.artemis.api.core.Interceptor;
import org.apache.activemq.artemis.core.protocol.core.Packet;
import org.apache.activemq.artemis.spi.core.protocol.RemotingConnection;

public class MyInterceptor implements Interceptor {
      @Override
      public boolean intercept(Packet packet, RemotingConnection connection) throws ActiveMQException {
           System.out.println("Packet intercepted");
           return true;
      }
}
