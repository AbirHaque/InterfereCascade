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
public class ProjectMenuListeners
{
    public static void setListeners()
    {
        GUILoader.getProjectNew().addActionListener(new ActionListener() 
        {
              public void actionPerformed(ActionEvent e)
              {
                  //Create new project
                  GUILoader.getPreferencesPanel().setVisible(false);
                  JFrame fileChooserFrame = new JFrame();
                  JFileChooser fileChooser = new JFileChooser();
                  fileChooser.setDialogTitle("New Project");
                  int returnVal = fileChooser.showSaveDialog(fileChooserFrame);
                  if (returnVal == JFileChooser.APPROVE_OPTION)
                  {
                       File fileToSave = new File(fileChooser.getSelectedFile().toString() + ".IFCA");
                       try
                       {
                           GUILoader.setCurrentFile(fileToSave);
                           GUILoader.getCurrentFile().createNewFile();
                           GUILoader.setMusicPanelVisibility(true);
                           GUILoader.getFrame().setTitle("Interfere Cascade - " + GUILoader.getCurrentFile().getName());
                       }
                       catch (Exception x)
                       {
                           JOptionPane.showMessageDialog(null, "Failed to create project.", "Error", JOptionPane.PLAIN_MESSAGE);
                       }
                  }
              }
        });
        GUILoader.getProjectOpen().addActionListener(new ActionListener() 
        {
              public void actionPerformed(ActionEvent e)
              {
                  //Open existing project
                  GUILoader.getPreferencesPanel().setVisible(false);
                  JFrame fileChooserFrame = new JFrame();
                  JFileChooser fileChooser = new JFileChooser();
                  int returnVal = fileChooser.showDialog(fileChooserFrame, "Open Project");
                  if (returnVal == JFileChooser.APPROVE_OPTION)
                  {
                       File selectedFile = fileChooser.getSelectedFile();
                       try
                       {
                           if ((selectedFile.getName().substring(selectedFile.getName().length()-5,selectedFile.getName().length())).equals(".IFCA"))
                           {
                               GUILoader.setCurrentFile(selectedFile);
                               GUILoader.setMusicPanelVisibility(true);
                               while(GUILoader.getData().getRowCount()>0)
                               {
                                   GUILoader.getModel().removeRow(GUILoader.getData().getRowCount()-1);
                               }
                               GUILoader.getData().setPreferredSize(new Dimension(1233,16*GUILoader.getData().getRowCount()));//Each row is 16 pixels
                               BufferedReader in = new BufferedReader(new FileReader(GUILoader.getCurrentFile()));
                               ArrayList<ArrayList<String>> csvNotes = new ArrayList<ArrayList<String>>();
                               String tempo = in.readLine();
                               GUILoader.getTempoInput().setText(tempo);
                               String tempInstruments[] = (in.readLine()).split(",");
                               for (int i = 0; i < GUILoader.getInstruments().length; i++)
                               {
                                   GUILoader.getInstruments()[i].setSelectedIndex(Integer.valueOf(tempInstruments[i]));
                               }
                               in.readLine();
                               while(in.ready())
                               {   
                                   String line = in.readLine();
                                   String delimiter = ",";
                                   csvNotes.add(new ArrayList<String>(Arrays.asList((line).split(delimiter))));
                               }
                               for (int currentNoteIndex = 0; currentNoteIndex < csvNotes.size()-1; currentNoteIndex++)
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
                                   for (int currentTrack = 0; currentTrack < csvNotes.get(currentNoteIndex).size(); currentTrack++)
                                   {
                                       if (currentTrack == 8)
                                       {
                                           if((csvNotes.get(currentNoteIndex).get(currentTrack)).equals("|"))
                                           {
                                               GUILoader.getModel().setValueAt("", currentNoteIndex, GUILoader.getModel().getColumnCount()-2);
                                           }
                                           else
                                           {
                                               GUILoader.getModel().setValueAt((csvNotes.get(currentNoteIndex).get(currentTrack)), currentNoteIndex, GUILoader.getModel().getColumnCount()-2);
                                           }
                                       }
                                       if (currentTrack == csvNotes.get(currentNoteIndex).size()-2)
                                       {
                                           if((csvNotes.get(currentNoteIndex).get(currentTrack)).equals("|"))
                                           {
                                               GUILoader.getModel().setValueAt("", currentNoteIndex, 9);
                                           }
                                           else
                                           {
                                               GUILoader.getModel().setValueAt((csvNotes.get(currentNoteIndex).get(currentTrack)), currentNoteIndex, 9);
                                           }
                                       }
                                       else
                                       {
                                           if((csvNotes.get(currentNoteIndex).get(currentTrack)).equals("|"))
                                           {
                                               GUILoader.getModel().setValueAt("", currentNoteIndex, currentTrack+1);
                                           }
                                           else
                                           {
                                               GUILoader.getModel().setValueAt((csvNotes.get(currentNoteIndex).get(currentTrack)), currentNoteIndex, currentTrack+1);
                                           }
                                       }
                                   }
                               }
                               GUILoader.getData().setPreferredSize(new Dimension(1233,16*GUILoader.getData().getRowCount()));//Each row is 16 pixels
                               GUILoader.getFrame().setTitle("Interfere - " + GUILoader.getCurrentFile().getName());
                           }
                           else
                           {
                               JOptionPane.showMessageDialog(null, "Unrecognizable file format.", "Error", JOptionPane.PLAIN_MESSAGE);
                           }
                       }
                       catch (Exception x)
                       {
                           JOptionPane.showMessageDialog(null, "Failed to open project.", "Error", JOptionPane.PLAIN_MESSAGE);
                       }
                  }
              }
        });
       
        GUILoader.getProjectSave().addActionListener(new ActionListener() 
        {
              public void actionPerformed(ActionEvent e)
              {
                  //Save contents of music panel to current file
                  if(GUILoader.isMusicPanelVisible())
                  {
                      try
                      {
                          if (GUILoader.getTempoInput().getText().equals(""))
                          {
                              GUILoader.getTempoInput().setText("100");
                          }
                          int  tempo = Integer.valueOf(GUILoader.getTempoInput().getText());
                          String musicFileName = GUILoader.getCurrentFile().getAbsolutePath();
                          PrintWriter out = new PrintWriter(new FileWriter(musicFileName));
                          for (int i = 0; i < GUILoader.getInstruments().length; i++)
                          {
                              if (GUILoader.getInstruments()[i].getItemAt(GUILoader.getInstruments()[i].getSelectedIndex()).equals(""))
                              {
                                  GUILoader.getInstruments()[i].setSelectedIndex(0);//was 1
                              }
                          }
                          out.println(tempo);
                          for (int i = 0; i < 9; i++)
                          {
                              out.print(GUILoader.getInstruments()[i].getSelectedIndex()+",");
                          }
                          out.print("0,");//was 1
                          for (int i = 10; i < GUILoader.getInstruments().length; i++)
                          {
                              out.print(GUILoader.getInstruments()[i].getSelectedIndex()+",");
                          }
                          out.print(GUILoader.getInstruments()[9].getSelectedIndex()+",");
                          out.println("0");//was 1
                          out.println("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
                          boolean endFound = false;
                          for (int row = 0; row < GUILoader.getData().getRowCount(); row++)
                          {
                              for(int column = 1; column < 9; column++)
                              {
                                  String cellValue = (GUILoader.getData().getValueAt(row, column)+",").replace("null","|");
                                  if (cellValue.equals(","))
                                  {
                                      out.print("|,");
                                  }
                                  else
                                  {
                                      out.print(cellValue);
                                  }
                              }
                              String cellValue = (GUILoader.getData().getValueAt(row, GUILoader.getData().getColumnCount()-2)+",").replace("null","|");
                              if (cellValue.equals(","))
                              {
                                  out.print("|,");
                              }
                              else
                              {
                                  out.print(cellValue);
                              }
                              for(int column = 10; column < GUILoader.getData().getColumnCount()-2; column++)
                              {
                                  cellValue = (GUILoader.getData().getValueAt(row, column)+",").replace("null","|");
                                  if (cellValue.equals(","))
                                  {
                                      out.print("|,");
                                  }
                                  else
                                  {
                                      out.print(cellValue);
                                  }
                              }
                              cellValue = (GUILoader.getData().getValueAt(row, 9)+",").replace("null","|");
                              if (cellValue.equals(","))
                              {
                                  out.print("|,");
                              }
                              else
                              {
                                  out.print(cellValue);
                              }
                              cellValue = (GUILoader.getData().getValueAt(row, GUILoader.getData().getColumnCount()-1)+"").replace("null","|");
                              if (cellValue.equals(""))
                              {
                                  out.println("|");
                              }
                              else
                              {
                                  out.println(cellValue);
                              }
                          }
                          out.println("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
                          out.close();
                          JOptionPane.showMessageDialog(null, "Successfully saved project.", "Saved", JOptionPane.PLAIN_MESSAGE);
                      }
                      catch (Exception x)
                      {
                          JOptionPane.showMessageDialog(null, "Failed to save project.", "Error", JOptionPane.PLAIN_MESSAGE);
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











