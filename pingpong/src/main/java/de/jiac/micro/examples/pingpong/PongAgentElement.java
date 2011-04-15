/*
 * MicroJIAC - A Lightweight Agent Framework
 * This file is part of Example: PingPong.
 *
 * Copyright (c) 2007-2011 DAI-Labor, Technische Universität Berlin
 *
 * This library includes software developed at DAI-Labor, Technische
 * Universität Berlin (http://www.dai-labor.de)
 *
 * This library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this library.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.jiac.micro.examples.pingpong;

import java.io.IOException;
import java.util.Enumeration;

import org.slf4j.Logger;

import de.jiac.micro.agent.AbstractReactiveBehaviour;
import de.jiac.micro.agent.handle.ICommunicationHandle;
import de.jiac.micro.core.IAgent;
import de.jiac.micro.core.ILifecycleAware;
import de.jiac.micro.core.io.IMessage;
import de.jiac.micro.core.io.IMulticastAddress;
import de.jiac.micro.core.scope.AgentScope;

/**
 * PONG agent element.
 * Reagiert nur auf Nachrichten. (daher wird dieses Agentenelement als AbstractReactiveBehabiour implementiert. Siehe auch die Klasse Ping.)
 * 
 * @author Erdene-Ochir Tuguldur
 */
public class PongAgentElement extends AbstractReactiveBehaviour implements ILifecycleAware {
	/*
	 * Wir wollen auf Nachrichten (IMessage) hoeren!
	 */
	protected Class[] filterDataTypes() {
		return new Class[] {IMessage.class};
	}

	/*
	 * Wenn der Agent eine Nachricht empfaengt wird diese Funktion aufgerufen.
	 */
	protected void runShort(Enumeration sensorReadings) {
		while (sensorReadings.hasMoreElements()) {
			IMessage message = (IMessage) sensorReadings.nextElement();
			Object content = message.getContent();
			_logger.info("a message received: " + content);
			_logger.info("now sending a PONG message...");
			sendPongMessage();
		}
	}
	   
    private void sendPongMessage() {
        String content = "This is a PONG message!";
        IMessage message = _communicationHandle.createMessage();
        message.setContent(content);
        
        try {
            _communicationHandle.sendMessage(_address, message);
        } catch (IOException e) {
            _logger.error("Could not send the message to the pingpong group", e);
        }
    }
	
	private ICommunicationHandle _communicationHandle;
	private Logger _logger;
	
	private IMulticastAddress _address;

	public void start() {
        super.start();
        
		// Referenz auf den Agenten
		IAgent agent = AgentScope.getAgentReference();
		// Referenz auf die Kommunikationsschnittstelle
		_communicationHandle = (ICommunicationHandle) agent.getHandle(ICommunicationHandle.class);
		// Referenz auf den Logger
		_logger = agent.getLogger();
		
		// Wir wollen jetzt die Gruppe "pingpong" eintretten, damit wir dem Ping Agenten kommunizieren koennen.
		try {
			// Der Gruppenname ist in der Agentendefination definiert. Siehe resources/config/common.xml
			String groupName = (String) agent.getProperty(IPingPongConstants.GROUP_NAME_KEY);
			_address = _communicationHandle.getMulticastAddressForName(groupName);
			_communicationHandle.joinGroup(_address);
		} catch (IOException e) {
			_logger.error("Could not join the pingpong group", e);
		}
	}

	public void stop() {
		// Der Agent faehrt runter -> wir brauchen die Gruppe nicht mehr.
		try {
			_communicationHandle.leaveGroup(_address);
		} catch (IOException e) {
			_logger.error("Could not leave the pingpong group", e);
		}
		
		super.stop();
	}
}
