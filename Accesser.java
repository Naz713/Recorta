package Recortador;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.nio.file.*;

public class Accesser{
	protected static final String savingDBFN = "CromasDB";
	protected static final String sourceDN = "Cromas";
	protected static final String imageDBDN = "ImagenesCromasDB";
	protected static final String cromasViewingDN = "Originales/";

    public static File Retrive(int i) {
		try {
    		return (new File(sourceDN)).listFiles()[i];
		} catch (NullPointerException | SecurityException e) {
			e.printStackTrace();
		}
        return null;
	}
	public static BufferedImage copyBI(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
	/**Converts a given Image into a BufferedImage
	* @param img The Image to be converted
	* @return The converted BufferedImage
	*/
	public static BufferedImage toBufferedImage(Image img){
    	if (img instanceof BufferedImage){
    	    return (BufferedImage) img;
    	}// Create a buffered image with transparency
    	BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
    	// Draw the image on to the buffered image
    	Graphics2D bGr = bimage.createGraphics();
    	bGr.drawImage(img, 0, 0, null);
    	bGr.dispose();
    	return bimage;
    }
    public static BufferedImage getImage(File imageFile){
        try{
            return ImageIO.read(imageFile);
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
    public static BufferedImage scaleImage(Image img,int width,int height){
    	return toBufferedImage(img.getScaledInstance(width,height,Image.SCALE_SMOOTH));
    }
    public static BufferedImage scaleImage(BufferedImage img,double proportion){
    	return toBufferedImage(img.getScaledInstance((int)(img.getWidth()*proportion),(int)(img.getHeight()*proportion),Image.SCALE_SMOOTH));
    }
    /*public static boolean savetoDB(Croma x){
    	FileWriter fw=null; BufferedWriter bw=null; File file = null;
    	try {
    		file = new File(savingDBFN);
    		file.setWritable(true,false);
			fw = new FileWriter(file, true);
			bw = new BufferedWriter(fw);
				bw.write(x.toString()+"\n");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (file != null){
					file.setReadOnly();
					file = null;
				}
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return true;
	}*/
	//Guardar otras 7??
	public static boolean saveImage(String directoryName, BufferedImage image, String fileName){
		try {
			Files.createDirectories((new File(directoryName)).toPath());
    		File outputfile = new File(directoryName+"/"+fileName);
    		ImageIO.write(image, "jpg", outputfile);
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	public static void main(String[] args) {
		try{
			BufferedImage i = ImageIO.read(Retrive(1));
        }catch(IOException e){
            e.printStackTrace();
        }
	}
}