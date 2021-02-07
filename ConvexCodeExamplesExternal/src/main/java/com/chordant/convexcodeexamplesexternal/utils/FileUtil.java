/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chordant.convexcodeexamplesexternal.utils;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

/**
 *
 * @author groov
 */
public class FileUtil {

    private static String filePath = "C:/Temp2";

    public static void saveMessageToFile(String msg) {

        String filename = getTimestamp().replaceAll(" ", "-").replaceAll(":", "-");

        String fileUri = filePath + "/" + filename + ".msg";

        try {
            FileOutputStream fos = new FileOutputStream(fileUri);

            DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
            
            outStream.writeBytes(msg);
            //outStream.writeUTF(msg);
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readMessageFromFile(String filepath) {

        String message = null;

        try {         
            RandomAccessFile reader = new RandomAccessFile(filepath, "r");
            FileChannel channel = reader.getChannel();

            int bufferSize = 1024;
            if (bufferSize > channel.size()) {
                bufferSize = (int) channel.size();
            }
            ByteBuffer buff = ByteBuffer.allocate(bufferSize);
            channel.read(buff);
            buff.flip();
            
            return new String(buff.array());
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getTimestamp() {
        Date date = new Date();
        return date.toString();

    }

}
