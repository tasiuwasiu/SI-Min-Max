package app.model;

public class AlgorithmHelper
{
	private int size;
	private int[][] tokenTable;
	private int treeSize;
	
	private int currentPlayer;
	private int xPos;
	private int yPos;
	
	private long time;
	
	public AlgorithmHelper()
	{
		currentPlayer = GameHelper.NO_PLAYER;
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
		treeSize = 3;
	}
	
	public void calculate(int code)
	{
		if(code == GameHelper.MIN_MAX)
		{
			long start = System.currentTimeMillis(); 
			MinMaxAlgorithm minMaxAlgorithm = new MinMaxAlgorithm(tokenTable, currentPlayer, size, treeSize);
			xPos = minMaxAlgorithm.getChangedX();
			yPos = minMaxAlgorithm.getChangedY();
			long stop = System.currentTimeMillis();
			time = stop-start;
		}
		else
			if (code == GameHelper.ALFA_BETA)
			{
				long start = System.currentTimeMillis();
				AlphaBetaAlgorithm alphaBetaAlgorithm = new AlphaBetaAlgorithm(tokenTable, currentPlayer, size, treeSize);
				xPos = alphaBetaAlgorithm.getChangedX();
				yPos = alphaBetaAlgorithm.getChangedY();
				long stop = System.currentTimeMillis();
				time = stop-start;
			}
	}
	
	public void setTreeSize(int s)
	{
		if (s>0)
			treeSize=s;
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
