package main.java.com.zeruls.game.util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MakeSound {
    private final int BUFFER_SIZE = 128000;
    private File soundFile;
    private AudioInputStream audioStream;
    private AudioFormat audioFormat;
    private SourceDataLine sourceLine;

    public void playSound(String filename) {
        String strFilename = filename;

        try {
            soundFile = new File(strFilename);
        }catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }

        try {
            audioStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        audioFormat = audioStream.getFormat();

        DataLine.Info info = new DataLine.Info(SourceDataLine.class,audioFormat);
        try {
            sourceLine = (SourceDataLine)AudioSystem.getLine(info);
            sourceLine.open(audioFormat);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        sourceLine.start();

        int nBytesRedad = 0;
        byte[] abData = new byte[BUFFER_SIZE];
        while(nBytesRedad != -1) {
            try {
                nBytesRedad = audioStream.read(abData,0,abData.length);
            } catch(IOException e) {
                e.printStackTrace();
            }
            if(nBytesRedad >= 0) {
                @SuppressWarnings("unused")
                        int nBytesWritten = sourceLine.write(abData,0,nBytesRedad);
            }
            sourceLine.drain();
            sourceLine.close();
        }
    }
}
