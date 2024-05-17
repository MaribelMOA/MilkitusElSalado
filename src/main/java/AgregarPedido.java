import javax.print.Doc;
import javax.swing.*;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Vector;

public class AgregarPedido extends JDialog{
    private JButton btnTacosPescado;
    private JButton btnCaldoDeMariscoMediano;
    private JButton btnTacosDeCamaron;
    private JButton btnConsome;
    private JButton bntCoctelChico;
    private JButton btnCoctelMediano;
    private JButton btnCMChico;
    private JButton btnCaldoDeMariscoGrande;
    private JButton btnTacosDeMarlin;
    private JButton btnEnchilados;
    private JButton btnOstion;
    private JButton btnTacosPulpoE;
    private JButton btnAguachile;
    private JButton btnTostadasCevicheCamaron;
    private JButton btnTostadasDeCevichePescado;
    private JButton btnJamaica;
    private JButton btnHorchata;
    private JButton btnSodaP;
    private JButton btnSodaV;
    private JSpinner spCantidad;
    private JSpinner spExtra;
    private JButton agregarExtraButton;
    private JButton btnGuardar;
    private JButton btnBorrar;
    private JButton btnPagar;
    private JPanel pnlAgregar;
    private JTable tbOrdenes;
    private JLabel lblNoPedido;
    private JLabel lblExtra;
    private JLabel lblPrecioTotal;
    public BufferedImage image;
    public ImageIcon imgTacosPescado;
    public Icon icono;
    DefaultTableModel tbModel;
    public int noPedido;
    public double cantExtra,sum;

    public AgregarPedido(int numPedido){
        setContentPane(pnlAgregar);
        pnlAgregar.setBorder(BorderFactory.createTitledBorder("Milkitus El Salado-HOME"));
        pnlAgregar.setSize(1000, 700);

        setModal(true);

        spCantidad.setValue(1);
        spExtra.setValue(1);

        //tbModel=(DefaultTableModel)tbOrdenes.getModel();
        tbOrdenes.setVisible(true);
        noPedido=numPedido;
        lblNoPedido.setText("No. Pedido: #"+String.valueOf(noPedido));

        cantExtra=0;
        lblExtra.setText(String.valueOf(cantExtra));
        //  tbOrdenes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //JScrollPane pane=new JScrollPane(tbOrdenes);
        //add(pane);

        try {
            image = ImageIO.read(new File("images/tacosPescado.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgTacosPescado=new ImageIcon(image);
      //  icono=new ImageIcon(imgTacosPescado.getImage().getScaledInstance(btnTacosPescado.getWidth(),btnTacosPescado.getHeight(),Image.SCALE_DEFAULT));

        btnTacosPescado.setIcon(icono);
        tbOrdenes.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                DefaultTableModel modelo = new DefaultTableModel();
                modelo.addColumn("Numero");
                modelo.addColumn("Nombre");
                modelo.addColumn("Cantidad");
                modelo.addColumn("Subtotal");
                tbOrdenes.setModel(modelo);
                int num=0;
                sum=0;
                Orden ordenComida=null;
                CConexion objetoConexion=new CConexion();
                try{

                    Connection con=objetoConexion.estableceConexion();
                    Statement stat=con.createStatement();
                    String consulta="Select * FROM (Select*from orden_comida oc union select * from orden_bebida)R where R.No_Pedido=?";
                    PreparedStatement preparedStatement=con.prepareStatement(consulta);
                    preparedStatement.setInt(1,numPedido);

                    ResultSet resultSet=preparedStatement.executeQuery();
                    while(resultSet.next()){
                        ordenComida=new Orden();
                        ordenComida.No_Pedido=resultSet.getInt("No_Pedido");
                        ordenComida.ID_Orden=resultSet.getInt("ID_OrdenC");
                        ordenComida.ID_Producto=resultSet.getString("ID_Comida");
                        ordenComida.Cantidad=resultSet.getInt("Cantidad");
                        ordenComida.Subtotal=resultSet.getDouble("Subtotal");
                        sum+=ordenComida.Subtotal;
                        num++;
                        Menu temp=buscarComida(ordenComida.ID_Producto);
                        DefaultTableModel model2 = (DefaultTableModel) tbOrdenes.getModel();
                        if(temp!=null){
                            String fila[]= {String.valueOf(num),temp.getNombre(),String.valueOf( ordenComida.Cantidad),String.valueOf( ordenComida.Subtotal)};
                            fila[0]=String.valueOf(num);
                            model2.addRow(fila);
                        }else{
                            Menu_Bebida temp2=buscarBebida(ordenComida.ID_Producto);
                            String fila[]= {String.valueOf(num),temp2.getNombre(),String.valueOf( ordenComida.Cantidad),String.valueOf( ordenComida.Subtotal)};
                            fila[0]=String.valueOf(num);
                            model2.addRow(fila);
                        }
                        //tbOrdenes.setModel(modelo);
                    }
                    sum+=Double.valueOf(lblExtra.getText());
                    lblPrecioTotal.setText(String.valueOf(sum));

                    stat.close();
                    con.close();

                }catch(Exception ex){
                    ex.printStackTrace();
                }


            }

        });

        //regresarButton.addActionListener(e -> dispose());

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnAguachile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_comida="A1";
                int valorSpinner = Integer.parseInt(spCantidad.getValue().toString());
                agregarOrdenComida(id_comida,valorSpinner);
                tbOrdenes.setVisible(true);
            }
        });
        btnConsome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_comida="C1";
                int valorSpinner = Integer.parseInt(spCantidad.getValue().toString());
                agregarOrdenComida(id_comida,valorSpinner);
                tbOrdenes.setVisible(true);
            }
        });
        bntCoctelChico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_comida="CC";
                int valorSpinner = Integer.parseInt(spCantidad.getValue().toString());
                agregarOrdenComida(id_comida,valorSpinner);
                tbOrdenes.setVisible(true);
            }
        });
        btnCoctelMediano.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_comida="CM";
                int valorSpinner = Integer.parseInt(spCantidad.getValue().toString());
                agregarOrdenComida(id_comida,valorSpinner);
                tbOrdenes.setVisible(true);
            }
        });
        btnCaldoDeMariscoMediano.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_comida="CM2";
                int valorSpinner = Integer.parseInt(spCantidad.getValue().toString());
                agregarOrdenComida(id_comida,valorSpinner);
                tbOrdenes.setVisible(true);
            }
        });
        btnCMChico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_comida="CM1";
                int valorSpinner = Integer.parseInt(spCantidad.getValue().toString());
                agregarOrdenComida(id_comida,valorSpinner);
                tbOrdenes.setVisible(true);
            }
        });
        btnCaldoDeMariscoGrande.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_comida="CM3";
                int valorSpinner = Integer.parseInt(spCantidad.getValue().toString());
                agregarOrdenComida(id_comida,valorSpinner);
                tbOrdenes.setVisible(true);
            }
        });
        btnEnchilados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_comida="E1";
                int valorSpinner = Integer.parseInt(spCantidad.getValue().toString());
                agregarOrdenComida(id_comida,valorSpinner);
                tbOrdenes.setVisible(true);
            }
        });
        btnOstion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_comida="O1";
                int valorSpinner = Integer.parseInt(spCantidad.getValue().toString());
                agregarOrdenComida(id_comida,valorSpinner);
                tbOrdenes.setVisible(true);
            }
        });
        btnTacosPescado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_comida="T1";
                int valorSpinner = Integer.parseInt(spCantidad.getValue().toString());
                agregarOrdenComida(id_comida,valorSpinner);
                tbOrdenes.setVisible(true);
            }
        });
        btnTacosDeCamaron.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_comida="T2";
                int valorSpinner = Integer.parseInt(spCantidad.getValue().toString());
                agregarOrdenComida(id_comida,valorSpinner);
                tbOrdenes.setVisible(true);
            }
        });
        btnTacosDeMarlin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_comida="T3";
                int valorSpinner = Integer.parseInt(spCantidad.getValue().toString());
                agregarOrdenComida(id_comida,valorSpinner);
                tbOrdenes.setVisible(true);
            }
        });
        btnTacosPulpoE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_comida="T4";
                int valorSpinner = Integer.parseInt(spCantidad.getValue().toString());
                agregarOrdenComida(id_comida,valorSpinner);
                tbOrdenes.setVisible(true);
            }
        });
        btnTostadasCevicheCamaron.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_comida="TC1";
                int valorSpinner = Integer.parseInt(spCantidad.getValue().toString());
                agregarOrdenComida(id_comida,valorSpinner);
                tbOrdenes.setVisible(true);
            }
        });
        btnTostadasDeCevichePescado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_comida="TC2";
                int valorSpinner = Integer.parseInt(spCantidad.getValue().toString());
                agregarOrdenComida(id_comida,valorSpinner);
                tbOrdenes.setVisible(true);
            }
        });
        agregarExtraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // int cantExtra=Integer.parseInt(spExtra.getValue().toString());
                cantExtra+=Integer.parseInt(spExtra.getValue().toString());
                lblExtra.setText(String.valueOf(cantExtra));
                sum+=cantExtra;
                lblPrecioTotal.setText(String.valueOf(Double.valueOf(lblPrecioTotal.getText())+cantExtra));


            }
        });
        btnJamaica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_comida="A1";
                int valorSpinner = Integer.parseInt(spCantidad.getValue().toString());
                agregarOrdenBebida(id_comida,valorSpinner);
                tbOrdenes.setVisible(true);
            }
        });
        btnHorchata.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_comida="A2";
                int valorSpinner = Integer.parseInt(spCantidad.getValue().toString());
                agregarOrdenBebida(id_comida,valorSpinner);
                tbOrdenes.setVisible(true);
            }
        });
        btnSodaP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_comida="S1";
                int valorSpinner = Integer.parseInt(spCantidad.getValue().toString());
                agregarOrdenBebida(id_comida,valorSpinner);
                tbOrdenes.setVisible(true);
            }
        });
        btnSodaV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_comida="S2";
                int valorSpinner = Integer.parseInt(spCantidad.getValue().toString());
                agregarOrdenBebida(id_comida,valorSpinner);
                tbOrdenes.setVisible(true);
            }
        });
        btnBorrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tbModel=(DefaultTableModel)tbOrdenes.getModel();
                if(tbOrdenes.getSelectedRowCount()==1){
                    int selectedRow=tbOrdenes.getSelectedRow();


                    String fila[]= {(String)tbModel.getValueAt(selectedRow,1),(String)tbModel.getValueAt(selectedRow,2),
                            (String)tbModel.getValueAt(selectedRow,3)};
                    System.out.println("\nNombre:"+fila[0]+"\nCantidad: "+fila[1]+"\nSubtotal: "+fila[2]);

                    Orden ordenBorrar=buscarIDOrden(noPedido,fila[0],Integer.parseInt(fila[1]),Double.valueOf(fila[2]));
                    ordenBorrar.toString();
                    COrden_Comida ordenC=new COrden_Comida();
                    COrden_Bebida ordenB=new COrden_Bebida();

                    int existeOrdenComida=ordenC.existeIDOrdenComida(ordenBorrar);
                    if(existeOrdenComida==1){
                        ordenC.deleteOrden(ordenBorrar.ID_Producto);
                        JOptionPane.showMessageDialog(null,"Orden de comida eliminado exsitosamente.");
                    }
                    else{
                        int existeOrdenBebida=ordenB.existeIDOrdenBebida(ordenBorrar);
                        if(existeOrdenBebida==1){
                            ordenC.deleteOrden(ordenBorrar.ID_Producto);
                            JOptionPane.showMessageDialog(null,"Orden de bebida eliminado exsitosamente.");
                        }else JOptionPane.showMessageDialog(null,"Orden no encontrarad en BD.");

                    }
                    tbModel.removeRow(selectedRow);
                }else{
                    if(tbOrdenes.getSelectedRowCount()==0){
                        JOptionPane.showMessageDialog(null,"No selecciono ninguna orden.");
                    }else{
                        JOptionPane.showMessageDialog(null,"Porfavor selecciona una sola orden");
                    }
                }
            }
        });
        btnPagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pagar pnlPagar=new Pagar(Double.valueOf(lblPrecioTotal.getText())+Double.valueOf(lblExtra.getText()),noPedido,cantExtra);
                pnlPagar.pack();
                pnlPagar.setVisible(true);
                dispose();
            }
        });
    }

    public void agregarOrdenComida(String id, int cantidad){

        if(cantidad<=0){
            JOptionPane.showMessageDialog(this,"Porfavor ingrese una cantida diferente a 0");
        }else{
            Menu comida=buscarComida(id);
            double subtotal=cantidad*comida.Precio;
            int noOrden=tbOrdenes.getRowCount()+1;

            //String data[]={String.valueOf(noOrden),comida.Nombre,String.valueOf(subtotal)};
            Vector v=new Vector();
            v.add(String.valueOf(noOrden));
            v.add(comida.getNombre());
            v.add(String.valueOf(cantidad));
            v.add(String.valueOf(subtotal));
            String nombre=comida.getNombre();

            DefaultTableModel model2 = (DefaultTableModel) tbOrdenes.getModel();
            Object[] fila=new Object[4];
            fila[0]=String.valueOf(noOrden);
            fila[1]=comida.getNombre();
            fila[2]= String.valueOf(cantidad);
            fila[3]=String.valueOf(subtotal);

            model2.addRow(fila);
            tbOrdenes.setModel(model2);
            spCantidad.setValue(1);

            CPedido thisPedido=new CPedido();
            COrden_Comida objetoComida=new COrden_Comida();
            objetoComida.AgregarOrdenBD(noPedido,id, cantidad,subtotal);
            //JOptionPane.showMessageDialog(this,"Se agrego la orden");
        }
    }
    private Menu buscarComida(String ID_Comida){
        Menu comida=null;
        CConexion objetoConexion=new CConexion();
        try{

            Connection con=objetoConexion.estableceConexion();
            Statement stat=con.createStatement();
            String consulta="SELECT *FROM menu_comida WHERE ID_Comida=?";
            PreparedStatement preparedStatement=con.prepareStatement(consulta);
            preparedStatement.setString(1,ID_Comida);

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

    public void agregarOrdenBebida(String id, int cantidad){

        if(cantidad<0){
            JOptionPane.showMessageDialog(this,"Porfavor ingrese una cantida diferente a 0");
        }else{
            Menu_Bebida bebida=buscarBebida(id);
            double subtotal=cantidad*bebida.Precio;
            int noOrden=tbOrdenes.getRowCount()+1;

            //String data[]={String.valueOf(noOrden),comida.Nombre,String.valueOf(subtotal)};
            Vector v=new Vector();
            v.add(String.valueOf(noOrden));
            v.add(bebida.getNombre());
            v.add(String.valueOf(cantidad));
            v.add(String.valueOf(subtotal));

            DefaultTableModel model2 = (DefaultTableModel) tbOrdenes.getModel();
            Object[] fila=new Object[4];
            fila[0]=String.valueOf(noOrden);
            fila[1]=bebida.getNombre();
            fila[2]= String.valueOf(cantidad);
            fila[3]=String.valueOf(subtotal);

            model2.addRow(fila);
            tbOrdenes.setModel(model2);
            spCantidad.setValue(1);

            CPedido thisPedido=new CPedido();
            COrden_Bebida objetoBebida=new COrden_Bebida();
            objetoBebida.AgregarOrdenBD(noPedido,id, cantidad,subtotal);
            //JOptionPane.showMessageDialog(this,"Se agrego la orden");
        }
    }

    private Menu_Bebida buscarBebida(String ID_Bebida){
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
    public Orden buscarIDOrden(int No_Pedido, String Nombre, int Cant,double Subtotal){

        Menu comida=buscarComida(Nombre);
        Menu_Bebida bebida=buscarBebida(Nombre);
        String ID_Producto;
        if(comida!=null){
            ID_Producto=comida.getID_Producto();
        }else{
            //if(bebida!=null)
                ID_Producto= bebida.getID_Producto();
        }

        Orden ordenBorrar=null;
        CConexion objetoConexion=new CConexion();
        try{

            Connection con=objetoConexion.estableceConexion();
            Statement stat=con.createStatement();
            String consulta="Select * FROM (Select*from orden_comida oc union select * from orden_bebida)R where R.No_Pedido="+noPedido;
            PreparedStatement preparedStatement=con.prepareStatement(consulta);
            preparedStatement.setInt(1,No_Pedido);
            preparedStatement.setString(2,ID_Producto);
            preparedStatement.setInt(3,Cant);
            preparedStatement.setDouble(4,Subtotal);

            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                ordenBorrar=new Orden();
                ordenBorrar.No_Pedido=resultSet.getInt("No_Pedido");
                ordenBorrar.ID_Orden=resultSet.getInt("ID_OrdenC");
                ordenBorrar.ID_Producto=resultSet.getString("ID_Comida");
                ordenBorrar.Cantidad=resultSet.getInt("Cantidad");
                ordenBorrar.Subtotal=resultSet.getDouble("Subtotal");

                //tbOrdenes.setModel(modelo);
            }

            stat.close();
            con.close();

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ordenBorrar;
    }
}
