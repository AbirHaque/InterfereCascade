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
import java.net.*;
public class MoreMenuListeners
{
    public static void setListeners()
    {
        GUILoader.getMoreCMD().addActionListener(new ActionListener() 
        {
              public void actionPerformed(ActionEvent e)
              {
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
        GUILoader.getMoreAbout().addActionListener(new ActionListener() 
        {
              public void actionPerformed(ActionEvent e)
              {
                  //Display information about software
                  JOptionPane.showMessageDialog(null, "Version: "+GUILoader.getVersion()+"\nAuthor: Abir Haque\nContact: abir.haque.usa@gmail.com\nHomepage: abirhaque.github.io", "About", JOptionPane.PLAIN_MESSAGE);
              }
        });
        GUILoader.getMoreHelp().addActionListener(new ActionListener() 
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
        GUILoader.getMoreHomePage().addActionListener(new ActionListener() 
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
        GUILoader.getMorePreferences().addActionListener(new ActionListener() 
        {
              public void actionPerformed(ActionEvent e)
              {
                  //Bring preferences panel
                  GUILoader.getMusicPanel().setVisible(false);
                  GUILoader.getPreferencesPanel().setVisible(true);
              }
        });
        GUILoader.getMoreUpdate().addActionListener(new ActionListener() 
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
                      if (!(GUILoader.getVersion()).equals(versions.get(versions.size()-1)))
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
        GUILoader.getMoreLicense().addActionListener(new ActionListener() 
        {
              public void actionPerformed(ActionEvent e)
              {
                  //Bring licence information panel
                  try
                  {
                      BufferedReader in = new BufferedReader(new FileReader("LICENSE.txt"));
                      String licenseString = "";
                      while(in.ready())
                      {
                          licenseString+=in.readLine()+"\n";
                      }
                      in.close();
                      JTextArea text = new JTextArea(licenseString);
                      text.setEditable(false);
                      Dimension newDim = new Dimension(700,400);
                      JScrollPane scroll = new JScrollPane(text);  
                      scroll.setPreferredSize(newDim);
                      scroll.setMinimumSize(newDim);
                      scroll.setPreferredSize(newDim);
                      scroll.setMaximumSize(newDim);
                      scroll.setSize(newDim);
                      scroll.revalidate();
                      scroll.getVerticalScrollBar().setUnitIncrement(20);
                      scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                      scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                      JOptionPane.showMessageDialog(null, scroll, "License Information", JOptionPane.PLAIN_MESSAGE);
                  }
                  catch(Exception x)
                  {
                      JOptionPane.showMessageDialog(null, "Failed to display license information. You can still view license information in the <LICENSE.txt> file.", "Error", JOptionPane.PLAIN_MESSAGE);
                  }
              }
        });
    }
}
