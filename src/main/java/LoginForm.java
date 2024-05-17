import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class LoginForm extends JDialog{
    private JTextField txtUsername;
    private JButton btnOk;
    private JButton btnCancel;
    private JPanel loginPanel;
    private JPasswordField pfPassword;
    public LoginForm(JFrame parent){
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(550,450));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //CConexion objetoConexion=new CConexion();
        //objetoConexion.estableceConexion();


        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user=txtUsername.getText();
                String password=String.valueOf(pfPassword.getPassword());
                usuario=autentificarUsuario(user,password);

                if(usuario!=null){
                    dispose();
                    Home pnlHome=new Home();
                    pnlHome.pack();
                    pnlHome.setVisible(true);
                    dispose();
                }
                else{
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Usuario o contrasena invalida",
                            "Intente de nuevo",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    public Empleado usuario;
    private Empleado autentificarUsuario(String user,String password){
        Empleado usuario=null;
        CConexion objetoConexion=new CConexion();
        try{


            Connection con=objetoConexion.estableceConexion();
            Statement stat=con.createStatement();
            String consulta="SELECT *FROM empleado WHERE nombre=? AND password=?";
            PreparedStatement preparedStatement=con.prepareStatement(consulta);
            preparedStatement.setString(1,user);
            preparedStatement.setString(2,password);

            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                usuario=new Empleado();
                usuario.ID_Empleado=resultSet.getInt("ID_Empleado");
                usuario.Nombre=resultSet.getString("Nombre");
                usuario.ApellidoPaterno=resultSet.getString("ApellidoPaterno");
                usuario.ApellidoMaterno=resultSet.getString("ApellidoMaterno");
                usuario.Password=resultSet.getString("Password");

            }
            stat.close();
            con.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        return usuario;
    }

    public static void main(String[] args){
        LoginForm loginForm=new LoginForm(null);

        Empleado user=loginForm.usuario;
        if(user!=null){
            System.out.println("Autentificacion Exitosa de:\n "+user.Nombre+" "+user.ApellidoPaterno+" "+user.ApellidoMaterno);
            System.out.println("\nID_Empleado: "+user.ID_Empleado);

        }else{
            System.out.println("Autentificacion cancelada");
        }

    }

}
