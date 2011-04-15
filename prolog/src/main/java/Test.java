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

import org.prolog4j.*;

public class Test {
    public static void main(String[] args) {
        Prover p = ProverFactory.getProver();
        Solution<String> sol = p.solve("likes(X, Y).").on("X");
        SolutionIterator<String> si = sol.iterator();
        while (si.hasNext()) {
            String x = si.next();
            String y = si.get("Y");
            System.out.printf("%s likes %s.", x, y);
        }
    }
}