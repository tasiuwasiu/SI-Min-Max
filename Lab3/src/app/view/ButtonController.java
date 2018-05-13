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
	Button but_min_max;
	
	@FXML
	Button but_alfa_beta;
	
	@FXML
	TextField tf_size;
	
	@FXML
	Text t_player;
	
	@FXML
	Text t_red_player_score;
	
	@FXML
	Text t_green_player_score;
	
	@FXML
	Text t_time;
	
	MainApp application;
	GameHelper gHelper;
	
	@FXML
	private void initialize()
	{
		
	}
	
	private int getSize()
	{
		int x = 0;
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
	
	@FXML
	private void minMaxMove()
	{	
		gHelper.computerMove(GameHelper.MIN_MAX);
	}
	
	@FXML
	private void alfaBetaMove()
	{
		gHelper.computerMove(GameHelper.ALFA_BETA);
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
	
	public void setGreenPlayerScore(String score)
	{
		t_green_player_score.setText(score);
	}
	
	public void setRedPlayerScore(String score)
	{
		t_red_player_score.setText(score);
	}
	
	public void setTime(String time)
	{
		t_time.setText(time);
	}

}
