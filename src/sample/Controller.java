package sample;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.opencv.core.*;

import org.opencv.imgcodecs.*;
import org.opencv.imgproc.Moments;
import org.opencv.videoio.*;



import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Controller {
    @FXML
    Button idButtun, reduceTropy, startBotButton;
    @FXML
    ImageView myIMG, myIMG2, myIMG3, myIMG4, myIMG5, myIMG6;
    @FXML
    TextArea statusTextArea;
    @FXML
    TextField cannyEdgeThreshHold;
    @FXML
    Slider threshHoldCannySlider;
    @FXML
    Slider fromHueSlider;
    @FXML
    Slider toHueSlider;
    @FXML
    Slider fromSatSlider;
    @FXML
    Slider toSatSlider;
    @FXML
    Slider fromVSlider;
    @FXML
    Slider toVSlider;
    @FXML
    TextField fromHue, toHue, fromSat, toSat, fromV, toV;
    @FXML
    RadioButton redRadioButton, yellowRadioButton, pinkRadioButton, purpleRadioButton;



    private MyOpenCVclass myOpenCVobject;

    public void initialize() {


        myOpenCVobject = new MyOpenCVclass();
        System.out.println("on start");
        bindAllSliders();

    }

    public void redRadioButtonPressed() {
        manageRadioButton("red");
    }

    public void yellowRadioButtonPressed() {
        manageRadioButton("yellow");
    }

    public void pinkRadioButtonPressed() {
        manageRadioButton("pink");
    }

    public void blueRadioButtonpressed(){
        manageRadioButton("blue");
    }

    public void manageRadioButton(String colorChosen) {


        switch (colorChosen) {
            case "red":
                fromHue.setText("0");
                toHue.setText("29");
                fromSat.setText("0");
                toSat.setText("7");
                fromV.setText("116");//142
                toV.setText("189");//265
                System.out.println("Red pressed");
                break;
            case "yellow":
                fromHue.setText("0");
                toHue.setText("10");
                fromSat.setText("182");
                toSat.setText("242");
                fromV.setText("170");
                toV.setText("351");
                System.out.println("yellow pressed");
                break;
            case "pink":
                fromHue.setText("77");
                toHue.setText("255");
                fromSat.setText("12");
                toSat.setText("121");
                fromV.setText("214");
                toV.setText("262");
                System.out.println("pink pressed");
                break;
            case "purple":
                break;
            case "white":
                fromHue.setText("200");
                toHue.setText("228");
                fromSat.setText("214");
                toSat.setText("230");
                fromV.setText("89");
                toV.setText("227");
                System.out.println("white pressed");
                break;
            case "blue":
                fromHue.setText("107");
                toHue.setText("163");
                fromSat.setText("43");
                toSat.setText("102");
                fromV.setText("0");
                toV.setText("20");
                System.out.println("blue pressed");
                break;
        }
    }

    public void bindAllSliders() {
        threshHoldCannySlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                cannyEdgeThreshHold.textProperty().setValue(String.valueOf((int) threshHoldCannySlider.getValue()));
            }
        });

        fromHueSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                fromHue.textProperty().setValue(String.valueOf((int) fromHueSlider.getValue()));
            }
        });

        toHueSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                toHue.textProperty().setValue(String.valueOf((int) toHueSlider.getValue()));
            }
        });

        fromSatSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                fromSat.textProperty().setValue(String.valueOf((int) fromSatSlider.getValue()));
            }
        });

        toSatSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                toSat.textProperty().setValue(String.valueOf((int) toSatSlider.getValue()));
            }
        });

        fromVSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                fromV.textProperty().setValue(String.valueOf((int) fromVSlider.getValue()));
            }
        });

        toVSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                toV.textProperty().setValue(String.valueOf((int) toVSlider.getValue()));
            }
        });


    }

    public void detectColor_ButtonPressed() {
        Mat original_img = new Mat();

        Thread myT = new Thread(new Runnable() {
            @Override
            public void run() {
                VideoCapture cap = new VideoCapture();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cap.open(0);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // cap.read(original_img);
                if (cap.isOpened()) {
                    while (true) {
                        System.out.println("camera open");
                        cap.read(original_img);
                        //   Highgui.imwrite("D:\\YY.bmp",original_img);
                        Mat original_img2 = original_img.clone();
                        Mat original_img3 = original_img.clone();
                        Mat gray = original_img.clone();
                        Mat thr = original_img.clone();
                        ///detect Color

                        //convert to hsv
                        //     Imgproc.cvtColor(original_img, original_img,Imgproc.COLOR_BGR2HSV);

                        //  Core.inRange(original_img,new Scalar(170,5,5),new Scalar(180,254,254),original_img2);//pink
                        //      Core.inRange(original_img,new Scalar(0,5,5),new Scalar(1,254,254),original_img2);//red
                        //   Core.inRange(original_img,new Scalar(81,1,1),new Scalar(83,254,254),original_img2);//red
                        Imgproc.blur(original_img, original_img, new Size(3, 3));
                        try {
                            Core.inRange(original_img, new Scalar(Integer.parseInt(fromHue.getText()), Integer.parseInt(fromSat.getText()), Integer.parseInt(fromV.getText())), new Scalar(Integer.parseInt(toHue.getText()), Integer.parseInt(toSat.getText()), Integer.parseInt(toV.getText())), original_img2);//pink
                        } catch (NumberFormatException ex) {

                            Core.inRange(original_img, new Scalar(1, 1, 1), new Scalar(254, 254, 254), original_img2);//red
                        }
                        Mat afterInRange = original_img2.clone();
//                        Imgproc.dilate(original_img2, original_img2, new Mat());
//                        List<MatOfPoint> points = new ArrayList<>();
//
//                        Imgproc.findContours(original_img2, points, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
//                        //DARW COUNTOR
//                        //    Imgproc.drawContours(original_img2,points,6,new Scalar(255,0,0),2);
//
//                        for (int idx = 0; idx < points.size(); idx++) {
//                            Imgproc.drawContours(original_img3, points, idx, new Scalar(255, 0, 0), 8);
//                        }

                        Double cannyThreshHoldD = 0.0;
                        try {
                            cannyThreshHoldD = Double.parseDouble(cannyEdgeThreshHold.getText());
                        } catch (NumberFormatException ex) {
                            cannyThreshHoldD = 0.0;
                        }


                        Imgproc.Canny(original_img2, original_img2, cannyThreshHoldD, cannyThreshHoldD * 3);
                        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
                        Imgproc.dilate(original_img2, original_img2, element);

                        original_img3 = myOpenCVobject.img1ORimg2(original_img, original_img2);
                        Core.addWeighted(original_img3, 0.5, original_img, 0.5, 0.0, original_img);
                        boolean redLed = false;
                        double blackWhitePixel;
                        int whitePixelFramCounter = 0;


                        Core.MinMaxLocResult mmr = Core.minMaxLoc(original_img2);
                        Point matchLoc;
                        matchLoc = mmr.minLoc;

                        for (int rows = 0; rows < original_img.rows(); rows++) {
                            for (int cols = 0; cols < original_img.cols(); cols++) {
                                blackWhitePixel = original_img2.get(rows, cols)[0];
                                if (blackWhitePixel != 0.0) {
                                    //start counting if you count 50 then foundMatch!
                                    whitePixelFramCounter += 1;
                                    if (whitePixelFramCounter == 5000) {
                                        System.out.println(blackWhitePixel);
                                        System.out.println("row=" + rows + " cols=" + cols);
                                        //Imgproc.drawMarker(original_img, new Point(cols + 30, rows + 70), new Scalar(255, 0, 0), 9, 9, 20, 3);

                                        //find center of mass
                                        Imgproc.threshold( original_img2, thr, 100,255,Imgproc.THRESH_BINARY);
                                        Moments  m = Imgproc.moments(thr,true);
                                        Point p=new Point(m.m10/m.m00, m.m01/m.m00);
                                        Imgproc.putText(original_img,"RED",p,4,3.0,new Scalar(0,0,255),3);
                                        //end find center of mass

                                        redLed = true;

                                    } else {
                                        if (redLed) {

                                            redLed = false;
                                        }

                                    }

                                }

                            }
                        }


                        //Core.drawMarker(original_img3,new Point(200,200),new Scalar(255,0,0),9,90,20,3);//draw +

                        // Mat afterCannyAnd_AndConverToHSV=myOpenCVobject.rgb2hsv(original_img2);
                        //======================================================///////////play to screen
                        Image myimage = myOpenCVobject.matToImage(original_img);
                        myIMG.setPreserveRatio(true);
                        myIMG.setImage(myimage);

                        Image myimage2 = myOpenCVobject.matToImage(afterInRange);
                        myIMG2.setPreserveRatio(true);
                        myIMG2.setImage(myimage2);

                        Image myimage3 = myOpenCVobject.matToImage(original_img3);
                        myIMG3.setPreserveRatio(true);
                        myIMG3.setImage(myimage3);

//                        MatOfPoint2f points=new MatOfPoint2f();
//                        Moments moments=Imgproc.moments(points);
//                        Point center=new Point();
//
//                        center.x=moments.get_m10()/moments.get_m00();
//                        center.y=moments.get_m01()/moments.get_m00();

                        //try to find countor from img2





                        System.out.println("stop her");

                        //    Image myimage4 = myOpenCVobject.matToImage(original_img4);
                        //     myIMG4.setPreserveRatio(true);
                        //    myIMG4.setImage(myimage4);
                        //   cap.release();

                    }
                } else {
                    System.out.println("not opened");
                }

            }
        });

        myT.start();
    }

    public void startCamera_ButtonPressed() {
        Mat original_img = myOpenCVobject.imread("D:\\jss4\\openCV_PrintS\\croped.png");
        // Mat framMat = new Mat();
        Thread myT = new Thread(new Runnable() {
            @Override
            public void run() {
                VideoCapture cap = new VideoCapture();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cap.open(0);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // cap.read(original_img);
                if (cap.isOpened()) {
                    while (true) {
                        System.out.println("camera open");


                        cap.read(original_img);

                        //   Highgui.imwrite("D:\\YY.bmp",original_img);
                        Mat original_img2 = original_img.clone();

                        Imgproc.cvtColor(original_img2, original_img2, Imgproc.COLOR_BGR2GRAY);
                        Imgproc.blur(original_img2, original_img2, new Size(3, 3));
                        Double cannyThreshHoldD = 0.0;
                        try {
                            cannyThreshHoldD = Double.parseDouble(cannyEdgeThreshHold.getText());
                        } catch (NumberFormatException ex) {
                            cannyThreshHoldD = 0.0;
                        }

                        Imgproc.Canny(original_img2, original_img2, cannyThreshHoldD, cannyThreshHoldD * 3);
                        // Core.bitwise_or(original_img,original_img2,original_img2);
                        //
                        //Imgproc.findContours(original_img2,new );
                        Image myimage = myOpenCVobject.matToImage(original_img2);

                        myIMG.setPreserveRatio(true);
                        myIMG.setImage(myimage);
                        //   cap.release();

                    }
                } else {
                    System.out.println("not opened");
                }


            }
        });

        myT.start();
    }

    public void buttunPressed() throws IOException {
//        BufferedImage cropimg= ImageIO.read(new File("D:\\jss4\\openCV_PrintS\\croped.png"));
        Mat original_img22 = myOpenCVobject.imread("D:\\jss4\\openCV_PrintS\\croped.png");
        original_img22 = myOpenCVobject.rgb2gray(original_img22);

//        myOpenCVobject.imwrite(original_img22,"D:\\jss4\\openCV_PrintS\\croped_gray.png");

        BufferedImage cropimg = ImageIO.read(new File("D:\\openCv\\images\\digits.png"));

       // ITesseract instance = new Tesseract();
      //  instance.setDatapath("D:\\jss4\\openCV_PrintS\\Tess4J-3.2.1-src\\Tess4J\\tessdata");
//        instance.setLanguage("eng");
      //  String result = instance.doOCR(cropimg);
       // System.out.println(result);
        //load image
        Mat original_img = myOpenCVobject.imread("C:\\Users\\Lampel_Nuc\\IdeaProjects\\tryOpenCV\\src\\sample\\clash_royal_open_screen_orig.png");
        //clone image
        Mat myMat = original_img.clone();
        //create empty MAT
        Mat destination = myOpenCVobject.createMat(original_img.rows(), original_img.cols());

        //convert img to hsv
        //myMat=myOpenCVobject.rgb2hsv(original_img);
        //save image
        //myOpenCVobject.imwrite(original_img,"C:\\Users\\Lampel_Nuc\\IdeaProjects\\tryOpenCV\\src\\sample\\testingImg.jpg");

        //create a MASK (binary image 0 or 1 only BW)
        //filter imgae by color output is binary
        Mat mask = myOpenCVobject.hsvColorFilter(myMat, 19, 22, 150, 255, 0, 255);
        //perform AND between 2 images
        destination = myOpenCVobject.img1ANDimg2(original_img, mask);

        //loop throue mat array
        int counter = 0;
        int maxValue = 0;
        int maxRow = 0, maxCol = 0;

        for (int i = 0; i < destination.rows(); i++) {

            for (int j = 0; j < destination.cols(); j++) {
                double[] rgbPixel = destination.get(i, j);
                double[] rgbNextPixel = destination.get(i, j + 1);
                if (rgbPixel[0] > 0) {

                    System.out.println("row=" + i + " col=" + j + "value=" + rgbPixel[0]);
                    int previousRow = i;

                    counter++;
                    System.out.println(counter);

                    if (counter > maxValue) {
                        maxValue = counter;
                        maxRow = i;
                        maxCol = j;
                    }

                    if (rgbNextPixel[0] == 0) {
                        counter = 0;
                    }

                }

            }
        }
        System.out.println("maxrow=" + maxRow + " maxCOL=" + maxCol);
        //draw line

        Imgproc.line(destination, new Point(863, 623), new Point(1055, 623), new Scalar(255, 0, 0), 5);
        //draw rectangle
        Imgproc.rectangle(destination, new Point(863, 625), new Point(1055, 690), new Scalar(0, 255, 0), 8);
        //conver MAT to Image (for display on javafx - imageview)
        Image myimage = myOpenCVobject.matToImage(destination);

        myIMG.setPreserveRatio(true);
        myIMG.setImage(myimage);

        // load the image
        Mat original_img2 = myOpenCVobject.imread("C:\\Users\\Lampel_Nuc\\IdeaProjects\\tryOpenCV\\src\\sample\\clash_royal_open_screen_orig.png");
        //detect lines in img
        Mat img = myOpenCVobject.detectLinesInImage(original_img2, 200);

        myIMG.setImage(myOpenCVobject.matToImage(img));

        //take a screenshote of your screen and convert it from buffered imag to img
        Image screenCapture = myOpenCVobject.printScreen("C:\\openCV_PrintS\\PS.bmp");

        System.out.println("pressed");
    }

    public void reduceTropy_ButtonPressed() throws InterruptedException {
        //save printscreen to file path
        startBotButton.setText("Stop Bot");
        statusTextArea.appendText("Starting bot\n");

        Thread myt = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    boolean foundMatch;

                    //statusTextArea.appendText("searching for start Button\n");
                    //myOpenCVobject.printScreen("C:\\openCV_PrintS\\Print_Screen.bmp");
                    Mat img = myOpenCVobject.imread("D:\\openCv\\images\\results.bmp");
                    Mat templ = myOpenCVobject.imread("D:\\openCv\\images\\doublet_BlueStack.png");
                    Mat templ2 = myOpenCVobject.imread("D:\\openCv\\images\\okBlueStack.png");
                    Mat templ3 = myOpenCVobject.imread("D:\\openCv\\images\\osLettersAfterDownAleague.png");
                    Mat templ4 = myOpenCVobject.imread("D:\\openCv\\images\\tropySign.png");

                    foundMatch = myOpenCVobject.objectDetectionAndButtonPress(img, templ4);
                    Mat croppedImg = myOpenCVobject.cropImg(img, 780, 222, 55, 15);//.x+4 .y+4
                    // generate gray scale and blur
                    Mat gray = new Mat();
                    Imgproc.cvtColor(croppedImg, gray, Imgproc.COLOR_BGR2GRAY);
                    Imgproc.blur(gray, gray, new Size(3, 3));
                    // detect the edges
                    Mat edges = new Mat();
                    int ratio = 3;
                    Imgproc.Canny(gray, edges, 100, 100 * ratio);

                    Mat process = myOpenCVobject.detectLinesInImage(croppedImg, 100);
                    myOpenCVobject.imwrite(edges, "D:\\openCv\\images\\processed.png");

                    myOpenCVobject.imwrite(croppedImg, "D:\\openCv\\images\\croped.png");

                    foundMatch = myOpenCVobject.objectDetectionAndButtonPress(img, templ);
                    if (foundMatch) {
                        statusTextArea.appendText("Found Battle Button And pressed it\n");
                        statusTextArea.appendText("Starting war phase lets wait for lose\n");
                    }

                    foundMatch = myOpenCVobject.objectDetectionAndButtonPress(img, templ2);
                    if (foundMatch) {
                        statusTextArea.appendText("Found ok Button And pressed it\n");

                    }
                    foundMatch = myOpenCVobject.objectDetectionAndButtonPress(img, templ3);
                    if (foundMatch) {
                        statusTextArea.appendText("Found close Button And pressed it\n");
                        statusTextArea.appendText("We Went Down A league\n");
                    }

                }
            }
        });
        myt.start();

    }

    public int[] findMaxColorArea(Mat destination) {
        //loop throue mat array
        int counter = 0;
        int maxValue = 0;
        int maxRow = 0, maxCol = 0;

        for (int i = 0; i < destination.rows(); i++) {

            for (int j = 0; j < destination.cols(); j++) {
                double[] rgbPixel = destination.get(i, j);
                double[] rgbNextPixel = destination.get(i, j + 1);
                if (rgbPixel[0] > 0) {

                    System.out.println("row=" + i + " col=" + j + "value=" + rgbPixel[0]);
                    int previousRow = i;


                    counter++;
                    System.out.println(counter);

                    if (counter > maxValue) {
                        maxValue = counter;
                        maxRow = i;
                        maxCol = j;
                    }

                    if (rgbNextPixel[0] == 0) {
                        counter = 0;
                    }

                }

            }
        }
        int[] returnArray = null;
        returnArray[0] = maxValue;
        returnArray[1] = maxRow;
        returnArray[2] = maxCol;

        return returnArray;
    }


}

