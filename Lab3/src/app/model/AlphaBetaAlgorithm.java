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
	
	public AlphaBetaAlgorithm(int[][] tTable, int currentPlayer, int size, int treeSize)
	{
		tokenTable = new int[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				tokenTable[i][j] = tTable[i][j];
		createPossibilities(currentPlayer, currentPlayer, size, treeSize);
		AlphaBetaAlgorithm min= getMin(treeSize);
		changedX = min.getChangedX();
		changedY = min.getChangedY();
	}
	
	public AlphaBetaAlgorithm(int[][] tTable, int currentPlayer, int nextPlayer, int x, int y, int size, int treeSize)
	{
		changedX = x;
		changedY = y;
		tokenTable = new int[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				tokenTable[i][j] = tTable[i][j];
		tokenTable[x][y] = nextPlayer;
		calculateValue(currentPlayer);
		nextPossibilities = new ArrayList<>();
		if (nextPlayer == GameHelper.GREEN_PLAYER)
			createPossibilities(currentPlayer, GameHelper.RED_PLAYER, size, treeSize);
		else
			createPossibilities(currentPlayer, GameHelper.GREEN_PLAYER, size, treeSize);
		
	}
	
	public void createPossibilities(int currentPlayer, int nextPlayer, int size, int treeSize) 
	{
		if(treeSize<0)
			return;

		
	}
	
	private void calculateValue(int currentPlayer)
	{
		// TODO value calc
		tableValue = ThreadLocalRandom.current().nextInt(0,10);
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
	
	public AlphaBetaAlgorithm getMin(int treeSize)
	{
		if (treeSize>1)
		{
			int min = Integer.MAX_VALUE;
			int index = 0;
			for (int i=nextPossibilities.size(); i>=0; --i)
			{
				int temp = nextPossibilities.get(i).getMax(treeSize-1).getValue();
				if (temp < min)
				{
					min = temp;
					index = i;
				}
			}
			return nextPossibilities.get(index);			
		}
		else
			return this;
	}

	public AlphaBetaAlgorithm getMax(int treeSize)
	{
		if (treeSize>1)
		{
			int max = Integer.MIN_VALUE;
			int index = 0;
			for (int i=nextPossibilities.size(); i>=0; --i)
			{
				int temp = nextPossibilities.get(i).getMin(treeSize-1).getValue();
				if (temp > max)
				{
					max = temp;
					index = i;
				}
			}
			return nextPossibilities.get(index);			
		}
		else
			return this;
	}
	
	
	
}
