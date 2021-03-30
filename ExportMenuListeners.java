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
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ExportMenuListeners
{
    public static void setListeners()
    {
        GUILoader.getExportMIDI().addActionListener(new ActionListener() 
        {
              public void actionPerformed(ActionEvent e)
              {
                  if(GUILoader.isMusicPanelVisible())
                  {
                      JFrame fileChooserFrame = new JFrame();
                      JFileChooser fileChooser = new JFileChooser();
                      fileChooser.setDialogTitle("Export MIDI");
                      int returnVal = fileChooser.showSaveDialog(fileChooserFrame);
                      if (returnVal == JFileChooser.APPROVE_OPTION)
                      {
                           String outputFileName = fileChooser.getSelectedFile().toString() + ".mid";
                           try
                           {
                               if (GUILoader.getTempoInput().getText().equals(""))
                               {
                                   GUILoader.getTempoInput().setText("100");
                               }
                               int  tempo = Integer.valueOf(GUILoader.getTempoInput().getText());
                               String musicFileName = GUILoader.getCurrentFile().getAbsolutePath();
                               MusicPlayer.writeMidi(musicFileName,tempo,outputFileName);
                               JOptionPane.showMessageDialog(null, "Successfully exported MIDI.", "Exported", JOptionPane.PLAIN_MESSAGE);
                           }
                           catch (Exception x)
                           {
                               JOptionPane.showMessageDialog(null, "Failed to export MIDI.", "Error", JOptionPane.PLAIN_MESSAGE);
                           }
                      }
                  }
                  else
                  {
                      JOptionPane.showMessageDialog(null, "Must create or open project.", "Error", JOptionPane.PLAIN_MESSAGE);
                  }
              }
        });
        GUILoader.getExportWAV().addActionListener(new ActionListener() 
        {
              public void actionPerformed(ActionEvent e)
              {
                  if(GUILoader.isMusicPanelVisible())
                  {
                      JFrame fileChooserFrame = new JFrame();
                      JFileChooser fileChooser = new JFileChooser();
                      fileChooser.setDialogTitle("Export WAV");
                      int returnVal = fileChooser.showSaveDialog(fileChooserFrame);
                      if (returnVal == JFileChooser.APPROVE_OPTION)
                      {
                           String outputFileName = fileChooser.getSelectedFile().toString() + ".wav";
                           try
                           {
                               if (GUILoader.getTempoInput().getText().equals(""))
                               {
                                   GUILoader.getTempoInput().setText("100");
                               }
                               int  tempo = Integer.valueOf(GUILoader.getTempoInput().getText());
                               String musicFileName = GUILoader.getCurrentFile().getAbsolutePath();
                               MusicPlayer.writeWav(musicFileName,tempo,outputFileName);
                               JOptionPane.showMessageDialog(null, "Successfully exported WAV.", "Exported", JOptionPane.PLAIN_MESSAGE);
                           }
                           catch (Exception x)
                           {
                               JOptionPane.showMessageDialog(null, "Failed to export WAV."+x, "Error", JOptionPane.PLAIN_MESSAGE);
                           }
                      }
                  }
                  else
                  {
                      JOptionPane.showMessageDialog(null, "Must create or open project.", "Error", JOptionPane.PLAIN_MESSAGE);
                  }
              }
        });
        GUILoader.getExportAU().addActionListener(new ActionListener() 
        {
              public void actionPerformed(ActionEvent e)
              {
                  if(GUILoader.isMusicPanelVisible())
                  {
                      JFrame fileChooserFrame = new JFrame();
                      JFileChooser fileChooser = new JFileChooser();
                      fileChooser.setDialogTitle("Export AU");
                      int returnVal = fileChooser.showSaveDialog(fileChooserFrame);
                      if (returnVal == JFileChooser.APPROVE_OPTION)
                      {
                           String outputFileName = fileChooser.getSelectedFile().toString() + ".au";
                           try
                           {
                               if (GUILoader.getTempoInput().getText().equals(""))
                               {
                                   GUILoader.getTempoInput().setText("100");
                               }
                               int  tempo = Integer.valueOf(GUILoader.getTempoInput().getText());
                               String musicFileName = GUILoader.getCurrentFile().getAbsolutePath();
                               MusicPlayer.writeAu(musicFileName,tempo,outputFileName);
                               JOptionPane.showMessageDialog(null, "Successfully exported AU.", "Exported", JOptionPane.PLAIN_MESSAGE);
                           }
                           catch (Exception x)
                           {
                               JOptionPane.showMessageDialog(null, "Failed to export AU.", "Error", JOptionPane.PLAIN_MESSAGE);
                           }
                      }
                  }
                  else
                  {
                      JOptionPane.showMessageDialog(null, "Must create or open project.", "Error", JOptionPane.PLAIN_MESSAGE);
                  }
              }
        });
        GUILoader.getExportAIFF().addActionListener(new ActionListener() 
        {
              public void actionPerformed(ActionEvent e)
              {
                  if(GUILoader.isMusicPanelVisible())
                  {
                      JFrame fileChooserFrame = new JFrame();
                      JFileChooser fileChooser = new JFileChooser();
                      fileChooser.setDialogTitle("Export WAV");
                      int returnVal = fileChooser.showSaveDialog(fileChooserFrame);
                      if (returnVal == JFileChooser.APPROVE_OPTION)
                      {
                           String outputFileName = fileChooser.getSelectedFile().toString() + ".aiff";
                           try
                           {
                               if (GUILoader.getTempoInput().getText().equals(""))
                               {
                                   GUILoader.getTempoInput().setText("100");
                               }
                               int  tempo = Integer.valueOf(GUILoader.getTempoInput().getText());
                               String musicFileName = GUILoader.getCurrentFile().getAbsolutePath();
                               MusicPlayer.writeAiff(musicFileName,tempo,outputFileName);
                               JOptionPane.showMessageDialog(null, "Successfully exported AIFF.", "Exported", JOptionPane.PLAIN_MESSAGE);
                           }
                           catch (Exception x)
                           {
                               JOptionPane.showMessageDialog(null, "Failed to export AIFF.", "Error", JOptionPane.PLAIN_MESSAGE);
                           }
                      }
                  }
                  else
                  {
                      JOptionPane.showMessageDialog(null, "Must create or open project.", "Error", JOptionPane.PLAIN_MESSAGE);
                  }
              }
        });
    }
}
