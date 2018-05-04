package app.view;

import app.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ButtonController
{
	
	@FXML
	Button but_size;
	
	@FXML
	TextField tf_size;
	
	MainApp application=null;
	
	@FXML
	private void initialize()
	{
		
	}
	
	private int getSize()
	{
		int x=0;
		try
		{
			x = Integer.parseInt(tf_size.getText());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return x;
	}
	
	@FXML
	public void handleSizeSet()
	{
		application.setGameSize(getSize());
	}
	
	public void setApp(MainApp app)
	{
		application = app;
	}

}
