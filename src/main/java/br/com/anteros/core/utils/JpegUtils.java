package br.com.anteros.core.utils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.RenderedImage;
import java.io.*;


public class JpegUtils {

    public static void main(String[] args) throws IOException {
        FileInputStream is = new FileInputStream(new File("/Users/edsonmartins/Downloads/edson.jpg"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IOUtils.copy(is,baos);
        byte[] bytes = baos.toByteArray();
        byte[] jpeg = compressJPEG(bytes, 0.2f);

        FileOutputStream fos = new FileOutputStream(new File("/Users/edsonmartins/Downloads/edson_1.jpg"));
        BufferedOutputStream bs = new BufferedOutputStream(fos);
        bs.write(jpeg);
        bs.close();
    }


    public static byte[] compressJPEG(byte[] source, float compressionQuality) throws IOException{
        RenderedImage image = ImageIO.read(new ByteArrayInputStream(source));
        ImageWriter jpegWriter = ImageIO.getImageWritersByFormatName("jpg").next();
        ImageWriteParam jpegWriteParam = jpegWriter.getDefaultWriteParam();
        jpegWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpegWriteParam.setCompressionQuality(compressionQuality);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(ImageOutputStream output = ImageIO.createImageOutputStream(baos)){
            jpegWriter.setOutput(output);
            IIOImage outputImage = new IIOImage(image, null, null);
            jpegWriter.write(null, outputImage, jpegWriteParam);
        }
        jpegWriter.dispose();
        return baos.size()==0?source:baos.toByteArray();
    }
}