package app.view;

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

	private GameHelper gHelper;
	
	private GraphicsContext gContext;
	
	private float columnSize;
	
	@FXML
	private void initialize()
	{
		drawLines(5);
		drawArea.setOnMouseReleased((e)->
		{
			gHelper.humanMove(e.getX(), e.getY());
		});
	}
	
	public void setApp(GameHelper ga)
	{
		gHelper = ga;
	}
	
	public void drawLines(int size)
	{
		gContext = drawArea.getGraphicsContext2D();
		gContext.clearRect(0, 0, 700, 700);
		columnSize = 700f/size;
		gContext.setStroke(Color.BLACK);
		gContext.setLineWidth(3);
		gContext.strokeRect(0, 0, 700, 700);
		for (int i = 1; i < size; i++)
		{
			gContext.strokeLine(i*columnSize, 0, i*columnSize, 700);
			gContext.strokeLine(0, i*columnSize, 700, i*columnSize);				
		}
	}
	
	public void placeToken(int x, int y, Paint color)
	{
		gContext.setFill(color);
		float offset = columnSize*0.1f;
		gContext.fillOval((columnSize*x)+offset, (columnSize*y)+offset, columnSize-(offset*2), columnSize-(offset*2));
	}
}
