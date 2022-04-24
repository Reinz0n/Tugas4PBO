
package tugas4pbo;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Register extends JFrame {
    String[][] data = new String[50][3];
    JLabel lUser, lPassword;
    JTextField tfUser, tfPassword;
    JButton bLogin, bRegister;
    JPanel panelForm, panelTombol;
    String DBurl = "jdbc:mysql://localhost/tugasjdbc";
    String DBusername = "root";
    String DBpassword = "";
    Connection koneksi;
    Statement statement;
    ResultSet resultSet;
    
    public Register(){
        setTitle("Register");
        setSize(400, 150);
        
        lUser = new JLabel("Username    :");
        lPassword = new JLabel("Password    :");
        tfUser = new JTextField(20);
        tfPassword = new JTextField(20);
        bRegister = new JButton("Register");
        panelForm = new JPanel(new GridLayout(2, 2));
        panelTombol = new JPanel(new FlowLayout());
        
        setLayout(new BorderLayout());
        add(panelForm, BorderLayout.CENTER);
        panelForm.add(lUser);
        panelForm.add(tfUser);
        panelForm.add(lPassword);
        panelForm.add(tfPassword);
        add(panelTombol, BorderLayout.SOUTH);
        panelTombol.add(bRegister);
        
        bRegister.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                cekRegister();
            }
        });
        
        setVisible(true);
        setLocationRelativeTo(null);
    }
    
    public void cekRegister(){
        boolean found = false;
        String user, password;
        user = tfUser.getText();
        password = tfPassword.getText();
        if(user.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(null, "Username atau Password belum diisi!", "", JOptionPane.ERROR_MESSAGE);
        } else{
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                koneksi = DriverManager.getConnection(DBurl, DBusername, DBpassword);
                statement = koneksi.createStatement();
                String sql = "select * from users";
                resultSet = statement.executeQuery(sql);
                int p = 0;
                while(resultSet.next()){
                    data[p][0] = resultSet.getString("id");
                    data[p][1] = resultSet.getString("username");
                    data[p][2] = resultSet.getString("password");
                    p++;
                }
                for(int i = 0; i <= p; i++){
                    if(user.equals(data[i][1])){
                        found = true;
                        JOptionPane.showMessageDialog(null, "Username sudah dipakai!", "", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                }
                if(found == false){
                    statement.executeUpdate("insert into users values(" + null + ",'" + user + "','" + password + "')");
                    JOptionPane.showMessageDialog(null, "Berhasil mendaftarkan user!", "", JOptionPane.INFORMATION_MESSAGE);
                }

                statement.close();
                koneksi.close();
            } catch (SQLException ex){
                JOptionPane.showMessageDialog(null, "Data Gagal Disimpan!", "Hasil", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex){
                JOptionPane.showMessageDialog(null, "Driver Tidak Ditemukan!", "Hasil", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
