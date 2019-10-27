package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;
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
import java.sql.Time;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.GameController;
import ca.mcgill.ecse223.quoridor.model.Quoridor;

public class QuoridorPage extends JFrame implements MouseMotionListener{

	/**
	 * default 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel errorMessage;
	private String error = null;
	
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
	
	private JButton newGameButton;
	private JButton createP1Button;
	private JButton createP2Button;
	private JButton selectP1Button;
	private JButton selectP2Button;
	private JButton timeSetButton;
	
	
	private JButton saveGameButton;
	private JButton loadGameButton;
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
	
	private Timer timer;
	
	private Quoridor q;
	private GameController gc;
	
	
	// Layered panes helps with managing Z index of walls and pawns, when being moved or grabbed
	private JLayeredPane lp;
	
	private RectComp [][] tiles;
	private WallComp []whiteWalls;
	private WallComp []blackWalls;
	
	private WallComp grabbedWall;
	
	
	public QuoridorPage(){
		q=QuoridorApplication.getQuoridor();
		gc=new GameController();
		gc.initQuorridor();
		lp = getLayeredPane();
		lp.addMouseMotionListener(this);
		initComponents();
		refreshData();
	}

	private void initComponents() {
		
		setSize(800, 800);
		
		int buttonH=30;
		int buttonW=125;
		
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
		
		title = new JLabel();
		title.setFont(new Font("Serif",Font.PLAIN,36));
		title.setText("Quoridor");
		
		bannerMessage = new JLabel();
		bannerMessage.setFont(new Font("Serif",Font.PLAIN,26));
		bannerMessage.setForeground(Color.BLUE);
		bannerMessage.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
		bannerMessage.setText(banner);
		
		initButtons();
		addListeners();
		
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
		
		tiles= new RectComp[9][9];
		for (int i=0;i<9;i++) {
			for (int j=0;j<9;j++) {
				tiles[i][j]=new RectComp();
			}
		}
		
		// Creating all the rectangles symbolizing walls, 10 for each player
		whiteWalls= new WallComp[10];
		blackWalls= new WallComp[10];
		for (int i=0;i<10;i++) {
			whiteWalls[i] = new WallComp();
			blackWalls[i] = new WallComp();
		}
		
		toggleBoard(false);
		
		GroupLayout layout = new GroupLayout(lp);
		lp.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		ParallelGroup pq=layout.createParallelGroup();
		SequentialGroup sq=layout.createSequentialGroup();
		
		createBoard(pq,sq,layout);
		
		layout.setVerticalGroup(
				layout.createSequentialGroup().addComponent(title, GroupLayout.PREFERRED_SIZE, 40,GroupLayout.PREFERRED_SIZE)
				.addComponent(bannerMessage, GroupLayout.PREFERRED_SIZE, 40,GroupLayout.PREFERRED_SIZE)
				.addComponent(errorMessage)
				.addComponent(p1Name)
				.addComponent(timeRem1)
				.addGroup(layout.createParallelGroup()
					.addGroup(layout.createSequentialGroup()
						.addComponent(newGameButton, GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
						.addComponent(saveGameButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
						.addComponent(loadGameButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
						.addComponent(resignGameButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
						.addComponent(drawGameButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
						.addComponent(stepForwardButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
						.addComponent(stepBackwardButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
						.addComponent(jumpStartButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
						.addComponent(jumpEndButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
						.addComponent(acceptDrawButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
						.addComponent(declineDrawButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
						.addComponent(quitButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
						.addComponent(replayGameButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
						.addComponent(p1NameField,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
						.addComponent(p2NameField,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createParallelGroup()
								.addComponent(minField,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
								.addComponent(secField,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
								)
					)
				/*.addGroup(layout.createParallelGroup().addComponent(quitButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
						.addComponent(replayGameButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE))*/
					.addGroup(sq)
					.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup()
									.addComponent(createP1Button,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
									.addComponent(selectP1Button,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
									)
							.addGroup(layout.createParallelGroup()
									.addComponent(createP2Button,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
									.addComponent(selectP2Button,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
									)
							.addComponent(timeSetButton,GroupLayout.PREFERRED_SIZE, buttonH,GroupLayout.PREFERRED_SIZE)
							)
			).addComponent(p2Name)
			.addComponent(timeRem2)
		);
		layout.setHorizontalGroup(
				layout.createParallelGroup().addComponent(title, GroupLayout.PREFERRED_SIZE, 600,GroupLayout.PREFERRED_SIZE)
				.addComponent(bannerMessage, GroupLayout.PREFERRED_SIZE, 750,GroupLayout.PREFERRED_SIZE)
				.addComponent(errorMessage)
				.addComponent(p1Name)
				.addComponent(timeRem1)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup()
						.addComponent(newGameButton, GroupLayout.PREFERRED_SIZE, buttonW ,GroupLayout.PREFERRED_SIZE)
						.addComponent(saveGameButton, GroupLayout.PREFERRED_SIZE, buttonW ,GroupLayout.PREFERRED_SIZE)
						.addComponent(loadGameButton, GroupLayout.PREFERRED_SIZE, buttonW ,GroupLayout.PREFERRED_SIZE)
						.addComponent(resignGameButton, GroupLayout.PREFERRED_SIZE, buttonW ,GroupLayout.PREFERRED_SIZE)
						.addComponent(drawGameButton, GroupLayout.PREFERRED_SIZE, buttonW ,GroupLayout.PREFERRED_SIZE)
						.addComponent(stepForwardButton, GroupLayout.PREFERRED_SIZE, buttonW ,GroupLayout.PREFERRED_SIZE)
						.addComponent(stepBackwardButton, GroupLayout.PREFERRED_SIZE, buttonW ,GroupLayout.PREFERRED_SIZE)
						.addComponent(jumpStartButton, GroupLayout.PREFERRED_SIZE, buttonW ,GroupLayout.PREFERRED_SIZE)
						.addComponent(jumpEndButton, GroupLayout.PREFERRED_SIZE, buttonW ,GroupLayout.PREFERRED_SIZE)
						.addComponent(acceptDrawButton,GroupLayout.PREFERRED_SIZE, buttonW,GroupLayout.PREFERRED_SIZE)
						.addComponent(declineDrawButton,GroupLayout.PREFERRED_SIZE, buttonW,GroupLayout.PREFERRED_SIZE)
						.addComponent(quitButton, GroupLayout.PREFERRED_SIZE, buttonW ,GroupLayout.PREFERRED_SIZE)
						.addComponent(replayGameButton, GroupLayout.PREFERRED_SIZE, buttonW ,GroupLayout.PREFERRED_SIZE)
						.addComponent(p1NameField,GroupLayout.PREFERRED_SIZE, buttonW,GroupLayout.PREFERRED_SIZE)
						.addComponent(p2NameField,GroupLayout.PREFERRED_SIZE, buttonW,GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createSequentialGroup()
								.addComponent(minField,GroupLayout.PREFERRED_SIZE, buttonW,GroupLayout.PREFERRED_SIZE)
								.addComponent(secField,GroupLayout.PREFERRED_SIZE, buttonW,GroupLayout.PREFERRED_SIZE)
								)
					)
						/*.addGroup(layout.createSequentialGroup().addComponent(quitButton, GroupLayout.PREFERRED_SIZE, buttonW ,GroupLayout.PREFERRED_SIZE)
								.addComponent(replayGameButton, GroupLayout.PREFERRED_SIZE, buttonW ,GroupLayout.PREFERRED_SIZE))*/
					.addGroup(pq)
					.addGroup(layout.createParallelGroup()
							.addGroup(layout.createSequentialGroup()
									.addComponent(createP1Button,GroupLayout.PREFERRED_SIZE, buttonW,GroupLayout.PREFERRED_SIZE)
									.addComponent(selectP1Button,GroupLayout.PREFERRED_SIZE, buttonW,GroupLayout.PREFERRED_SIZE)
									)
							.addGroup(layout.createSequentialGroup()
									.addComponent(createP2Button,GroupLayout.PREFERRED_SIZE, buttonW,GroupLayout.PREFERRED_SIZE)
									.addComponent(selectP2Button,GroupLayout.PREFERRED_SIZE, buttonW,GroupLayout.PREFERRED_SIZE)
									)
							.addComponent(timeSetButton,GroupLayout.PREFERRED_SIZE, buttonW,GroupLayout.PREFERRED_SIZE)
							)
			).addComponent(p2Name)
			.addComponent(timeRem2)
		);
		
		//pack();
	}

	private void refreshData() {
		errorMessage.setText(error);
		bannerMessage.setText(banner);
		if (error == null || error.length() == 0) {
			//update
		}
		
	}
	
	private void createBoard(ParallelGroup pq,SequentialGroup sq, GroupLayout layout) {
		// In horizontal layout, walls are in same group
		// while board is its own.
		SequentialGroup sWalls = layout.createSequentialGroup();
		SequentialGroup sBoard = layout.createSequentialGroup();
		pq.addGroup(sWalls);
		pq.addGroup(sBoard);
		
		// In vertical layout, first is black Stock, then the board, then the white Stock
		ParallelGroup pBlackWalls = layout.createParallelGroup();
		ParallelGroup pBoard = layout.createParallelGroup();
		ParallelGroup pWhiteWalls = layout.createParallelGroup();
		sq.addGroup(pBlackWalls);
		sq.addGroup(pBoard);
		sq.addGroup(pWhiteWalls);
		
		// Add walls to each layout
		for(int i=0;i<=9;i++) {
			// In horizontal layout, pairs of black&white walls are in parallel
			sWalls.addGroup(layout.createParallelGroup()
					.addComponent(blackWalls[i])
					.addComponent(whiteWalls[i])
			);
			
			// In vertical layout, all black walls are in parallel
			// and white walls are in parallel
			pBlackWalls.addComponent(blackWalls[i]);
			pWhiteWalls.addComponent(whiteWalls[i]);
		}
		
		// Create tiles rows and cols
		SequentialGroup s1=layout.createSequentialGroup();
		SequentialGroup s2=layout.createSequentialGroup();
		SequentialGroup s3=layout.createSequentialGroup();
		SequentialGroup s4=layout.createSequentialGroup();
		SequentialGroup s5=layout.createSequentialGroup();
		SequentialGroup s6=layout.createSequentialGroup();
		SequentialGroup s7=layout.createSequentialGroup();
		SequentialGroup s8=layout.createSequentialGroup();
		SequentialGroup s9=layout.createSequentialGroup();
		ParallelGroup p1=layout.createParallelGroup();
		ParallelGroup p2=layout.createParallelGroup();
		ParallelGroup p3=layout.createParallelGroup();
		ParallelGroup p4=layout.createParallelGroup();
		ParallelGroup p5=layout.createParallelGroup();
		ParallelGroup p6=layout.createParallelGroup();
		ParallelGroup p7=layout.createParallelGroup();
		ParallelGroup p8=layout.createParallelGroup();
		ParallelGroup p9=layout.createParallelGroup();
		
		// Add tiles to rows and cols
		for (int i=0;i<9;i++) {
			s1.addComponent(tiles[1][i]);
			p1.addComponent(tiles[1][i]);
			
			s2.addComponent(tiles[2][i]);
			p2.addComponent(tiles[2][i]);
			
			s3.addComponent(tiles[3][i]);
			p3.addComponent(tiles[3][i]);
			
			s4.addComponent(tiles[4][i]);
			p4.addComponent(tiles[4][i]);
			
			s5.addComponent(tiles[5][i]);
			p5.addComponent(tiles[5][i]);
			
			s6.addComponent(tiles[6][i]);
			p6.addComponent(tiles[6][i]);
			
			s7.addComponent(tiles[7][i]);
			p7.addComponent(tiles[7][i]);
			
			s8.addComponent(tiles[8][i]);
			p8.addComponent(tiles[8][i]);
			
			s9.addComponent(tiles[0][i]);
			p9.addComponent(tiles[0][i]);
		}
		pBoard.addGroup(s1);
		sBoard.addGroup(p1);
		pBoard.addGroup(s2);
		sBoard.addGroup(p2);
		pBoard.addGroup(s3);
		sBoard.addGroup(p3);
		pBoard.addGroup(s4);
		sBoard.addGroup(p4);
		pBoard.addGroup(s5);
		sBoard.addGroup(p5);
		pBoard.addGroup(s6);
		sBoard.addGroup(p6);
		pBoard.addGroup(s7);
		sBoard.addGroup(p7);
		pBoard.addGroup(s8);
		sBoard.addGroup(p8);
		pBoard.addGroup(s9);
		sBoard.addGroup(p9);
	}
	
	private void finishGame() {
		//set screen to results
		
		//todo
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
		// clear error message
		//GameController gc= new GameController();
		
		error = "";
		newGameButton.setVisible(false);
		loadGameButton.setVisible(false);
		saveGameButton.setVisible(false);
		drawGameButton.setVisible(false);
		resignGameButton.setVisible(false);
		
		// call the controller
		gc.initGame(q);
		
		p1NameField.setVisible(true);
		//p2NameField.setVisible(true);
		//minField.setVisible(true);
		//secField.setVisible(true);
		createP1Button.setVisible(true);
		//createP2Button.setVisible(true);
		selectP1Button.setVisible(true);
		//selectP2Button.setVisible(true);
		//timeSetButton.setVisible(true);
		
		
		banner="New Game";
		/*try {
			gc.initGame();
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}*/
		// update visuals
		
		/*saveGameButton.setVisible(true);
		resignGameButton.setVisible(true);
		drawGameButton.setVisible(true);
		newGameButton.setVisible(false);
		quitButton.setVisible(true);
		toggleBoard();
		banner="GamePlay";*/
		
		refreshData();
	}
	
	private void createP1ButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		//toggleBoard(); to be implemented
		
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
			saveGameButton.setVisible(true);
			resignGameButton.setVisible(true);
			drawGameButton.setVisible(true);
			newGameButton.setVisible(false);
			quitButton.setVisible(true);
			toggleBoard(true);
			banner="GamePlay";
			
			Time t=q.getCurrentGame().getBlackPlayer().getRemainingTime();
			timeRem1.setText(convT2S(t));
			timeRem2.setText(convT2S(t));
			timeRem1.setVisible(true);
			
			p1Name.setVisible(true);
			p2Name.setVisible(true);
			
			gc.startTheClock(q,timer);
			//timer.start();
			
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
		//toggleBoard(false); to be implemented
		
		GameController gc= new GameController();
		error = "";
		
		// update visuals
		banner = "Save Game"; //for testing
		refreshData();
	}
	
	private void loadGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		//toggleBoard(false); to be implemented
		
		GameController gc= new GameController();
		error = "";
		
		
		// update visuals
		banner = "Load Game"; //for testing
		refreshData();
	}
	
	private void resignGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		
		GameController gc= new GameController();		
		error = "";
		//todo
		
		finishGame();
	}
	
	private void drawGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		toggleBoard(false);
		GameController gc= new GameController();		
		error = "";
		
		quitButton.setVisible(false);
		saveGameButton.setVisible(false);
		loadGameButton.setVisible(false);
		resignGameButton.setVisible(false);
		drawGameButton.setVisible(false);
		
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
		
		//todo
		
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
		
		
		quitButton.setVisible(true);
		saveGameButton.setVisible(true);
		loadGameButton.setVisible(true);
		resignGameButton.setVisible(true);
		drawGameButton.setVisible(true);
		
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
		
		loadGameButton = new JButton();
		loadGameButton.setText("Load Game");
		
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
	}
	
	private void addListeners() {
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
		
		loadGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadGameButtonActionPerformed(evt);
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
		
	}
	
	
	
	private void toggleBoard(boolean vis) {
		//boolean vis=!tiles[0][0].isVisible();
		for (int i=0;i<9;i++) {
			for (int j=0;j<9;j++) {
				tiles[i][j].setVisible(vis);
			}
		}
		// Hide the walls also
		for (int i=0;i<10;i++) {
			whiteWalls[i].setVisible(vis);
			blackWalls[i].setVisible(vis);
		}
	}
	
	private String convT2S(Time t) {
		long tt=t.getTime();
		int min=(int)(tt/1000)/60;
		int sec=(int)(tt/1000)%60;
		return("Time Remainning: "+min+":"+sec);
	}
	
	class RectComp extends JComponent {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		Rectangle rect;
		Color color;
		//Rectangle [][] tiles=new Rectangle[9][9];
        public RectComp() {
    		rect=new Rectangle(40,40);
    		color= Color.ORANGE;
        	/*for (int i=0;i<9;i++) {
    			for (int j=0;j<9;j++) {
    				tiles[i][j]=new Rectangle(20,20);
    			}
    		}*/
        }
        public Color getColor() {
        	return this.color;
        }
        public void setColor(Color c) {
        	this.color = c;
        }
        
        public Rectangle getRect() {
        	return this.rect;
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.draw(rect);
            /*for (int i=0;i<9;i++) {
    			for (int j=0;j<9;j++) {
    				g2.draw(tiles[i][j]);
    			}
    		}*/
            
            g2.setColor(color);
            g2.fill(rect);
            /*for (int i=0;i<9;i++) {
    			for (int j=0;j<9;j++) {
    				g2.fill(tiles[i][j]);
    			}
    		}*/

        }
    }
	
	public void mouseMoved(java.awt.event.MouseEvent e) {
		System.out.println("yeet");
    	if (grabbedWall != null) {
    		System.out.println("in");
    		grabbedWall.getRect().setLocation(e.getX(),e.getY());
            grabbedWall.repaint();
    	}
    }
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	class WallComp extends RectComp{
		private boolean grabbed;
		
		public WallComp() {
			this.rect = new Rectangle(10,40);
    		this.color = Color.BLUE;
    		this.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                	grab(e.getX(),e.getY());
                    repaint();
                }
            });
		}
		
		public void grab(int x,int y) {
			this.color = Color.RED;
        	lp.moveToFront(this); // remove from Group Layout
        	grabbedWall = this;
        	rect.setLocation(x,y);
        	System.out.println(grabbedWall.toString());
		}
		
		public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.draw(rect);
            g2.setColor(color);
            g2.fill(rect);
        }

	}
}
