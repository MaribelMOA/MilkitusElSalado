import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class COrden_Bebida {
    public void AgregarOrdenBD(int No_Pedido, String ID_Bebida, int Cant,double Subtotal){
        CConexion objetoConexion=new CConexion();

        String consulta="INSERT orden_bebida(No_Pedido,ID_Bebida,Cantidad,subtotal) VALUES(?,?,?,?); ";
        try{
            CallableStatement cs=objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1,No_Pedido);
            cs.setString(2,ID_Bebida);
            cs.setInt(3,Cant);
            cs.setDouble(4,Subtotal);

            cs.execute();
            //JOptionPane.showMessageDialog(null,"Se agrego el pedido a la base de datos");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"NO se agrego la orden a la base de datos");
        }
    }
    public Orden buscarIDOrdenBebida(int No_Pedido, String Nombre_Bebida, int Cant,double Subtotal){

        Menu_Bebida comida=buscarBebida(Nombre_Bebida);

        Orden ordenBebida=null;
        CConexion objetoConexion=new CConexion();
        try{

            Connection con=objetoConexion.estableceConexion();
            Statement stat=con.createStatement();
            String consulta="SELECT * FROM orden_bebida WHERE No_Pedido=? AND ID_Bebida=? AND Cantidad=? AND Subtotal=?";
            PreparedStatement preparedStatement=con.prepareStatement(consulta);
            preparedStatement.setInt(1,No_Pedido);
            preparedStatement.setString(2,comida.getID_Producto());
            preparedStatement.setInt(3,Cant);
            preparedStatement.setDouble(4,Subtotal);

            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                ordenBebida=new Orden();
                ordenBebida.No_Pedido=resultSet.getInt("No_Pedido");
                ordenBebida.ID_Orden=resultSet.getInt("ID_OrdenB");
                ordenBebida.ID_Producto=resultSet.getString("ID_Bebida");
                ordenBebida.Cantidad=resultSet.getInt("Cantidad");
                ordenBebida.Subtotal=resultSet.getDouble("Subtotal");

                //tbOrdenes.setModel(modelo);
            }

            stat.close();
            con.close();

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ordenBebida;

    }
    public int existeIDOrdenBebida(Orden orden){

        // Menu comida=buscarIDComida(Nombre_Comida);

        Orden ordenBebida=null;
        CConexion objetoConexion=new CConexion();
        try{

            Connection con=objetoConexion.estableceConexion();
            Statement stat=con.createStatement();
            String consulta="SELECT * FROM orden_bebida WHERE No_Pedido=? AND ID_Bebida=? AND Cantidad=? AND Subtotal=?";
            PreparedStatement preparedStatement=con.prepareStatement(consulta);
            preparedStatement.setInt(1,orden.No_Pedido);
            preparedStatement.setString(2,orden.ID_Producto);
            preparedStatement.setInt(3,orden.Cantidad);
            preparedStatement.setDouble(4,orden.Subtotal);

            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                ordenBebida=new Orden();
                ordenBebida.No_Pedido=resultSet.getInt("No_Pedido");
                ordenBebida.ID_Orden=resultSet.getInt("ID_OrdenB");
                ordenBebida.ID_Producto=resultSet.getString("ID_Bebida");
                ordenBebida.Cantidad=resultSet.getInt("Cantidad");
                ordenBebida.Subtotal=resultSet.getDouble("Subtotal");

                //tbOrden
                //tbOrdenes.setModel(modelo);
            }

            stat.close();
            con.close();

        }catch(Exception ex){
            ex.printStackTrace();
        }
        if(ordenBebida!=null) return 1;
        else return 0;

    }
    public void deleteOrden(String ID_OrdenB){

        CConexion objetoConexion=new CConexion();
        String consulta="DELETE *FROM orden_bebida WHERE ID_OrdenB=?";
        try{
            CallableStatement cs=objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1,ID_OrdenB);
            cs.execute();
            //JOptionPane.showMessageDialog(null,"Se agrego el pedido a la base de datos");

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"NO se ELIMINO la orden a la base de datos");
        }

    }
    public Menu_Bebida buscarBebida(String ID_Bebida){
        Menu_Bebida bebida=null;
        CConexion objetoConexion=new CConexion();
        try{

            Connection con=objetoConexion.estableceConexion();
            Statement stat=con.createStatement();
            String consulta="SELECT *FROM menu_bebida WHERE ID_Bebida=?";
            PreparedStatement preparedStatement=con.prepareStatement(consulta);
            preparedStatement.setString(1,ID_Bebida);

            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                bebida=new Menu_Bebida();
                bebida.ID_Producto=resultSet.getString("ID_Bebida");
                bebida.Nombre=resultSet.getString("Nombre");
                bebida.Precio=resultSet.getDouble("Precio");
                bebida.Tipo=resultSet.getString("Tipo");
            }
            stat.close();
            con.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return bebida;
    }
}