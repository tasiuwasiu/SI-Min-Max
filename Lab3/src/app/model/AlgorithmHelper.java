package app.model;

public class AlgorithmHelper
{
	public static final int AGGRESSIVENESS = 2;
	
	private int size;
	private byte[][] tokenTable;
	private int treeSize=3;
	
	private byte currentPlayer;
	private int xPos;
	private int yPos;
	
	private long time;
	
	public AlgorithmHelper()
	{
		currentPlayer = GameHelper.NO_PLAYER;
	}
	
	public void setData(byte[][] t, byte p, int s)
	{
		size = s;
		currentPlayer = p;
		time = 0;
		
		tokenTable = new byte[size][size];
		for (int i = 0; i < s; i++)
			for (int j = 0; j < s; j++)
				tokenTable[i][j] = t[i][j];
	}
	
	public void calculate(int code, int freeTokens)
	{
		if(code == GameHelper.MIN_MAX)
		{
			long start = System.currentTimeMillis(); 
			MinMaxAlgorithm minMaxAlgorithm = new MinMaxAlgorithm(tokenTable, currentPlayer, size, treeSize, freeTokens);
			xPos = minMaxAlgorithm.getChangedX();
			yPos = minMaxAlgorithm.getChangedY();
			long stop = System.currentTimeMillis();
			time = stop-start;
		}
		else
			if (code == GameHelper.ALFA_BETA)
			{
				long start = System.currentTimeMillis();
				AlphaBetaAlgorithm alphaBetaAlgorithm = new AlphaBetaAlgorithm(tokenTable, currentPlayer, size, treeSize, freeTokens);
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
