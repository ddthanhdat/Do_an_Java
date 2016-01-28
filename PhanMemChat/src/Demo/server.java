/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Demo;

import java.awt.Container;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
                                                                               
/**
 *
 * @author ddthanhdat
 */
public class server {
    // frame
    JFrame j;
    Container c;
    JTextArea textdis;
    JTextField textgui;
    JButton gui;
    
    //
    Socket s;
    OutputStream os;
    InputStream is;
    inputstream threadnhap;
    
    PrintWriter pw;
    ServerSocket ss;
    private void GUI(){
        // frame
        j = new JFrame("server");
        j.setBounds(10, 10, 500, 500);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // text
        textdis = new JTextArea();
        textdis.setBounds(10, 10, 480, 300);
        textgui = new JTextField();
        textgui.setBounds(10, 320, 300, 30);
        entertextgui entergui = new entertextgui();
        textgui.addKeyListener(entergui);
        // button
        gui = new JButton("Gui");
        gui.setBounds(320, 320, 80, 30);
        guidi gd = new guidi();
        gui.addActionListener(gd);
                
        
        // container
        c= j.getContentPane();
        c.setLayout(null);
        c.add(textdis);
        c.add(textgui);
        c.add(gui);
        
        j.setVisible(true);
    }
    public server(){
        GUI(); // Giao dien
        KhoiTao();
        
    }
    private void KhoiTao(){
        
        try {
            ss = new ServerSocket(1234);
            System.out.println("Da tao server port 1234");
            s = ss.accept();
            
            System.out.println("Co client ket noi");
            os = s.getOutputStream();
            is = s.getInputStream();
            threadnhap = new inputstream();
            threadnhap.start();
        } catch (IOException ex) {
            System.out.println("Loi phan socket: " +ex.getMessage());
        }
    }
    
    // Hàm để gủi dữ liệu lên Stream
    private void nutgui(){
        
        try{
            
            
            pw =new PrintWriter(os);
            String gui=textgui.getText();
            System.out.println("Gui: "+gui);
            textdis.setText(textdis.getText()+"Server(me): "+gui+"\n");
            textgui.setText("");
            pw.println(gui); pw.flush();
        } catch (NullPointerException e){
            System.out.println("Loi nhap xuat, do chua co client ket noi");
            JOptionPane.showMessageDialog(textdis, "Loi");
            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(0);
        }
    }
    
    
    // class key listenr của textgui dùng gõ phím enter để gủi.
    class entertextgui implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                nutgui();
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
    
    
    
    class inputstream extends Thread{

        @Override
        public void run() {
            Scanner sc = new Scanner(is);
            String nhan ="";
            while(true){
                nhan = sc.nextLine();
                textdis.setText(textdis.getText()+"Client: "+nhan+"\n");
                System.out.println("\n---- Nhan: " + nhan);
            }
        }
        
    }
    
    class guidi implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
           nutgui();
        }
        
    }
    public static void main(String[] args) {
        server b = new server();
    }
}
