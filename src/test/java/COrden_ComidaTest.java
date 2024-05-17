import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class COrden_ComidaTest {

    @Test
    void agregarOrdenBD() {
    }

    @Test
    void buscarIDOrdenComida() {
    }

    @Test
    public void testExisteIDOrdenComida_Existente() {
        // Configuración de prueba
        Orden orden = new Orden();
        orden.No_Pedido = 1;
        orden.ID_Producto = "T1"; // Un ID de producto existente en la base de datos
        orden.Cantidad = 2;
        orden.Subtotal = 70.0;


        // Llamada a la función
        COrden_Comida cOrdenComida = new COrden_Comida();
        int resultado = cOrdenComida.existeIDOrdenComida(orden);


        // Verificación del resultado
        assertEquals(1, resultado);
    }


    @Test
    void deleteOrden() {
    }

    @Test
    void buscarIDComida() {
    }

    @Test
    void cantidadOrdenFinalizada() {
    }
}