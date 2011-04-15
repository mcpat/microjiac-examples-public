/**
 * MicroJIAC - A Lightweight Agent Framework
 * This file is part of Example: Scala.
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

package de.jiac.micro.examples.scala

import de.jiac.micro.agent._
import de.jiac.micro.agent.memory._

class PeriodicScalaAgentElement extends AbstractActiveBehaviour with ISensor {
	var memory: IShortTermMemory = null
	
	override def setShortTermMemory(m: IShortTermMemory): Unit = {
		memory = m
	}
	
	override def start(): Unit = {
		setPeriod(1000)
		super.start()	
	}
	
	override def runShort(): Unit = {
		println("I am an active behaviour!");
		memory.notice(new MyFact());
	}
}