/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.controller;
import ca.mcgill.ecse223.quoridor.model.*;

// line 5 "../../../../../PawnStateMachine.ump"
public class PawnBehavior
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PawnBehavior Attributes
  private StepMove stagedStep;
  private JumpMove stagedJump;
  private boolean isValid;
  private int row;
  private int col;
  private String side;

  //PawnBehavior State Machines
  public enum PawnSM { Placed, Stepping, JumpingStraight, JumpingDiagonal }
  private PawnSM pawnSM;

  //PawnBehavior Associations
  private Game currentGame;
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PawnBehavior(StepMove aStagedStep, JumpMove aStagedJump, boolean aIsValid, int aRow, int aCol, String aSide)
  {
    stagedStep = aStagedStep;
    stagedJump = aStagedJump;
    isValid = aIsValid;
    row = aRow;
    col = aCol;
    side = aSide;
    setPawnSM(PawnSM.Placed);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStagedStep(StepMove aStagedStep)
  {
    boolean wasSet = false;
    stagedStep = aStagedStep;
    wasSet = true;
    return wasSet;
  }

  public boolean setStagedJump(JumpMove aStagedJump)
  {
    boolean wasSet = false;
    stagedJump = aStagedJump;
    wasSet = true;
    return wasSet;
  }

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

  public StepMove getStagedStep()
  {
    return stagedStep;
  }

  public JumpMove getStagedJump()
  {
    return stagedJump;
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
        // line 21 "../../../../../PawnStateMachine.ump"
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
        // line 24 "../../../../../PawnStateMachine.ump"
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
        // line 27 "../../../../../PawnStateMachine.ump"
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
        // line 33 "../../../../../PawnStateMachine.ump"
        updateModel(row,col);				//TODO all of these should have isValid as a barrier
        setPawnSM(PawnSM.Placed);
        wasEventProcessed = true;
        break;
      case JumpingStraight:
        // line 41 "../../../../../PawnStateMachine.ump"
        updateModel(row,col);
        setPawnSM(PawnSM.Placed);
        wasEventProcessed = true;
        break;
      case JumpingDiagonal:
        // line 48 "../../../../../PawnStateMachine.ump"
        updateModel(row,col);
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
        setPawnSM(PawnSM.Placed);
        wasEventProcessed = true;
        break;
      case JumpingStraight:
        setPawnSM(PawnSM.Placed);
        wasEventProcessed = true;
        break;
      case JumpingDiagonal:
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
      case Placed:
        // line 20 "../../../../../PawnStateMachine.ump"
        isValid = false;
        break;
      case Stepping:
        // line 32 "../../../../../PawnStateMachine.ump"
        isValid = isLegalStep();
        break;
      case JumpingStraight:
        // line 40 "../../../../../PawnStateMachine.ump"
        isValid = isLegalJump();
        break;
      case JumpingDiagonal:
        // line 47 "../../../../../PawnStateMachine.ump"
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

  // line 56 "../../../../../PawnStateMachine.ump"
  public void updateModel(int row, int col){
    //TODO either create new game position and set current or update current one
  }


  /**
   * Returns the current row number of the pawn
   */
  // line 60 "../../../../../PawnStateMachine.ump"
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
  // line 73 "../../../../../PawnStateMachine.ump"
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
  // line 84 "../../../../../PawnStateMachine.ump"
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
  // line 97 "../../../../../PawnStateMachine.ump"
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
  // line 108 "../../../../../PawnStateMachine.ump"
  public boolean isLegalStep(){
    int opRow = getOpponentPawnRow();
    	int opCol = getOpponentPawnColumn();
    	
    	int curRow = getCurrentPawnRow();
		int curCol = getCurrentPawnColumn();
		
		String opSide = isOpponentAdjacent(curRow,curCol,opRow,opCol);
    	
    	Boolean walla= wallBlocking(opSide,true);  //if adjacent wall
    	if (walla){
    		return false;
    	}
    	
    	return true;
  }


  /**
   * Returns if it is legal to jump in the given direction
   */
  // line 126 "../../../../../PawnStateMachine.ump"
  public boolean isLegalJump(){
    int opRow = getOpponentPawnRow();
    	int opCol = getOpponentPawnColumn();
    	
    	int curRow = getCurrentPawnRow();
		int curCol = getCurrentPawnColumn();
		
		String opSide = isOpponentAdjacent(curRow,curCol,opRow,opCol);
    	Boolean wallb= wallBlocking(opSide,false); 
    	Boolean walla= wallBlocking(opSide,true);  //if adjacent wall
		
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

  // line 149 "../../../../../PawnStateMachine.ump"
  public boolean isLegalJumpDiag(){
    int opRow = getOpponentPawnRow();
    	int opCol = getOpponentPawnColumn();
    	
    	int curRow = getCurrentPawnRow();
    	int curCol = getCurrentPawnColumn();
    	
    	String opSide = isOpponentAdjacent(curRow,curCol,opRow,opCol);
    	Boolean wallb= wallBlocking(opSide,false); 
    	Boolean walla= wallBlocking(opSide,true);  //if adjacent wall
    	if ((!wallb)||(walla)){
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

  // line 187 "../../../../../PawnStateMachine.ump"
  public void initPawn(int row, int col, String side){
    this.row = row;
		this.col = col;
		this.side = side;
  }

  // line 193 "../../../../../PawnStateMachine.ump"
  public boolean wallBlocking(String side, boolean isStep){
    int curRow = getCurrentPawnRow();
    	int curCol = getCurrentPawnColumn();
    	int distance;
    	if(isStep){
    		distance=1;
    	}
    	else {
    		distance = 2;
    	}
    	
    	if(this.side.equals("up")){
			//TODO checks all walls on board for white then black
			
			GamePosition curr= currentGame.getCurrentPosition();
		
			//List<Wall> wWall = curr.getWhiteWallsOnBoard();
			//List<Wall> bWall = curr.getBlackWallsOnBoard();
		} 
		
		else if(this.side.equals("right")){
			
		} 
		else if(this.side.equals("left")){
			
		} 
		else if(this.side.equals("down")){
			
		}
		
		return false;
  }

  // line 226 "../../../../../PawnStateMachine.ump"
  public String isOpponentAdjacent(int curR, int curC, int oR, int oC){
    if ((curR==oR)&&(curC==oC+1)){
    		return "left";
    	}
    	else if ((curR==oR)&&(curC==oC-1)){
    		return "right";
    	}
    	
    	else if ((curC==oC)&&(curR==oR+1)){
    		return "down";
    	}
    	else if ( (curC==oC)&&(curC==oR-1)){
    		return "up";
    	}
    	else {
    		return "no";
    	}
  }


  /**
   * Action to be called when an illegal move is attempted
   */
  // line 245 "../../../../../PawnStateMachine.ump"
  public void illegalMove(){
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "isValid" + ":" + getIsValid()+ "," +
            "row" + ":" + getRow()+ "," +
            "col" + ":" + getCol()+ "," +
            "side" + ":" + getSide()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "stagedStep" + "=" + (getStagedStep() != null ? !getStagedStep().equals(this)  ? getStagedStep().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "stagedJump" + "=" + (getStagedJump() != null ? !getStagedJump().equals(this)  ? getStagedJump().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentGame = "+(getCurrentGame()!=null?Integer.toHexString(System.identityHashCode(getCurrentGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 250 "../../../../../PawnStateMachine.ump"
  enum MoveDirection 
  {
    East, South, West, North;
  }

  
}