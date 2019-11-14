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

  public PawnBehavior(boolean aIsValid, String aSide, String aStatus)
  {
    isValid = aIsValid;
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

  public boolean initiateDiagonalJump(String side)
  {
    boolean wasEventProcessed = false;
    
    PawnSM aPawnSM = pawnSM;
    switch (aPawnSM)
    {
      case Placed:
        // line 17 "../../../../../PawnStateMachine.ump"
        
        setPawnSM(PawnSM.JumpingDiagonal);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean initiateSorJ(String side)
  {
    boolean wasEventProcessed = false;
    
    PawnSM aPawnSM = pawnSM;
    switch (aPawnSM)
    {
      case Placed:
        if (!(isAJump()))
        {
        // line 19 "../../../../../PawnStateMachine.ump"
          
          setPawnSM(PawnSM.Stepping);
          wasEventProcessed = true;
          break;
        }
        if (isAJump())
        {
        // line 21 "../../../../../PawnStateMachine.ump"
          
          setPawnSM(PawnSM.JumpingStraight);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean dropPawn()
  {
    boolean wasEventProcessed = false;
    
    PawnSM aPawnSM = pawnSM;
    switch (aPawnSM)
    {
      case Stepping:
        if (getIsValid())
        {
        // line 26 "../../../../../PawnStateMachine.ump"
          legalMove(false);
          setPawnSM(PawnSM.Placed);
          wasEventProcessed = true;
          break;
        }
        if (!getIsValid())
        {
        // line 29 "../../../../../PawnStateMachine.ump"
          illegalMove();
          setPawnSM(PawnSM.Placed);
          wasEventProcessed = true;
          break;
        }
        break;
      case JumpingStraight:
        if (getIsValid())
        {
        // line 36 "../../../../../PawnStateMachine.ump"
          legalMove(true);
          setPawnSM(PawnSM.Placed);
          wasEventProcessed = true;
          break;
        }
        if (!getIsValid())
        {
        // line 39 "../../../../../PawnStateMachine.ump"
          illegalMove();
          setPawnSM(PawnSM.Placed);
          wasEventProcessed = true;
          break;
        }
        break;
      case JumpingDiagonal:
        if (getIsValid())
        {
        // line 45 "../../../../../PawnStateMachine.ump"
          legalMove(true);
          setPawnSM(PawnSM.Placed);
          wasEventProcessed = true;
          break;
        }
        if (!getIsValid())
        {
        // line 49 "../../../../../PawnStateMachine.ump"
          illegalMove();
          setPawnSM(PawnSM.Placed);
          wasEventProcessed = true;
          break;
        }
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
        // line 25 "../../../../../PawnStateMachine.ump"
        isValid = isLegalStep();
        break;
      case JumpingStraight:
        // line 35 "../../../../../PawnStateMachine.ump"
        isValid = isLegalJump();
        break;
      case JumpingDiagonal:
        // line 44 "../../../../../PawnStateMachine.ump"
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
   * Returns the current row number of the pawn
   */
  // line 57 "../../../../../PawnStateMachine.ump"
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
  // line 70 "../../../../../PawnStateMachine.ump"
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
  // line 81 "../../../../../PawnStateMachine.ump"
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
  // line 94 "../../../../../PawnStateMachine.ump"
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
  // line 105 "../../../../../PawnStateMachine.ump"
  public boolean isLegalStep(){
    int opRow = getOpponentPawnRow();
    	int opCol = getOpponentPawnColumn();
    	
    	int curRow = getCurrentPawnRow();
		int curCol = getCurrentPawnColumn();
		
		//String opSide = isOpponentAdjacent(curRow,curCol,opRow,opCol);
    	
    	Boolean walla= isWallBlocking(curRow,curCol,this.side,true);  //if adjacent wall
    	if (walla){
    		return false;
    	}
    	
    	return true;
  }


  /**
   * Returns if it is legal to jump in the given direction
   */
  // line 123 "../../../../../PawnStateMachine.ump"
  public boolean isLegalJump(){
    int opRow = getOpponentPawnRow();
    	int opCol = getOpponentPawnColumn();
    	
    	int curRow = getCurrentPawnRow();
		int curCol = getCurrentPawnColumn();
		
		String opSide = isOpponentAdjacent(curRow,curCol,opRow,opCol);
    	Boolean wallb= isWallBlocking(curRow,curCol,opSide,false); 	//wall adjacent to opponent
    	Boolean walla= isWallBlocking(curRow,curCol,this.side,true);  //if adjacent wall
		System.out.print(" opside="+opSide);
		System.out.print(" wallb="+wallb);
    	System.out.print(" walla="+walla);
		if ((wallb)||(walla)){
    		return false;
    	}
		else if(this.side.compareTo(opSide)==0){
			return true;
		} 
		
		else {
			return false;
		}
  }

  // line 148 "../../../../../PawnStateMachine.ump"
  public boolean isLegalJumpDiag(){
    int opRow = getOpponentPawnRow();
    	int opCol = getOpponentPawnColumn();
    	
    	int curRow = getCurrentPawnRow();
    	int curCol = getCurrentPawnColumn();
    	
    	String opSide = isOpponentAdjacent(curRow,curCol,opRow,opCol);
    	Boolean wallb= isWallBlocking(curRow,curCol,opSide,false); 
    	Boolean walla= isWallBlocking(curRow,curCol,opSide,true);  //if adjacent wall
    	Boolean border= isBorderBlocking(opRow,opCol,opSide);
    	System.out.print(" opside="+opSide);
    	System.out.print(" border="+border);
    	System.out.print(" wallb="+wallb);
    	System.out.print(" walla="+walla);
    	if ((!border)&&(!wallb)){
    		return false;
    	}
    	else if (walla){
    		return false;
    	}
		else if(this.side.compareTo("upleft")==0){
			if((opSide.compareTo("up")==0) || opSide.compareTo("left")==0){
				return true;
			}
		} 
		
		else if(this.side.compareTo("upright")==0){
			if((opSide.compareTo("up")==0) || opSide.compareTo("right")==0){
				return true;
			}
		} 
		else if(this.side.compareTo("downleft")==0){
			if((opSide.compareTo("down")==0) || opSide.compareTo("left")==0){
				return true;
			}
		} 
		else if(this.side.compareTo("downright")==0){
			if((opSide.compareTo("down")==0) || opSide.compareTo("right")==0){
				return true;
			}
		}
		
    	return false;
  }

  // line 194 "../../../../../PawnStateMachine.ump"
  public boolean isWallBlocking(int curRow, int curCol, String cside, boolean isStep){
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
		
    	if(cside.equals("up")){
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
		else if(cside.equals("right")){
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
		else if(cside.equals("left")){
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
		else if(cside.equals("down")){
			for(Wall w: wWall){
				if (w.getMove().getWallDirection().toString().compareTo("Horizontal")==0){
					if (w.getMove().getTargetTile().getRow()==curRow-1+distance){
						if ((w.getMove().getTargetTile().getColumn()==curCol)||(w.getMove().getTargetTile().getColumn()==curCol-1)){
							return true;
						}
					}
				}
			}
			for(Wall w: bWall){
				if (w.getMove().getWallDirection().toString().compareTo("Horizontal")==0){
					if (w.getMove().getTargetTile().getRow()==curRow-1+distance){
						if ((w.getMove().getTargetTile().getColumn()==curCol)||(w.getMove().getTargetTile().getColumn()==curCol-1)){
							return true;
						}
					}
				}
			}
		}
		
		return false;
  }

  // line 291 "../../../../../PawnStateMachine.ump"
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

  // line 310 "../../../../../PawnStateMachine.ump"
  public Boolean isBorderBlocking(int opRow, int opCol, String opSide){
    if ((opSide.compareTo("up")==0)&&(opRow==1)){
    		return true;
    	}
    	
    	else if ((opSide.compareTo("down")==0)&&(opRow==9)){
    		return true;
    	}
    	
    	else if ((opSide.compareTo("left")==0)&&(opCol==1)){
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
  // line 332 "../../../../../PawnStateMachine.ump"
  public void illegalMove(){
    status="illegal";
  }

  // line 336 "../../../../../PawnStateMachine.ump"
  public void legalMove(boolean isJump){
    status="success";
    	GamePosition curr= currentGame.getCurrentPosition();
    	int curRow = getCurrentPawnRow()-1;
    	int curCol = getCurrentPawnColumn()-1;
		int r=curRow,j=curCol;
		int distance=2;
		if (!isJump){
			distance=1;
		}
		
		if(this.side.compareTo("up")==0){
			r=curRow-distance;
		} 
		
		else if(this.side.compareTo("right")==0){
			j=curCol+distance;
		} 
		else if(this.side.compareTo("left")==0){
			j=curCol-distance;
		} 
		else if(this.side.compareTo("down")==0){
			r=curRow+distance;
		}
		
		else if(this.side.compareTo("upleft")==0){
			r=curRow-1;
			j=curCol-1;
		} 
		
		else if(this.side.compareTo("upright")==0){
			r=curRow-1;
			j=curCol+1;
		} 
		else if(this.side.compareTo("downleft")==0){
			r=curRow+1;
			j=curCol-1;
		} 
		else if(this.side.compareTo("downright")==0){
			r=curRow+1;
			j=curCol+1;
		}
		
		if (player.hasGameAsWhite()) {
			curr.setWhitePosition(new PlayerPosition(player, currentGame.getQuoridor().getBoard().getTile(r*9+j)));
		}
		else {
			curr.setBlackPosition(new PlayerPosition(player, currentGame.getQuoridor().getBoard().getTile(r*9+j)));
		}
  }

  // line 387 "../../../../../PawnStateMachine.ump"
  public Boolean isAJump(){
    int opRow = getOpponentPawnRow();
    	int opCol = getOpponentPawnColumn();
    	
    	int curRow = getCurrentPawnRow();
    	int curCol = getCurrentPawnColumn();
    	String opSide=isOpponentAdjacent(curRow, curCol, opRow, opCol);
		if (this.side.compareTo(opSide)==0){
			return true;
		}
		return false;
  }


  public String toString()
  {
    return super.toString() + "["+
            "isValid" + ":" + getIsValid()+ "," +
            "side" + ":" + getSide()+ "," +
            "status" + ":" + getStatus()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "currentGame = "+(getCurrentGame()!=null?Integer.toHexString(System.identityHashCode(getCurrentGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 402 "../../../../../PawnStateMachine.ump"
  enum MoveDirection 
  {
    East, South, West, North;
  }

  
}