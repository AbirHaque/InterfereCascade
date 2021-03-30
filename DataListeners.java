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
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
public class DataListeners
{
    public static void setListeners()
    {
        GUILoader.getData().registerKeyboardAction(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                StringSelection stsel;
                StringBuffer sbf=new StringBuffer();
                int columns=GUILoader.getData().getSelectedColumnCount();
                int rows=GUILoader.getData().getSelectedRowCount();
                int[] rowsselected=GUILoader.getData().getSelectedRows();
                int[] colsselected=GUILoader.getData().getSelectedColumns();
                for (int i=0;i<rows;i++)
                {
                    for (int j=0;j<columns;j++)
                    {
                        if(String.valueOf(GUILoader.getData().getValueAt(rowsselected[i],colsselected[j])).replace("null","").equals(""))
                        {
                            sbf.append("[Empty cell]");
                        }
                        else
                        {
                            sbf.append(GUILoader.getData().getValueAt(rowsselected[i],colsselected[j])+"");
                        }
                        if (j<columns-1) 
                        {
                            sbf.append("\t");
                        }
                    }
                    sbf.append("\n");
                }
                stsel  = new StringSelection(sbf.toString().replace("null",""));
                GUILoader.getClipboard().setContents(stsel,stsel);
            }
        } , "Copy", GUILoader.getCopy(), JComponent.WHEN_FOCUSED);
        GUILoader.getData().registerKeyboardAction(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int startRow=(GUILoader.getData().getSelectedRows())[0];
                int startCol=(GUILoader.getData().getSelectedColumns())[0];
                try
                {
                    String trstring= (String)(GUILoader.getClipboard().getContents(GUILoader.getClipboard()).getTransferData(DataFlavor.stringFlavor));
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
                            if (startRow+i< GUILoader.getData().getRowCount() && startCol+j< GUILoader.getData().getColumnCount())
                            {
                               if(value.equals("[Empty cell]"))
                               {
                                   GUILoader.getData().setValueAt("",startRow+i,startCol+j);
                               }
                               else
                               {
                                   GUILoader.getData().setValueAt(value,startRow+i,startCol+j);
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
        } , "Paste", GUILoader.getPaste(), JComponent.WHEN_FOCUSED);
        GUILoader.getData().registerKeyboardAction(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                StringSelection stsel;
                StringBuffer sbf=new StringBuffer();
                int startRow=(GUILoader.getData().getSelectedRows())[0];
                int startCol=(GUILoader.getData().getSelectedColumns())[0];
                int columns=GUILoader.getData().getSelectedColumnCount();
                int rows=GUILoader.getData().getSelectedRowCount();
                int[] rowsselected=GUILoader.getData().getSelectedRows();
                int[] colsselected=GUILoader.getData().getSelectedColumns();
                for (int i=0;i<rows;i++)
                {
                    for (int j=0;j<columns;j++)
                    {
                        if(String.valueOf(GUILoader.getData().getValueAt(rowsselected[i],colsselected[j])).replace("null","").equals(""))
                        {
                            sbf.append("[Empty cell]");
                        }
                        else
                        {
                            sbf.append(GUILoader.getData().getValueAt(rowsselected[i],colsselected[j])+"");
                        }
                        if (j<columns-1) 
                        {
                            sbf.append("\t");
                        }
                    }
                    sbf.append("\n");
                }
                stsel  = new StringSelection(sbf.toString().replace("null",""));
                GUILoader.getClipboard().setContents(stsel,stsel);
                
                try
                {
                    String trstring= (String)(GUILoader.getClipboard().getContents(GUILoader.getClipboard()).getTransferData(DataFlavor.stringFlavor));
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
                            if (startRow+i< GUILoader.getData().getRowCount() && startCol+j< GUILoader.getData().getColumnCount())
                            {
                                GUILoader.getData().setValueAt("",startRow+i,startCol+j);
                            }
                        }
                    }
                }
                catch(Exception x)
                {
                      JOptionPane.showMessageDialog(null, "Failed to execute cut shortcut.", "Error", JOptionPane.PLAIN_MESSAGE);
                }
            }
        } , "Cut", GUILoader.getCut(), JComponent.WHEN_FOCUSED);
        GUILoader.getData().registerKeyboardAction(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int startRow=(GUILoader.getData().getSelectedRows())[0];
                int startCol=(GUILoader.getData().getSelectedColumns())[0];
                int columns=GUILoader.getData().getSelectedColumnCount();
                int rows=GUILoader.getData().getSelectedRowCount();
                int[] rowsselected=GUILoader.getData().getSelectedRows();
                int[] colsselected=GUILoader.getData().getSelectedColumns();
                try
                {
                    for (int i=0;i<rows;i++)
                    {
                        for (int j=0;j<columns;j++)
                        {
                            GUILoader.getData().setValueAt("",rowsselected[i],colsselected[j]);
                        }
                    }
                }
                catch(Exception x)
                {
                      JOptionPane.showMessageDialog(null, "Failed to execute delete shortcut.", "Error", JOptionPane.PLAIN_MESSAGE);
                }
            }
        } , "Delete", GUILoader.getDelete(), JComponent.WHEN_FOCUSED);
    }
}