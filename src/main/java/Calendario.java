import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class Calendario extends JDialog {
    private JTextArea txtDescripcion;
    private JPanel pnlDateChooser;
    private JPanel pnlCalendario;
    private JButton btnMostrar;
    Calendar cid=Calendar.getInstance();
    JDateChooser dateChooser=new JDateChooser(cid.getTime());
    Double totalPrecio, totalExtra;
    ArrayList<CantComida> ordenIDs;
   // ArrayList<Integer>


    public Calendario(){
        setContentPane(pnlCalendario);
        pnlCalendario.setBorder(BorderFactory.createTitledBorder("Milkitus El Salado-HOME"));
        pnlCalendario.setSize(1000, 700);
        setModal(true);
        dateChooser.setDateFormatString("yyyy-MM-dd");
        pnlDateChooser.add(dateChooser);

        ordenIDs=new ArrayList<>();
        CMenu objetoMenu=new CMenu();
        ordenIDs=objetoMenu.IDComidas();

        totalExtra=0.0;
        totalPrecio=0.0;


        btnMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SimpleDateFormat dtForm=new SimpleDateFormat("yyyy-MM-dd");
                String dt=dtForm.format(dateChooser.getDate());
                System.out.println(dt);
                CPedido objetoPedido=new CPedido();
                ArrayList<Pedido> totalPedidos=objetoPedido.makeListPedidosFinalizados(dt);
                COrden_Comida ordenes=new COrden_Comida();
                ArrayList<Orden> totalOrdenes=new ArrayList<>();
                for (Pedido p:totalPedidos){
                    totalPrecio+=p.Precio_Total;
                    totalExtra+=p.Extra;
                   // ArrayList<Orden> tempOrden=new ArrayList<>();
                    //tempOrden=ordenes.CantidadOrdenFinalizada(p);
                    totalOrdenes.addAll(ordenes.CantidadOrdenFinalizada(p));
                }
                String text;
                if(totalOrdenes.isEmpty()){
                    text="\nNO HUBO VENTAS REGISTRADAS ESTE DIA";

                }else{
                    for(Orden o:totalOrdenes){
                        for(CantComida c:ordenIDs){
                            if(o.ID_Producto.equals(c.getID_Comida()))
                                c.Cantidad++;
                        }
                    }
                    Collections.sort(ordenIDs, new Comparator<CantComida>() {
                        @Override
                        public int compare(CantComida comida1, CantComida comida2) {
                            return comida2.getCantidad() - comida1.getCantidad();
                        }
                    });

                    text="\tGANANCIA TOTAL EL DIA "+dt+" fue: \n\t$"+String.valueOf(totalPrecio)+" pesos";
                    text+="\n\n\tGANANCIA EN COSTOS EXTRAS: \n\t$"+String.valueOf(totalExtra)+" pesos";
                    text+="\n\n\tTOP 3 COMIDAS MAS PEDIDAS ESTE DIA: \n";
                    for(int i=0;i<3;i++){
                        text+= "\n\t"+String.valueOf(i+1)+"-"+ordenIDs.get(i).Nombre+": "+ordenIDs.get(i).Cantidad;
                    }
                }

                txtDescripcion.setText(text);

            }
        });
    }


}
