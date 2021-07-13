package org.libjpegturbo.turbojpeg;

import java.io.File;
import java.io.FileInputStream;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// call JEncoder to decompress and compress an image file
			// params: image file name, quality of compression, JPEG block size (8 recommended by JPEG)

			FileInputStream is = new FileInputStream(new File("/Users/edsonmartins/Downloads/squirrel_animal_nature.jpg"));
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        IOUtils.copy(is,baos);
//        byte[] bytes = baos.toByteArray();

            new JEncoder("test.jpg", 1, 8);
        } catch (Exception e) {
        	e.printStackTrace();
        }

	}

}
