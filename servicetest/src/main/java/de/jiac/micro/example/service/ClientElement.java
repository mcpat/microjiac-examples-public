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

import java.util.Random;

import org.slf4j.Logger;

import de.jiac.micro.agent.AbstractActiveBehaviour;
import de.jiac.micro.core.ILifecycleAware;
import de.jiac.micro.core.scope.AgentScope;
import de.jiac.micro.core.scope.Scope;
import de.jiac.micro.ext.service.IServiceContext;
import de.jiac.micro.ext.service.IServiceHandle;

/**
 * @author Marcel Patzlaff
 * @version $Revision:$
 */
public class ClientElement extends AbstractActiveBehaviour implements ILifecycleAware {
    private IServiceHandle _serviceHandle;
    private Random _random;
    protected Logger logger;
    
    public void cleanup() {
        logger= null;
        _random= null;
        super.cleanup();
    }

    public void initialise() {
        super.initialise();
        logger= AgentScope.getAgentReference().getLogger();
        _random= new Random();
    }

    public void start() {
        super.start();
        _serviceHandle= (IServiceHandle) AgentScope.getAgentHandle(IServiceHandle.class);
    }

    public void stop() {
        _serviceHandle= null;
        super.stop();
    }

    protected void runShort() {
        if(_serviceHandle == null) {
            logger.error("CE: could find service handle");
        } else {
            final IServiceContext context= _serviceHandle.createContext(IExampleService.class);
            final Runnable runner= new Runnable() {
                public void run() {
                    int f1= _random.nextInt(500);
                    int f2= _random.nextInt(500);
                    
                    String message= f1 + " times " + f2 + " is";
                    
                    logger.debug(" -> want to know what " + message + "...");
                    int result= ((IExampleService) context).doMultiply(f1, f2);
                    logger.debug(" -> it is " + result);
                    logger.debug(" -> want to tell it others...");
                    message= "hey you! " + message + " " + result;
                    ((IExampleService)context).doPrint(message);
                    logger.debug(" -> others were told");
                }
            };
            
            Thread thr= Scope.createScopeAwareThread(runner, "serviceRunner");
            thr.start();
            
            logger.debug("CE: new thread for service invocation");
        }
    }
}
