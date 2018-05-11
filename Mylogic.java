import com.bgautier.*;
import java.util.ArrayList;
import java.util.Map;

public class Mylogic extends Logic{
    @Override
    public <T> T catalog(Object catalEdited) 
<<<<<<< HEAD
    {
	return null;
    }
=======
	{
		return null;
	}
>>>>>>> 29e979d04884e5bf08ddb6329b619d0eda36bc72

    @Override
    public Object creaUsuario(String nombre, String local, int edad) {
        return null;
    }

    @Override
    public String addItem(Object user, Product book, int cant)
    {
    	return null;
    }
    	
    @Override
    public Map<String, Integer> getProductsSelected(Object user) {
        return null;
    }

    @Override
    public String removeItem(Object user, Product book, int cant) {
	    return null;
    }

    @Override
    public String finalizarCompra() {
	    return null;
    }
}
