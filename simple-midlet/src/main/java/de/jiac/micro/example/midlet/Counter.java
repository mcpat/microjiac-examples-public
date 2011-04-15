/*
 * MicroJIAC - A Lightweight Agent Framework
 * This file is part of Example: Simple-MIDlet.
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
package de.jiac.micro.example.midlet;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import de.jiac.micro.agent.AbstractActiveBehaviour;
import de.jiac.micro.core.scope.AgentScope;
import de.jiac.micro.midp.ui.IUIAccessor;

/**
 * @author Marcel Patzlaff
 * @version $Revision:$
 */
public class Counter extends AbstractActiveBehaviour implements CommandListener {
    private final class Visual extends Canvas {
        private final Font _font;
        private final char[] _chars;
        
        protected Visual() {
            _font= Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_LARGE);
            _chars= new char[2];
        }
        
        protected void paint(Graphics g) {
            _chars[0]= (char) ((counter / 10) + '0');
            _chars[1]= (char) ((counter % 10) + '0');
            
            Image buffer= Image.createImage(getWidth(), getHeight());
            Graphics gb= buffer.getGraphics();
            
            int x= (getWidth() - _font.charsWidth(_chars, 0, 2)) / 2;
            int y= (getHeight() - _font.getHeight()) / 2;
            
            gb.setFont(_font);
            gb.drawChars(_chars, 0, 2, x, y, 0);
            g.drawImage(buffer, 0, 0, 0);
        }
    }
    
    private IUIAccessor _uiRoot;
    private Visual _visual;
    protected volatile int counter;
    
    public void cleanup() {
        _uiRoot= null;
        _visual= null;
        super.cleanup();
    }

    public void initialise() {
        super.initialise();
        _uiRoot= (IUIAccessor) AgentScope.getAgentHandle(IUIAccessor.class);
        _visual= new Visual();
        _uiRoot.setAgentUI(_visual, this);
        counter= 0;
    }
    
    public void commandAction(Command c, Displayable d) {
        
    }

    protected void runShort() {
        counter= (counter + 1) % 100;
        _visual.repaint();
    }
}
