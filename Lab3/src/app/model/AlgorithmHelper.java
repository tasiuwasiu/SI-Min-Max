package app.model;

import java.util.concurrent.ThreadLocalRandom;

public class AlgorithmHelper
{
	private int size;
	private int[][] tokenTable;
	
	private int currentPlayer;
	private int xPos;
	private int yPos;
	
	private AlfaBetaAlgorithm abAlgorithm;
	private MinMaxAlgorithm mmAlgorithm;
	private long time;
	
	public AlgorithmHelper()
	{
		currentPlayer = GameHelper.NO_PLAYER;
		abAlgorithm = new AlfaBetaAlgorithm();
		mmAlgorithm = new MinMaxAlgorithm();
	}
	
	
	public void setData(int[][] t, int p, int s)
	{
		size = s;
		currentPlayer = p;
		tokenTable = new int[size][size];
		for (int i = 0; i < s; i++)
			for (int j = 0; j < s; j++)
				tokenTable[i][j] = t[i][j];
		time = 0;
	}
	
	public void calculate(int code)
	{
		if(code == GameHelper.MIN_MAX)
		{
			xPos = ThreadLocalRandom.current().nextInt(0,size);
			yPos = ThreadLocalRandom.current().nextInt(0,size);
			time = 1000;
		}
		else
			if (code == GameHelper.ALFA_BETA)
			{
				xPos = ThreadLocalRandom.current().nextInt(0,size);
				yPos = ThreadLocalRandom.current().nextInt(0,size);
				time = 2000;
			}
	}
	
	public int getXPosition()
	{
		return xPos;
	}
	
	public int getYPosition()
	{
		return yPos;
	}
	
	public long getTime()
	{
		return time;
	}
	
}
