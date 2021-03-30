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
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class RowListeners
{
    public static void setListeners()
    {
        GUILoader.getAddRowButton().addActionListener(new ActionListener() 
        {
          public void actionPerformed(ActionEvent e)
          {
              for(int i = 0; i < Integer.valueOf(GUILoader.getAddRowInput().getText()); i++)
              {
                  int beat = GUILoader.getData().getRowCount();
                  String improperFraction = "";
                  if(beat % 8 == 0)
                  {
                      improperFraction = String.valueOf(beat/8);
                  }
                  else{
                      if(beat > 8)
                      {
                          improperFraction = String.valueOf(beat/8) + " " + String.valueOf(beat%8) + "/8";
                      }
                      else
                      {
                          improperFraction = String.valueOf(beat%8)+"/8";
                      }
                  }
                  GUILoader.getModel().addRow(new Object[]{improperFraction/*,"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"*/});
              }
              GUILoader.getData().setPreferredSize(new Dimension(1233,16*GUILoader.getData().getRowCount()));//Each row is 16 pixels
          }
        });
        GUILoader.getRemoveRowButton().addActionListener(new ActionListener() 
        {
          public void actionPerformed(ActionEvent e)
          {
              for(int i = 0; i < Integer.valueOf(GUILoader.getRemoveRowInput().getText()); i++)
              {
                  GUILoader.getModel().removeRow(GUILoader.getData().getRowCount()-1);
              }
              GUILoader.getData().setPreferredSize(new Dimension(1233,16*GUILoader.getData().getRowCount()));//Each row is 16 pixels
          }
        });
        GUILoader.getSeekMusicButton().addActionListener(new ActionListener() 
            {
                  public void actionPerformed(ActionEvent e)
                  {
                      if ((GUILoader.getSeekMusicInput().getText()).equals(""))
                      {
                          GUILoader.getSeekMusicInput().setText("0");
                      }
                      try
                      {
                          int row = 0;
                          String improperFraction = GUILoader.getSeekMusicInput().getText();
                          if (improperFraction.contains("/"))
                          {
                              improperFraction = "0 "+improperFraction;
                              String[] separatedFraction = improperFraction.split(" ");
                              row = (Integer.valueOf(separatedFraction[0])*8)+Integer.valueOf(separatedFraction[1].substring(0,1));
                          }
                          if (improperFraction.contains(" ") && improperFraction.contains("/"))
                          {
                              String[] separatedFraction = improperFraction.split(" ");
                              row = (Integer.valueOf(separatedFraction[0])*8)+Integer.valueOf(separatedFraction[1].substring(0,1));
                          }
                          else
                          {
                              row = Integer.valueOf(improperFraction)*8;
                              
                          }
                          GUILoader.getData().setCellSelectionEnabled(true);
                          GUILoader.getData().changeSelection(row, 0, false, false);
                          GUILoader.getData().editCellAt(row, 0);
                          GUILoader.getData().getEditorComponent().requestFocus();
                      }
                      catch(Exception x)
                      {
                          JOptionPane.showMessageDialog(null, "Failed to seek to specified cell.", "Error", JOptionPane.PLAIN_MESSAGE);
                      }
                  }
            });
    }
}