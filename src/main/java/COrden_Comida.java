import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class COrden_Comida {
    public void AgregarOrdenBD(int No_Pedido, String ID_Comida, int Cant,double Subtotal){
        CConexion objetoConexion=new CConexion();

        String consulta="INSERT orden_comida(No_Pedido,ID_Comida,Cantidad,subtotal) VALUES(?,?,?,?); ";
        try{
            CallableStatement cs=objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1,No_Pedido);
            cs.setString(2,ID_Comida);
            cs.setInt(3,Cant);
            cs.setDouble(4,Subtotal);

            cs.execute();
            //JOptionPane.showMessageDialog(null,"Se agrego el pedido a la base de datos");

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"NO se agrego la orden a la base de datos");
        }
    }

    public Orden buscarIDOrdenComida(int No_Pedido, String Nombre_Comida, int Cant,double Subtotal){

        Menu comida=buscarIDComida(Nombre_Comida);

        Orden ordenComida=null;
        CConexion objetoConexion=new CConexion();
        try{

            Connection con=objetoConexion.estableceConexion();
            Statement stat=con.createStatement();
            String consulta="SELECT *FROM orden_comida WHERE No_Pedido=?,ID_Comida=?,Cantidad=?,Subtotal=?";
            PreparedStatement preparedStatement=con.prepareStatement(consulta);
            preparedStatement.setInt(1,No_Pedido);
            preparedStatement.setString(2,comida.getID_Producto());
            preparedStatement.setInt(3,Cant);
            preparedStatement.setDouble(4,Subtotal);

            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                ordenComida=new Orden();
                ordenComida.No_Pedido=resultSet.getInt("No_Pedido");
                ordenComida.ID_Orden=resultSet.getInt("ID_OrdenC");
                ordenComida.ID_Producto=resultSet.getString("ID_Comida");
                ordenComida.Cantidad=resultSet.getInt("Cantidad");
                ordenComida.Subtotal=resultSet.getDouble("Subtotal");

                //tbOrdenes.setModel(modelo);
            }

            stat.close();
            con.close();

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ordenComida;

    }
    public int existeIDOrdenComida(Orden orden){

       // Menu comida=buscarIDComida(Nombre_Comida);

        Orden ordenComida=null;
        CConexion objetoConexion=new CConexion();
        try{

            Connection con=objetoConexion.estableceConexion();
            Statement stat=con.createStatement();
            String consulta="SELECT * FROM orden_comida WHERE No_Pedido=? AND ID_Comida=? AND Cantidad=? AND Subtotal=?";
            PreparedStatement preparedStatement=con.prepareStatement(consulta);
            preparedStatement.setInt(1,orden.No_Pedido);
            preparedStatement.setString(2,orden.ID_Producto);
            preparedStatement.setInt(3,orden.Cantidad);
            preparedStatement.setDouble(4,orden.Subtotal);

            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                ordenComida=new Orden();
                ordenComida.No_Pedido=resultSet.getInt("No_Pedido");
                ordenComida.ID_Orden=resultSet.getInt("ID_OrdenC");
                ordenComida.ID_Producto=resultSet.getString("ID_Comida");
                ordenComida.Cantidad=resultSet.getInt("Cantidad");
                ordenComida.Subtotal=resultSet.getDouble("Subtotal");

                //tbOrdenes.setModel(modelo);
            }

            stat.close();
            con.close();

        }catch(Exception ex){
            ex.printStackTrace();
        }
        if(ordenComida!=null) return 1;
        else return 0;

    }
    public void deleteOrden(String ID_OrdenC){

        CConexion objetoConexion=new CConexion();
        String consulta="DELETE *FROM orden_comida WHERE ID_OrdenC=?";
        try{
            CallableStatement cs=objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1,ID_OrdenC);
            cs.execute();
            //JOptionPane.showMessageDialog(null,"Se agrego el pedido a la base de datos");

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"NO se ELIMINO la orden a la base de datos");
        }

    }
    public Menu buscarIDComida(String Nombre_Comida){
        Menu comida=null;
        CConexion objetoConexion=new CConexion();
        try{

            Connection con=objetoConexion.estableceConexion();
            Statement stat=con.createStatement();
            String consulta="SELECT *FROM orden_comida WHERE Nombre=?";
            PreparedStatement preparedStatement=con.prepareStatement(consulta);
            preparedStatement.setString(1,Nombre_Comida);

            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                comida=new Menu();
                comida.ID_Producto=resultSet.getString("ID_Comida");
                comida.Nombre=resultSet.getString("Nombre");
                comida.Precio=resultSet.getDouble("Precio");
            }
            stat.close();
            con.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        return comida;
    }
    public ArrayList<Orden> CantidadOrdenFinalizada(Pedido p){
        Orden comida=null;
        ArrayList<Orden> listaOrdenes=new ArrayList<>();
        CConexion objetoConexion=new CConexion();

        try{

            Connection con=objetoConexion.estableceConexion();
            Statement stat=con.createStatement();
            String consulta="select *from orden_comida where No_Pedido=?";
            PreparedStatement preparedStatement=con.prepareStatement(consulta);
            preparedStatement.setInt(1,p.No_Pedido);

            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                comida=new Orden();
                comida.ID_Producto=resultSet.getString("ID_Comida");
                comida.Cantidad=resultSet.getInt("Cantidad");
                comida.ID_Orden=resultSet.getInt("ID_OrdenC");
                comida.No_Pedido=resultSet.getInt("No_Pedido");
                comida.Subtotal=resultSet.getDouble("Subtotal");
                listaOrdenes.add(comida);
            }
            stat.close();
            con.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        return listaOrdenes;
    }

}
