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
    private static JComboBox[] instruments;
    private static JTable data;
    private static DefaultTableModel model;
    private static File currentFile = new File("");
    private static int deltaMillisecondsCMD;
    private static String version = "0.0.3";
    private static boolean isScrolling = false;
    private static Clipboard clipboard;
    private static KeyStroke copy;
    private static KeyStroke paste;
    private static KeyStroke cut;
    private static KeyStroke delete;
    private static JFrame frame;
    private static JSpinner spinner;
    private static JButton cancelPreferencesButton;
    private static JButton savePreferencesButton;
    private static JButton playMusicButton;
    private static JButton stopMusicButton;
    private static JButton addRowButton;
    private static JButton removeRowButton;
    private static JButton seekMusicButton;
    private static JTextField seekMusicInput;
    private static JTextField tempoInput;
    private static JTextField addRowInput;
    private static JTextField removeRowInput;
    private static JMenuItem projectNew;
    private static JMenuItem projectOpen;
    private static JMenuItem projectSave;
    private static JMenuItem exportMIDI;
    private static JMenuItem exportWAV;
    private static JMenuItem exportAU;
    private static JMenuItem exportAIFF;
    private static JMenuItem moreCMD;
    private static JMenuItem moreAbout;
    private static JMenuItem moreHelp;
    private static JMenuItem moreHomePage;
    private static JMenuItem morePreferences;
    private static JMenuItem moreUpdate;
    private static JMenuItem moreLicense;
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
    public static String getVersion()
    {
        return version;
    }
    public static void setVersion(String input)
    {
        version = input;
    }
    public static void setIsScrolling(boolean state)
    {
        isScrolling = state;
    }
    public static boolean getIsScrolling()
    {
        return isScrolling;
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
    public static JComboBox[] getInstruments()
    {
        return instruments;
    }
    public static JTable getData()
    {
        return data;
    }
    public static Clipboard getClipboard()
    {
        return clipboard;
    }
    public static KeyStroke getCopy()
    {
        return copy;
    }
    public static KeyStroke getCut()
    {
        return cut;
    }
    public static KeyStroke getPaste()
    {
        return paste;
    }
    public static KeyStroke getDelete()
    {
        return delete;
    }
    public static JFrame getFrame()
    {
        return frame;
    }
    public static JSpinner getSpinner()
    {
        return spinner;
    }
    public static JButton getCancelPreferencesButton()
    {
        return cancelPreferencesButton;
    }
    public static JButton getSavePreferencesButton()
    {
        return savePreferencesButton;
    }
    public static JButton getPlayMusicButton()
    {
        return playMusicButton;
    }
    public static JButton getStopMusicButton()
    {
        return stopMusicButton;
    }
    public static JButton getAddRowButton()
    {
        return addRowButton;
    }
    public static JButton getRemoveRowButton()
    {
        return removeRowButton;
    }
    public static DefaultTableModel getModel()
    {
        return model;
    }
    public static JTextField getSeekMusicInput()
    {
        return seekMusicInput;
    }
    public static JTextField getTempoInput()
    {
        return tempoInput;
    }
    public static JTextField getAddRowInput()
    {
        return addRowInput;
    }
    public static JTextField getRemoveRowInput()
    {
        return removeRowInput;
    }
    public static JButton getSeekMusicButton()
    {
        return seekMusicButton;
    }
    public static JMenuItem getProjectNew()
    {
        return projectNew;
    }
    public static JMenuItem getProjectOpen()
    {
        return projectOpen;
    }
    public static JMenuItem getProjectSave()
    {
        return projectSave;
    }
    public static JMenuItem getExportMIDI()
    {
        return exportMIDI;
    }
    public static JMenuItem getExportWAV()
    {
        return exportWAV;
    }
    public static JMenuItem getExportAU()
    {
        return exportAU;
    }
    public static JMenuItem getExportAIFF()
    {
        return exportAIFF;
    }
    public static JMenuItem getMoreCMD()
    {
        return moreCMD;
    }
    public static JMenuItem getMoreAbout()
    {
        return moreAbout;
    }
    public static JMenuItem getMoreHelp()
    {
        return moreHelp;
    }
    public static JMenuItem getMoreHomePage()
    {
        return moreHomePage;
    }
    public static JMenuItem getMorePreferences()
    {
        return morePreferences;
    }
    public static JMenuItem getMoreUpdate()
    {
        return moreUpdate;
    }
    public static JMenuItem getMoreLicense()
    {
        return moreLicense;
    }
    public static void main(String[] args) throws Exception
    {
        loadPreferences();
        
        GUILoader guiLoader = new GUILoader();
        
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        copy = KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK, false);
        paste = KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK, false);
        cut = KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK, false);
        delete = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
        
        frame = new JFrame("Interfere Cascade");
        
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        ImageIcon logo = new ImageIcon("logo.png");
        
        
        
         
        getPreferencesPanel().setLayout(new GridLayout(2,10));
        
        JLabel deltaMillisecondsCMDLabel = new JLabel("Delay in scroller between notes: ");
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(getDeltaMillisecondsCMD(),1,100,1);
        spinner = new JSpinner(spinnerModel);
        JFormattedTextField formattedTextField = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
        formattedTextField.setEditable(false);
        cancelPreferencesButton = new JButton("Cancel");
        savePreferencesButton = new JButton("Save");
        PreferencesListeners.setListeners();
        getPreferencesPanel().add(deltaMillisecondsCMDLabel);
        getPreferencesPanel().add(spinner);
        getPreferencesPanel().add(cancelPreferencesButton);
        getPreferencesPanel().add(savePreferencesButton);
        
        
        
        
        
        
        getMusicPanel().setLayout(new BorderLayout());
    
        JPanel topMusicPanel = new JPanel();
        topMusicPanel.setLayout(new GridLayout(0,18));
        JPanel notePanel = new JPanel(); 
            playMusicButton = new JButton("Play");
            stopMusicButton = new JButton("Stop");
            seekMusicButton = new JButton("Seek:");
            addRowButton = new JButton("+");
            removeRowButton = new JButton("-");
            seekMusicInput = new JTextField(5);
            tempoInput = new JTextField(5);
            addRowInput = new JTextField(5);
            removeRowInput = new JTextField(5);
            String[] instrumentList = DefaultStrings.getInstrumentList();
            instruments = new JComboBox[15];
            data = new JTable(0,18); //18*96000, so 96000,17?
            JTextField beatCell = new JTextField();
            beatCell.setEditable(false);
            data.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(beatCell));
            model = (DefaultTableModel) data.getModel();
    
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
    
                RowListeners.setListeners();
                data.setColumnSelectionAllowed(true);
                data.setRowSelectionAllowed(true);
                DataListeners.setListeners();//Better having the series of ActionListeners in its own class than dealing with 1000+ lines of code.
                
                
                
            JScrollPane scroll = new JScrollPane(notePanel);
            scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            getMusicPanel().add(scroll);
            scroll.setPreferredSize(new Dimension(1250,400));
            scroll.getVerticalScrollBar().setUnitIncrement(20);
            
            
            PlaybackListeners.setListeners();
                

            
        /*
         * Menu bar components
         */
        JMenuBar menuBar = new JMenuBar();
        JMenu projectMenu = new JMenu("Project");
        
        /*
         * Project menu components
         */
            projectNew = new JMenuItem("New");
            projectOpen = new JMenuItem("Open");
            projectSave = new JMenuItem("Save");
            ProjectMenuListeners.setListeners();
            projectMenu.add(projectNew);
            projectMenu.add(projectOpen);
            projectMenu.add(projectSave);
            menuBar.add(projectMenu);
        
        /*
         * Export menu components
         */
        JMenu exportMenu = new JMenu("Export");
            exportMIDI = new JMenuItem("MIDI");
            exportWAV = new JMenuItem("WAV");
            exportAU = new JMenuItem("AU");
            exportAIFF = new JMenuItem("AIFF");
            ExportMenuListeners.setListeners();
            exportMenu.add(exportMIDI);
            exportMenu.add(exportWAV);
            exportMenu.add(exportAU);
            exportMenu.add(exportAIFF);
            menuBar.add(exportMenu);
        
        /*
         * More menu components
         */
        JMenu moreMenu = new JMenu("More");
            moreCMD = new JMenuItem("CMD Playback");
            moreAbout = new JMenuItem("About");
            moreHelp = new JMenuItem("Help");
            moreHomePage = new JMenuItem("Home Page");
            morePreferences = new JMenuItem("Preferences");
            moreUpdate = new JMenuItem("Update");
            moreLicense = new JMenuItem("License Information");
            MoreMenuListeners.setListeners();
            moreMenu.add(moreCMD);
            moreMenu.add(moreAbout);
            moreMenu.add(moreHelp);
            moreMenu.add(moreHomePage);
            moreMenu.add(morePreferences);
            moreMenu.add(moreUpdate);
            moreMenu.add(moreLicense);
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