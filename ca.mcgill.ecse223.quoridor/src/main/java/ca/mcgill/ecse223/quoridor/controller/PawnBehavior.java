/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.controller;
import java.util.List;
import ca.mcgill.ecse223.quoridor.model.*;

// line 5 "../../../../../PawnStateMachine.ump"
public class PawnBehavior
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PawnBehavior Attributes
  private boolean isValid;
  private int row;
  private int col;
  private String side;
  private String status;

  //PawnBehavior State Machines
  public enum PawnSM { Placed, Stepping, JumpingStraight, JumpingDiagonal }
  private PawnSM pawnSM;

  //PawnBehavior Associations
  private Game currentGame;
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PawnBehavior(boolean aIsValid, int aRow, int aCol, String aSide, String aStatus)
  {
    isValid = aIsValid;
    row = aRow;
    col = aCol;
    side = aSide;
    status = aStatus;
    setPawnSM(PawnSM.Placed);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsValid(boolean aIsValid)
  {
    boolean wasSet = false;
    isValid = aIsValid;
    wasSet = true;
    return wasSet;
  }

  public boolean setRow(int aRow)
  {
    boolean wasSet = false;
    row = aRow;
    wasSet = true;
    return wasSet;
  }

  public boolean setCol(int aCol)
  {
    boolean wasSet = false;
    col = aCol;
    wasSet = true;
    return wasSet;
  }

  public boolean setSide(String aSide)
  {
    boolean wasSet = false;
    side = aSide;
    wasSet = true;
    return wasSet;
  }

  public boolean setStatus(String aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsValid()
  {
    return isValid;
  }

  public int getRow()
  {
    return row;
  }

  public int getCol()
  {
    return col;
  }

  public String getSide()
  {
    return side;
  }

  public String getStatus()
  {
    return status;
  }

  public String getPawnSMFullName()
  {
    String answer = pawnSM.toString();
    return answer;
  }

  public PawnSM getPawnSM()
  {
    return pawnSM;
  }

  public boolean initiateDiagonalJump(int row,int col,String side)
  {
    boolean wasEventProcessed = false;
    
    PawnSM aPawnSM = pawnSM;
    switch (aPawnSM)
    {
      case Placed:
        // line 18 "../../../../../PawnStateMachine.ump"
        initPawn(row,col,side);
        setPawnSM(PawnSM.JumpingDiagonal);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean initiateStep(int row,int col,String side)
  {
    boolean wasEventProcessed = false;
    
    PawnSM aPawnSM = pawnSM;
    switch (aPawnSM)
    {
      case Placed:
        // line 21 "../../../../../PawnStateMachine.ump"
        initPawn(row,col,side);
        setPawnSM(PawnSM.Stepping);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean initiateStraightJump(int row,int col,String side)
  {
    boolean wasEventProcessed = false;
    
    PawnSM aPawnSM = pawnSM;
    switch (aPawnSM)
    {
      case Placed:
        // line 24 "../../../../../PawnStateMachine.ump"
        initPawn(row,col,side);
        setPawnSM(PawnSM.JumpingStraight);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean dropPawn(int row,int col)
  {
    boolean wasEventProcessed = false;
    
    PawnSM aPawnSM = pawnSM;
    switch (aPawnSM)
    {
      case Stepping:
        // line 30 "../../../../../PawnStateMachine.ump"
        updateModel(row,col);				//TODO all of these should have isValid as a barrier
    			legalMove();
        setPawnSM(PawnSM.Placed);
        wasEventProcessed = true;
        break;
      case JumpingStraight:
        // line 41 "../../../../../PawnStateMachine.ump"
        updateModel(row,col);
    			legalMove();
        setPawnSM(PawnSM.Placed);
        wasEventProcessed = true;
        break;
      case JumpingDiagonal:
        // line 51 "../../../../../PawnStateMachine.ump"
        updateModel(row,col);
    			legalMove();
        setPawnSM(PawnSM.Placed);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean cancel()
  {
    boolean wasEventProcessed = false;
    
    PawnSM aPawnSM = pawnSM;
    switch (aPawnSM)
    {
      case Stepping:
        // line 34 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSM(PawnSM.Placed);
        wasEventProcessed = true;
        break;
      case JumpingStraight:
        // line 45 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSM(PawnSM.Placed);
        wasEventProcessed = true;
        break;
      case JumpingDiagonal:
        // line 55 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSM(PawnSM.Placed);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setPawnSM(PawnSM aPawnSM)
  {
    pawnSM = aPawnSM;

    // entry actions and do activities
    switch(pawnSM)
    {
      case Stepping:
        // line 29 "../../../../../PawnStateMachine.ump"
        isValid = isLegalStep();
        break;
      case JumpingStraight:
        // line 40 "../../../../../PawnStateMachine.ump"
        isValid = isLegalJump();
        break;
      case JumpingDiagonal:
        // line 50 "../../../../../PawnStateMachine.ump"
        isValid = isLegalJumpDiag();
        break;
    }
  }
  /* Code from template association_GetOne */
  public Game getCurrentGame()
  {
    return currentGame;
  }

  public boolean hasCurrentGame()
  {
    boolean has = currentGame != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }

  public boolean hasPlayer()
  {
    boolean has = player != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setCurrentGame(Game aNewCurrentGame)
  {
    boolean wasSet = false;
    currentGame = aNewCurrentGame;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setPlayer(Player aNewPlayer)
  {
    boolean wasSet = false;
    player = aNewPlayer;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    currentGame = null;
    player = null;
  }


  /**
   * TODO account for diagonal jumps via wall
   */
  // line 64 "../../../../../PawnStateMachine.ump"
  public void updateModel(int row, int col){
    status="success";
		//TODO either create new game position and set current or update current one
  }


  /**
   * Returns the current row number of the pawn
   */
  // line 69 "../../../../../PawnStateMachine.ump"
  public int getCurrentPawnRow(){
    GamePosition pos = currentGame.getCurrentPosition();
    	if (player.hasGameAsWhite()){
	    	return pos.getWhitePosition().getTile().getRow();
	    }
	    else{
	    	return pos.getBlackPosition().getTile().getRow();
	    }
  }


  /**
   * Returns the current column number of the pawn
   */
  // line 82 "../../../../../PawnStateMachine.ump"
  public int getCurrentPawnColumn(){
    GamePosition pos=currentGame.getCurrentPosition();
    	if (player.hasGameAsWhite()){
	    	return pos.getWhitePosition().getTile().getColumn();
	    }
	    else{
	    	return pos.getBlackPosition().getTile().getColumn();
	    }
  }


  /**
   * Returns the current row number of the pawn
   */
  // line 93 "../../../../../PawnStateMachine.ump"
  public int getOpponentPawnRow(){
    GamePosition pos=currentGame.getCurrentPosition();
    	if (player.hasGameAsWhite()){
	    	return pos.getBlackPosition().getTile().getRow();
	    }
	    else{
	    	return pos.getWhitePosition().getTile().getRow();
	    }
  }


  /**
   * Returns the current column number of the pawn
   */
  // line 106 "../../../../../PawnStateMachine.ump"
  public int getOpponentPawnColumn(){
    GamePosition pos=currentGame.getCurrentPosition();
    	if (player.hasGameAsWhite()){
	    	return pos.getBlackPosition().getTile().getColumn();
	    }
	    else{
	    	return pos.getWhitePosition().getTile().getColumn();
	    }
  }


  /**
   * Returns if it is legal to step in the given direction
   */
  // line 117 "../../../../../PawnStateMachine.ump"
  public boolean isLegalStep(){
    int opRow = getOpponentPawnRow();
    	int opCol = getOpponentPawnColumn();
    	
    	int curRow = getCurrentPawnRow();
		int curCol = getCurrentPawnColumn();
		
		String opSide = isOpponentAdjacent(curRow,curCol,opRow,opCol);
    	
    	Boolean walla= isWallBlocking(curRow,curCol,opSide,true);  //if adjacent wall
    	if (walla){
    		return false;
    	}
    	
    	return true;
  }


  /**
   * Returns if it is legal to jump in the given direction
   */
  // line 135 "../../../../../PawnStateMachine.ump"
  public boolean isLegalJump(){
    int opRow = getOpponentPawnRow();
    	int opCol = getOpponentPawnColumn();
    	
    	int curRow = getCurrentPawnRow();
		int curCol = getCurrentPawnColumn();
		
		String opSide = isOpponentAdjacent(curRow,curCol,opRow,opCol);
    	Boolean wallb= isWallBlocking(opRow,opCol,opSide,false); 	//wall adjacent to opponent
    	Boolean walla= isWallBlocking(curRow,curCol,opSide,true);  //if adjacent wall
		
		if ((!wallb)||(walla)){
    		return false;
    	}
		else if(this.side.compareTo(opSide)==0){
			return true;
		} 
		
		else {
			return false;
		}
  }

  // line 158 "../../../../../PawnStateMachine.ump"
  public boolean isLegalJumpDiag(){
    int opRow = getOpponentPawnRow();
    	int opCol = getOpponentPawnColumn();
    	
    	int curRow = getCurrentPawnRow();
    	int curCol = getCurrentPawnColumn();
    	
    	String opSide = isOpponentAdjacent(curRow,curCol,opRow,opCol);
    	Boolean wallb= isWallBlocking(curRow,curRow,opSide,false); 
    	Boolean walla= isWallBlocking(curRow,curCol,opSide,true);  //if adjacent wall
    	Boolean border= isBorderBlocking(opRow,opCol,opSide);
    	if (((!border)&&(!wallb))||(walla)){
    		return false;
    	}
		else if(this.side.equals("upleft")){
			if((opSide.equals("up")) || opSide.equals("left")){
				return true;
			}
		} 
		
		else if(this.side.equals("upright")){
			if((opSide.equals("up")) || opSide.equals("right")){
				return true;
			}
		} 
		else if(this.side.equals("downleft")){
			if((opSide.equals("down")) || opSide.equals("left")){
				return true;
			}
		} 
		else if(this.side.equals("downright")){
			if((opSide.equals("down")) || opSide.equals("right")){
				return true;
			}
		}
		
    	return false;
  }

  // line 197 "../../../../../PawnStateMachine.ump"
  public void initPawn(int row, int col, String side){
    this.row = row;
		this.col = col;
		this.side = side;
  }

  // line 203 "../../../../../PawnStateMachine.ump"
  public boolean isWallBlocking(int curRow, int curCol, String side, boolean isStep){
    int distance;
    	if(isStep){
    		distance=1;
    	}
    	else {
    		distance = 2;
    	}
    	GamePosition curr= currentGame.getCurrentPosition();
		
		List<Wall> wWall = curr.getWhiteWallsOnBoard();
		List<Wall> bWall = curr.getBlackWallsOnBoard();
		
    	if(this.side.equals("up")){
			for(Wall w: wWall){
				if (w.getMove().getWallDirection().toString().compareTo("Horizontal")==0){
					if (w.getMove().getTargetTile().getRow()==curRow-distance){
						if ((w.getMove().getTargetTile().getColumn()==curCol)||(w.getMove().getTargetTile().getColumn()==curCol-1)){
							return true;
						}
					}
				}
			}
			for(Wall w: bWall){
				if (w.getMove().getWallDirection().toString().compareTo("Horizontal")==0){
					if (w.getMove().getTargetTile().getRow()==curRow-distance){
						if ((w.getMove().getTargetTile().getColumn()==curCol)||(w.getMove().getTargetTile().getColumn()==curCol-1)){
							return true;
						}
					}
				}
			}
		}
		else if(this.side.equals("right")){
			for(Wall w: wWall){
				if (w.getMove().getWallDirection().toString().compareTo("Vertical")==0){
					if (w.getMove().getTargetTile().getColumn()==curCol-1+distance){
						if ((w.getMove().getTargetTile().getRow()==curRow)||(w.getMove().getTargetTile().getRow()==curRow-1)){
							return true;
						}
					}
				}
			}
			for(Wall w: bWall){
				if (w.getMove().getWallDirection().toString().compareTo("Vertical")==0){
					if (w.getMove().getTargetTile().getColumn()==curCol-1+distance){
						if ((w.getMove().getTargetTile().getRow()==curRow)||(w.getMove().getTargetTile().getRow()==curRow-1)){
							return true;
						}
					}
				}
			}
		} 
		else if(this.side.equals("left")){
			for(Wall w: wWall){
				if (w.getMove().getWallDirection().toString().compareTo("Vertical")==0){
					if (w.getMove().getTargetTile().getColumn()==curCol-distance){
						if ((w.getMove().getTargetTile().getRow()==curRow)||(w.getMove().getTargetTile().getRow()==curRow-1)){
							return true;
						}
					}
				}
			}
			for(Wall w: bWall){
				if (w.getMove().getWallDirection().toString().compareTo("Vertical")==0){
					if (w.getMove().getTargetTile().getColumn()==curCol-distance){
						if ((w.getMove().getTargetTile().getRow()==curRow)||(w.getMove().getTargetTile().getRow()==curRow-1)){
							return true;
						}
					}
				}
			}
		} 
		else if(this.side.equals("down")){
			for(Wall w: wWall){
				if (w.getMove().getWallDirection().toString().compareTo("Horizontal")==0){
					if (w.getMove().getTargetTile().getRow()==curRow+1-distance){
						if ((w.getMove().getTargetTile().getColumn()==curCol)||(w.getMove().getTargetTile().getColumn()==curCol-1)){
							return true;
						}
					}
				}
			}
			for(Wall w: bWall){
				if (w.getMove().getWallDirection().toString().compareTo("Horizontal")==0){
					if (w.getMove().getTargetTile().getRow()==curRow+1-distance){
						if ((w.getMove().getTargetTile().getColumn()==curCol)||(w.getMove().getTargetTile().getColumn()==curCol-1)){
							return true;
						}
					}
				}
			}
		}
		
		return false;
  }

  // line 300 "../../../../../PawnStateMachine.ump"
  public String isOpponentAdjacent(int curR, int curC, int oR, int oC){
    if ((curR==oR)&&(curC==oC+1)){
    		return "left";
    	}
    	else if ((curR==oR)&&(curC==oC-1)){
    		return "right";
    	}
    	
    	else if ((curC==oC)&&(curR==oR+1)){
    		return "up";
    	}
    	else if ( (curC==oC)&&(curR==oR-1)){
    		return "down";
    	}
    	else {
    		return "no";
    	}
  }

  // line 319 "../../../../../PawnStateMachine.ump"
  public Boolean isBorderBlocking(int opRow, int opCol, String opSide){
    if ((opSide.compareTo("up")==0)&&(opRow==1)){
    		return true;
    	}
    	
    	else if ((opSide.compareTo("down")==0)&&(opRow==9)){
    		return true;
    	}
    	
    	else if ((opSide.compareTo("left")==0)&&(opCol==0)){
    		return true;
    	}
    	
    	else if ((opSide.compareTo("right")==0)&&(opCol==9)){
    		return true;
    	}
    	else {
    		return false;
    	}
  }


  /**
   * Action to be called when an illegal move is attempted
   */
  // line 341 "../../../../../PawnStateMachine.ump"
  public void illegalMove(){
    status="illegal";
  }

  // line 345 "../../../../../PawnStateMachine.ump"
  public void legalMove(){
    status="success";
		isValid=true;
  }


  public String toString()
  {
    return super.toString() + "["+
            "isValid" + ":" + getIsValid()+ "," +
            "row" + ":" + getRow()+ "," +
            "col" + ":" + getCol()+ "," +
            "side" + ":" + getSide()+ "," +
            "status" + ":" + getStatus()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "currentGame = "+(getCurrentGame()!=null?Integer.toHexString(System.identityHashCode(getCurrentGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 352 "../../../../../PawnStateMachine.ump"
  enum MoveDirection 
  {
    East, South, West, North;
  }

  
}