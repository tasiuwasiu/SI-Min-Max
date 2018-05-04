package app.model;

import java.util.function.IntPredicate;

import app.view.ButtonController;
import app.view.GameAreaController;
import javafx.scene.paint.Color;

public class GameHelper
{
	public final static int NO_PLAYER=0;
	public final static int GREEN_PLAYER = 1;
	public final static int RED_PLAYER = 2;
	
	private int size;
	int player = 2;
	float linePos;
	GameAreaController gaController;
	ButtonController bController;
	private int[][] tokenTable;
	
	public GameHelper()
	{
		setSize(5);
	}
	
	public int getPos(double val)
	{
		for (int i=1; i<=size;i++)
		{
			if (val < i*linePos)
				return (i-1);
		}
		return -1; 
	}
	
	public void processMove(double x, double y)
	{
		int xColumn = getPos(x);
		int yColumn = getPos(y);
		
		if(xColumn<0 || yColumn<0)
			return;
		
		if(tokenTable[xColumn][yColumn]!=NO_PLAYER)
			return;
		
		if(player == GREEN_PLAYER)
		{
			gaController.placeToken(xColumn, yColumn, Color.GREEN);
			player = RED_PLAYER;
			bController.setPlayer("Czerwony");
			tokenTable[xColumn][yColumn]=GREEN_PLAYER;
			return;
		}
		if(player == RED_PLAYER)
		{
			gaController.placeToken(xColumn, yColumn, Color.RED);
			player = GREEN_PLAYER;
			bController.setPlayer("Zielony");
			tokenTable[xColumn][yColumn]=RED_PLAYER;
		}
	}
	
	public void setSize(int s)
	{
		size = s;
		linePos = 700f/size;
		tokenTable = new int[size][size];
		clearTable();
	}
	
	private void clearTable()
	{
		for (int i=0; i<size; i++)
			for (int j=0; j<size; j++)
				tokenTable[i][j]=NO_PLAYER;
	}
	
	public void setGController (GameAreaController g)
	{
		gaController = g;
	}
	
	public void setBController (ButtonController b)
	{
		bController = b;
	}
	
	
}
