package com.dhgh.jspatch.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.dhgh.jspatch.util.JSReader.JsFile;

public class ReadFromFile {
    public static JsFile readFile(String file) throws IOException{
        File filename = new File(file); 
        String fileName = filename.getName();
        StringBuffer result = new StringBuffer();
        InputStream is = null;
        try {
        	is = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is)); 
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            	result.append(line +"\r\n");
            }
            reader.close();
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new JsFile(fileName, result.toString());
    }
    
}