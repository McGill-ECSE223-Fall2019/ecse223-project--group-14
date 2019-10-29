package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.sql.Time;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.GameController;
import ca.mcgill.ecse223.quoridor.model.Quoridor;

public class QuoridorPage extends JFrame{

	/**
	 * default 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel errorMessage;
	private String error = null;
	
	private JLabel turnMessage1;
	private JLabel turnMessage2;
	
	private JLabel title;
	private JLabel bannerMessage;
	private String banner = "Main Menu";
	
	private JTextField p1NameField;
	private JLabel p1Name;
	
	private JTextField p2NameField;
	private JLabel p2Name;
	
	private JTextField minField;
	private JTextField secField;
	private JLabel timeRem1;
	private JLabel timeRem2;
	
	private JTextField saveField;
	private JTextField loadField;
	
	
	private JButton newGameButton;
	private JButton createP1Button;
	private JButton createP2Button;
	private JButton selectP1Button;
	private JButton selectP2Button;
	private JButton timeSetButton;
	
	
	private JButton saveGameButton;
	private JButton loadGameButton;
	private JButton saveFileButton;
	private JButton loadFileButton;
	
	private JButton resignGameButton;
	private JButton drawGameButton;
	
	private JButton acceptDrawButton;
	private JButton declineDrawButton;
	
	private JButton replayGameButton;
	
	private JButton stepForwardButton;
	private JButton stepBackwardButton;
	private JButton jumpStartButton;
	private JButton jumpEndButton;
	private JButton quitButton;
	
	private JButton endTurnButton;
	
	/*private JButton upButton;
	private JButton rightButton;
	private JButton downButton;
	private JButton leftButton;*/
	
	private final int buttonH=30;
	private final int buttonW=125;
	
	private boolean currPlayer;	//true for white, false for black
	
	private Timer timer;
	
	private Quoridor q;
	private GameController gc;
	
	private TileComponent [][] tiles;
	private WallComponent [] bwalls;
	private WallComponent [] wwalls;
	
	private PawnComponent wPawn;
	private PawnComponent bPawn;
	
	public QuoridorPage(){
		q=QuoridorApplication.getQuoridor();
		gc=new GameController();
		gc.initQuorridor();
		
		QuoridorMouseListener listener = new QuoridorMouseListener(this);
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
        
		initComponents();
		refreshData();
	}

	private void initComponents() {
		
		setSize(650, 800);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		
		
		currPlayer=true;
		
		turnMessage1= new JLabel();
		turnMessage1.setForeground(Color.RED);
		turnMessage1.setText("YOUR TURN!");
		turnMessage1.setVisible(false);
		
		turnMessage2= new JLabel();
		turnMessage2.setForeground(Color.RED);
		turnMessage2.setText("YOUR TURN!");
		turnMessage2.setVisible(false);
		
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		errorMessage.setText("");
		
		p1NameField=new JTextField();
		p1NameField.setVisible(false);
		p1Name=new JLabel();
		p1Name.setText("");
		p1Name.setVisible(false);
		
		p2NameField=new JTextField();
		p2NameField.setVisible(false);
		p2Name=new JLabel();
		p2Name.setText("");
		p2Name.setVisible(false);
		
		minField=new JTextField();
		minField.setVisible(false);
		secField=new JTextField();
		secField.setVisible(false);
		timeRem1=new JLabel();
		timeRem1.setText("");
		timeRem1.setVisible(false);
		timeRem2=new JLabel();
		timeRem2.setText("");
		timeRem2.setVisible(false);
		
		saveField=new JTextField();
		saveField.setVisible(false);
		loadField=new JTextField();
		loadField.setVisible(false);
		
		title = new JLabel();
		title.setFont(new Font("Serif",Font.PLAIN,36));
		title.setText("Quoridor");
		
		bannerMessage = new JLabel();
		bannerMessage.setFont(new Font("Serif",Font.PLAIN,26));
		bannerMessage.setForeground(Color.BLUE);
		bannerMessage.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
		bannerMessage.setText(banner);
		
		initButtons();
		addListners();
		
		ActionListener count=new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				boolean over=gc.countdown(q);
				if (over) {
					finishGame();
				}
				else {
					if (q.getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsBlack()) {
						Time t=q.getCurrentGame().getBlackPlayer().getRemainingTime();
						timeRem2.setText(convT2S(t));
					}
					else {
						Time t=q.getCurrentGame().getWhitePlayer().getRemainingTime();
						timeRem1.setText(convT2S(t));
					}
				}
			}
		};
		timer=new Timer(1000,count);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Quoridor");
		
		tiles= new TileComponent[9][9];
		for (int i=0;i<9;i++) {
			for (int j=0;j<9;j++) {
				tiles[i][j]=new TileComponent();
			}
		}
		bwalls= new WallComponent[10];
		for (int i=0;i<10;i++) {
			bwalls[i]=new WallComponent(Color.BLACK);
		}
		
		wwalls= new WallComponent[10];
		for (int i=0;i<10;i++) {
			wwalls[i]=new WallComponent(Color.WHITE);
		}
		
		wPawn=new PawnComponent(Color.WHITE);
		bPawn=new PawnComponent(Color.BLACK);
		
		wPawn.setBounds(157, 417, 25, 25);
		bPawn.setBounds(557, 417, 25, 25);
		add(wPawn);
		add(bPawn);
		wPawn.setVisible(true);
		bPawn.setVisible(true);
		
		//wwalls[0].contains(5, 5); can be used to determine valid wall placement
		
		toggleBoard(false);
		getContentPane().setLayout(null);
		int y=260;
		
		title.setBounds(10, 10, 600, 40);
		add(title);
		bannerMessage.setBounds(10, 60, 600, 40);
		add(bannerMessage);
		errorMessage.setBounds(10, 110, 600, 15);
		add(errorMessage);
		p1Name.setBounds(10, 125, 600, 15);
		add(p1Name);
		timeRem1.setBounds(10, 140, 600, 15);
		add(timeRem1);
		turnMessage1.setBounds(10, 165, 100, 15);
		add(turnMessage1);
		
		newGameButton.setBounds(10, y, buttonW, buttonH);
		add(newGameButton);
		loadGameButton.setBounds(10, y+30, buttonW, buttonH);
		add(loadGameButton);
		saveGameButton.setBounds(10, y, buttonW, buttonH);
		add(saveGameButton);
		resignGameButton.setBounds(10, y+60, buttonW, buttonH);
		add(resignGameButton);
		drawGameButton.setBounds(10, y+90, buttonW, buttonH);
		add(drawGameButton);
		
		stepForwardButton.setBounds(10, y, buttonW, buttonH);
		add(stepForwardButton);
		stepBackwardButton.setBounds(10, y+30, buttonW, buttonH);
		add(stepBackwardButton);
		jumpStartButton.setBounds(10, y+60, buttonW, buttonH);
		add(jumpStartButton);
		jumpEndButton.setBounds(10, y+90, buttonW, buttonH);
		add(jumpEndButton);
		quitButton.setBounds(10, y+120, buttonW, buttonH);
		add(quitButton);
		
		/*upButton.setFont(new Font("Serif",Font.PLAIN,11));
		upButton.setBounds(35, y+180, 68, buttonH);
		add(upButton);
		rightButton.setFont(new Font("Serif",Font.PLAIN,11));
		rightButton.setBounds(80, y+210, 68, buttonH);
		add(rightButton);
		downButton.setFont(new Font("Serif",Font.PLAIN,11));
		downButton.setBounds(35, y+240, 68, buttonH);
		add(downButton);
		leftButton.setFont(new Font("Serif",Font.PLAIN,11));
		leftButton.setBounds(10, y+210, 68, buttonH);
		add(leftButton);*/
		
		acceptDrawButton.setBounds(10, y, buttonW, buttonH);
		add(acceptDrawButton);
		declineDrawButton.setBounds(10, y+30, buttonW, buttonH);
		add(declineDrawButton);
		
		replayGameButton.setBounds(10, y, buttonW, buttonH);
		add(replayGameButton);
		
		endTurnButton.setBounds(10, y-30, buttonW, buttonH);		//could be placed better
		add(endTurnButton);
		
		p1NameField.setBounds(10, 160, 200, buttonH);
		add(p1NameField);
		
		p2NameField.setBounds(10, 160, 200, buttonH);
		add(p2NameField);
		
		minField.setBounds(10, 160, buttonW, buttonH);
		add(minField);
		
		secField.setBounds(140, 160, buttonW, buttonH);
		add(secField);
		
		createP1Button.setBounds(270, 160, buttonW, buttonH);
		add(createP1Button);
		
		selectP1Button.setBounds(400, 160, buttonW, buttonH);
		add(selectP1Button);
		
		createP2Button.setBounds(270, 160, buttonW, buttonH);
		add(createP2Button);
		
		selectP2Button.setBounds(400, 160, buttonW, buttonH);
		add(selectP2Button);
		
		timeSetButton.setBounds(270, 160, buttonW, buttonH);
		add(timeSetButton);
		
		saveField.setBounds(10, 160, 200, buttonH);
		add(saveField);
		
		saveFileButton.setBounds(270, 160, buttonW, buttonH);
		add(saveFileButton);
		
		loadField.setBounds(10, 160, 200, buttonH);
		add(loadField);
		
		loadFileButton.setBounds(270, 160, buttonW, buttonH);
		add(loadFileButton);
		
		
		p2Name.setBounds(10, 675, 600, 15);
		add(p2Name);
		timeRem2.setBounds(10, 690, 600, 15);
		add(timeRem2);
		turnMessage2.setBounds(10, 715, 100, 15);
		add(turnMessage2);
		
		for (int i=0;i<10;i++) {
			wwalls[i].setBounds(150+(WallComponent.wallW+10)*i, 125, WallComponent.wallW, WallComponent.wallH);
			add(wwalls[i]);
			bwalls[i].setBounds(150+(WallComponent.wallW+10)*i, 675, WallComponent.wallW, WallComponent.wallH);
			add(bwalls[i]);
		}
		
		for (int i=0;i<9;i++) {
			for (int j=0;j<9;j++) {
				tiles[i][j].setBounds(150+50*i,210+50*j, 40, 40);
				add(tiles[i][j]);
			}
		}
		
		
	}

	private void refreshData() {
		errorMessage.setText(error);
		bannerMessage.setText(banner);
		if (error == null || error.length() == 0) {
			//update
		}
		
	}
	
	
	private void finishGame() {
		//set screen to results
		
		//TODO
		timer.stop();
		timeRem1.setVisible(false);
		timeRem2.setVisible(false);
		banner="Game Over";
		newGameButton.setVisible(false);
		saveGameButton.setVisible(false);
		loadGameButton.setVisible(false);
		resignGameButton.setVisible(false);
		drawGameButton.setVisible(false);
		
		acceptDrawButton.setVisible(false);
		declineDrawButton.setVisible(false);
		
		replayGameButton.setVisible(true);
		quitButton.setVisible(true);
		
		toggleBoard(false);
		
		refreshData();
	}
	
	private void newGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error = "";
		
		newGameButton.setVisible(false);
		loadGameButton.setVisible(false);
		
		// call the controller
		gc.initGame(q);
		
		p1NameField.setVisible(true);
		createP1Button.setVisible(true);
		selectP1Button.setVisible(true);
		
		
		banner="New Game";
		/*try {
			gc.initGame();
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}*/
		// update visuals
		
		refreshData();
	}
	
	private void createP1ButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error="";
		String success= gc.createUsername(q,p1NameField.getText(),"white");
		if (success.compareTo(p1NameField.getText())==0) {
			p1NameField.setVisible(false);
			p2NameField.setVisible(true);
			createP1Button.setVisible(false);
			createP2Button.setVisible(true);
			selectP1Button.setVisible(false);
			selectP2Button.setVisible(true);
			
			p1Name.setText(success);
			refreshData();
			
		}
		else {
			error=success;
			p1NameField.setText("");
			refreshData();
		}
	}
	
	private void createP2ButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error="";
		String success= gc.createUsername(q,p2NameField.getText(),"black");
		if (success.compareTo(p2NameField.getText())==0) {
			p2NameField.setVisible(false);
			createP2Button.setVisible(false);
			selectP2Button.setVisible(false);
			minField.setVisible(true);
			secField.setVisible(true);
			timeSetButton.setVisible(true);
			
			p2Name.setText(success);
			refreshData();
			
		}
		else {
			error=success;
			p2NameField.setText("");
			refreshData();
		}
	}
	
	private void selectP1ButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error="";
		String success= gc.selectUsername(q,p1NameField.getText(),"white");
		if (success.compareTo(p1NameField.getText())==0) {
			p1NameField.setVisible(false);
			p2NameField.setVisible(true);
			createP1Button.setVisible(false);
			createP2Button.setVisible(true);
			selectP1Button.setVisible(false);
			selectP2Button.setVisible(true);
			
			p1Name.setText(success);
			refreshData();
			
		}
		else {
			error=success;
			p1NameField.setText("");
			refreshData();
		}
	}
	
	private void selectP2ButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error="";
		String success= gc.selectUsername(q,p2NameField.getText(),"black");
		if (success.compareTo(p2NameField.getText())==0) {
			p2NameField.setVisible(false);
			createP2Button.setVisible(false);
			selectP2Button.setVisible(false);
			minField.setVisible(true);
			secField.setVisible(true);
			timeSetButton.setVisible(true);
			
			p2Name.setText(success);
			refreshData();
			
		}
		else {
			error=success;
			p2NameField.setText("");
			refreshData();
		}
	}
	
	private void timeSetButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error="";
		//uncomment once implemented
		try {
			gc.setTime(q,Integer.parseInt(minField.getText()),Integer.parseInt(secField.getText()));
		} catch (Exception e) {
			error ="invalid input";
		}
		
		if (error.compareTo("")==0) {
			minField.setVisible(false);
			secField.setVisible(false);
			timeSetButton.setVisible(false);
			
			newGameButton.setVisible(false);
			
			toggleMainButtons(true);
			toggleBoard(true);
			banner="GamePlay";
			
			Time t=q.getCurrentGame().getBlackPlayer().getRemainingTime();
			timeRem1.setText(convT2S(t));
			timeRem2.setText(convT2S(t));
			timeRem1.setVisible(true);
			
			p1Name.setVisible(true);
			p2Name.setVisible(true);
			
			gc.startTheClock(q,timer);
			
			refreshData();
			
		}
		else {
			secField.setText("");
			minField.setText("");
			refreshData();
		}
	}
	
	
	private void saveGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		toggleBoard(false);
		toggleMainButtons(false);
		
		saveFileButton.setVisible(true);
		saveField.setVisible(true);
		banner = "Save Game";
		refreshData();
	}
	
	private void saveFileButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		//toggleBoard(false); to be implemented
		
		GameController gc= new GameController();
		error = "";
		//TODO
		
		// update visuals
		banner = "GamePlay"; 
		saveFileButton.setVisible(false);
		saveField.setVisible(false);
		toggleMainButtons(true);
		toggleBoard(true);
		refreshData();
	}
	
	private void loadGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		newGameButton.setVisible(false);
		toggleBoard(false); 
		toggleMainButtons(false);
		loadFileButton.setVisible(true);
		loadField.setVisible(true);
		
		// update visuals
		banner = "Load Game"; 
		refreshData();
	}
	
	private void loadFileButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		//toggleBoard(false); to be implemented
		
		GameController gc= new GameController();
		error = "";
		
		//TODO
		//reset view with new loaded file
		//p1Name
		//p2Name
		//remTime?
		
		
		// update visuals
		banner = "GamePlay"; 
		loadFileButton.setVisible(false);
		loadField.setVisible(false);
		toggleMainButtons(true);
		toggleBoard(true);
		
		
		
		refreshData();
	}
	
	private void resignGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
		
		finishGame();
	}
	
	private void drawGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		toggleBoard(false);
		GameController gc= new GameController();		
		error = "";
		
		toggleMainButtons(false);
		
		acceptDrawButton.setVisible(true);
		declineDrawButton.setVisible(true);
		
		// update visuals
		banner = "Draw Proposal"; //for testing
		refreshData();
	}
	
	private void acceptDrawButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		//GameController gc= new GameController();		
		//toggleBoard(false);
		error = "";
		
		//TODO
		
		// update visuals
		error = "accept draw"; //for testing
		finishGame();
	}
	
	private void declineDrawButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		//GameController gc= new GameController();		
		toggleBoard(true);
		
		error = "";
		
		//todo
		
		toggleMainButtons(true);
		
		acceptDrawButton.setVisible(false);
		declineDrawButton.setVisible(false);
		// update visuals
		banner = "GamePlay"; //for testing
		
	}
	
	
	private void replayGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message		
		toggleBoard(true);
		
		error = "";
		
		replayGameButton.setVisible(false);
		
		stepForwardButton.setVisible(true);
		stepBackwardButton.setVisible(true);
		jumpStartButton.setVisible(true);
		jumpEndButton.setVisible(true);
		quitButton.setVisible(true);
		
		banner = "Replay Mode";
		refreshData();
	}
	
	private void stepForwardButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		GameController gc= new GameController();		
		error = "";

		// update visuals
		error = "step for"; //for testing
		refreshData();
	}
	
	private void stepBackwardButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		GameController gc= new GameController();		
		error = "";
		
		// update visuals
		error = "step back"; //for testing
		refreshData();
	}
	
	private void jumpStartButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		GameController gc= new GameController();		
		error = "";

		// update visuals
		error = "jump start"; //for testing
		refreshData();
	}
	
	private void jumpEndButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		GameController gc= new GameController();		
		error = "";

		// update visuals
		error = "jump end"; //for testing
		refreshData();
	}
	
	private void quitButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		//GameController gc= new GameController();		
		toggleBoard(false);
		
		error = "";
		timer.stop();
		newGameButton.setVisible(true);
		loadGameButton.setVisible(true);
		saveGameButton.setVisible(false);
		resignGameButton.setVisible(false);
		drawGameButton.setVisible(false);
		
		/*upButton.setVisible(false);
		leftButton.setVisible(false);
		rightButton.setVisible(false);
		downButton.setVisible(false);*/
		
		p1Name.setVisible(false);
		p2Name.setVisible(false);
		timeRem1.setVisible(false);
		timeRem2.setVisible(false);
		
		replayGameButton.setVisible(false);
		stepForwardButton.setVisible(false);
		stepBackwardButton.setVisible(false);
		jumpStartButton.setVisible(false);
		jumpEndButton.setVisible(false);
		quitButton.setVisible(false);
		
		// update visuals
		banner = "Main Menu"; //for testing
		q.getCurrentGame().delete();
		refreshData();
	}
	
	/*private void upButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//call gc 
	}
	
	private void rightButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//call gc 
	}
	
	private void downButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//call gc 
	}
	
	private void leftButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//call gc 
	}*/
	
	private void endTurnButtonActionPerformed(java.awt.event.ActionEvent evt) {	//this is the same as switch player
		//TODO
		//set movable pawn and walls
		
		currPlayer=!currPlayer;
		gc.switchPlayer(q);
		if (currPlayer) {
			timeRem1.setVisible(true);
			timeRem2.setVisible(false);
			turnMessage1.setVisible(true);
			turnMessage2.setVisible(false);
		}
		else {
			timeRem1.setVisible(false);
			timeRem2.setVisible(true);
			turnMessage1.setVisible(false);
			turnMessage2.setVisible(true);
		}
	}
	
	private void initButtons() {
		//regular
		newGameButton = new JButton();
		newGameButton.setText("New Game");
		
		createP1Button= new JButton();
		createP1Button.setText("Create P1");
		createP1Button.setVisible(false);
		
		createP2Button= new JButton();
		createP2Button.setText("Create P2");
		createP2Button.setVisible(false);
		
		selectP1Button= new JButton();
		selectP1Button.setText("Select P1");
		selectP1Button.setVisible(false);
		
		selectP2Button= new JButton();
		selectP2Button.setText("Select P2");
		selectP2Button.setVisible(false);
		
		timeSetButton= new JButton();
		timeSetButton.setText("Set Time");
		timeSetButton.setVisible(false);
		
		saveGameButton = new JButton();
		saveGameButton.setText("Save Game");
		saveGameButton.setVisible(false);
		
		saveFileButton=new JButton();
		saveFileButton.setText("Save");
		saveFileButton.setVisible(false);
		
		loadGameButton = new JButton();
		loadGameButton.setText("Load Game");
		
		loadFileButton=new JButton();
		loadFileButton.setText("Load");
		loadFileButton.setVisible(false);
		
		resignGameButton = new JButton();
		resignGameButton.setText("Resign Game");
		resignGameButton.setVisible(false);
		
		drawGameButton = new JButton();
		drawGameButton.setText("Offer Draw");
		drawGameButton.setVisible(false);
		
		acceptDrawButton = new JButton();
		acceptDrawButton.setText("Accept");
		acceptDrawButton.setVisible(false);
		
		declineDrawButton = new JButton();
		declineDrawButton.setText("Decline");
		declineDrawButton.setVisible(false);
		
		//replay button
		replayGameButton = new JButton();
		replayGameButton.setText("Replay Mode");
		replayGameButton.setVisible(false);
		
		//replay mode buttons
		stepForwardButton = new JButton();
		stepForwardButton.setText("Step Forward");
		stepForwardButton.setVisible(false);
		
		stepBackwardButton = new JButton();
		stepBackwardButton.setText("Step Backward");
		stepBackwardButton.setVisible(false);
		
		jumpStartButton = new JButton();
		jumpStartButton.setText("Jump Start");
		jumpStartButton.setVisible(false);
		
		jumpEndButton = new JButton();
		jumpEndButton.setText("Jump End");
		jumpEndButton.setVisible(false);
		
		quitButton = new JButton();
		quitButton.setText("Quit");
		quitButton.setVisible(false);
		
		endTurnButton= new JButton();
		endTurnButton.setText("End Turn");
		endTurnButton.setVisible(false);
		
		/*upButton= new JButton();
		upButton.setText("UP");
		upButton.setVisible(false);
		
		rightButton= new JButton();
		rightButton.setText("RIGHT");
		rightButton.setVisible(false);
		
		downButton= new JButton();
		downButton.setText("DOWN");
		downButton.setVisible(false);
		
		leftButton= new JButton();
		leftButton.setText("LEFT");
		leftButton.setVisible(false);*/
	}
	
	private void addListners() {
		newGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				newGameButtonActionPerformed(evt);
			}
		});
		
		createP1Button.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				createP1ButtonActionPerformed(evt);
			}
		});
		
		createP2Button.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				createP2ButtonActionPerformed(evt);
			}
		});
		
		selectP1Button.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				selectP1ButtonActionPerformed(evt);
			}
		});
		
		selectP2Button.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				selectP2ButtonActionPerformed(evt);
			}
		});
		
		timeSetButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				timeSetButtonActionPerformed(evt);
			}
		});
		
		
		saveGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveGameButtonActionPerformed(evt);
			}
		});
		
		saveFileButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveFileButtonActionPerformed(evt);
			}
		});
		
		loadGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadGameButtonActionPerformed(evt);
			}
		});
		
		loadFileButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadFileButtonActionPerformed(evt);
			}
		});
		
		resignGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				resignGameButtonActionPerformed(evt);
			}
		});
		
		drawGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				drawGameButtonActionPerformed(evt);
			}
		});
		
		acceptDrawButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				acceptDrawButtonActionPerformed(evt);
			}
		});
		
		declineDrawButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				declineDrawButtonActionPerformed(evt);
			}
		});
		
		replayGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				replayGameButtonActionPerformed(evt);
			}
		});
		
		stepForwardButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				stepForwardButtonActionPerformed(evt);
			}
		});
		
		stepBackwardButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				stepBackwardButtonActionPerformed(evt);
			}
		});
		
		jumpStartButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jumpStartButtonActionPerformed(evt);
			}
		});
		
		jumpEndButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jumpEndButtonActionPerformed(evt);
			}
		});
		
		quitButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				quitButtonActionPerformed(evt);
			}
		});
		
		endTurnButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				endTurnButtonActionPerformed(evt);
			}
		});
		
		/*upButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				upButtonActionPerformed(evt);
			}
		});
		
		rightButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				rightButtonActionPerformed(evt);
			}
		});
		
		downButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				downButtonActionPerformed(evt);
			}
		});
		
		leftButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				leftButtonActionPerformed(evt);
			}
		});*/
	}
	
	
	
	private void toggleBoard(boolean vis) {
		//boolean vis=!tiles[0][0].isVisible();
		for (int i=0;i<9;i++) {
			for (int j=0;j<9;j++) {
				tiles[i][j].setVisible(vis);
			}
			wwalls[i].setVisible(vis);
			bwalls[i].setVisible(vis);
		}
		wwalls[9].setVisible(vis);
		bwalls[9].setVisible(vis);
		
		wPawn.setVisible(vis);
		bPawn.setVisible(vis);
		

		/*upButton.setVisible(vis);
		rightButton.setVisible(vis);
		downButton.setVisible(vis);
		leftButton.setVisible(vis);*/
	}
	
	private void toggleMainButtons(boolean vis) {
		quitButton.setVisible(vis);
		saveGameButton.setVisible(vis);
		loadGameButton.setVisible(vis);
		resignGameButton.setVisible(vis);
		drawGameButton.setVisible(vis);
		endTurnButton.setVisible(vis);
		
		if (currPlayer) {
			turnMessage1.setVisible(vis);
		}
		else {
			turnMessage2.setVisible(vis);
		}
	}
	
	private String convT2S(Time t) {
		long tt=t.getTime();
		int min=(int)(tt/1000)/60;
		int sec=(int)(tt/1000)%60;
		return("Time Remainning: "+min+":"+sec);
	}

	private void switchPlayer() {			//should be called by either drop wall or end turn button
		//TODO
		
		currPlayer=!currPlayer;
		if (currPlayer) {
			timeRem1.setVisible(true);
			timeRem2.setVisible(false);
		}
		else {
			timeRem1.setVisible(false);
			timeRem2.setVisible(true);
		}
	}

}