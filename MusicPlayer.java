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
import javax.sound.midi.*; 
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.*;
public class MusicPlayer
{
    private static Sequencer sequencer;
    private static Sequence sequence;
    private static Map<Integer,String> commands;
    public static void setMidiSequence(String musicFileName) throws Exception
    {
        setMidiSequence(musicFileName, 100,4);
    }
    public static void setMidiSequence(String musicFileName, int requestedTempo, int requestedResolution) throws Exception
    {
        sequencer = MidiSystem.getSequencer();
        BufferedReader in = new BufferedReader(new FileReader(musicFileName));
        int timingResolution = requestedResolution;
        sequencer.open();
        sequence = new Sequence(Sequence.PPQ, timingResolution);
        ArrayList<ArrayList<String>> csvNotes = new ArrayList<ArrayList<String>>();
        commands = new HashMap<Integer,String>();
        int tempo = Integer.valueOf(in.readLine());
        if (requestedTempo!=tempo)
        {
            requestedTempo = tempo;
        }
        while(in.ready())
        {   
            String line = in.readLine();
            String delimiter = ",";
            if (line.contains("\t"))
            {
                delimiter = "\t";
            }
            csvNotes.add(new ArrayList<String>(Arrays.asList((line).split(delimiter))));
        }
        int tracks = (csvNotes.get(0)).size()-2;//exclude event column and channel 16
        int totalNotes = csvNotes.size();
        for (int currentTrack = 0; currentTrack < tracks; currentTrack++)
        {
            ArrayList<String> trackNotes = new ArrayList<String>();
            for (int currentNoteIndex = 1; currentNoteIndex < totalNotes; currentNoteIndex++)
            {
                if (csvNotes.get(currentNoteIndex).get(currentTrack).equals(""))
                {
                    csvNotes.get(currentNoteIndex).set(currentTrack, "|");
                }
                if (csvNotes.get(currentNoteIndex).get(currentTrack).contains("|"))
                {
                    trackNotes.add("-1");
                }
                else
                {
                    if (csvNotes.get(currentNoteIndex).get(currentTrack).contains("-")||csvNotes.get(currentNoteIndex).get(currentTrack).contains("#")||csvNotes.get(currentNoteIndex).get(currentTrack).contains("b"))
                    {
                        String noteCode = csvNotes.get(currentNoteIndex).get(currentTrack);
                        Map<String,Integer> midiNotes = new HashMap<String,Integer>();
                            midiNotes.put("C",0);
                            midiNotes.put("D",2);
                            midiNotes.put("E",4);
                            midiNotes.put("F",5);
                            midiNotes.put("G",7);
                            midiNotes.put("A",9);
                            midiNotes.put("B",11);
                        if (noteCode.contains("-"))
                        {
                            String[] splitNote = noteCode.split("-");
                            int midiValue = midiNotes.get(splitNote[0])+(12*(2+Integer.valueOf(splitNote[1])));
                            trackNotes.add(String.valueOf(midiValue));
                        }
                        if (noteCode.contains("#"))
                        {
                            String[] splitNote = noteCode.split("#");
                            int midiValue = midiNotes.get(splitNote[0])+(12*(2+Integer.valueOf(splitNote[1])))+1;
                            trackNotes.add(String.valueOf(midiValue));
                        }
                        if (noteCode.contains("b"))
                        {
                            String[] splitNote = noteCode.split("b");
                            int midiValue = midiNotes.get(splitNote[0])+(12*(2+Integer.valueOf(splitNote[1])))-1;
                            trackNotes.add(String.valueOf(midiValue));
                         }
                    }
                    else
                    {
                        if(csvNotes.get(currentNoteIndex).get(currentTrack).contains("*"))
                        {
                            String command = csvNotes.get(currentNoteIndex).get(currentTrack);
                            command = command.substring(command.indexOf("*")+1);
                            int deltaSleep = 1000/(((tempo+10)*8)/GUILoader.getDeltaMillisecondsCMD());
                            if ((currentNoteIndex*deltaSleep)%(deltaSleep*1000) == 0)
                            {
                                commands.put(currentNoteIndex*(deltaSleep-4), command);
                            }
                            else
                            {
                                commands.put(currentNoteIndex*deltaSleep, command);
                            }
                            trackNotes.add("0");
                        }
                        else
                        {
                            trackNotes.add(csvNotes.get(currentNoteIndex).get(currentTrack));
                        }
                    }
                }       
            }
            Track track = sequence.createTrack();
            int trackInstrument = Integer.valueOf(csvNotes.get(0).get(currentTrack));
            if(trackInstrument>128)
            {
                trackInstrument = trackInstrument % 127;
            }
            track.add(new MidiEvent(new ShortMessage(192,currentTrack+1,trackInstrument,0),1));
            int volume = 100;
            for (int i = 5; i < (4*(totalNotes-1))+5;i+=4)
            {
                if ((trackNotes.get(0)).contains("~"))
                {
                    String[] noteWithVolume = (trackNotes.get(0)).split("~");
                    volume = Integer.valueOf(noteWithVolume[1]);
                    trackNotes.set(0, noteWithVolume[0]);
                }
                int note = Integer.valueOf(trackNotes.get(0));
                if(trackNotes.size()>1 && (trackNotes.get(1)).equals("-1"))
                {
                    if ((trackNotes.get(0)).equals("0"))
                    {
                        track.add(new MidiEvent(new ShortMessage(144,currentTrack+1,note,0),i));
                        trackNotes.remove(0);
                        while((trackNotes.get(0)).equals("-1"))
                        {
                            i+=4;
                            trackNotes.remove(0);
                        }
                        track.add(new MidiEvent(new ShortMessage(128,currentTrack+1,note,0),i+2));
                    }
                    else
                    {
                        track.add(new MidiEvent(new ShortMessage(144,currentTrack+1,note,volume),i));
                        trackNotes.remove(0);
                        while((trackNotes.get(0)).equals("-1"))
                        {
                            i+=4;
                            trackNotes.remove(0);
                        }
                        track.add(new MidiEvent(new ShortMessage(128,currentTrack+1,note,volume),i+2));
                    }
                }
                else
                {
                    if (note !=0)
                    {
                        if (note > 108)
                        {
                            note = note % 108;
                        }
                        track.add(new MidiEvent(new ShortMessage(144,currentTrack+1,note,volume),i));
                        track.add(new MidiEvent(new ShortMessage(128,currentTrack+1,note,volume),i+2));
                    }
                    trackNotes.remove(0);
                }
            }
        }
        //Read event column
        for (int currentNoteIndex = 1; currentNoteIndex < totalNotes; currentNoteIndex++)
        {
            if(csvNotes.get(currentNoteIndex).get(tracks+1).contains("*"))
            {
                String command = csvNotes.get(currentNoteIndex).get(tracks+1);
                command = command.substring(command.indexOf("*")+1);
                int deltaSleep = 1000/(((tempo+10)*8)/GUILoader.getDeltaMillisecondsCMD());
                if ((currentNoteIndex*deltaSleep)%(deltaSleep*1000) == 0)
                {
                    commands.put(currentNoteIndex*(deltaSleep-4), command);
                }
                else
                {
                    commands.put(currentNoteIndex*deltaSleep, command);
                }
            } 
        }
        sequencer.setSequence(sequence);
        sequencer.setTempoInBPM(tempo);
    }
    public static void stop() throws Exception
    {
        sequencer.stop();
    }
    public static void start(String musicFileName) throws Exception
    { 
        start(musicFileName, 100);
    }
    public static void start(String musicFileName, int tempo) throws Exception
    { 
        setMidiSequence(musicFileName, tempo*10,tempo/3/*4*/);
        sequencer.start();
    }
    public static void startCMD(String musicFileName, int tempo) throws Exception
    { 
        setMidiSequence(musicFileName, tempo*10,tempo/3/*4*/);
        sequencer.start();
        BufferedReader in2 = new BufferedReader(new FileReader(new File(musicFileName)));
        in2.readLine();
        int ms = 0;
        int deltaSleep = 1000/(((tempo+10)*8)/GUILoader.getDeltaMillisecondsCMD());//((100-(tempo-100))/8)*7;/*100bpm ~ 70*///Originally 60
        while(in2.ready())
        {
            System.out.println((in2.readLine()).replace(",","\t"));
            if (ms % (deltaSleep*1000)/*60000*/ == 0)
            {
                ms = deltaSleep;//Originally 60
                Thread.sleep(deltaSleep-4);
            }
            else
            {               
                Thread.sleep(deltaSleep);
                ms+=deltaSleep;//Originally 60
            }
            if (commands.containsKey(ms))
            {
                Process p = new ProcessBuilder("cmd", "/c", commands.get(ms)).inheritIO().start();
            }
        }
        Process p = new ProcessBuilder("cmd", "/c", "color 07").inheritIO().start();
    }
    public static void writeMidi(String musicFileName, String outputFileName) throws Exception
    { 
        writeMidi(musicFileName, 100, outputFileName);
    }
    public static void writeMidi(String musicFileName, int tempo, String outputFileName) throws Exception
    { 
        setMidiSequence(musicFileName, tempo, tempo/3);//60/4;=15  120/2=60
        MidiSystem.write(sequence, 1, new File(outputFileName));
    }
    public static void writeWav(String musicFileName, int tempo, String outputFileName) throws Exception
    { 
        setMidiSequence(musicFileName, tempo, tempo/3);//60/4;=15  120/2=60
        MidiSystem.write(sequence, 1, new File("temp.mid"));
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("temp.mid"));
    AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, new File(outputFileName));
    }
    public static void writeAu(String musicFileName, int tempo, String outputFileName) throws Exception
    { 
        setMidiSequence(musicFileName, tempo, tempo/3);//60/4;=15  120/2=60
        MidiSystem.write(sequence, 1, new File("temp.mid"));
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("temp.mid"));
    AudioSystem.write(audioStream, AudioFileFormat.Type.AU, new File(outputFileName));
    }
    public static void writeAiff(String musicFileName, int tempo, String outputFileName) throws Exception
    { 
        setMidiSequence(musicFileName, tempo, tempo/3);//60/4;=15  120/2=60
        MidiSystem.write(sequence, 1, new File("temp.mid"));
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("temp.mid"));
    AudioSystem.write(audioStream, AudioFileFormat.Type.AIFF, new File(outputFileName));
    }
}