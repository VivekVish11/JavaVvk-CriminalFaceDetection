/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymajorproject;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author LENOVO
 */
public class SelectCriminalData {
    
    static Connection conn;
    static ResultSet rs;
    static PreparedStatement pst;
    static File fpic=null;
    
    public SelectCriminalData(){
        conn=JavaConnect.ConnectDb();
    }
    
    
    public static ImageIcon selectImg(int width, int height){
        ImageIcon img1=null;
        JFileChooser chooser =new JFileChooser();
        chooser.showOpenDialog(null);
        File f =chooser.getSelectedFile();       
        if(f!=null){
            ImageIcon myimage=new ImageIcon(f.toString());
            Image img= myimage.getImage();
            Image newimage=img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon image=new ImageIcon(newimage);           
            img1 = image;
        }
        fpic=f;
        return img1;
    }
    
    public  String Criminalid(){
        Random rd = new Random();
        int nextInt = rd.nextInt(100000+1);
        String cid=String.valueOf(nextInt);
        return cid;
    }
    
    public  void uploadDetails(String cid,String name,String aname,String dob,String age,
            String gender,String address,String city,String state, String arresteddate,String crime, File fimg1){
        try{            
//            String cid = Criminalid();
            String sql="insert into CriminalData(Cid, Name, Aname, DOB, Age, Gender,"
                    + " Address, City, State, ArrestedDate, Crime,Photo) values(?,?,?,?,?,?,?,?,?,?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setString(1, cid);
            pst.setString(2, name);
            pst.setString(3, aname);
            pst.setString(4, dob);
            pst.setString(5, age);
            pst.setString(6, gender);
            pst.setString(7, address);
            pst.setString(8, city);
            pst.setString(9, state);
            pst.setString(10, arresteddate);
            pst.setString(11, crime);
            
            FileInputStream fis=new FileInputStream(fimg1);
            pst.setBlob(12, fis);
            
            pst.execute();
            pst.close();
            JOptionPane.showMessageDialog(null,"Uploaded Suucessfully");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        finally {
            try {
                pst.close();
            }
            catch (Exception e) {
            }
        }
    }
    
}
