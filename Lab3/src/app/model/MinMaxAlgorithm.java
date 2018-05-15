package app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MinMaxAlgorithm
{
	private int changedX;
	private int changedY;
	private int tableValue;
	private int[][] tokenTable;
	private List<MinMaxAlgorithm> nextPossibilities;
	
	public MinMaxAlgorithm(int[][] tTable, int currentPlayer, int size, int treeSize)
	{
		treeSize--;
		tokenTable = new int[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				tokenTable[i][j] = tTable[i][j];
		nextPossibilities = new ArrayList<>();
		createPossibilities(currentPlayer, currentPlayer, size, treeSize);
		MinMaxAlgorithm min= getMin(treeSize);
		changedX = min.getChangedX();
		changedY = min.getChangedY();
		System.out.println(changedX + " , " + changedY);
	}
	
	private MinMaxAlgorithm(int[][] tTable, int currentPlayer, int nextPlayer, int x, int y, int size, int treeSize)
	{
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
	
	private void createPossibilities(int currentPlayer, int nextPlayer, int size, int treeSize) 
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
						nextPossibilities.add(new MinMaxAlgorithm(tokenTable, currentPlayer, nextPlayer, i, j, size, treeSize-1));
					}
				}
		}	
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
	
	private MinMaxAlgorithm getMin(int treeSize)
	{
		if (treeSize>1)
		{
			int min = Integer.MAX_VALUE;
			MinMaxAlgorithm minObject = null;
			for (int i=nextPossibilities.size()-1; i>=0; i--)
			{
				MinMaxAlgorithm temp = nextPossibilities.get(i).getMax(treeSize-1);
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

	private MinMaxAlgorithm getMax(int treeSize)
	{
		if (treeSize>1)
		{
			int max = Integer.MIN_VALUE;
			MinMaxAlgorithm maxObject = null;
			for (int i=nextPossibilities.size(); i>0; --i)
			{
				MinMaxAlgorithm temp = nextPossibilities.get(i).getMin(treeSize-1);
				if (temp.getValue() > max)
				{
					max = temp.getValue();
					maxObject = temp;
				}
			}
			return maxObject;			
		}
		else
			return this;
	}
	
	
	
}
