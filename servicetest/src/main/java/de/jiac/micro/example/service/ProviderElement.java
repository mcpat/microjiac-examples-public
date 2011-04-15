/*
 * MicroJIAC - A Lightweight Agent Framework
 * This file is part of Example: Service-Test.
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
package de.jiac.micro.example.service;

import org.slf4j.Logger;

import de.jiac.micro.agent.IAgentElement;
import de.jiac.micro.core.ILifecycleAware;
import de.jiac.micro.core.scope.AgentScope;
import de.jiac.micro.ext.service.IServiceHandle;

/**
 * @author Marcel Patzlaff
 * @version $Revision:$
 */
public class ProviderElement implements IAgentElement, ILifecycleAware {
    private final static class ServiceImpl implements IExampleService {
        protected ServiceImpl() {}
        
        public int doMultiply(int f1, int f2) {
            return f1 * f2;
        }

        public void doPrint(String message) {
            System.out.println("ExampleService::print '" + message + "'");
        }

        public String doGetDescription() {
            return "An example Service";
        }
    }
    
    private ServiceImpl _impl;
    private Logger _logger;
    
    public void cleanup() {
        _impl= null;
        _logger= null;
    }

    public void initialise() {
        _impl= new ServiceImpl();
        _logger= AgentScope.getAgentReference().getLogger();
    }

    public void start() {
        IServiceHandle sh= (IServiceHandle) AgentScope.getAgentHandle(IServiceHandle.class);
        
        if(sh == null) {
            _logger.error("PE: could not deploy service");
        } else {
            sh.deployService(_impl);
        }
    }

    public void stop() {
        IServiceHandle sh= (IServiceHandle) AgentScope.getAgentHandle(IServiceHandle.class);
        
        if(sh == null) {
            _logger.error("PE: could not undeploy service");
        } else {
            sh.undeployService(_impl);
        }
    }
}
