import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CMenuTest {
    // Prueba para verificar si se obtienen los identificadores de comida correctamente
    @Test
    public void testIDComidas() {
        // Crear un objeto de la clase CMenu
        CMenu menu = new CMenu();

        // Llamar al método IDComidas para obtener la lista de identificadores de comida
        ArrayList<CantComida> listaIds = menu.IDComidas();

        // Verificar que la lista no sea nula
        assertNotNull(listaIds);

        // Verificar que la lista tenga al menos un elemento
        assertTrue(listaIds.size() > 0);

        // Verificar que cada elemento de la lista tenga un ID_Comida no nulo ni vacío
        for (CantComida comida : listaIds) {
            assertNotNull(comida.getID_Comida());
            assertFalse(comida.getID_Comida().isEmpty());
        }
    }
}