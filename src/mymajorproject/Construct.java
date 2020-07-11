/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymajorproject;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 *
 * @author LENOVO
 */
public class Construct {
    
    int i,hr,fr,ey,no,lip,ch;
    int ht1,ht2,ht3,ht4,ht5,ht6;
    int ha1,ha2,ha3,ha4,ha5,ha6;
    int cids[]=new int[6];
    
    int finalhairht,finalfhht,finaleyesht, finalnoseht, finallipsht, finalchinht;
	ArrayList hairAL=new ArrayList();
	ArrayList fhAL=new ArrayList();
	ArrayList eyesAL=new ArrayList();
	ArrayList noseAL=new ArrayList();
	ArrayList lipsAL=new ArrayList();
	ArrayList chinAL=new ArrayList();
        
        Connection con;
	Statement st;
	
	int nextheight=0;
	
//	JComboBox cHair,cForehead,cEyes,cNose,cLips,cChin;
//	JButton bSave,bCancel,bClear;
//	
//	JLabel lHair,lForehead,lEyes,lNose,lLips,lChin;
//	JLabel lHair1,lForehead1,lEyes1,lNose1,lLips1,lChin1;
//	
	int hair,forehead,eyes,nose,lips,chin;
	String s="";
        
        Construct(){
//            
//            cHair.addActionListener(this);
//		cForehead.addActionListener(this);
//		cEyes.addActionListener(this);
//		cNose.addActionListener(this);
//		cLips.addActionListener(this);
//		cChin.addActionListener(this);
            
        }
    
}
