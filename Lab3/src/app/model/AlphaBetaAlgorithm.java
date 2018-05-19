package app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AlphaBetaAlgorithm
{
	private int changedX;
	private int changedY;
	private int tableValue;
	private int[][] tokenTable;
	private List<AlphaBetaAlgorithm> nextPossibilities;
	private int alpha;
	private int beta;
	
	public AlphaBetaAlgorithm(int[][] tTable, int currentPlayer, int size, int treeSize)
	{
		alpha = Integer.MIN_VALUE;
		beta = Integer.MAX_VALUE;
		treeSize--;
		tokenTable = new int[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				tokenTable[i][j] = tTable[i][j];
		nextPossibilities = new ArrayList<>();
		createPossibilities(currentPlayer, currentPlayer, size, treeSize);
		AlphaBetaAlgorithm min= getBest(treeSize);
		changedX = min.getChangedX();
		changedY = min.getChangedY();
		System.out.println(changedX + " , " + changedY);
	}
	
	private AlphaBetaAlgorithm(int[][] tTable, int currentPlayer, int nextPlayer, int x, int y, int size, int treeSize, int alpha, int beta)
	{
		this.alpha = alpha;
		this.beta = beta;
		changedX = x;
		changedY = y;
		tokenTable = new int[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				tokenTable[i][j] = tTable[i][j];
		tokenTable[x][y] = nextPlayer;
		
		nextPossibilities = new ArrayList<>();
		if (nextPlayer == GameHelper.GREEN_PLAYER)
			createPossibilities(currentPlayer, GameHelper.RED_PLAYER, size, treeSize);
		else
			createPossibilities(currentPlayer, GameHelper.GREEN_PLAYER, size, treeSize);
		
	}
	
	public void createPossibilities(int currentPlayer, int nextPlayer, int size, int treeSize) 
	{
		if(treeSize==1)
			calculateValue(currentPlayer);
		else
		{
			for (int i = 0; i < size; i++)
				for (int j = 0; j < size; j++)
				{
					if (tokenTable[i][j] == GameHelper.NO_PLAYER)
					{
						nextPossibilities.add(new AlphaBetaAlgorithm(tokenTable, currentPlayer, nextPlayer, i, j, size, treeSize-1, alpha, beta));
					}
				}
		}	
	}
	
	private void calculateValue(int currentPlayer)
	{
		try
		{
			Thread.sleep(10);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tableValue = ThreadLocalRandom.current().nextInt(500);
		alpha=tableValue;
		beta=tableValue;
	}
	
	public int getValue()
	{
		return tableValue;
	}
	
	public int getChangedX()
	{
		return changedX;
	}
	
	public int getChangedY()
	{
		return changedY;
	}
	
	public int getAlpha()
	{
		return alpha;
	}
	
	public int getBeta()
	{
		return beta;
	}
	
	public void setChangedX(int x)
	{
		changedX=x;
	}
	
	public void setChangedY(int y)
	{
		changedY=y;
	}

	private AlphaBetaAlgorithm getBest(int treeSize)
	{
		if (treeSize>1)
		{
			int min = Integer.MAX_VALUE;
			AlphaBetaAlgorithm minObject = null;
			for (int i=nextPossibilities.size()-1; i>=0; i--)
			{
				AlphaBetaAlgorithm temp = nextPossibilities.get(i).getMax(treeSize-1);
				if (temp.getValue() < min)
				{
					min = temp.getValue();
					minObject = temp;
				}
			}
			return minObject;			
		}
		else
			return this;
	}
	
	public AlphaBetaAlgorithm getMin(int treeSize)
	{
		if (treeSize>1)
		{
			int min = Integer.MAX_VALUE;
			AlphaBetaAlgorithm minObject = null;
			for (int i=nextPossibilities.size()-1; i>=0; i--)
			{
				AlphaBetaAlgorithm temp = nextPossibilities.get(i).getMax(treeSize-1);
				if (temp.getValue() < min)
				{
					alpha = temp.getBeta();
					min = temp.getValue();
					minObject = temp;
				}
				if (alpha>=beta)
					return minObject;
			}
			minObject.setChangedX(changedX);
			minObject.setChangedY(changedY);
			return minObject;			
		}
		else
			return this;
	}

	public AlphaBetaAlgorithm getMax(int treeSize)
	{
		if (treeSize>1)
		{
			int max = Integer.MIN_VALUE;
			AlphaBetaAlgorithm maxObject = null;
			for (int i=nextPossibilities.size(); i>0; --i)
			{
				AlphaBetaAlgorithm temp = nextPossibilities.get(i).getMin(treeSize-1);
				if (temp.getValue() > max)
				{
					beta = temp.getAlpha();
					max = temp.getValue();
					maxObject = temp;
				}
				
				if(alpha>=beta)
					return maxObject;
			}
			maxObject.setChangedX(changedX);
			maxObject.setChangedY(changedY);
			return maxObject;			
		}
		else
			return this;
	}
	
	
	
}
