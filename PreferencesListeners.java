/*
Interfere Cascade is a MIDI composition spreadsheet editor.

Copyright 2021 Abir Haque

This file is part of Interfere Cascade.

Interfere Cascade is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Interfere Cascade is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Interfere Cascade in the file labeled <LICENSE.txt>.  If not, see <https://www.gnu.org/licenses/>.
*/
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class PreferencesListeners
{
    public static void setListeners()
    {
        GUILoader.getCancelPreferencesButton().addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                GUILoader.setPreferencesVisibility(false);
            }
        });
        GUILoader.getSavePreferencesButton().addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    GUILoader.setDeltaMillisecondsCMD(Integer.valueOf(String.valueOf(GUILoader.getSpinner().getValue())));
                    
                    PrintWriter out = new PrintWriter(new FileWriter("preferences.txt"));
                    out.println("deltaMillisecondsCMD="+GUILoader.getDeltaMillisecondsCMD());
                    out.close();
                    
                    GUILoader.setPreferencesVisibility(false);
                    JOptionPane.showMessageDialog(null, "Successfully saved preferences.", "Saved", JOptionPane.PLAIN_MESSAGE);
                }
                catch(Exception x)
                {
                }
            }
        });
    }
}
