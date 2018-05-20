package app.model;

import java.util.ArrayList;
import java.util.List;

public class MinMaxAlgorithm
{
	private byte currentPlayer;
	private byte pointPlayer;
	
	private int changedX;
	private int changedY;
	
	private byte[][] tokenTable;
	private int size;
	private int freeTokens;
	private int tableValue;
	
	private List<MinMaxAlgorithm> nextPossibilities;
	
	public MinMaxAlgorithm(byte[][] tTable, byte currentPlayer, int size, int treeSize, int freeTokens)
	{
		this.currentPlayer = currentPlayer;
		this.size = size;
		this.freeTokens=freeTokens;
		nextPossibilities = new ArrayList<>(freeTokens);
		
		tokenTable = new byte[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				tokenTable[i][j] = tTable[i][j];
		
		createPossibilities(currentPlayer, size, treeSize-1);
		MinMaxAlgorithm min= getBest(treeSize-1);
		changedX = min.getChangedX();
		changedY = min.getChangedY();
	}
	
	private MinMaxAlgorithm(byte[][] tTable, byte currentPlayer, byte nextPlayer, int x, int y, int size, int treeSize, int freeTokens, int previousTableValue)
	{
		this.size = size;
		changedX = x;
		changedY = y;
		this.currentPlayer = currentPlayer;
		this.freeTokens=freeTokens;
		tableValue = previousTableValue;
		pointPlayer = nextPlayer;
		nextPossibilities = new ArrayList<>(freeTokens);
		
		tokenTable = new byte[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				tokenTable[i][j] = tTable[i][j];
		tokenTable[x][y] = nextPlayer;
		
		calculateValue(currentPlayer);
	}
	
	private void createPossibilities(byte nextPlayer, int size, int treeSize) 
	{
		if(treeSize>1)
		{
			for (int i = 0; i < size; i++)
				for (int j = 0; j < size; j++)
				{
					if (tokenTable[i][j] == GameHelper.NO_PLAYER)
					{
						nextPossibilities.add(new MinMaxAlgorithm(tokenTable, currentPlayer, nextPlayer, i, j, size, treeSize-1, freeTokens-1, tableValue));
					}
				}
		}	
	}
	
	private void calculateValue(int currentPlayer)
	{
		int points = processPoints();
		if (currentPlayer == pointPlayer)
			tableValue -= points;
		else
			tableValue += (points*AlgorithmHelper.AGGRESSIVENESS);
	}
	
	private int processPoints()
	{
		int addScore = 0;
		addScore+= calculatePointsColumn(changedX, changedY);
		addScore+= calculatePointsRow(changedX, changedY);
		addScore+= calculatePointsUpLeftDiagonal(changedX, changedY);
		addScore+= calculatePointsUpRightDiagonal(changedX, changedY);
				
		return addScore;
	}
	
	private int calculatePointsRow(int x, int y)
	{
		int points = 0;
		boolean isContinuous = true;
		
		for (int i=(y-1); i>=0; i--)
		{
			if (tokenTable[x][i]==GameHelper.NO_PLAYER)
				return 0;
			if (tokenTable[x][i]==pointPlayer && isContinuous)
			{
				points++;
			}
			else
				isContinuous = false;
		}
		
		isContinuous = true;
		
		for (int i=y; i<size; i++)
		{
			if (tokenTable[x][i]==GameHelper.NO_PLAYER)
				return 0;
			if (tokenTable[x][i]==pointPlayer && isContinuous)
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
			if (tokenTable[i][y]==GameHelper.NO_PLAYER)
				return 0;
			if (tokenTable[i][y]==pointPlayer && isContinuous)
			{
				points++;
			}
			else
				isContinuous = false;
		}
		
		isContinuous = true;
		
		for (int i=(x+1); i<size; i++)
		{
			if (tokenTable[i][y]==GameHelper.NO_PLAYER)
				return 0;
			if (tokenTable[i][y]==pointPlayer && isContinuous)
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
			if (tokenTable[j][i]==GameHelper.NO_PLAYER)
				return 0;
			if (tokenTable[j][i]==pointPlayer && isContinuous)
			{
				points++;
			}
			else
				isContinuous = false;
		}
		
		isContinuous = true;
		
		for (int i=y+1, j=x+1; i<size && j<size; i++, j++)
		{
			if (tokenTable[j][i]==GameHelper.NO_PLAYER)
				return 0;
			if (tokenTable[j][i]==pointPlayer && isContinuous)
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
			if (tokenTable[j][i]==GameHelper.NO_PLAYER)
				return 0;
			if (tokenTable[j][i]==pointPlayer && isContinuous)
			{
				points++;
			}
			else
				isContinuous = false;
		}
		
		isContinuous = true;
		
		for (int i=y+1, j=x-1; i<size && j>=0; i++, j--)
		{
			if (tokenTable[j][i]==GameHelper.NO_PLAYER)
				return 0;
			if (tokenTable[j][i]==pointPlayer && isContinuous)
			{
				points++;
			}
			else
				isContinuous = false;
		}
		
		return points==1?0:points;
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
	
	public void setChangedX(int x)
	{
		changedX=x;
	}
	
	public void setChangedY(int y)
	{
		changedY=y;
	}
	
	private MinMaxAlgorithm getBest(int treeSize)
	{
		if(nextPossibilities.isEmpty())
			return this;
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
	
	private MinMaxAlgorithm getMin(int treeSize)
	{
		if (pointPlayer == GameHelper.GREEN_PLAYER)
			createPossibilities(GameHelper.RED_PLAYER, size, treeSize);
		else
			createPossibilities(GameHelper.GREEN_PLAYER, size, treeSize);
		
		if(nextPossibilities.isEmpty())
			return this;
		if (treeSize>1)
		{
			int min = Integer.MAX_VALUE;
			MinMaxAlgorithm temp;
			MinMaxAlgorithm minObject = null;
			for (int i=nextPossibilities.size()-1; i>=0; i--)
			{
				temp = nextPossibilities.get(i).getMax(treeSize-1);
				if (temp.getValue() < min)
				{
					min = temp.getValue();
					minObject = temp;
				}
			}
			nextPossibilities.clear();
			minObject.setChangedX(changedX);
			minObject.setChangedY(changedY);
			return minObject;			
		}
		else
			return this;
	}

	private MinMaxAlgorithm getMax(int treeSize)
	{
		if (pointPlayer == GameHelper.GREEN_PLAYER)
			createPossibilities(GameHelper.RED_PLAYER, size, treeSize);
		else
			createPossibilities(GameHelper.GREEN_PLAYER, size, treeSize);
		
		if(nextPossibilities.isEmpty())
			return this;
		if (treeSize>1)
		{
			int max = Integer.MIN_VALUE;
			MinMaxAlgorithm temp;
			MinMaxAlgorithm maxObject = null;
			for (int i=nextPossibilities.size()-1; i>=0; i--)
			{
				temp = nextPossibilities.get(i).getMin(treeSize-1);
				if (temp.getValue() > max)
				{
					max = temp.getValue();
					maxObject = temp;
				}
			}
			nextPossibilities.clear();
			maxObject.setChangedX(changedX);
			maxObject.setChangedY(changedY);
			return maxObject;			
		}
		else
			return this;
	}
	
	
	
}
