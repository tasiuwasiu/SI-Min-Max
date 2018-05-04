package app.view;

import app.MainApp;
import app.model.GameHelper;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class GameAreaController
{

	@FXML
	private Canvas drawArea;
	
	GraphicsContext gContext;
	MainApp application;
	int size=0;
	float columnSize;
	GameHelper gHelper;
	
	@FXML
	private void initialize()
	{
		drawLines(5);
		drawArea.setOnMouseReleased((e)->
		{
			double x = e.getX();
			double y = e.getY();
			processClick(x, y);
		});
	}
	
	public void drawLines(int number)
	{
		size=number;
		gContext = drawArea.getGraphicsContext2D();
		gContext.clearRect(0, 0, 700, 700);
		columnSize = 700f/number;
		gContext.setStroke(Color.BLACK);
		gContext.setLineWidth(3);
		gContext.strokeRect(0, 0, 700, 700);
		for (int i=1; i<number; i++)
		{
			gContext.strokeLine(i*columnSize, 0, i*columnSize, 700);
			gContext.strokeLine(0, i*columnSize, 700, i*columnSize);				
		}
	}
	
	public void setApp(MainApp app, GameHelper ga)
	{
		application = app;
		gHelper = ga;
	}
	
	private void processClick(double x, double y)
	{
		System.out.println(gHelper.getPos(x) + ", " + gHelper.getPos(y));
		gHelper.processMove(x, y);
	}
	
	public void placeToken(int x, int y, Paint color)
	{
		gContext.setFill(color);
		gContext.fillOval(columnSize*x, columnSize*y, columnSize, columnSize);
	}
}
