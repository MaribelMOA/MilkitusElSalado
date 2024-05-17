import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CMenu {
    public ArrayList<CantComida> IDComidas(){
        ArrayList<CantComida> listaIds=new ArrayList<>();
        //Menu comida=null;
        CantComida comida=null;
        CConexion objetoConexion=new CConexion();
        try{

            Connection con=objetoConexion.estableceConexion();
            Statement stat=con.createStatement();
            String consulta="SELECT *FROM menu_comida ";
            PreparedStatement preparedStatement=con.prepareStatement(consulta);

            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                comida=new CantComida();
                comida.ID_Comida=resultSet.getString("ID_Comida");
                comida.Nombre=resultSet.getString("Nombre");
                comida.Cantidad=0;
                listaIds.add(comida);
            }
            stat.close();
            con.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        return listaIds;
    }
}
