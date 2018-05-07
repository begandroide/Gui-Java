
import com.bgautier.*;

public class Main{
	public static void main(String[] args)
	{
		try{
			UserInterface gui = new UserInterface(new Mylogic());
			gui.Modela();
		}catch(Exception err){
		}
	}
}
