import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pagar extends JDialog{
    private JTextField txtPrecioPagado;
    private JButton btnPagar;
    private JLabel lblPrecioTotal;
    private JLabel lblCambio;
    private JPanel pnlPagar;
    private JLabel lblTitulo;
    private double cambio;

    public Pagar(double PrecioTotal,int noPedido, double cantExtra){
        setContentPane(pnlPagar);
        pnlPagar.setBorder(BorderFactory.createTitledBorder("Milkitus El Salado-HOME"));
        pnlPagar.setSize(1000, 700);


        setModal(true);
        cambio=0;
        lblTitulo.setText("PAGO DE PEDIDO #"+noPedido);
        lblPrecioTotal.setText(String.valueOf(PrecioTotal));
        lblCambio.setText(String.valueOf(cambio));
        if(!txtPrecioPagado.getText().isEmpty()){
            cambio=PrecioTotal-Double.valueOf(txtPrecioPagado.getText());
            lblCambio.setText(String.valueOf(cambio));
        }
        btnPagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtPrecioPagado.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,
                            "Porfavor ingrese la cantidad pagada por el cliente",
                            "Intente de nuevo",
                            JOptionPane.ERROR_MESSAGE);
                }
                else{
                    double precioPagado=Double.valueOf(txtPrecioPagado.getText());
                    if(precioPagado>=PrecioTotal){
                        cambio=Double.valueOf(txtPrecioPagado.getText())-PrecioTotal;
                        lblCambio.setText(String.valueOf(cambio));
                        CPedido pagarPedido=new CPedido();
                        pagarPedido.AgregarPedidoCompletoBD(noPedido,PrecioTotal,cantExtra,precioPagado,cambio);
                        //dispose();
                        if(cambio>0){
                            JOptionPane.showMessageDialog(null,
                                    "Cambio fue de: $"+cambio+"mxn");
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,
                                "Precio ingresado no es valido, debe ser una cantidad mayor o igual al precio total del pedido",
                                "Intente de nuevo",
                                JOptionPane.ERROR_MESSAGE);
                    }

                }

            }
        });
    }
}
