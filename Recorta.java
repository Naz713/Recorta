package Recortador;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.lang.Math;
public class Recorta{
	protected static File sourceFile;
	public static void main(String[] args) {
		try{
			sourceFile = new File(Accesser.sourceDN);
		}catch (Exception e) {
			e.printStackTrace();
		}
		run();
	}
	public static void run() {
		System.gc();
		//System.out.println((croma==null)+"");
		File[] list = sourceFile.listFiles();
                /*for (File X : list ) {
                        System.out.println(X.getName());
                }
                System.out.println("temina");*/
		//System.out.println(sourceFile.getName()+"/"+list.length);
		if(list.length<=1){
                        if (list.length==1) {
                                //System.out.println(""+sourceFile.listFiles()[0].getName());
                                File oculta = sourceFile.listFiles()[0];
                                if (oculta.isDirectory()) {
                                        sourceFile = oculta;
                                        //System.out.println("deep");
                                        run();
                                        return;
                                }
                                if (oculta.isHidden()) {
                                        oculta.delete();
                                }else{
                                        execute(oculta);
                                        return;
                                }
                        }
			if ( !sourceFile.getName().equals(Accesser.sourceDN)  ) {
				File temporal = sourceFile;
				sourceFile = sourceFile.getParentFile();
				if (!temporal.delete()) {
					System.out.println("Carpeta no borrada, no vacia.");
				}
				//System.out.println("regreso");
				run();
				return;
			}else{
				//System.out.println("sale");
				System.exit(0);
			}
		}
		File actual = list[0];
                if (actual.isHidden()) {
                        actual.delete();
                        actual = list[1];
                }
		if (actual.isDirectory()) {
			sourceFile = actual;
			//System.out.println("deep");
			run();
			return;
		}
		execute(actual);
	}
	static void execute(File imageFile){
		BufferedImage image = null;
		try{
			image = ImageIO.read(imageFile);
		}catch (Exception e) {
			e.printStackTrace();
		}

                //String name = image.getName();

        long sumX = 0;
        long sumY = 0;
        long sumTot = 0;
        WritableRaster data = image.getRaster();
        //System.out.println(""+data.getNumDataElements());
        for (int i=0; i<image.getWidth() ;i++) {
        	for (int j=0; j<image.getHeight() ;j++) {
        		int[] arr = null;
        		arr = data.getPixel(i,j,arr);
        		double av = (arr[0]+arr[1]+arr[2])/3;
        		if (av<200) {
        			sumTot++;
        			sumX += i;
        			sumY += j;
        			//System.out.println(i+","+j+","+sumX+","+sumY);
        		}
        	}
        }
        int x = (int)(sumX/sumTot);
        int y = (int)(sumY/sumTot);
        //sumX = null; sumY = null; sumTot = null;
        //long d = Math.min(Math.min(x,width-x),Math.min(y,height-y)); //recorte grande
        int tilt = 15;
        int space = 100;
        int humbral = 51;

        int last1 = x;
        for (int i = x;i<image.getWidth()-tilt;i++ ) {
        	int[] arr = null;
        	arr = data.getPixel(i,y,arr);
        	double av = (arr[0]+arr[1]+arr[2])/3;
                int aux = i+tilt;
        	arr = data.getPixel(aux,y,arr);
        	double av2 = (arr[0]+arr[1]+arr[2])/3;
        	//System.out.println(av+","+av2+","+Math.abs(av-av2));
        	if(Math.abs(av-av2)>humbral){
                        arr = data.getPixel(aux,y+space,arr);
                        av2 = (arr[0]+arr[1]+arr[2])/3;
                        if (Math.abs(av-av2)>humbral) {
                                arr = data.getPixel(aux,y-space,arr);
                                av2 = (arr[0]+arr[1]+arr[2])/3;
                                if (Math.abs(av-av2)>humbral) {
                                last1 = aux;
                                }
                        }
        		//System.out.println(last+"");
        	}
        }
        //int d = last-x;
        int last2 = x;
        //System.out.println(""+d);
        for (int i = x;i>tilt;i-- ) {
        	int[] arr = null;
        	arr = data.getPixel(i,y,arr);
        	double av = (arr[0]+arr[1]+arr[2])/3;
        	int aux = i-tilt;
                arr = data.getPixel(aux,y,arr);
                double av2 = (arr[0]+arr[1]+arr[2])/3;
                //System.out.println(av+","+av2+","+Math.abs(av-av2));
                if(Math.abs(av-av2)>humbral){
                        arr = data.getPixel(aux,y+space,arr);
                        av2 = (arr[0]+arr[1]+arr[2])/3;
                        if (Math.abs(av-av2)>humbral) {
                                arr = data.getPixel(aux,y-space,arr);
                                av2 = (arr[0]+arr[1]+arr[2])/3;
                                if (Math.abs(av-av2)>humbral) {
                                last2 = aux;
                                }
                        }
                        //System.out.println(last+"");
                }
        }
        //d = Math.max(d,x-last);
        //System.out.println(""+d);
        int last3 = y;
        for (int i = y;i<image.getHeight()-tilt;i++ ) {
        	int[] arr = null;
        	arr = data.getPixel(x,i,arr);
        	double av = (arr[0]+arr[1]+arr[2])/3;
        	int aux = i+tilt;
                arr = data.getPixel(x,aux,arr);
                double av2 = (arr[0]+arr[1]+arr[2])/3;
                //System.out.println(av+","+av2+","+Math.abs(av-av2));
                if(Math.abs(av-av2)>humbral){
                        arr = data.getPixel(x+space,aux,arr);
                        av2 = (arr[0]+arr[1]+arr[2])/3;
                        if (Math.abs(av-av2)>humbral) {
                                arr = data.getPixel(x-space,aux,arr);
                                av2 = (arr[0]+arr[1]+arr[2])/3;
                                if (Math.abs(av-av2)>humbral) {
                                last3 = aux;
                                }
                        }
                        //System.out.println(last+"");
                }
        }
        //d = Math.max(d,last-y);
        //System.out.println(last+","+d);
        int last4 = y;
        for (int i = y;i>tilt;i-- ) {
        	int[] arr = null;
        	arr = data.getPixel(x,i,arr);
        	double av = (arr[0]+arr[1]+arr[2])/3;
        	int aux = i-tilt;
                arr = data.getPixel(x,aux,arr);
                double av2 = (arr[0]+arr[1]+arr[2])/3;
                //System.out.println(av+","+av2+","+Math.abs(av-av2));
                if(Math.abs(av-av2)>humbral){
                        arr = data.getPixel(x+space,aux,arr);
                        av2 = (arr[0]+arr[1]+arr[2])/3;
                        if (Math.abs(av-av2)>humbral) {
                                arr = data.getPixel(x-space,aux,arr);
                                av2 = (arr[0]+arr[1]+arr[2])/3;
                                if (Math.abs(av-av2)>humbral) {
                                last4 = aux;
                                }
                        }
                        //System.out.println(last+"");
                }
        }
        //d = Math.max(d,y-last);
        //System.out.println(""+d);


        
        int d = Math.max(Math.max(last1-x,x-last2),Math.max(last3-y,y-last4));
        System.out.println(d+","+x+"."+y+"\n"+last1+","+last2+","+last3+","+last4);
        System.out.println(imageFile.getName());
        //int[] arr = null;
        //data.setPixels(0,0,2*d,2*d,data.getPixels(x-d,y-d,2*d,2*d,arr));
        //WritableRaster raster = data.createWritableChild(last2,last4,last1-last2,last3-last4,0,0,null);
        WritableRaster raster = data.createWritableChild(Math.max(0,x-d),Math.max(0,y-d),Math.min(image.getWidth()-x+d,d+d),Math.min(image.getHeight()-y+d,d+d),0,0,null);
        data = null;
        ColorModel cm = image.getColorModel();
        image = new BufferedImage(cm,raster,cm.isAlphaPremultiplied(),null);
        cm=null;
        Accesser.saveImage("Recortados/"+sourceFile.getPath(),image,imageFile.getName());
        Accesser.saveImage("Escalados/"+sourceFile.getPath(),Accesser.scaleImage(image,908,908),imageFile.getName());
        imageFile.delete();
        imageFile = null;
        raster = null;
        /*JFrame frame = new JFrame("Recorador");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new JScrollPane(new JLabel(new ImageIcon(image))),BorderLayout.CENTER);
        frame.setVisible(true);*/
        image = null;
        run();
	}
}