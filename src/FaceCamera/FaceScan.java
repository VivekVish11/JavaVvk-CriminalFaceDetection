/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FaceCamera;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.IntBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.opencv.face.FaceRecognizer;
//////

//import org.opencv.core.Mat;
//import org.opencv.objdetect.CascadeClassifier;
//import org.opencv.videoio.VideoCapture;



public class FaceScan {

    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    private FaceScan.DaemonThread myThread = null;

    //javacv
    VideoCapture webSource = null;
    Mat cameraImage = new Mat();
    CascadeClassifier cascade = new CascadeClassifier("harcascade_frontalface_alt.xml");
    FaceRecognizer recognizer = LBPHFaceRecognizer.create();
    BytePointer mem = new BytePointer();
    RectVector detectedFacces = new RectVector();

    String root, firstname, lastname, officeperson, dob;
    int numSamples = 25, sample = 1, idPerson;

    public FaceScan(int id, String fname, String lname, String dob, String office) {
        this.idPerson = id;
        this.firstname = fname;
        this.lastname = lname;
        this.officeperson = office;
        this.dob = dob;

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
                                opencv_core.Rect dadosFace = detectedFaces.get(0);
                                Imgproc.rectangle(imageColor, dadosFace, new Scalar(255, 255, 255, 5));
                                Mat face = new Mat(imageGray, dadosFace);
                                opencv_imgproc.resize(face, face, new Size(160, 160));

                                if (saveButton.getModel().isPressed()) {

                                    if (sample <= numSamples) {
                                        String cropped = "D:\\project\\New folder\\Samples\\person." + idPerson + "." + sample + ".jpg";
                                        Highgui.imwrite(cropped, face);

                                        //
                                        counterLabel.setText(String.valueOf(sample));
                                        sample++;
                                    }
                                    if (sample > 25) {
                                        generate();
                                        insertDatabase();
                                        System.out.println("File Generated");
                                        stopCamera();
                                    }
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
                    } catch (IOException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, " Error in camera open");
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error in open Camera (intrruped)" + ex);
                    }
                }
            }
        }
    }

    private void generate() {
        File directory = new File("D:\\project\\New folder\\Samples\\");
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".jpg") || name.endsWith(".png");
            }
        };
        File[] files = directory.listFiles(filter);
        MatVector photos = new MatVector(files.length);
        Mat labels = new Mat(files.length, 1, CV_32SC1);
        IntBuffer labelBuffer = labels.createBuffer();

        int counter = 0;
        for (File image : files) {
            Mat photo = imread(image.getAbsolutePath(), Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
            int idPer = Integer.parseInt(image.getName().split("\\.")[1]);
            opencv_imgproc.resize(photo, photo, new Size(160, 160));

            photos.put(counter, photo);
            labelBuffer.put(counter, idPer);
            counter++;
        }
        FaceRecognizer lbph = LBPHFaceRecognizer();
        lbph.train(photos, labels);
        lbph.save("D:\\project\\Samples\\ClassifierLBPH.yml");

    }

    public void insertDatabase() {
        ControlPerson cp = new ControlPerson();
        ModelPerson mp = new ModelPerson();

        mp.setId(idPerson);
        mp.setFname(firstname);
        mp.setLname(lastname);
//            mp.setDob(((JTextField) txt_dob.getDateEditor().getUiComponent()).getText());
        mp.setDob(dob);
        mp.setOffice(officeperson);
        cp.insert(mp);

    }

    public void stopCamera() {
        myThread.runnable = false;
        webSource.release();
        dispose();
    }

    public void startCamera() {
        webSource = new VideoCapture(0);
        myThread = new Capture.DaemonThread();
        Thread t = new Thread(myThread);
        t.setDaemon(true);
        myThread.runnable = true;
        t.start();

    }

}
