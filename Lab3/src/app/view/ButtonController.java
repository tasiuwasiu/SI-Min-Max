package app.view;

import app.MainApp;
import app.model.GameHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ButtonController
{
	
	@FXML
	Button but_size;
	
	@FXML
	TextField tf_size;
	
	@FXML
	Text t_player;
	
	MainApp application=null;
	GameHelper gHelper;
	
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
	
	@FXML
	private void onEnter()
	{
		handleSizeSet();
	}
	
	public void setApp(MainApp app, GameHelper h)
	{
		application = app;
		gHelper = h;
	}
	
	public void setPlayer(String name)
	{
		t_player.setText(name);
	}

}
