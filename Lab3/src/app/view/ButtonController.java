package app.view;

import app.MainApp;
import app.model.GameHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
	Button but_finish_min_max;
	
	@FXML
	Button but_finish_alfa_beta;
	
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
	
	@FXML
	ChoiceBox<Integer> cb_tree_size;
	
	MainApp application;
	GameHelper gHelper;
	
	@FXML
	private void initialize()
	{
		cb_tree_size.getItems().removeAll(cb_tree_size.getItems());
		cb_tree_size.getItems().addAll(3,4,5,6,7,8,9,10,11,12,13);
		cb_tree_size.getSelectionModel().select(0);
		cb_tree_size.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2)
			{
				gHelper.setTreeSize(cb_tree_size.getItems().get((Integer) number2));
			}
		});
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
	
	@FXML
	private void minMaxfinish()
	{
		gHelper.finishGameAsComputer(GameHelper.MIN_MAX);
	}
	
	@FXML
	private void alfaBetaFinish()
	{
		gHelper.finishGameAsComputer(GameHelper.ALFA_BETA);
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
