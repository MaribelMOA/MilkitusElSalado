import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class CConexionTest {
    @Test
    public void testEstableceConexion() throws SQLException {
        CConexion cConexion = new CConexion();
        Connection conexion = cConexion.estableceConexion();
        assertNotNull(conexion); // Verifica que la conexión no sea nula
        assertTrue(conexion.isValid(5)); // Verifica que la conexión sea válida (timeout de 5 segundos)
    }
}