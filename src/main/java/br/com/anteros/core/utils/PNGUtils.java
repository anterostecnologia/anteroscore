package br.com.anteros.core.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class PNGUtils {
	
	
	public static byte[] compressPNG(byte[] source) {
		List<String> commandLine = new ArrayList<String>();	
		commandLine.add("/usr/local/bin/pngquant");
		commandLine.add("-");	
		byte[] baosResult=null;
		try {
			baosResult = execute(commandLine,source);		
		} catch (IOException e) {
			commandLine.clear();
			commandLine.add("/usr/local/bin/pngquant");
			commandLine.add("-");			
			try {
				baosResult = execute(commandLine,source);
			} catch (IOException e1) {
			}
		}
		return (baosResult==null?source:baosResult);
	}
	
	protected static byte[] execute(List<String> commandLine, byte[] image) throws IOException {
	    ProcessBuilder processBuilder = new ProcessBuilder(commandLine);
	    final Process process = processBuilder.start();
	    OutputStream stdin = process.getOutputStream ();
	    final InputStream stderr = process.getErrorStream ();
	    final InputStream stdout = process.getInputStream ();
	    final ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    Thread errorT = new Thread() {
	        public void run() {
	            byte[] buf = new byte[1024];
	            int len;
	            try {
	                while ((len = stdout.read(buf)) != -1) {
	                    bos.write(buf, 0, len);
	                    bos.flush();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    };
	    errorT.start();
	    Thread outputT = new Thread() {
	        public void run() {
	            byte[] buf = new byte[1024];
	            int len;
	            try {
	                while ((len = stderr.read(buf)) != -1) {
	                    System.err.write(buf, 0, len);
	                    System.err.flush();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    };
	    outputT.start();
	    stdin.write(image);
	    stdin.flush();
	    stdin.close();
	    try {
	        process.waitFor();
	        errorT.join();
	        outputT.join();
	    } catch (InterruptedException e1) {
	        e1.printStackTrace();
	    }

	    stdin.close();
	    stderr.close();
	    stdout.close();
	    return bos.toByteArray();
	}

}
