package app.view;

import app.MainApp;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class GameAreaController
{

	@FXML
	private Canvas drawArea;
	
	MainApp application;
	
	@FXML
	private void initialize()
	{
		drawLines(5);
	}
	
	public void drawLines(int number)
	{
		GraphicsContext gContext = drawArea.getGraphicsContext2D();
		gContext.clearRect(0, 0, 700, 700);
		float x = 700f/number;
		gContext.setStroke(Color.BLACK);
		gContext.setLineWidth(3);
		gContext.strokeRect(0, 0, 700, 700);
		for (int i=1; i<number; i++)
		{
			gContext.strokeLine(i*x, 0, i*x, 700);
			gContext.strokeLine(0, i*x, 700, i*x);
						
		}
	}
	
	public void setApp(MainApp app)
	{
		application = app;
	}
}
