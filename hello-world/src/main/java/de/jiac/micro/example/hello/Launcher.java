/*
 * MicroJIAC - A Lightweight Agent Framework
 * This file is part of Example: Hello-World.
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
package de.jiac.micro.example.hello;

import com.github.libxjava.lang.SimpleClassLoader;

import de.jiac.micro.core.IScope;
import de.jiac.micro.core.scope.NodeScope;


/**
 * @author Marcel Patzlaff
 * @version $Revision:$
 */
public class Launcher {
    public static void main(String[] args) throws Exception {
        NodeScope nodeScope= new NodeScope();
        nodeScope.setup(
            new SimpleClassLoader(),
            "de.jiac.micro.internal.latebind.config_hello_HelloNode",
            null
        );
        
        nodeScope.signal(IScope.SIG_START);
    }
}
