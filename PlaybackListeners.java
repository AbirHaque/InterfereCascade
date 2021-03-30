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
import java.awt.event.*;
public class PlaybackListeners
{
    public static void setListeners()
    {
        GUILoader.getPlayMusicButton().addActionListener(new ActionListener() 
            {
                  public void actionPerformed(ActionEvent e)
                  {
                      if((GUILoader.getSeekMusicInput().getText()).equals(""))
                      {
                          GUILoader.getSeekMusicInput().setText("0");
                      }
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
                                  GUILoader.getInstruments()[i].setSelectedIndex(0);//was 0, originally 1
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
                          
                          MusicPlayer.start(musicFileName,tempo);
                          GUILoader.setIsScrolling(true);
                          Runnable r = new Runnable() 
                          {
                              public void run()
                              {
                                  try
                                  {
                                      for (int row = 0; row < GUILoader.getData().getRowCount(); row++)
                                      {
                                          GUILoader.getData().setCellSelectionEnabled(false);
                                          GUILoader.getData().changeSelection(row, 0, false, false);
                                          GUILoader.getData().editCellAt(row, 0);
                                          GUILoader.getData().getEditorComponent().requestFocus();
                                          int cellsPerBeat = 8; //Make user editable
                                          Thread.sleep(1000/(((Integer.valueOf(GUILoader.getTempoInput().getText())+10/*add 10 to make faster (gui loads too slowly)*/)*cellsPerBeat)/GUILoader.getDeltaMillisecondsCMD()));
                                          GUILoader.getData().setCellSelectionEnabled(true);
                                          if(GUILoader.getIsScrolling() == false)
                                          {
                                              row = GUILoader.getData().getRowCount();
                                          }
                                      }
                                      GUILoader.setIsScrolling(false);
                                  }
                                  catch(Exception x)
                                  {
                                  }
                              }
                          };
                          Thread scroller = new Thread(r);
                          scroller.start();
                      }
                      catch(Exception x)
                      {
                          x.printStackTrace();
                          JOptionPane.showMessageDialog(null, x, "Error", JOptionPane.PLAIN_MESSAGE);
                      }
                  }
            });
            GUILoader.getStopMusicButton().addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        MusicPlayer.stop();
                        GUILoader.setIsScrolling(false);
                    }
                    catch(Exception x)
                    {
                        JOptionPane.showMessageDialog(null, "Failed to stop player.", "Error", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });
    }
}
