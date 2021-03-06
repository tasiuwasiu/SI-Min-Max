package app;

import java.io.IOException;

import app.model.GameHelper;
import app.view.ButtonController;
import app.view.GameAreaController;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MainApp extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	private GameAreaController gameController;
	private ButtonController buttonController;
	private GameHelper gHelper;
	private Thread compThread;
	
	@Override
	public void start(Stage primaryStage) 
	{
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Stratego");
		gHelper = new GameHelper();
		initRoot();
		initButtons();
		initGamePanel();
		gHelper.setSize(5);
	}
	
	private void initRoot()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void initButtons()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ButtonLayout.fxml"));
			AnchorPane buttonLayout = (AnchorPane) loader.load();
			rootLayout.setRight(buttonLayout);
			buttonController = loader.getController();
			buttonController.setApp(this, gHelper);
			gHelper.setBController(buttonController);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void initGamePanel()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/GamePanel.fxml"));
			AnchorPane gamePanel = (AnchorPane) loader.load();
			rootLayout.setLeft(gamePanel);
			gameController = loader.getController();
			gameController.setApp(gHelper);
			gHelper.setGController(gameController);
		}
		catch (IOException e)
		{	
			e.printStackTrace();
		}
	}
	
	public void setGameSize(int number)
	{
		try
		{
			compThread.interrupt();
		}
		catch (NullPointerException e)
		{
			System.out.println("No thread assigned");
		}
		gHelper.setSize(number);
		gameController.drawLines(number);
	}
	
	public Stage getPrimaryStage()
	{
		return primaryStage;
	}
	
	public void finishAsComputer(int code)
	{
		Task<Void> task = new Task<Void> () {
			@Override
			protected Void call() throws Exception
			{
				gHelper.finishGameAsComputer(code);
				return null;
			}
		
		};
		compThread = new Thread(task);
		compThread.start();
	
	}
	
	
	public static void main(String[] args) 
	{
		launch(args);
		
	}
}
