/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymajorproject;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.MemoryImageSource;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author LENOVO
 */
public class ConstructPage extends javax.swing.JFrame implements ActionListener{
    
    Connection con;
    Statement st;
    
    String stor;
    String selcrimid="";

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
    
    int nextheight=0;
    
    int hair,forehead,eyes,nose,lips,chin;
    String s="";

    /**
     * Creates new form ConstructPage
     */
    public ConstructPage() {
        super("FaceIdentification v1.0 - ConstructPage");
        initComponents();
        
        
        cHair.addActionListener(this);
        cForehead.addActionListener(this);
        cEyes.addActionListener(this);
        cNose.addActionListener(this);
        cLips.addActionListener(this);
        cChin.addActionListener(this);
        
        Container c=getContentPane();
		c.setLayout(null);
	
		c.add(cHair);
		c.add(cForehead);
		c.add(cEyes);
		c.add(cNose);
		c.add(cLips);
		c.add(cChin);

		c.add(bSave);
		c.add(bCancel);
		c.add(bClear);

//		c.add(lHair);
//		c.add(lForehead);
//		c.add(lEyes);
//		c.add(lNose);
//		c.add(lLips);
//		c.add(lChin);
//		
//		c.add(lHair1);
//		c.add(lForehead1);
//		c.add(lEyes1);
//		c.add(lNose1);
//		c.add(lLips1);
//		c.add(lChin1);
        
        setSize(950,700);
        setResizable(false);
        java.awt.Dimension screen=java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Rectangle frame=getBounds();
        this.setLocation((screen.width-frame.width)/2,(screen.height-frame.height)/2);
        
        try
		{ 
//			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                        Class.forName("com.mysql.jdbc.Driver");
                        
//			Database d1=new Database();
//			con=DriverManager.getConnection("jdbc:odbc:face","system","tiger");
                        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/face?zeroDateTimeBehavior=convertToNull","root","");
                        
                        
			st=con.createStatement();
			ResultSet rs=st.executeQuery("select * from face_height");
			while(rs.next())
		{
			ArrayList a1=new ArrayList();
			selcrimid=rs.getString(1);
			a1.add(new Integer(Integer.parseInt(selcrimid)));
			if(selcrimid.equalsIgnoreCase("Select Hair"))
				JOptionPane.showMessageDialog(this,"Select the hair of the Criminal to construct the Photo", "constructing Photograph", JOptionPane.ERROR_MESSAGE);
	        FileInputStream hairfin=new FileInputStream("Clips\\hair\\hair"+selcrimid+".gif");
			DataInputStream hairdin=new DataInputStream(hairfin);
			int len=hairfin.available();
			int hairpixels[]=new int[len];
			int i=0;
			while(hairfin.available()>0)
			{
				hairpixels[i]=hairdin.readInt();
				i++;
			}
			hairfin.close();
			hairdin.close();
			int hairht=Integer.parseInt(rs.getString(2));
			a1.add(new Integer(hairht));
			hairAL.add(a1);
			Image hairclipimg=createImage(new MemoryImageSource(200,hairht,hairpixels,0,200));
			ImageIcon hairicn=new ImageIcon(hairclipimg);
			cHair.addItem(hairicn.toString());

           
			ArrayList a2=new ArrayList();
	        FileInputStream fhfin=new FileInputStream("Clips\\forehead\\forehead"+selcrimid+".gif");
			DataInputStream fhdin=new DataInputStream(fhfin);
			len=fhfin.available();
			int fhpixels[]=new int[len];
			i=0;
			while(fhfin.available()>0)
			{
				fhpixels[i]=fhdin.readInt();
				i++;
			}
			fhfin.close();
			fhdin.close();
			int fhht=Integer.parseInt(rs.getString(3));
			a2.add(new Integer(Integer.parseInt(selcrimid)));
			a2.add(new Integer(fhht));
			fhAL.add(a2);
			Image fhclipimg=createImage(new MemoryImageSource(200,fhht,fhpixels,0,200));
			ImageIcon fhicn=new ImageIcon(fhclipimg);
			cForehead.addItem(fhicn.toString());

            ArrayList a3=new ArrayList();
	        FileInputStream eyesfin=new FileInputStream("Clips\\eyes\\eyes"+selcrimid+".gif");
			DataInputStream eyesdin=new DataInputStream(eyesfin);
			len=eyesfin.available();
			int eyespixels[]=new int[len];
			i=0;
			while(eyesfin.available()>0)
			{
				eyespixels[i]=eyesdin.readInt();
				i++;
			}
			eyesfin.close();
			eyesdin.close();
			int eyesht=Integer.parseInt(rs.getString(4));
            a3.add(new Integer(Integer.parseInt(selcrimid)));
			a3.add(new Integer(eyesht));
			eyesAL.add(a3);
			Image eyesclipimg=createImage(new MemoryImageSource(200,eyesht,eyespixels,0,200));
			ImageIcon eyesicn=new ImageIcon(eyesclipimg);
			cEyes.addItem(eyesicn.toString());


			ArrayList a4=new ArrayList();
			FileInputStream nosefin=new FileInputStream("Clips\\nose\\nose"+selcrimid+".gif");
			DataInputStream nosedin=new DataInputStream(nosefin);
			len=nosefin.available();
			int nosepixels[]=new int[len];
			i=0;
			while(nosefin.available()>0)
			{
				nosepixels[i]=nosedin.readInt();
				i++;
			}
			nosefin.close();
			nosedin.close();
			int noseht=Integer.parseInt(rs.getString(5));
			a4.add(new Integer(Integer.parseInt(selcrimid)));
			a4.add(new Integer(noseht));
			noseAL.add(a4);
			Image noseclipimg=createImage(new MemoryImageSource(200,noseht,nosepixels,0,200));
			ImageIcon noseicn=new ImageIcon(noseclipimg);
			cNose.addItem(noseicn.toString());

		
            ArrayList a5=new ArrayList();
	        FileInputStream lipsfin=new FileInputStream("Clips\\lips\\lips"+selcrimid+".gif");
			DataInputStream lipsdin=new DataInputStream(lipsfin);
			len=lipsfin.available();
			int lipspixels[]=new int[len];
			i=0;
			while(lipsfin.available()>0)
			{
				lipspixels[i]=lipsdin.readInt();
				i++;
			}
			lipsfin.close();
			lipsdin.close();
			int lipsht=Integer.parseInt(rs.getString(6));
			 a5.add(new Integer(Integer.parseInt(selcrimid)));
			a5.add(new Integer(lipsht));
			lipsAL.add(a5);
			Image lipsclipimg=createImage(new MemoryImageSource(200,lipsht,lipspixels,0,200));
			ImageIcon lipsicn=new ImageIcon(lipsclipimg);
			cLips.addItem(lipsicn.toString());
		
		     ArrayList a6=new ArrayList();
	        FileInputStream chinfin=new FileInputStream("Clips\\chin\\chin"+selcrimid+".gif");
			DataInputStream chindin=new DataInputStream(chinfin);
			len=chinfin.available();
			int chinpixels[]=new int[len];
			i=0;
			while(chinfin.available()>0)
			{
				chinpixels[i]=chindin.readInt();
				i++;
			}
			chinfin.close();
			chindin.close();
			int chinht=Integer.parseInt(rs.getString(7));
			 a6.add(new Integer(Integer.parseInt(selcrimid)));
			a6.add(new Integer(chinht));
			chinAL.add(a6);
			Image chinclipimg=createImage(new MemoryImageSource(200,chinht,chinpixels,0,200));
			ImageIcon chinicn=new ImageIcon(chinclipimg);
			cChin.addItem(chinicn.toString());
			
			}					
	}catch(Exception e)
	{ 
		JOptionPane.showMessageDialog(this,e.getMessage(), "Construct Face", JOptionPane.ERROR_MESSAGE);
	}
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cHair = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cForehead = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cEyes = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cNose = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cLips = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cChin = new javax.swing.JComboBox<>();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jLabel7 = new javax.swing.JLabel();
        bSave = new javax.swing.JButton();
        bCancel = new javax.swing.JButton();
        bClear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Hair");

        jLabel2.setText("Forehead");

        jLabel3.setText("Eyes");

        jLabel4.setText("Nose");

        jLabel5.setText("Lips");

        jLabel6.setText("Chin");

        jLabel7.setText("jLabel7");

        jDesktopPane1.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                .addContainerGap())
        );

        bSave.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        bSave.setText("Save");

        bCancel.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        bCancel.setText("Cancel");

        bClear.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        bClear.setText("Clear");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bSave)
                        .addGap(98, 98, 98)
                        .addComponent(bCancel)
                        .addGap(98, 98, 98)
                        .addComponent(bClear)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(cHair, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cForehead, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cEyes, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cNose, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cLips, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(87, 87, 87)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(110, 110, 110)
                                .addComponent(jLabel3)
                                .addGap(139, 139, 139)
                                .addComponent(jLabel4)
                                .addGap(140, 140, 140)
                                .addComponent(jLabel5)
                                .addGap(68, 68, 68)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(cChin, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(jLabel6))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(389, 389, 389)
                        .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cLips, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                            .addComponent(cNose, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cEyes)
                            .addComponent(cChin))
                        .addGap(57, 57, 57)
                        .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(120, 120, 120)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bCancel)
                            .addComponent(bClear)
                            .addComponent(bSave)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cForehead, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                        .addComponent(cHair, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ConstructPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConstructPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConstructPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConstructPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConstructPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancel;
    private javax.swing.JButton bClear;
    private javax.swing.JButton bSave;
    private javax.swing.JComboBox<String> cChin;
    private javax.swing.JComboBox<String> cEyes;
    private javax.swing.JComboBox<String> cForehead;
    private javax.swing.JComboBox<String> cHair;
    private javax.swing.JComboBox<String> cLips;
    private javax.swing.JComboBox<String> cNose;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
