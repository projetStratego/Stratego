package main.game.Pieces;


public class General extends Piece 
{
	public General() 
	{	
		NAME = "General";
		RANK = 9;
		NUMBER = 1;
		WEAKNESS = 0;	
		MOVE = 1;		
		currentNumber = 0;
		SEEN = false;
		hasMove = false;
		ref =8;
	}

	public General construct()
	{
		return new General();
	}
	
}
