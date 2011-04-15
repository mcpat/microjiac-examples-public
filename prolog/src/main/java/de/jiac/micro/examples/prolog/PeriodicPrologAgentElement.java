/*
 * MicroJIAC - A Lightweight Agent Framework
 * This file is part of Example: Prolog-Example.
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

package de.jiac.micro.examples.prolog;

import java.io.IOException;

import org.prolog4j.Prover;
import org.prolog4j.ProverFactory;
import org.prolog4j.Solution;
import org.prolog4j.SolutionIterator;

import de.jiac.micro.agent.AbstractActiveBehaviour;

public class PeriodicPrologAgentElement extends AbstractActiveBehaviour {
	private Prover _prover;
	private int _counter;
	
	public void initialise() {
		super.initialise();
		_prover = ProverFactory.getProver();
		// hier kann man verschiedene Libraries innerhalb einer Engine auswaehlen
		_prover.loadLibrary("alice.tuprolog.lib.BasicLibrary");
		try {
			// Prolog Datei einbinden
			_prover.loadTheory(PeriodicPrologAgentElement.class.getResourceAsStream("likes.pl"));
		} catch (IOException e) {			
			e.printStackTrace();
		}
		_counter = 0;
		setPeriod(2000);
	}

	@Override
	protected void runShort() {
		Solution<String> sol = _prover.solve("likes(X, Y).").on("X");
        // Does somebody like MicroJIAC?
        System.out.println("Mag irgendjemand irgendetwas? " + sol.isSuccess());
        // Who likes what?
        SolutionIterator<String> si= sol.iterator();
        while(si.hasNext()) {
            String who= si.next();
            String what= si.get("Y");
            System.out.println(who + " mag " + what);
        }
        
        _counter++;
        if (_counter == 2) {
        	// Claus mag jetzt Prolog
        	_prover.addTheory("likes(claus, prolog).");
        } else if (_counter == 3) {
        	// Benjamin mag jetzt MicroJIAC
        	_prover.addTheory("likes(benjamin, microJIAC).");
        } else if (_counter == 4) {
        	// Benjamin mag MicroJIAC nicht mehr hoho
        	_prover.retract("likes(benjamin, microJIAC)");
        }
        
        System.out.println();
	}
}
