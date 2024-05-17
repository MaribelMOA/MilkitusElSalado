import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CPedido {
    public void AgregarPedidoBD(){
        CConexion objetoConexion=new CConexion();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStamp = df.format(new Date());
        //si te mandaran la fehca por un JDateChooser fecha,pondrias en el parentesis
        //fecha.getDate() en lugar de new Date()

        String consulta="INSERT pedido(Fecha_Orden,Estado) VALUES(?,?); ";
        try{
            CallableStatement cs=objetoConexion.estableceConexion().prepareCall(consulta);
            System.out.println("Entro");
            cs.setString(1,timeStamp);
            System.out.println("Entro2");
            String estado="EP";
            cs.setString(2,estado);
            System.out.println("Entro3");
            cs.execute();
            System.out.println("Entro4");
            JOptionPane.showMessageDialog(null,"Se agrego el pedido a la base de datos");

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"NO se agrego EL PEDIDO a la base de datos");
        }
    }
    public void AgregarPedidoCompletoBD(int No_Pedido, double Precio_Total, double Extra, double Precio_Pagado, double Cambio){
        CConexion objetoConexion=new CConexion();
        //si te mandaran la fehca por un JDateChooser fecha,pondrias en el parentesis
        //fecha.getDate() en lugar de new Date()

        //String consulta="update pedido set Precio_Total=?,Extra=?,Precio_Pagado=?,Cambio=?,Estado=? where No_Pedido=?";
        try{
           // Connection con=objetoConexion.estableceConexion();
           // Statement stat=con.createStatement();
            String consulta="update pedido set Precio_Total=?,Extra=?,Precio_Pagado=?,Cambio=?,Estado=? where No_Pedido=?";
           // PreparedStatement preparedStatement=con.prepareStatement(consulta);

            CallableStatement cs=objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setDouble(1,Precio_Total);
            cs.setDouble(2,Extra);
            String estado="F";
            cs.setDouble(3,Precio_Pagado);
            cs.setDouble(4,Cambio);
            cs.setString(5,estado);
            cs.setInt(6,No_Pedido);
           // preparedStatement.setInt(1,No_Pedido);

            cs.execute();
            JOptionPane.showMessageDialog(null,"Se agrego el pedido a la base de datos");

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"NO se agrego EL PEDIDO a la base de datos");
        }
    }
    public void CambiarEstadoProgreso(){
        CConexion objetoConexion=new CConexion();

        String consulta="INSERT INTO informenotas(Fecha_Orden,Estado)" +
                "values(?,?) ";
        /*CallableStatement cs=objetoConexion.estableceConexion().prepareCall(sql);
            cs.setString(1,user);
            cs.setString(2,password);*/
    }
    public void CambiarEstadoFin(){
        CConexion objetoConexion=new CConexion();

        String consulta="INSERT INTO informenotas(Fecha_Orden,Estado)" +
                "values(?,?) ";
        /*CallableStatement cs=objetoConexion.estableceConexion().prepareCall(sql);
            cs.setString(1,user);
            cs.setString(2,password);*/
    }

    public int buscarUltimoPedido(){
        //Pedido pedidoReciente=null;
        int noPedido=0;
        CConexion objetoConexion=new CConexion();
        try{
            Connection con=objetoConexion.estableceConexion();
            Statement stat=con.createStatement();
            String consulta="select * from pedido order by No_Pedido desc limit 1;";
            PreparedStatement preparedStatement=con.prepareStatement(consulta);

            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                // pedidoReciente=new Pedido();
                noPedido=resultSet.getInt("No_Pedido");
                //pedidoReciente.No_Pedido=resultSet.getInt("No_Pedido");
                //  pedidoReciente.Cambio=resultSet.getInt()


            }
            stat.close();
            con.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        return noPedido;
    }

    public ArrayList<Pedido> makeListPedidosProgreso(){
       // Pedido unPedido=null;
        ArrayList<Pedido> listPedidos=new ArrayList<>();
        CConexion objetoConexion=new CConexion();
        try{

            Connection con=objetoConexion.estableceConexion();
            Statement stat=con.createStatement();
            String consulta="SELECT *FROM pedido WHERE Estado=?";
            PreparedStatement preparedStatement=con.prepareStatement(consulta);
            String est="EP";
            preparedStatement.setString(1,est);

            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){

                Pedido unPedido=new Pedido();
                unPedido.No_Pedido=resultSet.getInt("No_Pedido");
                unPedido.Fecha_Orden=resultSet.getString("Fecha_Orden");
                unPedido.Estado=resultSet.getString("Estado");
                unPedido.Cambio=resultSet.getDouble("Cambio");
                unPedido.Extra=resultSet.getDouble("Extra");
                unPedido.Precio_Pagado=resultSet.getDouble("Precio_Pagado");
                unPedido.Precio_Total=resultSet.getDouble("Precio_Total");
                //tbOrdenes.setModel(modelo);
                listPedidos.add(unPedido);
            }
            stat.close();
            con.close();

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return listPedidos;
    }
    public ArrayList<Pedido> makeListPedidosFinalizados(String fecha){
        // Pedido unPedido=null;
        ArrayList<Pedido> listPedidos=new ArrayList<>();
        CConexion objetoConexion=new CConexion();
        try{

            Connection con=objetoConexion.estableceConexion();
            Statement stat=con.createStatement();
            String consulta="SELECT *FROM pedido WHERE Estado=? and  DATE(Fecha_orden)=?";
            PreparedStatement preparedStatement=con.prepareStatement(consulta);
            String est="F";
            preparedStatement.setString(1,est);
            preparedStatement.setString(2,fecha);

            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){

                Pedido unPedido=new Pedido();
                unPedido.No_Pedido=resultSet.getInt("No_Pedido");
                unPedido.Fecha_Orden=resultSet.getString("Fecha_Orden");
                unPedido.Estado=resultSet.getString("Estado");
                unPedido.Cambio=resultSet.getDouble("Cambio");
                unPedido.Extra=resultSet.getDouble("Extra");
                unPedido.Precio_Pagado=resultSet.getDouble("Precio_Pagado");
                unPedido.Precio_Total=resultSet.getDouble("Precio_Total");
                //tbOrdenes.setModel(modelo);
                listPedidos.add(unPedido);
            }
            stat.close();
            con.close();

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return listPedidos;
    }
}
