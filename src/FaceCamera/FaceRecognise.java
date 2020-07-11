/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 */
package FaceCamera;
 * and open the template in the editor.

import com.googlecode.javacpp.BytePointer;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_face;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.bytedeco.javacpp.opencv_face.LBPHFaceRecognizer;
import static org.bytedeco.javacpp.opencv_face.createLBPHFaceRecognizer;
import static org.bytedeco.javacpp.opencv_imgproc.COLOR_BGRA2GRAY;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;
import org.opencv.core.Rect;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

//import org.bytedeco.javacpp.opencv_videoio.VideoCapture;
import static org.opencv.imgproc.Imgproc.cvtColor;


public class FaceRecognise {

    private FaceRecognise.DaemonThread myThread = null;

    Connection conn;
    PreparedStatement pst;
    ResultSet rs
    JavaConnect connect = new JavaConnect();       

    //javaCV
    VideoCapture webSource = null;
    Mat cameraImage = new Mat();
    CascadeClassifier cascade = new CascadeClassifier("harcascade_frontalface_alt.xml");
    FaceRecognizer recognizer = createLBPHFaceRecognizer();
    BytePointer mem = new BytePointer();
    RectVector detectedFacces = new RectVector();

    //Vars
    String root, firstname, lastname, officeperson, dob;
    int idPerson;

    public FaceRecognise() {
 
        recognizer.read((opencv_core.FileNode) "D:\\project\\New folder\\Samples\\ClassifierLBPH.yml");
        recognizer.setThreshold(80);

        startCamera();
    }

    class DaemonThread implements Runnable {

        protected volatile boolean runnable = false;

        @Override
        public void run() {
            synchronized (this) {
                while (runnable) {
                    try {
                        if (webSource.grab()) {
                            webSource.retrieve(cameraImage);
                            Graphics g = (Graphics) label_photo.getGraphics();
                            Mat imageColor = new Mat();
                            imageColor = cameraImage;

                            Mat imageGray = new Mat();
                            cvtColor(imageColor, imageGray, COLOR_BGRA2GRAY);

                            RectVector detectedFaces = new RectVector();
                            cascade.detectMultiScale(imageColor, detectedFaces, 1, 1, 1, 0, new Size(150, 150), new Size(500, 500));

                            for (int i = 0; i < detectedFaces.size(); i++) {
                                Rect dadosFace = detectedFaces.get(0);
                                Imgproc.rectangle(imageColor, dadosFace, new Scalar(255, 255, 255, 5));
                                Mat face = new Mat(imageGray, dadosFace);
                                Imgproc.resize(face, face, new Size(160, 160));

                                IntPointer rotulo = new IntPointer();
                                DoublePointer cofidence = new DoublePointer();
                                recognizer.predict(face, rotulo, cofidence);
                                int predection = rotulo.get(0);
                                String name = null;

                                if (predection == -1) {
//                                    labelname.setText("AAAAA");
//                                    labeloffice.setText("");
                                    idPerson = 0;
                                } else {
                                    System.out.println("");
                                    idPerson = Integer.parseInt(predection);
                                    rec();
                                }
                            }

                            imencode(".bmp", cameraImage, mem);
                            Image im = ImageIO.read(new ByteArrayInputStream(mem.getStringBytes()));
                            BufferedImage buff = (BufferedImage) im;

                            if (g.drawImage(buff, 0, 0, getWidth(), getHeight() - 90, 0, 0, buff.getWidth(), buff.getHeight(), null)) {
                                if (runnable == false) {
                                    System.err.println("Save a photo");
                                    this.wait();
                                }
                            }
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, " Error in camera open");
                    }
                }
            }
        }
    }

    private void rec() {
        //recognizer face with database 
        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                connect.connects();
                try {
                    String sql = "Select * from person where id = " + String.valueOf(idPerson);
                    connect.executSQL(sql);
                    while (connect.rs.next()) {
                        labelname.setText(connect.rs.getString("fname") + " " + (connect.rs.getString("lname")));
                        labeloffice.setText(connect.rs.getString("office"));

                        System.out.println("Person : " + connect.rs.getString("id"));

                        Array ident = connect.rs.getArray(2);
                        String[] person = (String[]) ident.getArray();

                        for (int i = 0; i < person.length; i++) {
                            System.out.println(person[i]);
                        }
                    }
                } catch (Exception e) {
                }
                connect.disConnect();
                return null;
            }
        };
        worker.execute();
    }

    public void stopCamera() {
        myThread.runnable = false;
        webSource.release();
        dispose();
    }

    public void startCamera() {
        webSource = new VideoCapture(0);
        myThread = new FaceRecognizer.DaemonThread();
        Thread t = new Thread(myThread);
        t.setDaemon(true);
        myThread.runnable = true;
        t.start();

    }

}
