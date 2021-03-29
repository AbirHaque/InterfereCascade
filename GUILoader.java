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
import javax.imageio.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.datatransfer.*;
import java.awt.geom.*;
import java.net.*;
public class GUILoader
{
    private static boolean musicPanelVisible = false;  
    private static boolean preferencesPanelVisible = false;     
    private static JPanel musicPanel = new JPanel(); 
    private static JPanel preferencesPanel = new JPanel();
    private static File currentFile = new File("");
    private static int deltaMillisecondsCMD;
    private static String version = "0.0.1";
    private static boolean isScrolling = false;
    public static File getCurrentFile()
    {
        return currentFile;
    }
    public static void setCurrentFile(File file)
    {
        currentFile = file;
    }
    public static JPanel getMusicPanel()
    {
        return musicPanel;
    }
    public static boolean isMusicPanelVisible()
    {
        return musicPanelVisible;
    }
    public static void setMusicPanelVisibility(boolean requestedVisibility)
    {
        if (requestedVisibility == false)
        {
            musicPanelVisible = false;
            musicPanel.setVisible(false);
        }
        else
        {
            musicPanelVisible = true;
            musicPanel.setVisible(true);
        }
    }public static JPanel getPreferencesPanel()
    {
        return preferencesPanel;
    }
    public static boolean isPreferencesPanelVisible()
    {
        return preferencesPanelVisible;
    }
    public static void setPreferencesVisibility(boolean requestedVisibility)
    {
        if (requestedVisibility == false)
        {
            preferencesPanelVisible = false;
            preferencesPanel.setVisible(false);
        }
        else
        {
            preferencesPanelVisible = true;
            preferencesPanel.setVisible(true);
        }
    }
    public static void setDeltaMillisecondsCMD(int value)
    {
        deltaMillisecondsCMD=value;
    }
    public static int getDeltaMillisecondsCMD()
    {
        return deltaMillisecondsCMD;
    }
    public static void loadPreferences() throws Exception
    {
        if(!(new File("preferences.txt")).exists())
        { 
            PrintWriter out = new PrintWriter(new FileWriter("preferences.txt"));
            out.println("deltaMillisecondsCMD=60");
            out.close();
        }
        BufferedReader in = new BufferedReader(new FileReader("preferences.txt"));
        while(in.ready()) 
        {
            String line = in.readLine();
            if(line.contains("=")&&line.split("=")[0].equals("deltaMillisecondsCMD"))
            {
                setDeltaMillisecondsCMD(Integer.valueOf(line.split("=")[1]));
            }
        }
        in.close();
    }
    public static void main(String[] args) throws Exception
    {
        loadPreferences();
        
        GUILoader guiLoader = new GUILoader();
        
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        KeyStroke copy = KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK, false);
        KeyStroke paste = KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK, false);
        KeyStroke cut = KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK, false);
        KeyStroke delete = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
        
        JFrame frame = new JFrame("Interfere Cascade");
        /*
javax.swing.UIManager$LookAndFeelInfo[Metal javax.swing.plaf.metal.MetalLookAndFeel]
javax.swing.UIManager$LookAndFeelInfo[Nimbus javax.swing.plaf.nimbus.NimbusLookAndFeel]
javax.swing.UIManager$LookAndFeelInfo[CDE/Motif com.sun.java.swing.plaf.motif.MotifLookAndFeel]
javax.swing.UIManager$LookAndFeelInfo[Windows com.sun.java.swing.plaf.windows.WindowsLookAndFeel]
javax.swing.UIManager$LookAndFeelInfo[Windows Classic com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel]

         */
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //frame.getContentPane().setBackground(new Color(255,0,0,100));
        //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//frame.getContentPane().setBackground(Color.DARK_GRAY);
        ImageIcon logo = new ImageIcon("logo.png");
        
        
        
        
        
            
        getPreferencesPanel().setLayout(new GridLayout(2,10));
        
        JLabel deltaMillisecondsCMDLabel = new JLabel("Delay in scroller between notes: ");
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(getDeltaMillisecondsCMD(),1,100,1);
        JSpinner spinner = new JSpinner(spinnerModel);
        JFormattedTextField formattedTextField = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
        formattedTextField.setEditable(false);
        JButton cancelPreferencesButton = new JButton("Cancel");
        JButton savePreferencesButton = new JButton("Save");
        cancelPreferencesButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                setPreferencesVisibility(false);
            }
        });
        savePreferencesButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    setDeltaMillisecondsCMD(Integer.valueOf(String.valueOf(spinner.getValue())));
                    
                    PrintWriter out = new PrintWriter(new FileWriter("preferences.txt"));
                    out.println("deltaMillisecondsCMD="+getDeltaMillisecondsCMD());
                    out.close();
                    
                    setPreferencesVisibility(false);
                    JOptionPane.showMessageDialog(null, "Successfully saved preferences.", "Saved", JOptionPane.PLAIN_MESSAGE);
                }
                catch(Exception x)
                {
                }
            }
        });
        getPreferencesPanel().add(deltaMillisecondsCMDLabel);
        getPreferencesPanel().add(spinner);
        getPreferencesPanel().add(cancelPreferencesButton);
        getPreferencesPanel().add(savePreferencesButton);
        
        
        
        
        
        
        getMusicPanel().setLayout(new BorderLayout());
    
        JPanel topMusicPanel = new JPanel();
        topMusicPanel.setLayout(new GridLayout(0,18));
        JPanel notePanel = new JPanel(); 
            JButton playMusicButton = new JButton("Play");
            JButton stopMusicButton = new JButton("Stop");
            JButton seekMusicButton = new JButton("Seek:");
            JButton addRowButton = new JButton("+");
            JButton removeRowButton = new JButton("-");
            JTextField seekMusicInput = new JTextField(5);
            JTextField tempoInput = new JTextField(5);
            JTextField addRowInput = new JTextField(5);
            JTextField removeRowInput = new JTextField(5);
            String[] instrumentList = 
            {
                "Piano Accoustic",
                "Piano Bright",
                "Piano Electric",
                "Piano Honky-tonk",
                "Piano Rhodes",
                "Piano Chorused",
                "Harpsichord",
                "Clavinet",
                "Celesta",
                "Glockenspiel",
                "Music box",
                "Vibraphone",
                "Marimba",
                "Xylophone",
                "Bells Tubular",
                "Dulcimer",
                "Organ Hammond",
                "Organ Percussive",
                "Organ Rock",
                "Organ Church",
                "Organ Reed",
                "Accordion",
                "Harmonica",
                "Accordion Tango",
                "Guitar Acoustic Nylon",
                "Guitar Acoustic Steel",
                "Guitar Electric Jazz",
                "Guitar     Electric Cleen",
                "Guitar     Electric Muted",
                "Guitar Overdriven",
                "Guitar Distorted",
                "Guitar Harmonics",
                "Bass Acoustic",
                "Bass Electric Finger",
                "Bass Electric Pick",
                "Bass Fretless",
                "Bass Slap 1",
                "Bass Slap 2",
                "Bass Synth 1",
                "Bass Synth 2",
                "Violin",
                "Viola",
                "Cello",
                "Contrabass",
                "Strings Tremelo",
                "Strings Pizzacato",
                "Harp",
                "Timpani",
                "String Ensemble 1",
                "String Ensemble 1",
                "Strings Synth 1",
                "Strings Synth 2",
                "Voices Aahs",
                "Voice Oohs",
                "Voice Synth",
                "Orchestra Hit",
                "Trumpet",
                "Trombone",
                "Tuba",
                "Trumpet Muted",
                "Horn French",
                "Brass Section",
                "Brass Synth 1",
                "Brass Synth 2",
                "Sax Soprano",
                "Sax Alto",
                "Sax Tenor",
                "Sax Baritone",
                "Oboe",
                "Horn English",
                "Bassoon",
                "Clarinet",
                "Piccolo",
                "Flute",
                "Recorder",
                "Flute Pan",
                "Bottle Blow",
                "Shakuhachi",
                "Whistle",
                "Ocarina",
                "Lead Square",
                "Lead Sawtooth",
                "Lead Calliope",
                "Lead Chiffer",
                "Lead Charang",
                "Lead Voice",
                "Lead Fifths",
                "Lead Brass",
                "Pad New Age",
                "Pad Warm",
                "Pad Polysynth",
                "Pad Choir",
                "Pad Bowed",
                "Pad Metallic",
                "Pad Halo",
                "Pad Sweep",
                "FX Rain",
                "FX Soundtrack",
                "FX Crystal",
                "FX Atmosphere",
                "FX Brightness",
                "FX Goblins",
                "FX Echoes",
                "FX Sci-fi",
                "Sitar",
                "Banjo",
                "Shamisen",
                "Koto",
                "Kalimba",
                "Bagpipe",
                "Fiddle",
                "Shana",
                "Bell Tinkle",
                "Agogo",
                "Drums Steel",
                "Woodblock",
                "Drum Taiko",
                "Tom Melodic",
                "Drum Synth",
                "Cymbal Reverse",
                "Guitar Fret",
                "Breath",
                "Seashore",
                "Tweet",
                "Ring",
                "Helicopter",
                "Applause",
                "Gunshot"
            };
            JComboBox[] instruments = new JComboBox[15];
            JTable data = new JTable(0,18); //18*96000, so 96000,17?
            JTextField beatCell = new JTextField();
            beatCell.setEditable(false);
            data.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(beatCell));
            DefaultTableModel model = (DefaultTableModel) data.getModel();
    
                    topMusicPanel.add(playMusicButton);
                    topMusicPanel.add(stopMusicButton);
                    topMusicPanel.add(seekMusicButton);
                    topMusicPanel.add(seekMusicInput);
                    topMusicPanel.add(new JLabel("Tempo:"));
                    topMusicPanel.add(tempoInput);
                    for (int i = 1; i <= 8; i++)
                    {
                        topMusicPanel.add(new JLabel());
                    }
                    topMusicPanel.add(addRowButton);
                    topMusicPanel.add(addRowInput);
                    topMusicPanel.add(removeRowButton);
                    topMusicPanel.add(removeRowInput);
                    topMusicPanel.add(new JLabel("Instrument:"));
                    for (int i = 0; i < instruments.length; i++)
                    {
                        //instruments[i] = new JTextField(5);
                        instruments[i] = new JComboBox(instrumentList);
                        instruments[i].setEditable(false);
                        
                        Dimension newDim = new Dimension(1,5);

                        instruments[i].setMinimumSize(newDim);
                        instruments[i].setPreferredSize(newDim);
                        instruments[i].setMaximumSize(newDim);
                        instruments[i].setSize(newDim);
                        instruments[i].revalidate();
                        
                        JPopupMenu popUp = (JPopupMenu) instruments[i].getUI().getAccessibleChild(instruments[i], 0);
                        JScrollPane scroll = (JScrollPane) popUp.getComponent(0);
                        scroll.setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL));
                        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                        scroll.getVerticalScrollBar().setUnitIncrement(20);
                        scroll.getHorizontalScrollBar().setUnitIncrement(20);
                        topMusicPanel.add(instruments[i]);
                    }
                    topMusicPanel.add(new JLabel("Percussion"));
                    topMusicPanel.add(new JLabel("Event"));
                    getMusicPanel().add(topMusicPanel, BorderLayout.PAGE_START);
    
                        notePanel.add(data);
    
                addRowButton.addActionListener(new ActionListener() 
                {
                  public void actionPerformed(ActionEvent e)
                  {
                      for(int i = 0; i < Integer.valueOf(addRowInput.getText()); i++)
                      {
                          int beat = data.getRowCount();
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
                          model.addRow(new Object[]{improperFraction/*,"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"*/});
                      }
                      data.setPreferredSize(new Dimension(1233,16*data.getRowCount()));//Each row is 16 pixels
                  }
                });
                removeRowButton.addActionListener(new ActionListener() 
                {
                  public void actionPerformed(ActionEvent e)
                  {
                      for(int i = 0; i < Integer.valueOf(removeRowInput.getText()); i++)
                      {
                          model.removeRow(data.getRowCount()-1);
                      }
                      data.setPreferredSize(new Dimension(1233,16*data.getRowCount()));//Each row is 16 pixels
                  }
                });            
                data.setColumnSelectionAllowed(true);
                data.setRowSelectionAllowed(true);
                data.registerKeyboardAction(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        StringSelection stsel;
                        StringBuffer sbf=new StringBuffer();
                        int columns=data.getSelectedColumnCount();
                        int rows=data.getSelectedRowCount();
                        int[] rowsselected=data.getSelectedRows();
                        int[] colsselected=data.getSelectedColumns();
                        for (int i=0;i<rows;i++)
                        {
                            for (int j=0;j<columns;j++)
                            {
                                if(data.getValueAt(rowsselected[i],colsselected[j]).equals(""))
                                {
                                    sbf.append("[Empty cell]");
                                }
                                else
                                {
                                    sbf.append(data.getValueAt(rowsselected[i],colsselected[j]));
                                }
                                if (j<columns-1) 
                                {
                                    sbf.append("\t");
                                }
                            }
                            sbf.append("\n");
                        }
                        stsel  = new StringSelection(sbf.toString().replace("null",""));
                        clipboard.setContents(stsel,stsel);
                    }
                } , "Copy", copy, JComponent.WHEN_FOCUSED);
                data.registerKeyboardAction(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        int startRow=(data.getSelectedRows())[0];
                        int startCol=(data.getSelectedColumns())[0];
                        try
                        {
                            String trstring= (String)(clipboard.getContents(clipboard).getTransferData(DataFlavor.stringFlavor));
                            StringTokenizer st1=new StringTokenizer(trstring,"\n");
                            for(int i=0;st1.hasMoreTokens();i++)
                            {
                                String rowstring=st1.nextToken();
                                String delimiter = ",";
                                if (rowstring.contains("\t"))
                                {
                                    delimiter = "\t";
                                }
                                StringTokenizer st2=new StringTokenizer(rowstring,delimiter);
                                for(int j=0;st2.hasMoreTokens();j++)
                                {
                                    String value=(String)st2.nextToken();
                                    if (startRow+i< data.getRowCount() && startCol+j< data.getColumnCount())
                                    {
                                       if(value.equals("[Empty cell]"))
                                       {
                                           data.setValueAt("",startRow+i,startCol+j);
                                       }
                                       else
                                       {
                                           data.setValueAt(value,startRow+i,startCol+j);
                                       }
                                    }
                                }
                            }
                        }
                        catch(Exception x)
                        {
                              JOptionPane.showMessageDialog(null, "Failed to execute paste shortcut.", "Error", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                } , "Paste", paste, JComponent.WHEN_FOCUSED);
                data.registerKeyboardAction(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        StringSelection stsel;
                        StringBuffer sbf=new StringBuffer();
                        int startRow=(data.getSelectedRows())[0];
                        int startCol=(data.getSelectedColumns())[0];
                        int columns=data.getSelectedColumnCount();
                        int rows=data.getSelectedRowCount();
                        int[] rowsselected=data.getSelectedRows();
                        int[] colsselected=data.getSelectedColumns();
                        for (int i=0;i<rows;i++)
                        {
                            for (int j=0;j<columns;j++)
                            {
                                if(data.getValueAt(rowsselected[i],colsselected[j]).equals(""))
                                {
                                    sbf.append("[Empty cell]");
                                }
                                else
                                {
                                    sbf.append(data.getValueAt(rowsselected[i],colsselected[j]));
                                }
                                if (j<columns-1) 
                                {
                                    sbf.append("\t");
                                }
                            }
                            sbf.append("\n");
                        }
                        stsel  = new StringSelection(sbf.toString().replace("null",""));
                        clipboard.setContents(stsel,stsel);
                        
                        try
                        {
                            String trstring= (String)(clipboard.getContents(clipboard).getTransferData(DataFlavor.stringFlavor));
                            StringTokenizer st1=new StringTokenizer(trstring,"\n");
                            for(int i=0;st1.hasMoreTokens();i++)
                            {
                                String rowstring=st1.nextToken();
                                String delimiter = ",";
                                if (rowstring.contains("\t"))
                                {
                                    delimiter = "\t";
                                }
                                StringTokenizer st2=new StringTokenizer(rowstring,delimiter);
                                for(int j=0;st2.hasMoreTokens();j++)
                                {
                                    String value=(String)st2.nextToken();
                                    if (startRow+i< data.getRowCount() && startCol+j< data.getColumnCount())
                                    {
                                        data.setValueAt("",startRow+i,startCol+j);
                                    }
                                }
                            }
                        }
                        catch(Exception x)
                        {
                              JOptionPane.showMessageDialog(null, "Failed to execute cut shortcut.", "Error", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                } , "Cut", cut, JComponent.WHEN_FOCUSED);
                data.registerKeyboardAction(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        int startRow=(data.getSelectedRows())[0];
                        int startCol=(data.getSelectedColumns())[0];
                        int columns=data.getSelectedColumnCount();
                        int rows=data.getSelectedRowCount();
                        int[] rowsselected=data.getSelectedRows();
                        int[] colsselected=data.getSelectedColumns();
                        try
                        {
                            for (int i=0;i<rows;i++)
                            {
                                for (int j=0;j<columns;j++)
                                {
                                    data.setValueAt("",rowsselected[i],colsselected[j]);
                                }
                            }
                        }
                        catch(Exception x)
                        {
                              JOptionPane.showMessageDialog(null, "Failed to execute delete shortcut.", "Error", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                } , "Delete", delete, JComponent.WHEN_FOCUSED);
                
                
            JScrollPane scroll = new JScrollPane(notePanel);
            scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            getMusicPanel().add(scroll);
            scroll.setPreferredSize(new Dimension(1250,400));
            scroll.getVerticalScrollBar().setUnitIncrement(20);
            
            playMusicButton.addActionListener(new ActionListener() 
            {
                  public void actionPerformed(ActionEvent e)
                  {
                      if((seekMusicInput.getText()).equals(""))
                      {
                          seekMusicInput.setText("0");
                      }
                      try
                      {
                          if (tempoInput.getText().equals(""))
                          {
                              tempoInput.setText("100");
                          }
                          int  tempo = Integer.valueOf(tempoInput.getText());
                          String musicFileName = getCurrentFile().getAbsolutePath();
                          PrintWriter out = new PrintWriter(new FileWriter(musicFileName));
                          for (int i = 0; i < instruments.length; i++)
                          {
                              if (instruments[i].getItemAt(instruments[i].getSelectedIndex()).equals(""))
                              {
                                  instruments[i].setSelectedIndex(0);//was 0, originally 1
                              }
                          }
                          out.println(tempo);
                          for (int i = 0; i < 9; i++)
                          {
                              out.print(instruments[i].getSelectedIndex()+",");
                          }
                          out.print("0,");//was 1
                          for (int i = 10; i < instruments.length; i++)
                          {
                              out.print(instruments[i].getSelectedIndex()+",");
                          }
                          out.print(instruments[9].getSelectedIndex()+",");
                          out.println("0");//was 1
                          out.println("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
                          boolean endFound = false;
                          for (int row = 0; row < data.getRowCount(); row++)
                          {
                              for(int column = 1; column < 9; column++)
                              {
                                  String cellValue = (data.getValueAt(row, column)+",").replace("null","|");
                                  if (cellValue.equals(","))
                                  {
                                      out.print("|,");
                                  }
                                  else
                                  {
                                      out.print(cellValue);
                                  }
                              }
                              String cellValue = (data.getValueAt(row, data.getColumnCount()-2)+",").replace("null","|");
                              if (cellValue.equals(","))
                              {
                                  out.print("|,");
                              }
                              else
                              {
                                  out.print(cellValue);
                              }
                              for(int column = 10; column < data.getColumnCount()-2; column++)
                              {
                                  cellValue = (data.getValueAt(row, column)+",").replace("null","|");
                                  if (cellValue.equals(","))
                                  {
                                      out.print("|,");
                                  }
                                  else
                                  {
                                      out.print(cellValue);
                                  }
                              }
                              cellValue = (data.getValueAt(row, 9)+",").replace("null","|");
                              if (cellValue.equals(","))
                              {
                                  out.print("|,");
                              }
                              else
                              {
                                  out.print(cellValue);
                              }
                              cellValue = (data.getValueAt(row, data.getColumnCount()-1)+"").replace("null","|");
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
                          isScrolling = true;
                          Runnable r = new Runnable() 
                          {
                              public void run()
                              {
                                  try
                                  {
                                      for (int row = 0; row < data.getRowCount(); row++)
                                      {
                                          data.setCellSelectionEnabled(false);
                                          data.changeSelection(row, 0, false, false);
                                          data.editCellAt(row, 0);
                                          data.getEditorComponent().requestFocus();
                                          int cellsPerBeat = 8; //Make user editable
                                          Thread.sleep(1000/(((Integer.valueOf(tempoInput.getText())+10/*add 10 to make faster (gui loads too slowly)*/)*cellsPerBeat)/getDeltaMillisecondsCMD()));
                                          data.setCellSelectionEnabled(true);
                                          if(isScrolling == false)
                                          {
                                              row = data.getRowCount();
                                          }
                                      }
                                      isScrolling = false;
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
            stopMusicButton.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        MusicPlayer.stop();
                        isScrolling = false;
                    }
                    catch(Exception x)
                    {
                        JOptionPane.showMessageDialog(null, "Failed to stop player.", "Error", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });
            seekMusicButton.addActionListener(new ActionListener() 
            {
                  public void actionPerformed(ActionEvent e)
                  {
                      if ((seekMusicInput.getText()).equals(""))
                      {
                          seekMusicInput.setText("0");
                      }
                      try
                      {
                          int row = 0;
                          String improperFraction = seekMusicInput.getText();
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
                          data.setCellSelectionEnabled(true);
                          data.changeSelection(row, 0, false, false);
                          data.editCellAt(row, 0);
                          data.getEditorComponent().requestFocus();
                      }
                      catch(Exception x)
                      {
                          JOptionPane.showMessageDialog(null, "Failed to seek to specified cell.", "Error", JOptionPane.PLAIN_MESSAGE);
                      }
                  }
            });
                
                
            
            /*
             * Menu bar components
             */
            JMenuBar menuBar = new JMenuBar();
            JMenu projectMenu = new JMenu("Project");
            
            /*
             * Project menu components
             */
                JMenuItem projectNew = new JMenuItem("New");
                JMenuItem projectOpen = new JMenuItem("Open");
                JMenuItem projectSave = new JMenuItem("Save");
                projectNew.addActionListener(new ActionListener() 
                {
                      public void actionPerformed(ActionEvent e)
                      {
                          //Create new project
                          getPreferencesPanel().setVisible(false);
                          JFrame fileChooserFrame = new JFrame();
                          JFileChooser fileChooser = new JFileChooser();
                          fileChooser.setDialogTitle("New Project");
                          int returnVal = fileChooser.showSaveDialog(fileChooserFrame);
                          if (returnVal == JFileChooser.APPROVE_OPTION)
                          {
                               File fileToSave = new File(fileChooser.getSelectedFile().toString() + ".IFCA");
                               try
                               {
                                   setCurrentFile(fileToSave);
                                   getCurrentFile().createNewFile();
                                   setMusicPanelVisibility(true);
                                   frame.setTitle("Interfere Cascade - " + getCurrentFile().getName());
                               }
                               catch (Exception x)
                               {
                                   JOptionPane.showMessageDialog(null, "Failed to create project.", "Error", JOptionPane.PLAIN_MESSAGE);
                               }
                          }
                      }
                });
                projectOpen.addActionListener(new ActionListener() 
                {
                      public void actionPerformed(ActionEvent e)
                      {
                          //Open existing project
                          getPreferencesPanel().setVisible(false);
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
                                       setCurrentFile(selectedFile);
                                       setMusicPanelVisibility(true);
                                       while(data.getRowCount()>0)
                                       {
                                           model.removeRow(data.getRowCount()-1);
                                       }
                                       data.setPreferredSize(new Dimension(1233,16*data.getRowCount()));//Each row is 16 pixels
                                       BufferedReader in = new BufferedReader(new FileReader(currentFile));
                                       ArrayList<ArrayList<String>> csvNotes = new ArrayList<ArrayList<String>>();
                                       String tempo = in.readLine();
                                       tempoInput.setText(tempo);
                                       String tempInstruments[] = (in.readLine()).split(",");
                                       for (int i = 0; i < instruments.length; i++)
                                       {
                                           instruments[i].setSelectedIndex(Integer.valueOf(tempInstruments[i]));
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
                                           int beat = data.getRowCount();
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
                                           model.addRow(new Object[]{improperFraction/*,"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"*/});
                                           for (int currentTrack = 0; currentTrack < csvNotes.get(currentNoteIndex).size(); currentTrack++)
                                           {
                                               if (currentTrack == 8)
                                               {
                                                   if((csvNotes.get(currentNoteIndex).get(currentTrack)).equals("|"))
                                                   {
                                                       model.setValueAt("", currentNoteIndex, model.getColumnCount()-2);
                                                   }
                                                   else
                                                   {
                                                       model.setValueAt((csvNotes.get(currentNoteIndex).get(currentTrack)), currentNoteIndex, model.getColumnCount()-2);
                                                   }
                                               }
                                               if (currentTrack == csvNotes.get(currentNoteIndex).size()-2)
                                               {
                                                   if((csvNotes.get(currentNoteIndex).get(currentTrack)).equals("|"))
                                                   {
                                                       model.setValueAt("", currentNoteIndex, 9);
                                                   }
                                                   else
                                                   {
                                                       model.setValueAt((csvNotes.get(currentNoteIndex).get(currentTrack)), currentNoteIndex, 9);
                                                   }
                                               }
                                               else
                                               {
                                                   if((csvNotes.get(currentNoteIndex).get(currentTrack)).equals("|"))
                                                   {
                                                       model.setValueAt("", currentNoteIndex, currentTrack+1);
                                                   }
                                                   else
                                                   {
                                                       model.setValueAt((csvNotes.get(currentNoteIndex).get(currentTrack)), currentNoteIndex, currentTrack+1);
                                                   }
                                               }
                                           }
                                       }
                                       data.setPreferredSize(new Dimension(1233,16*data.getRowCount()));//Each row is 16 pixels
                                       frame.setTitle("Interfere - " + getCurrentFile().getName());
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
       
        projectSave.addActionListener(new ActionListener() 
        {
              public void actionPerformed(ActionEvent e)
              {
                  //Save contents of music panel to current file
                  if(isMusicPanelVisible())
                  {
                      try
                      {
                          if (tempoInput.getText().equals(""))
                          {
                              tempoInput.setText("100");
                          }
                          int  tempo = Integer.valueOf(tempoInput.getText());
                          String musicFileName = getCurrentFile().getAbsolutePath();
                          PrintWriter out = new PrintWriter(new FileWriter(musicFileName));
                          for (int i = 0; i < instruments.length; i++)
                          {
                              if (instruments[i].getItemAt(instruments[i].getSelectedIndex()).equals(""))
                              {
                                  instruments[i].setSelectedIndex(0);//was 1
                              }
                          }
                          out.println(tempo);
                          for (int i = 0; i < 9; i++)
                          {
                              out.print(instruments[i].getSelectedIndex()+",");
                          }
                          out.print("0,");//was 1
                          for (int i = 10; i < instruments.length; i++)
                          {
                              out.print(instruments[i].getSelectedIndex()+",");
                          }
                          out.print(instruments[9].getSelectedIndex()+",");
                          out.println("0");//was 1
                          out.println("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
                          boolean endFound = false;
                          for (int row = 0; row < data.getRowCount(); row++)
                          {
                              for(int column = 1; column < 9; column++)
                              {
                                  out.print((data.getValueAt(row, column)+",").replace("null","|"));
                              }
                              out.print((data.getValueAt(row, data.getColumnCount()-2)+",").replace("null","|"));
                              for(int column = 10; column < data.getColumnCount()-2; column++)
                              {
                                  out.print((data.getValueAt(row, column)+",").replace("null","|"));
                              }
                              out.print((data.getValueAt(row, 9)+",").replace("null","|"));
                              out.println((data.getValueAt(row, data.getColumnCount()-1)+"").replace("null","|"));
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
        projectMenu.add(projectNew);
        projectMenu.add(projectOpen);
        projectMenu.add(projectSave);
        menuBar.add(projectMenu);
        
    /*
     * Export menu components
     */
    JMenu exportMenu = new JMenu("Export");
        JMenuItem exportMIDI = new JMenuItem("MIDI");
        JMenuItem exportWAV = new JMenuItem("WAV");
        JMenuItem exportAU = new JMenuItem("AU");
        JMenuItem exportAIFF = new JMenuItem("AIFF");
        exportMIDI.addActionListener(new ActionListener() 
        {
              public void actionPerformed(ActionEvent e)
              {
                  if(isMusicPanelVisible())
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
                               if (tempoInput.getText().equals(""))
                               {
                                   tempoInput.setText("100");
                               }
                               int  tempo = Integer.valueOf(tempoInput.getText());
                               String musicFileName = getCurrentFile().getAbsolutePath();
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
        exportWAV.addActionListener(new ActionListener() 
        {
              public void actionPerformed(ActionEvent e)
              {
                  if(isMusicPanelVisible())
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
                               if (tempoInput.getText().equals(""))
                               {
                                   tempoInput.setText("100");
                               }
                               int  tempo = Integer.valueOf(tempoInput.getText());
                               String musicFileName = getCurrentFile().getAbsolutePath();
                               MusicPlayer.writeWav(musicFileName,tempo,outputFileName);
                               JOptionPane.showMessageDialog(null, "Successfully exported WAV.", "Exported", JOptionPane.PLAIN_MESSAGE);
                           }
                           catch (Exception x)
                           {
                               JOptionPane.showMessageDialog(null, "Failed to export WAV.", "Error", JOptionPane.PLAIN_MESSAGE);
                           }
                      }
                  }
                  else
                  {
                      JOptionPane.showMessageDialog(null, "Must create or open project.", "Error", JOptionPane.PLAIN_MESSAGE);
                  }
              }
        });
        exportAU.addActionListener(new ActionListener() 
        {
              public void actionPerformed(ActionEvent e)
              {
                  if(isMusicPanelVisible())
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
                               if (tempoInput.getText().equals(""))
                               {
                                   tempoInput.setText("100");
                               }
                               int  tempo = Integer.valueOf(tempoInput.getText());
                               String musicFileName = getCurrentFile().getAbsolutePath();
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
        exportAIFF.addActionListener(new ActionListener() 
        {
              public void actionPerformed(ActionEvent e)
              {
                  if(isMusicPanelVisible())
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
                               if (tempoInput.getText().equals(""))
                               {
                                   tempoInput.setText("100");
                               }
                               int  tempo = Integer.valueOf(tempoInput.getText());
                               String musicFileName = getCurrentFile().getAbsolutePath();
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
        exportMenu.add(exportMIDI);
        exportMenu.add(exportWAV);
        exportMenu.add(exportAU);
        exportMenu.add(exportAIFF);
        menuBar.add(exportMenu);
        
    /*
     * More menu components
     */
    JMenu moreMenu = new JMenu("More");
        JMenuItem moreCMD = new JMenuItem("CMD Playback");
        JMenuItem moreAbout = new JMenuItem("About");
        JMenuItem moreHelp = new JMenuItem("Help");
        JMenuItem moreHomePage = new JMenuItem("Home Page");
        JMenuItem morePreferences = new JMenuItem("Preferences");
        JMenuItem moreUpdate = new JMenuItem("Update");
        
        moreCMD.addActionListener(new ActionListener() 
        {
              public void actionPerformed(ActionEvent e)
              {
                  if(isMusicPanelVisible())
                  {
                      try
                      {
                          if (tempoInput.getText().equals(""))
                          {
                              tempoInput.setText("100");
                          }
                          int  tempo = Integer.valueOf(tempoInput.getText());
                          String musicFileName = getCurrentFile().getAbsolutePath();
                          MusicPlayer.startCMD(musicFileName,tempo);
                          JOptionPane.showMessageDialog(null, "Successfully exported CMD.", "Exported", JOptionPane.PLAIN_MESSAGE);
                      }
                      catch (Exception x)
                      {
                          JOptionPane.showMessageDialog(null, "Failed to export CMD.", "Error", JOptionPane.PLAIN_MESSAGE);
                      }
                  }
                  else
                  {
                      JOptionPane.showMessageDialog(null, "Must create or open project.", "Error", JOptionPane.PLAIN_MESSAGE);
                  }
              }
        });
        moreAbout.addActionListener(new ActionListener() 
        {
              public void actionPerformed(ActionEvent e)
              {
                  //Display information about software
                  JOptionPane.showMessageDialog(null, "Author: Abir Haque\nContact: abir.haque.usa@gmail.com\nHomepage: abirhaque.github.io", "About", JOptionPane.PLAIN_MESSAGE);
              }
        });
        moreHelp.addActionListener(new ActionListener() 
        {
              public void actionPerformed(ActionEvent e)
              {
                  //Redirect user to help website
                  Desktop desktop = java.awt.Desktop.getDesktop();
                  try 
                  {
                      desktop.browse(new URI("https://interfere-docs.abirhaque.repl.co/docs.html"));
                  }
                  catch (Exception x) 
                  {
                      JOptionPane.showMessageDialog(null, "Failed to open help site. Navigate to https://interfere-docs.abirhaque.repl.co/docs.html to reach the help site.", "Error", JOptionPane.PLAIN_MESSAGE);
                  }
              }
        });
        moreHomePage.addActionListener(new ActionListener() 
        {
              public void actionPerformed(ActionEvent e)
              {
                  //Redirect user to homepage
                  Desktop desktop = java.awt.Desktop.getDesktop();
                  try 
                  {
                      desktop.browse(new URI("https://interfere-docs.abirhaque.repl.co"));
                  }
                  catch (Exception x) 
                  {
                      JOptionPane.showMessageDialog(null, "Failed to open homepage. Navigate to https://interfere-docs.abirhaque.repl.co to reach the homepage.", "Error", JOptionPane.PLAIN_MESSAGE);
                  }
              }
        });
        morePreferences.addActionListener(new ActionListener() 
        {
              public void actionPerformed(ActionEvent e)
              {
                  //Bring preferences panel
                  getMusicPanel().setVisible(false);
                  getPreferencesPanel().setVisible(true);
              }
        });
        moreUpdate.addActionListener(new ActionListener() 
        {
              public void actionPerformed(ActionEvent e)
              {
                  //Check for software update
                  try
                  {
                      URL versionPage = new URL("https://interfere-docs.abirhaque.repl.co/cascade/versions.txt");
                      BufferedReader in = new BufferedReader(new InputStreamReader(versionPage.openStream()));
                      ArrayList<String> versions = new ArrayList<String>();
                      while (in.ready())
                      {
                          versions.add(in.readLine());
                      }
                      in.close();
                      if (!version.equals(versions.get(versions.size()-1)))
                      {
                          JOptionPane.showMessageDialog(null, "Update available!", "Update", JOptionPane.PLAIN_MESSAGE);
                          Desktop desktop = java.awt.Desktop.getDesktop();
                          try 
                          {
                              desktop.browse(new URI("https://interfere-docs.abirhaque.repl.co/cascade/install.html"));
                          }
                          catch (Exception x) 
                          {
                              JOptionPane.showMessageDialog(null, "Failed to open homepage. Navigate to https://interfere-docs.abirhaque.repl.co/cascade/install.html to reach the install page.", "Error", JOptionPane.PLAIN_MESSAGE);
                          }
                      }
                      else
                      {
                          JOptionPane.showMessageDialog(null, "No updates available.", "Update", JOptionPane.PLAIN_MESSAGE);
                      }
                  }
                  catch (Exception x)
                  {
                      JOptionPane.showMessageDialog(null, "Failed to check for update.", "Error", JOptionPane.PLAIN_MESSAGE);
                  }
              }
        });
        moreMenu.add(moreCMD);
        moreMenu.add(moreAbout);
        moreMenu.add(moreHelp);
        moreMenu.add(moreHomePage);
        moreMenu.add(morePreferences);
        moreMenu.add(moreUpdate);
        menuBar.add(moreMenu);
            
            
            
            
        getMusicPanel().setVisible(false);
        getPreferencesPanel().setVisible(false);
        
        /*
         * Frame attributes
         */
        frame.setLayout(new FlowLayout());
        frame.add(getMusicPanel());//frame.add(musicPanel);
        frame.add(getPreferencesPanel());
        frame.pack();
        frame.setJMenuBar(menuBar);
        frame.setSize(1300,600);
        frame.setVisible(true);
        frame.setIconImage(logo.getImage());
    }
}