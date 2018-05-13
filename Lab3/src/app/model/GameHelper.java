package app.model;

import java.util.function.Predicate;

import javax.naming.SizeLimitExceededException;

import app.view.ButtonController;
import app.view.GameAreaController;
import javafx.scene.paint.Color;

public class GameHelper
{
	public final static int NO_PLAYER = 0;
	public final static int GREEN_PLAYER = 1;
	public final static int RED_PLAYER = 2;
	
	public final static int MIN_MAX = 10;
	public final static int ALFA_BETA = 20;
	
	private int size;
	private float columnSize;
	private int freeTokens;
	
	private int currentPlayer = RED_PLAYER;
	private int redScore;
	private int greenScore;
	
	private int[][] tokenTable;

	private GameAreaController gaController;
	private ButtonController bController;
	private AlgorithmHelper aHelper;
	
	public GameHelper()
	{
		aHelper = new AlgorithmHelper();
	}
	
	public void setGController(GameAreaController g)
	{
		gaController = g;
	}
	
	public void setBController(ButtonController b)
	{
		bController = b;
	}
	
	public void setSize(int s)
	{
		size = s;
		columnSize = 700f/size;
		redScore = 0;
		greenScore = 0;
		currentPlayer = RED_PLAYER;
		bController.setPlayer("Czerwony");
		bController.setRedPlayerScore(String.valueOf(redScore));
		bController.setGreenPlayerScore(String.valueOf(greenScore));
		tokenTable = new int[size][size];
		clearTable();
	}
	
	private void clearTable()
	{
		freeTokens = size*size;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				tokenTable[i][j] = NO_PLAYER;
	}
	
	public void computerMove(int code)
	{
		if (freeTokens < 1)
			return;
		
		aHelper.setData(tokenTable, currentPlayer, size);
		aHelper.calculate(code);
		bController.setTime(String.valueOf(aHelper.getTime() / 1000) + " s");
		makeMove(aHelper.getXPosition(), aHelper.getYPosition());
	}
	
	public void humanMove(double x, double y)
	{
		makeMove(getPos(x), getPos(y));
	}
	
	private int getPos(double val)
	{
		for (int i = 1; i <= size; i++)
		{
			if (val < i*columnSize)
				return (i-1);
		}
		return -1; 
	}
	
	private void makeMove (int xColumn, int yColumn)
	{
		if (xColumn < 0 || yColumn < 0 || xColumn > size || yColumn > size)
			return;
		
		if (tokenTable[xColumn][yColumn] != NO_PLAYER)
			return;
		
		if (currentPlayer == GREEN_PLAYER)
		{
			gaController.placeToken(xColumn, yColumn, Color.GREEN);
			tokenTable[xColumn][yColumn] = GREEN_PLAYER;
			processPoints(xColumn, yColumn);
			currentPlayer = RED_PLAYER;
			bController.setPlayer("Czerwony");
			isEndGame();
			return;
		}
		
		if (currentPlayer == RED_PLAYER)
		{
			gaController.placeToken(xColumn, yColumn, Color.RED);
			tokenTable[xColumn][yColumn] = RED_PLAYER;
			processPoints(xColumn, yColumn);
			currentPlayer = GREEN_PLAYER;
			bController.setPlayer("Zielony");
			isEndGame();
		}
	}
	
	private void isEndGame()
	{
		freeTokens--;
		if (freeTokens < 1)
			bController.setPlayer("Koniec Gry");
	}
	
	private void processPoints(int x, int y)
	{
		int addScore = 0;
		addScore+= calculatePointsColumn(x, y);
		addScore+= calculatePointsRow(x, y);
		addScore+= calculatePointsUpLeftDiagonal(x, y);
		addScore+= calculatePointsUpRightDiagonal(x, y);
				
		if (currentPlayer == RED_PLAYER)
		{
			redScore+= addScore;
			bController.setRedPlayerScore(String.valueOf(redScore));
		}
		if (currentPlayer == GREEN_PLAYER)
		{
			greenScore+= addScore;
			bController.setGreenPlayerScore(String.valueOf(greenScore));
		}
	}
	
	private int calculatePointsRow(int x, int y)
	{
		int points = 0;
		boolean isContinuous = true;
		
		for (int i=(y-1); i>=0; i--)
		{
			if (tokenTable[x][i]==NO_PLAYER)
				return 0;
			if (tokenTable[x][i]==currentPlayer && isContinuous)
			{
				points++;
			}
			else
				isContinuous = false;
		}
		
		isContinuous = true;
		
		for (int i=y; i<size; i++)
		{
			if (tokenTable[x][i]==NO_PLAYER)
				return 0;
			if (tokenTable[x][i]==currentPlayer && isContinuous)
			{
				points++;
			}
			else
				isContinuous = false;
		}
		
		return points==1?0:points;
	}
	
	private int calculatePointsColumn(int x, int y)
	{
		int points = 0;
		boolean isContinuous = true;
		
		for (int i=x; i>=0; i--)
		{
			if (tokenTable[i][y]==NO_PLAYER)
				return 0;
			if (tokenTable[i][y]==currentPlayer && isContinuous)
			{
				points++;
			}
			else
				isContinuous = false;
		}
		
		isContinuous = true;
		
		for (int i=(x+1); i<size; i++)
		{
			if (tokenTable[i][y]==NO_PLAYER)
				return 0;
			if (tokenTable[i][y]==currentPlayer && isContinuous)
			{
				points++;
			}
			else
				isContinuous = false;
		}
		
		return points==1?0:points;
	}
	
	private int calculatePointsUpLeftDiagonal(int x, int y)
	{
		int points = 0;
		boolean isContinuous = true;
		
		for (int i=y, j=x; i>=0 && j>=0; i--, j--)
		{
			if (tokenTable[j][i]==NO_PLAYER)
				return 0;
			if (tokenTable[j][i]==currentPlayer && isContinuous)
			{
				points++;
			}
			else
				isContinuous = false;
		}
		
		isContinuous = true;
		
		for (int i=y+1, j=x+1; i<size && j<size; i++, j++)
		{
			if (tokenTable[j][i]==NO_PLAYER)
				return 0;
			if (tokenTable[j][i]==currentPlayer && isContinuous)
			{
				points++;
			}
			else
				isContinuous = false;
		}
		
		return points==1?0:points;
	}
	
	private int calculatePointsUpRightDiagonal(int x, int y)
	{
		int points = 0;
		boolean isContinuous = true;
		
		for (int i=y, j=x; i>=0 && j<size; i--, j++)
		{
			if (tokenTable[j][i]==NO_PLAYER)
				return 0;
			if (tokenTable[j][i]==currentPlayer && isContinuous)
			{
				points++;
			}
			else
				isContinuous = false;
		}
		
		isContinuous = true;
		
		for (int i=y+1, j=x-1; i<size && j>=0; i++, j--)
		{
			if (tokenTable[j][i]==NO_PLAYER)
				return 0;
			if (tokenTable[j][i]==currentPlayer && isContinuous)
			{
				points++;
			}
			else
				isContinuous = false;
		}
		
		return points==1?0:points;
	}
	
}
