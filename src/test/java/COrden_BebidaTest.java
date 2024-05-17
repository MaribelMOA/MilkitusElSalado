import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class COrden_BebidaTest {

    @Test
    void testAgregarOrdenBD() {
    }

    @Test
    void testBuscarIDOrdenBebida() {
        // Datos de prueba
        int noPedido = 1;
        String IDBebida = "A1";
        int cantidad = 1;
        double subtotal = 25.00;
        // Crear una instancia de COrden_Bebida
        COrden_Bebida ordenBebida = new COrden_Bebida();

        // Llamar a la función para buscar una orden de bebida
        Orden ordenEncontrada = ordenBebida.buscarIDOrdenBebida(noPedido, IDBebida, cantidad, subtotal);

        // Verificar si se encontró la orden
        assertNotNull(ordenEncontrada);
        assertEquals(noPedido, ordenEncontrada.getNo_Pedido());
        assertEquals(IDBebida, ordenEncontrada.getID_Producto());
        assertEquals(cantidad, ordenEncontrada.getCantidad());
        assertEquals(subtotal, ordenEncontrada.getSubtotal(), 0.001); // Aquí 0.001 es la precisión aceptable para la comparación de números de punto flotante
    }

    @Test
    void testExisteIDOrdenBebida() {
        // Datos de prueba
        Orden orden = new Orden();
        orden.setNo_Pedido(1);
        orden.setID_Producto("A1");
        orden.setCantidad(1);
        orden.setSubtotal(25.00);

        // Crear una instancia de COrden_Bebida
        COrden_Bebida ordenBebida = new COrden_Bebida();

        // Llamar a la función para verificar si existe una orden de bebida
        int resultado = ordenBebida.existeIDOrdenBebida(orden);

        // Verificar si existe la orden de bebida
        assertEquals(1, resultado); // Debe ser 1 si existe la orden, de lo contrario 0
    }

    @Test
    void testDeleteOrden() {
    }
    @Test
    void testBuscarBebida() {
        // Datos de prueba
        String ID_Bebida = "A1";

        // Crear una instancia de COrden_Bebida
        COrden_Bebida ordenBebida = new COrden_Bebida();

        // Llamar al método para buscar la bebida
        Menu_Bebida bebida = ordenBebida.buscarBebida(ID_Bebida);

        // Verificar si la bebida se encontró correctamente
        assertNotNull(bebida);
        assertEquals("A1", bebida.getID_Producto());
        assertEquals("Agua de Jamaica", bebida.getNombre());
        assertEquals(25.0, bebida.getPrecio());
        assertEquals("P", bebida.getTipo());
    }

}