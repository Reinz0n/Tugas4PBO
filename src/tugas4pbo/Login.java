
package tugas4pbo;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Login extends JFrame{
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
    
    public Login(){
        setTitle("Login");
        setSize(400, 150);
        
        lUser = new JLabel("Username    :");
        lPassword = new JLabel("Password    :");
        tfUser = new JTextField(20);
        tfPassword = new JTextField(20);
        bLogin = new JButton("Login");
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
        panelTombol.add(bLogin);
        panelTombol.add(bRegister);
        
        bLogin.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                cekLogin();
            }
        });
        
        bRegister.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                    new Register();		
            }
        });
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
    }
    
    public void cekLogin(){
        int i = 0;
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
                boolean found = false;
                for(i = 0; i <= p; i++){
                    found = true;
                    if(user.equals(data[i][1]) && password.equals(data[i][2])){
                        JOptionPane.showMessageDialog(null, "Berhasil Login!", "", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    } else if(user.equals(data[i][1]) && !password.equals(data[i][2])){
                        JOptionPane.showMessageDialog(null, "Password salah!", "", JOptionPane.ERROR_MESSAGE);
                        break;
                    } else{
                        found = false;
                    }
                }
                if(!found){
                    JOptionPane.showMessageDialog(null, "Username tidak ditemukan!", "", JOptionPane.ERROR_MESSAGE);
                }

                statement.close();
                koneksi.close();
            } catch(SQLException ex){
                JOptionPane.showMessageDialog(null, "Data Gagal Disimpan!", "Hasil", JOptionPane.ERROR_MESSAGE);
            } catch(ClassNotFoundException ex){
                JOptionPane.showMessageDialog(null, "Driver Tidak Ditemukan!", "Hasil", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public static void main(String[] args) {
        new Login();
    }
}