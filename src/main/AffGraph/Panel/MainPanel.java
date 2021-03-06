/**
 * 
 */
package main.AffGraph.Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.AffGraph.InfoPiece.InfoPieceAllies;
import main.AffGraph.InfoPiece.InfoPieceEnnemies;
import main.game.BoardGame;
import main.game.Pieces.Piece;

/**
 * @author Maxime
 * 
 * Cette classe parmet de creer un conteneur qui affiche le plateau de jeu
 * 
 */
public class MainPanel extends JPanel {
	
	public GamePanel GAME_PANEL;

	public MainPanel(){
		super();
		super.setLayout(new BorderLayout());
		
		InfoPanel killedPiecePanel = new InfoPanel();
		killedPiecePanel.setBackground(Color.RED);
		killedPiecePanel.setLayout(new BoxLayout(killedPiecePanel, BoxLayout.PAGE_AXIS));
		killedPiecePanel.add(new JLabel("Pieces ennemies tuees"));
		for(Piece piece : BoardGame.getBoardGame().teamRed.charachter){
			killedPiecePanel.add(Box.createRigidArea(new Dimension(0, 30)));
			killedPiecePanel.add(new InfoPieceEnnemies(piece, BoardGame.getBoardGame().teamRed));
			
		}
		
		InfoPanel lostPiecePanel = new InfoPanel();
		lostPiecePanel.setBackground(Color.CYAN);
		lostPiecePanel.setLayout(new BoxLayout(lostPiecePanel, BoxLayout.PAGE_AXIS));
		lostPiecePanel.add(new JLabel("Pieces alliees perdues"));
		for(Piece piece : BoardGame.getBoardGame().teamBlue.charachter){
			lostPiecePanel.add(Box.createRigidArea(new Dimension(0, 30)));
			lostPiecePanel.add(new InfoPieceAllies(piece, BoardGame.getBoardGame().teamBlue));
			
		}
		
		GamePanel gamePanel = new GamePanel(this);
		this.GAME_PANEL = gamePanel;

		super.add(killedPiecePanel, BorderLayout.WEST);
		super.add(lostPiecePanel, BorderLayout.EAST);
		super.add(gamePanel, BorderLayout.CENTER);
		
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		super.paintChildren(g);
	}
}
