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

/*
 * $Id$ 
 */
package de.jiac.micro.examples.prolog;

import org.prolog4j.Prover;
import org.prolog4j.ProverFactory;
import org.prolog4j.Solution;
import org.prolog4j.SolutionIterator;

/**
 * @author Marcel Patzlaff
 * @version $Revision:$
 */
public class PrologTester {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        Prover prover= ProverFactory.getProver();
//        prover.loadLibrary("alice.tuprolog.lib.BasicLibrary");
        prover.loadTheory(PeriodicPrologAgentElement.class.getResourceAsStream("einstein.pl"));
        final long startTime= System.currentTimeMillis();
        Solution sol = prover.solve("einstein(X, Y).").on("Y");
        final long endTime= System.currentTimeMillis();
        System.out.println(prover.getClass().getName() + " time to solve: " + (endTime - startTime));
        SolutionIterator si= sol.iterator();
        while(si.hasNext()) {
            System.out.println(si.next());
        }
    }
}
