package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.*;



import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by Lampel_Nuc on 15/01/2017.
 */
public class MyOpenCVclass {
    //constractor
    public MyOpenCVclass()
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public Mat  rgb2hsv(Mat rgbMat)
    {
        Imgproc.cvtColor(rgbMat, rgbMat,Imgproc.COLOR_BGR2HSV);
        return  rgbMat;
    }

    public Mat detectLinesInImage( Mat img,int lowThreshold)
    {
        // generate gray scale and blur
        Mat gray = new Mat();
        Imgproc.cvtColor(img, gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.blur(gray, gray, new Size(3, 3));
        // detect the edges
        Mat edges = new Mat();
        int ratio = 3;
        Imgproc.Canny(gray, edges, lowThreshold, lowThreshold * ratio);

        Mat lines = new Mat();
        Imgproc.HoughLinesP(edges, lines, 1, Math.PI / 180, 50, 50, 10);

        for(int i = 0; i < lines.cols(); i++) {
            double[] val = lines.get(0, i);
            Imgproc.line(img, new Point(val[0], val[1]), new Point(val[2], val[3]), new Scalar(0, 0, 255), 2);
        }
        return img;
    }

    public Mat rgb2gray(Mat rgbMat)
    {
        Imgproc.cvtColor(rgbMat, rgbMat, Imgproc.COLOR_BGR2GRAY);
        return rgbMat;
    }

    public Mat bufferedImageToMat(BufferedImage bi)
    {
        Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
        return mat;
    }

    public Image matToImage(Mat inputMat)
    {
        //conver MAT to display on javafx
        MatOfByte byteMat = new MatOfByte();
        Imgcodecs.imencode(".png", inputMat, byteMat);
        Image outImage= new Image(new ByteArrayInputStream(byteMat.toArray()));
        return outImage;
    }


    public Mat imread(String filePath)
    {
        return Imgcodecs.imread(filePath);
    }

    public void imwrite(Mat imgToSave,String filepath)
    {
        Imgcodecs.imwrite(filepath,imgToSave);
    }

    public Mat hsvColorFilter(Mat inputImage,int fromHueValue,int toHueValue,int fromSaturationValue,int toSaturationValue,int fromVvalue,int toVvalue)
    {
        Mat filteredImage = new Mat(inputImage.rows(),inputImage.cols(), CvType.CV_8UC1);
        Core.inRange(inputImage,new Scalar(fromHueValue,fromSaturationValue,fromVvalue),new Scalar(toHueValue,toSaturationValue,toVvalue),filteredImage);
        return filteredImage;
    }

    public Mat createMat(int rows,int cols)
    {
        Mat destination = new Mat(rows,cols, CvType.CV_8UC1);
        return  destination;
    }

    public Mat img1ANDimg2(Mat img1,Mat img2)
    {
        Mat destination = new Mat(img1.rows(),img1.cols(), CvType.CV_8UC1);
        Core.bitwise_and(img1,img1,destination,img2);
        return destination;
    }

    public Mat img1ORimg2(Mat img1,Mat img2)
    {
        Mat destination = new Mat(img1.rows(),img1.cols(), CvType.CV_8UC1);

        Core.bitwise_or(img1,img1,destination,img2);
        return destination;
    }

    public Mat img1XORimg2(Mat img1,Mat img2)
    {
        Mat destination = new Mat(img1.rows(),img1.cols(), CvType.CV_8UC1);

        Core.bitwise_xor(img1,img1,destination,img2);
        return destination;
    }

    public Boolean objectDetectionAndButtonPress(Mat image,Mat template ) {
        boolean foundMatch=false;
        int result_cols = image.cols() - template.cols() + 1;
        int result_rows = image.rows() - template.rows() + 1;
        Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);

        Imgproc.matchTemplate(image, template, result, 0);
//        Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());

        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
        Point matchLoc;
        matchLoc = mmr.minLoc;
        if (mmr.minVal < 5000) {
            foundMatch=true;
            try {
                Robot rt = new Robot();
                System.out.println("x=" + matchLoc.x + "y=" + matchLoc.y);
                rt.mouseMove((int) matchLoc.x, (int) matchLoc.y);

                rt.mousePress(InputEvent.BUTTON1_MASK);
                Thread.sleep(1000);
                rt.mouseRelease(InputEvent.BUTTON1_MASK);
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return foundMatch;
    }

    public void scaleDownImageAndMaintainAscpectRatio(){

    }

    public Mat cropImg(Mat imagToCrop,int x,int y,int width,int hight)
    {
        Rect cutImage=new Rect(x,y,width,hight);
        Mat croppedImg=new Mat(imagToCrop,cutImage);
return  croppedImg;
    }

    public Image printScreen(String saveToPath)
    {
        Image myScreenCapture=null;
        //take a screenshote of your screen and convert it from buffered imag to img
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        try {
            Robot rt = new Robot();
            BufferedImage img = rt.createScreenCapture(new Rectangle((int) screen
                    .getWidth(), (int) screen.getHeight()));

            try {
                ImageIO.write(img,"bmp",new File(saveToPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            myScreenCapture= SwingFXUtils.toFXImage(img,null);

        } catch (AWTException e) {
            e.printStackTrace();
        }
return  myScreenCapture;
    }
}
