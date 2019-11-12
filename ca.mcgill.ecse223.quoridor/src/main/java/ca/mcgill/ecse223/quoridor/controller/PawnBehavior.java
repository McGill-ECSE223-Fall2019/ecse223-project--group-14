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

  //PawnBehavior State Machines
  public enum PawnSM { Placed, StagedMove }
  private PawnSM pawnSM;

  //PawnBehavior Associations
  private Game currentGame;
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PawnBehavior(StepMove aStagedStep, JumpMove aStagedJump)
  {
    stagedStep = aStagedStep;
    stagedJump = aStagedJump;
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

  public StepMove getStagedStep()
  {
    return stagedStep;
  }

  public JumpMove getStagedJump()
  {
    return stagedJump;
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

  public boolean initiateStep(MoveDirection dir)
  {
    boolean wasEventProcessed = false;
    
    PawnSM aPawnSM = pawnSM;
    switch (aPawnSM)
    {
      case Placed:
        if (isLegalStep(dir))
        {
        // line 15 "../../../../../PawnStateMachine.ump"
          stageStepMove(dir);
          setPawnSM(PawnSM.StagedMove);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean cancelStage()
  {
    boolean wasEventProcessed = false;
    
    PawnSM aPawnSM = pawnSM;
    switch (aPawnSM)
    {
      case StagedMove:
        setPawnSM(PawnSM.Placed);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean completeMove()
  {
    boolean wasEventProcessed = false;
    
    PawnSM aPawnSM = pawnSM;
    switch (aPawnSM)
    {
      case StagedMove:
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
  // line 37 "../../../../../PawnStateMachine.ump"
  public int getCurrentPawnRow(){
    GamePosition pos = QuoridorApplication.getQuoridor().getCurrentPosition();
    	Player currentPlayer = pos.getPlayerToMove();
    	if (currentPlayer.hasGameAsWhite())(
	    	return pos.getWhitePosition().getTile().getRow();
	    else{
	    	return pos.getBlackPosition().getTile().getRow();
	    }
  }


  /**
   * Returns the current column number of the pawn
   */
  // line 50 "../../../../../PawnStateMachine.ump"
  public int getCurrentPawnColumn(){
    GamePosition pos = QuoridorApplication.getQuoridor().getCurrentPosition();
    	Player currentPlayer = pos.getPlayerToMove();
    	if (currentPlayer.hasGameAsWhite())(
	    	return pos.getWhitePosition().getTile().getCol();
	    else{
	    	return pos.getBlackPosition().getTile().getCol();
	    }
  }


  /**
   * Returns if it is legal to step in the given direction
   */
  // line 59 "../../../../../PawnStateMachine.ump"
  public boolean isLegalStep(MoveDirection dir){
    return false;
  }


  /**
   * Returns if it is legal to jump in the given direction
   */
  // line 62 "../../../../../PawnStateMachine.ump"
  public boolean isLegalJump(MoveDirection dir){
    int row = getCurrentPawnRow();
		int col = getCurrentPawnCol();
		
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Board board = quoridor.getBoard();
		
		if(dir == MoveDirection.North){
			boolean destExists = (board.getTile(getIndex(row-2, col)) != -1);
			boolean opponentJumpable = (getOpponentPawnTile().equals(getTile(getIndex(row-1, col))));
		
			// Check if jump destination even exists
			if(destExists && opponentJumpable &&){
			}
		} else if(dir == MoveDirection.South){
			stagedStep = new StepMove(row+1, col, ...);
		} else if(dir == MoveDirection.West){
			stagedStep = new StepMove(row, col-1, ...);
		} else if(dir == MoveDirection.East){
			stagedStep = new StepMove(row, col+1, ...);
		}
		
    	return false;
  }

  // line 87 "../../../../../PawnStateMachine.ump"
  public void stageStepMove(String dir){
    int row = getCurrentPawnRow();
		int col = getCurrentPawnCol();
		
		if(dir == MoveDirection.North){
			stagedStep = new StepMove(row-1, col, ...);
		} else if(dir == MoveDirection.South){
			stagedStep = new StepMove(row+1, col, ...);
		} else if(dir == MoveDirection.West){
			stagedStep = new StepMove(row, col-1, ...);
		} else if(dir == MoveDirection.East){
			stagedStep = new StepMove(row, col+1, ...);
		}
  }

  // line 102 "../../../../../PawnStateMachine.ump"
  public void stageJumpMove(String dir){
    int row = getCurrentPawnRow();
		int col = getCurrentPawnCol();
		
		if(dir == MoveDirection.North){
			stagedStep = new StepMove(row-2, col, ...);
		} else if(dir == MoveDirection.South){
			stagedStep = new StepMove(row+2, col, ...);
		} else if(dir == MoveDirection.West){
			stagedStep = new StepMove(row, col-2, ...);
		} else if(dir == MoveDirection.East){
			stagedStep = new StepMove(row, col+2, ...);
		}
  }

  // line 117 "../../../../../PawnStateMachine.ump"
  public void cancelStage(){
    stagedStep = null;
		stagedJump = null;
  }


  /**
   * Action to be called when an illegal move is attempted
   */
  // line 122 "../../../../../PawnStateMachine.ump"
  public void illegalMove(){
    
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "stagedStep" + "=" + (getStagedStep() != null ? !getStagedStep().equals(this)  ? getStagedStep().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "stagedJump" + "=" + (getStagedJump() != null ? !getStagedJump().equals(this)  ? getStagedJump().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentGame = "+(getCurrentGame()!=null?Integer.toHexString(System.identityHashCode(getCurrentGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 126 "../../../../../PawnStateMachine.ump"
  enum MoveDirection 
  {
    East, South, West, North;
  }

  
}