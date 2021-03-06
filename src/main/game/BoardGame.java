package main.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import main.game.Pieces.*;

public class BoardGame implements Serializable
{
	public static void main(String[] args)
	{
		BoardGame boardGame = getBoardGame();
		boardGame.randFillInBoardGame();
		BoardGame bob = getBoardGame();
		bob.printTab();
		Position p = new Position(6,1);
		System.out.println(bob.canMove(bob.BOARD[3][0].position, p));
	}
	
	public static int LENGTHX;
	public static int LENGTHY;
	public Piece[][] BOARD;
	int LEFT;
	int RIGHT;
	int DOWN;
	int UP;
	public Team teamRed;
	public Team teamBlue;
	
	
	private static BoardGame instance = null;
	
	public static BoardGame getBoardGame()
	{
		if (instance == null)
			instance =  new BoardGame();
		return instance;
	}
	
	public BoardGame()
	{
		LEFT = 1;
		RIGHT = 2;
		DOWN = 3;
		UP = 4;
		LENGTHX = 10;
		LENGTHY = 10;
		BOARD = createBoardGame(LENGTHX,LENGTHY);
		teamRed = new Team(Team.RED);
		teamBlue = new Team(Team.BLUE);
	}	
	
	public static Piece[][] createBoardGame(int lengthX, int lengthY)
	{
		Piece[][] list = new Piece[lengthX][lengthY];
		list[2][4] = new Lake(2,4);
		list[3][4] = new Lake(3,4);
		list[2][5] = new Lake(2,5);
		list[3][5] = new Lake(3,5);
		list[7][4] = new Lake(7,4);
		list[7][5] = new Lake(7,5);
		list[6][5] = new Lake(6,5);
		list[6][4] = new Lake(6,4);
		
		return list;
	}
	
	private void printTab()
	{ 
		for(int i=0; i < BOARD.length; i++)
		{
			for(int j=0; j < BOARD[0].length;j ++)
			{
				if (BOARD[i][j] != null)
					System.out.print(" "+BOARD[i][j].NAME +" | "+BOARD[i][j].position.positionX+ ","+BOARD[i][j].position.positionY +" || ");	
				else 
					System.out.print(" X"+" | "+i+","+j+" || ");
			}
			System.out.println();
		}
	}
	
	public void setBoardGame(Piece[][] pieceTab){
		BOARD = pieceTab;
	}

	public static BoardGame boardGame = getBoardGame();
	
	public void fillIn(Piece p, int x, int y)
	{
			Piece piece = p.construct();
			piece.setPosition(x, y);
			piece.setTeam(Team.RED);
			BOARD[x][y] = piece;
	}
	
	public boolean isEmpty(int x, int y)
	{
		if (BOARD[x][y] == null)
			return true;
		return false;		
	}
	
	
	public void setMoveCharacter(Position p, Position a)
	{
				Piece move = BOARD[p.positionX][p.positionY];
				BOARD[p.positionX][p.positionY] = null;
				BOARD[a.positionX][a.positionY] = move;
				move.setPosition(a.positionX,a.positionY);
	}
	

	public static int countNumber(Piece[] list)
	{
		int count = 0;
		for(int i = 0; i<list.length; i++)
		{
			count += list[i].NUMBER;
		}
		return count;
	}
			
	public void swap(Position p, Position d)
	{
		Piece swap = BOARD[p.positionX][p.positionY];
		Position A = p;
		Position B = d;
		BOARD[p.positionX][p.positionY] = BOARD[d.positionX][d.positionY];
		BOARD[d.positionX][d.positionY] = swap;
		BOARD[p.positionX][p.positionY].setPosition(A.positionX,A.positionY);
		BOARD[d.positionX][d.positionY].setPosition(B.positionX,B.positionY);	
	}
	
	
	
	public void fillInBoardGame(Piece[] list)
	{
		
		int x=0;
		int numberPiece = 0;
		for(int k = 0; k < 4; k++)
		{
			for(int i = 0; i < BOARD.length; i++)
			{
				Piece currentPiece =list[x];
				if(numberPiece<currentPiece.NUMBER)
				{
					fillIn(currentPiece, i, k);
					numberPiece++;
				}
				if(numberPiece==currentPiece.NUMBER)
				{
					teamRed.charachter[currentPiece.ref].currentNumber=numberPiece;
					x++;
					numberPiece=0;
					
				}
			}
		}			
	}			 
	
	public void randFillInBoardGame()
	{
		Piece[] list = teamRed.charachter;
		fillInBoardGame(list);
		for(int k = 0; k < 4; k++)
		{
			for(int i = 0; i < BOARD.length; i++)
			{
				int randX = (int)(Math.random()*10);
				int randY = (int)(Math.random()*4);
				Piece p = BOARD[i][k];
				Piece l = BOARD[randX][randY];
				swap(p.position,l.position);
			}
		}
		int count = 0;
			for(int m = 0; m < BOARD[3].length ; m ++)
			{	
				if (BOARD[3][m] instanceof Bomb)
					count ++;
			}
			if (count == 6)
				randFillInBoardGame();	
	}
	
	public Piece[][] helpList() 
	{
		Piece[][] list = new Piece[LENGTHX+2][LENGTHY+2];
		
		for(int i = 0; i <BOARD.length; i++)
		{
			for(int j = 0; j < BOARD[0].length; j++)
			{
				list[i+1][j+1] = BOARD[i][j];
			}
		}
		return list;
	}
	
	
	
	public boolean isNextToSomeone(Position p)  
	{
		Piece[][] list = helpList();
			
		if (list[(p.positionX+1)+1][(p.positionY+1)] != null)
			return true;
		if (list[(p.positionX+1)-1][(p.positionY+1)] != null)
			return true;
		if (list[(p.positionX+1)][(p.positionY+1)+1] != null)
			return true;
		if (list[(p.positionX+1)][(p.positionY+1)-1] != null)
			return true;
		else;
			return false;
	}			
	
	public boolean isNextToSide(Position p) 
	{
		if(BOARD[p.positionX][p.positionY].position.positionX == 0)
			return true;
		else if(BOARD[p.positionX][p.positionY].position.positionY == 0)
			return true;
		else if(BOARD[p.positionX][p.positionY].position.positionX == (BOARD.length-1))
			return true;
		else if(BOARD[p.positionX][p.positionY].position.positionY ==(BOARD[0].length-1))
			return true;
		return false;	
	}
	
	public boolean isNextToCorner(Position p)
	{
		if((BOARD[p.positionX][p.positionY].position.positionX == 0)&&(BOARD[p.positionX][p.positionY].position.positionY == 0))
			return true;
		else if((BOARD[p.positionX][p.positionY].position.positionX == 0)&&(BOARD[p.positionX][p.positionY].position.positionY == (BOARD[0].length-1)))
			return true;
		else if((BOARD[p.positionX][p.positionY].position.positionX == (BOARD.length-1))&&(BOARD[p.positionX][p.positionY].position.positionY == 0))
			return true;
		else if((BOARD[p.positionX][p.positionY].position.positionX == (BOARD.length-1))&&(BOARD[p.positionX][p.positionY].position.positionY == (BOARD[0].length-1)))
			return true;
		return false;		
	}
	
	public int numberFriendsNextTo(Position p) 
	{
		Piece[][] list = helpList();
	
		int i = 0;
		
		if (isNextToSomeone(p) == false)
			return i;
		else
		{	
			if (list[(p.positionX+1)][(p.positionY+1)].sameTeam(list[(p.positionX+1) + 1][(p.positionY+1)]))
				i++;
			if (list[(p.positionX+1)][(p.positionY+1)].sameTeam(list[(p.positionX+1) - 1][(p.positionY+1)]))
				i++;
			if (list[(p.positionX+1)][(p.positionY+1)].sameTeam(list[(p.positionX+1)][(p.positionY+1) + 1]))
				i++;
			if (list[(p.positionX+1)][(p.positionY+1)].sameTeam(list[(p.positionX+1)][(p.positionY+1) - 1]))
				i++;
		}	
		return i;
	}	
		
	
	public boolean isOneMove(Position p, Position d) 
	{
		if((BOARD[p.positionX][p.positionY].position.positionX - d.positionX) == (-1))
			return true;
		else if((BOARD[p.positionX][p.positionY].position.positionX - d.positionX) == (1))
			return true;
		else if ((BOARD[p.positionX][p.positionY].position.positionY - d.positionY) == (-1))
			return true;
		else if ((BOARD[p.positionX][p.positionY].position.positionY - d.positionY) == (1))
			return true;
		return false;
				
	}	
	
	
	public boolean isDirectionInLine(Position p, Position d)
	{
		if ((p.positionX == d.positionX) || (p.positionY == d.positionY))
			return true;
		else; 
			return false;
	}

	public int returnDirection(Position p, Position d)
	{
		if (p.positionX == d.positionX)
		{
			if (p.positionY > d.positionY)
			{					
				return LEFT;
			}
			else;
			{	
				return RIGHT ;
			}	
		}
		else if(p.positionY == d.positionY)
		{	
			if(p.positionX > d.positionX)
			{
				return DOWN;
			}
			else;
			{
				return UP;
			}
		}
		return 0;
	}
	
	public boolean canMove(Position p)
	{
		if (BOARD[p.positionX][p.positionY].MOVE == 0)
			   return false;
		else if (numberFriendsNextTo(p) == 4)
			return false;
		
		else if (isNextToCorner(p) && numberFriendsNextTo(p) == 2)
			return false;
		
		else if (isNextToSide(p) && numberFriendsNextTo(p) == 3)
			return false;
		return true;
	}
	
	public boolean canMove(Position p, Position d) 
	{
		if (isDirectionInLine(p, d)==false)
			return false;
		
		else if (BOARD[p.positionX][p.positionY].MOVE == 0)
			return false;	
	
		else if (numberFriendsNextTo(p) == 4)
			return false;
		
		else if (isNextToCorner(p) && numberFriendsNextTo(p) == 2)
			return false;
		
		else if (isNextToSide(p) && numberFriendsNextTo(p) == 3)
			return false;		
		
		else if (BOARD[p.positionX][p.positionY].sameTeam(BOARD[d.positionX][d.positionY])) 
			return false;
		
		else if (BOARD[p.positionX][p.positionY].MOVE == 1)
		{
			if (isOneMove(p,d))
				return true;
			return false;
		}
		
		
		else if (BOARD[p.positionX][p.positionY].MOVE > 1)
		{	
			if (returnDirection(p, d) == LEFT)
			{
				for(int i = p.positionY; i > d.positionY+1; i--)
				{						
					if(BOARD[p.positionX][i-1]!= null)
						return false;
				}
				return BOARD[p.positionX][p.positionY].sameTeam(BOARD[d.positionX][d.positionY]) == false;
			}	
			else if (returnDirection(p, d) == RIGHT)
			{
				for(int i = p.positionY; i < d.positionY-1; i++)
				{						
					if(BOARD[p.positionX][i+1]!= null)
						return false;
				}
				return (BOARD[p.positionX][p.positionY].sameTeam(BOARD[d.positionX][d.positionY]) == false);
			}
			else if (returnDirection(p, d) == DOWN)
			{
				for(int i = p.positionX; i < d.positionX-1; i++)
				{						
					if (BOARD[i+1][p.positionY]!=null)
						return false;
				}
				return (BOARD[p.positionX][p.positionY].sameTeam(BOARD[d.positionX][d.positionY]) == false);
			}
			
			else if (returnDirection(p, d) == UP)
			{
				for(int i = p.positionX; i > d.positionX+1; i--)
				{				
					if (BOARD[i-1][p.positionY]!=null)
						return false;
				}
				return (BOARD[p.positionX][p.positionY].sameTeam(BOARD[d.positionX][d.positionY]) == false);
			}	
		}
		return false;
	}
	
	private int countPiece(){
		int counter=0;
		for(Piece[] line:BOARD){
			for(Piece piece:line){
				if(piece!=null)
					counter++;
			}
		}
		return counter;
	}
	
	private void writeObject(ObjectOutputStream oos)throws IOException
	, ClassNotFoundException{
		oos.writeInt(countPiece());
		for(Piece[] line:BOARD){
			for(Piece p:line){
				if(p!=null)
					oos.writeObject(p);
			}
		}
		oos.writeObject(teamRed);
		oos.writeObject(teamBlue);
	}
	
	private void readObject(ObjectInputStream ois)throws IOException
	, ClassNotFoundException{
		int numberPiece=ois.readInt();
		BOARD= new Piece[LENGTHY][LENGTHX];
		for(int i=0; i<numberPiece; i++){
			Piece p = (Piece) ois.readObject();
			BOARD[p.position.positionX][p.position.positionY]=p;
		}
		teamRed=(Team) ois.readObject();
		teamBlue=(Team) ois.readObject();
	}
}
