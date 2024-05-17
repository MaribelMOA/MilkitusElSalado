public class Orden {
    @Override
    public String toString() {
        return "Orden{" +
                "ID_Orden=" + ID_Orden +
                ", No_Pedido=" + No_Pedido +
                ", ID_Producto='" + ID_Producto + '\'' +
                ", Cantidad=" + Cantidad +
                ", Subtotal=" + Subtotal +
                '}';
    }

    public int ID_Orden;
    public int No_Pedido;
    public String ID_Producto;
    public int Cantidad;
    public double Subtotal;

    public int getNo_Pedido() {
        return No_Pedido;
    }

    public String getID_Producto() {
        return ID_Producto;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public double getSubtotal() {
        return Subtotal;
    }

    public void setNo_Pedido(int i) {
    }

    public void setID_Producto(String b001) {
    }

    public void setCantidad(int i) {
    }

    public void setSubtotal(double v) {
    }
}
