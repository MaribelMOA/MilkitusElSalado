import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PedidosProgreso extends JDialog {
    JLabel lblHead;
    JLabel lblSeleccionar;
    JLabel  lblDescripcion;

    public JTextArea txtDescripcion;
    public static  JComboBox selectTarea;
    public JButton btnCompletado;
    public JButton btnEditar;
    public JPanel panelNorth;
    public JPanel panelCenter;
    public JPanel panelSouth;
    public static JButton btnImagen;
    public BufferedImage image;
    private JComboBox cbSeleccionaPedido;
    private JButton btnModificar;
    private JLabel lblTitulo;
    private JScrollPane spScroll;
    private JPanel pnlProgreso;

    public PedidosProgreso(){
        setContentPane(pnlProgreso);
        pnlProgreso.setBorder(BorderFactory.createTitledBorder("Milkitus El Salado-HOME"));
        pnlProgreso.setSize(1000, 700);

        setModal(true);
        spScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        spScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

      //  txtDescripcion.setLineWrap(true);
       // txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setEditable(false);

        CPedido objectPedido=new CPedido();
        ArrayList<Pedido> listaPedidos =objectPedido.makeListPedidosProgreso();
        if(listaPedidos!=null){
            for (Pedido p:listaPedidos) {
                // ArrayList<String> splitThisTarea=splitTarea(tareas);pe
                String numPedido="Pedido #"+String.valueOf(p.No_Pedido);
                cbSeleccionaPedido.addItem(numPedido);
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"No hay tareas para mostrar");
        }




        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editar();
            }
        });
        cbSeleccionaPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedCombo();
            }
        });
    }


    private void selectedCombo(){


        String selected = (String) cbSeleccionaPedido.getSelectedItem();
        CPedido objectPedido=new CPedido();
        ArrayList<Pedido> listaPedidosEnProgreso=objectPedido.makeListPedidosProgreso();
        int numeroPedido=0;
        String text="";
        for(Pedido p:listaPedidosEnProgreso){
            String estePedido="Pedido #"+String.valueOf(p.No_Pedido);
            if(selected.equals(estePedido)){
                numeroPedido=p.No_Pedido;
                text+="Pedido #"+numeroPedido;
                text+="\nFecha: "+p.Fecha_Orden;
                int cantOrdenes=cantOrdenes(numeroPedido);
                text+="\nCantidad de ordenes: "+cantOrdenes;
            }
        }
        txtDescripcion.setText(text);



    }

    public int cantOrdenes(int noPedido){
        Orden ordenComida=null;
        CConexion objetoConexion=new CConexion();
        int canOrdenes=0;
        try{

            Connection con=objetoConexion.estableceConexion();
            Statement stat=con.createStatement();
            String consulta="Select * FROM (Select*from orden_comida oc union select * from orden_bebida)R where R.No_Pedido=?";
            PreparedStatement preparedStatement=con.prepareStatement(consulta);
            preparedStatement.setInt(1,noPedido);

            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                ordenComida=new Orden();
                ordenComida.No_Pedido=resultSet.getInt("No_Pedido");
                ordenComida.ID_Orden=resultSet.getInt("ID_OrdenC");
                ordenComida.ID_Producto=resultSet.getString("ID_Comida");
                ordenComida.Cantidad=resultSet.getInt("Cantidad");
                ordenComida.Subtotal=resultSet.getDouble("Subtotal");
                canOrdenes++;

            }
            stat.close();
            con.close();

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return canOrdenes;
    }


    public void editar(){
        String selected = (String) cbSeleccionaPedido.getSelectedItem();
        CPedido objectPedido=new CPedido();
        ArrayList<Pedido> listaPedidosEnProgreso=objectPedido.makeListPedidosProgreso();
        for(Pedido p:listaPedidosEnProgreso){
            String estePedido="Pedido #"+String.valueOf(p.No_Pedido);
            if(selected.equals(estePedido)){
                AgregarPedido pnlAgregar=new AgregarPedido(p.No_Pedido);
                pnlAgregar.pack();
                pnlAgregar.setVisible(true);
            }
        }
    }



}
