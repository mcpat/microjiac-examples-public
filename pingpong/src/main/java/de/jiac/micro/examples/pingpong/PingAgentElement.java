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

import org.slf4j.Logger;

import de.jiac.micro.agent.AbstractActiveBehaviour;
import de.jiac.micro.agent.handle.ICommunicationHandle;
import de.jiac.micro.core.IAgent;
import de.jiac.micro.core.ILifecycleAware;
import de.jiac.micro.core.io.IMessage;
import de.jiac.micro.core.io.IMulticastAddress;
import de.jiac.micro.core.scope.AgentScope;

/**
 * PING agent element.
 * sendet alle x Sekunden eine Ping Nachricht raus. (daher wird dieses Agentenelement von AbstractActiveBehaviour vererbt. Siehe auch die Klasse Pong.)
 * 
 * @author Erdene-Ochir Tuguldur
 */
public class PingAgentElement extends AbstractActiveBehaviour implements ILifecycleAware {
	/*
	 * Alle 3 Sekunden wird diese Funktion aufgerufen.
	 */
	public void runShort() {
		_logger.debug("sending a PING message...");
		sendPingMessage();
	}
	
	private void sendPingMessage() {
        String content = "This is a PING message!";
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
		// Referenz auf den Agenten
		IAgent agent = AgentScope.getAgentReference();
		// Referenz auf die Kommunikationsschnittstelle
		_communicationHandle = (ICommunicationHandle) agent.getHandle(ICommunicationHandle.class);
		// Referenz auf den Logger
		_logger = agent.getLogger();
		
		// Wir wollen jetzt die Gruppe "pingpong" eintretten, damit wir dem Pong Agenten kommunizieren koennen.		
		try {
			// Der Gruppenname ist in der Agentendefination definiert. Siehe resources/config/define.xml
			String groupName = (String) agent.getProperty(IPingPongConstants.GROUP_NAME_KEY);
			_address = _communicationHandle.getMulticastAddressForName(groupName);
			_communicationHandle.joinGroup(_address);
		} catch (IOException e) {
			_logger.error("Could not join the pingpong group", e);
		}
//		
//		// Alle 3 Sekunden soll eine Ping Nachricht geschickt werden.
//		setPeriod(3000);
		
		super.start();
	}

	public void stop() {
		super.stop();
		
		// Der Agent faehrt runter -> wir brauchen die Gruppe nicht mehr.
		try {
			_communicationHandle.leaveGroup(_address);
		} catch (IOException e) {
			_logger.error("Could not leave the pingpong group", e);
		}
	}
}
