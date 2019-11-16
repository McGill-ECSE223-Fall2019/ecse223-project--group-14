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
  public enum StatusSM { NextToBorderOrWall, NextToPlayer, NextToBorderOrWallAndPlayer, Default }
  private StatusSM statusSM;

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
    setStatusSM(StatusSM.NextToBorderOrWall);
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

  public String getStatusSMFullName()
  {
    String answer = statusSM.toString();
    return answer;
  }

  public PawnSM getPawnSM()
  {
    return pawnSM;
  }

  public StatusSM getStatusSM()
  {
    return statusSM;
  }

  public boolean initiate(String cside)
  {
    boolean wasEventProcessed = false;
    
    PawnSM aPawnSM = pawnSM;
    switch (aPawnSM)
    {
      case Placed:
        if (isDiag(cside))
        {
        // line 18 "../../../../../PawnStateMachine.ump"
          this.side=cside;
          setPawnSM(PawnSM.JumpingDiagonal);
          wasEventProcessed = true;
          break;
        }
        if (!(isAJump(cside))&&!(isDiag(cside)))
        {
        // line 21 "../../../../../PawnStateMachine.ump"
          this.side=cside;
          setPawnSM(PawnSM.Stepping);
          wasEventProcessed = true;
          break;
        }
        if (isAJump(cside)&&!(isDiag(cside)))
        {
        // line 24 "../../../../../PawnStateMachine.ump"
          this.side=cside;
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
        // line 30 "../../../../../PawnStateMachine.ump"
          legalMove(false);
          setPawnSM(PawnSM.Placed);
          wasEventProcessed = true;
          break;
        }
        if (!getIsValid())
        {
        // line 33 "../../../../../PawnStateMachine.ump"
          illegalMove();
          setPawnSM(PawnSM.Placed);
          wasEventProcessed = true;
          break;
        }
        break;
      case JumpingStraight:
        if (getIsValid())
        {
        // line 40 "../../../../../PawnStateMachine.ump"
          legalMove(true);
          setPawnSM(PawnSM.Placed);
          wasEventProcessed = true;
          break;
        }
        if (!getIsValid())
        {
        // line 43 "../../../../../PawnStateMachine.ump"
          illegalMove();
          setPawnSM(PawnSM.Placed);
          wasEventProcessed = true;
          break;
        }
        break;
      case JumpingDiagonal:
        if (getIsValid())
        {
        // line 49 "../../../../../PawnStateMachine.ump"
          legalMove(true);
          setPawnSM(PawnSM.Placed);
          wasEventProcessed = true;
          break;
        }
        if (!getIsValid())
        {
        // line 53 "../../../../../PawnStateMachine.ump"
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

  public boolean change()
  {
    boolean wasEventProcessed = false;
    
    StatusSM aStatusSM = statusSM;
    switch (aStatusSM)
    {
      case NextToBorderOrWall:
        if (!(isWOrBAdjacent())&&(isOpponentAdjacent().compareTo("no")!=0))
        {
          setStatusSM(StatusSM.NextToPlayer);
          wasEventProcessed = true;
          break;
        }
        if (isWOrBAdjacent()&&(isOpponentAdjacent().compareTo("no")!=0))
        {
          setStatusSM(StatusSM.NextToBorderOrWallAndPlayer);
          wasEventProcessed = true;
          break;
        }
        if (!(isWOrBAdjacent())&&(isOpponentAdjacent().compareTo("no")==0))
        {
          setStatusSM(StatusSM.Default);
          wasEventProcessed = true;
          break;
        }
        break;
      case NextToPlayer:
        if (isWOrBAdjacent()&&(isOpponentAdjacent().compareTo("no")==0))
        {
          setStatusSM(StatusSM.NextToBorderOrWall);
          wasEventProcessed = true;
          break;
        }
        if (isWOrBAdjacent()&&(isOpponentAdjacent().compareTo("no")!=0))
        {
          setStatusSM(StatusSM.NextToBorderOrWallAndPlayer);
          wasEventProcessed = true;
          break;
        }
        if (!(isWOrBAdjacent())&&(isOpponentAdjacent().compareTo("no")==0))
        {
          setStatusSM(StatusSM.Default);
          wasEventProcessed = true;
          break;
        }
        break;
      case NextToBorderOrWallAndPlayer:
        if (isWOrBAdjacent()&&(isOpponentAdjacent().compareTo("no")==0))
        {
          setStatusSM(StatusSM.NextToBorderOrWall);
          wasEventProcessed = true;
          break;
        }
        break;
      case Default:
        if (isWOrBAdjacent()&&(isOpponentAdjacent().compareTo("no")==0))
        {
          setStatusSM(StatusSM.NextToBorderOrWall);
          wasEventProcessed = true;
          break;
        }
        if (!(isWOrBAdjacent())&&(isOpponentAdjacent().compareTo("no")!=0))
        {
          setStatusSM(StatusSM.NextToPlayer);
          wasEventProcessed = true;
          break;
        }
        if (isWOrBAdjacent()&&(isOpponentAdjacent().compareTo("no")!=0))
        {
          setStatusSM(StatusSM.NextToBorderOrWallAndPlayer);
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
        // line 29 "../../../../../PawnStateMachine.ump"
        isValid = isLegalStep();
        break;
      case JumpingStraight:
        // line 39 "../../../../../PawnStateMachine.ump"
        isValid = isLegalJump();
        break;
      case JumpingDiagonal:
        // line 48 "../../../../../PawnStateMachine.ump"
        isValid = isLegalJumpDiag();
        break;
    }
  }

  private void setStatusSM(StatusSM aStatusSM)
  {
    statusSM = aStatusSM;
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
  // line 87 "../../../../../PawnStateMachine.ump"
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
  // line 100 "../../../../../PawnStateMachine.ump"
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
  // line 111 "../../../../../PawnStateMachine.ump"
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
  // line 124 "../../../../../PawnStateMachine.ump"
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
  // line 136 "../../../../../PawnStateMachine.ump"
  public boolean isLegalStep(){
    int curRow = getCurrentPawnRow();
		int curCol = getCurrentPawnColumn();
    	
    	Boolean walla= isWallBlocking(curRow,curCol,this.side,true);  //if adjacent wall
    	if (walla){
    		return false;
    	}
    	
    	return true;
  }


  /**
   * Returns if it is legal to jump in the given direction
   */
  // line 150 "../../../../../PawnStateMachine.ump"
  public boolean isLegalJump(){
    int curRow = getCurrentPawnRow();
		int curCol = getCurrentPawnColumn();
		
		String opSide = isOpponentAdjacent();
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

  // line 174 "../../../../../PawnStateMachine.ump"
  public boolean isLegalJumpDiag(){
    int curRow = getCurrentPawnRow();
    	int curCol = getCurrentPawnColumn();
    	int opRow = getOpponentPawnRow();
    	int opCol = getOpponentPawnColumn();
    	
    	String opSide = isOpponentAdjacent();
    	String otherSide= this.side.replace(opSide,"");
    	
    	Boolean wallb= isWallBlocking(curRow,curCol,opSide,false); //if wall blocking straight jump
    	Boolean walla= isWallBlocking(curRow,curCol,opSide,true);  //if adjacent wall
    	Boolean wallc= isWallBlocking(opRow,opCol,otherSide,true);  //if wall blocking second half of diagonal jump 
    	Boolean border= isBorderBlocking(opRow,opCol,opSide);
    	System.out.print(" opside="+opSide);
    	System.out.print(" border="+border);
    	System.out.print(" wallb="+wallb);
    	System.out.print(" walla="+walla);
    	if ((!border)&&(!wallb)){
    		return false;
    	}
    	else if ((walla)||(wallc)){
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


  /**
   * 
   * @author DariusPi
   * 
   * Determines if there is a wall or directly next to the player in any direction
   * 
   */
  // line 228 "../../../../../PawnStateMachine.ump"
  public boolean isWOrBAdjacent(){
    int curRow = getCurrentPawnRow();
    	int curCol = getCurrentPawnColumn();
    	
    	if (isWallBlocking(curRow,curCol,"up",true)){
    		return true;
    	}
    	
    	else if (isWallBlocking(curRow,curCol,"left",true)){
    		return true;
    	}
    	
    	else if (isWallBlocking(curRow,curCol,"right",true)){
    		return true;
    	}
    	
    	else if (isWallBlocking(curRow,curCol,"down",true)){
    		return true;
    	}
    	
    	else if (isBorderBlocking(curRow,curCol,"up")){
    		return true;
    	}
    	
    	else if (isBorderBlocking(curRow,curCol,"left")){
    		return true;
    	}
    	
    	else if (isBorderBlocking(curRow,curCol,"right")){
    		return true;
    	}
    	
    	else if (isBorderBlocking(curRow,curCol,"down")){
    		return true;
    	}
    	return false;
  }


  /**
   * 
   * @author DariusPi
   * 
   * Determines if there is a wall either next to the player or 2 away from the player for jumps
   * 
   */
  // line 272 "../../../../../PawnStateMachine.ump"
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


  /**
   * 
   * @author DariusPi
   * 
   * Guard returns side that opponent is on if any
   */
  // line 374 "../../../../../PawnStateMachine.ump"
  public String isOpponentAdjacent(){
    int oR = getOpponentPawnRow();
    	int oC = getOpponentPawnColumn();
    	
    	int curR = getCurrentPawnRow();
    	int curC = getCurrentPawnColumn();
    	
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


  /**
   * 
   * @author DariusPi
   * 
   * Guard returns if board border is blocking move
   */
  // line 404 "../../../../../PawnStateMachine.ump"
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
  // line 426 "../../../../../PawnStateMachine.ump"
  public void illegalMove(){
    status="illegal";
  }


  /**
   * 
   * @author DariusPi
   * 
   * Guard returns if desired step move is legal
   */
  // line 435 "../../../../../PawnStateMachine.ump"
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
			if (isJump){
				currentGame.addMove(new JumpMove(currentGame.numberOfPositions(), 0, player, currentGame.getQuoridor().getBoard().getTile(r*9+j),currentGame));
			}
			else {
				currentGame.addMove(new StepMove(currentGame.numberOfPositions(), 0, player, currentGame.getQuoridor().getBoard().getTile(r*9+j),currentGame));
			}
		}
		else {
			curr.setBlackPosition(new PlayerPosition(player, currentGame.getQuoridor().getBoard().getTile(r*9+j)));
			if (isJump){
				currentGame.addMove(new JumpMove(currentGame.numberOfPositions(), 1, player, currentGame.getQuoridor().getBoard().getTile(r*9+j),currentGame));
			}
			else {
				currentGame.addMove(new StepMove(currentGame.numberOfPositions(), 1, player, currentGame.getQuoridor().getBoard().getTile(r*9+j),currentGame));
			}
		}
		
		change();
  }


  /**
   * 
   * @author DariusPi
   * 
   * Guard returns if desired move is a straight jump
   */
  // line 505 "../../../../../PawnStateMachine.ump"
  public Boolean isAJump(String cside){
    String opSide=isOpponentAdjacent();
		if (cside.compareTo(opSide)==0){
			return true;
		}
		return false;
  }


  /**
   * 
   * @author DariusPi
   * 
   * Guard returns if desired move is a diagonal jump
   */
  // line 519 "../../../../../PawnStateMachine.ump"
  public Boolean isDiag(String cside){
    if(cside.compareTo("upleft")==0){
			return true;
		} 
		
		else if(cside.compareTo("upright")==0){
			return true;
		} 
		else if(cside.compareTo("downleft")==0){
			return true;
		} 
		else if(cside.compareTo("downright")==0){
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
  
  // line 538 "../../../../../PawnStateMachine.ump"
  enum MoveDirection 
  {
    East, South, West, North;
  }

  
}