/**
 * 
 */
package main.AffGraph;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Maxime
 *
 */
public class Window extends JFrame {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Window.init();

	}
	
	private JPanel[] panelDisplayer = new JPanel[2];      //Contient les diff�rents conteneurs a afficher
	private int currentPanel ;
	private static  Window window ;
	/**
	 * Construit la fenetre de jeu de taille maximale
	 * 
	 * @param null
	 * @return void
	 */
	
	public Window(){
		JFrame frame= new JFrame();
		Rectangle bounds = getMaxBounds();
		frame.setSize(bounds.width, bounds.height);
		frame.setTitle("Stratego");
		frame.setLocation(0, 0);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new WindowAdapter(frame));
		frame.add(createMenu());

		
		panelDisplayer[0] =  createMenu();
		panelDisplayer[1] =  createModSelect();
		currentPanel = 0;
	
	}
	
	public static void init(){
		window = new Window();
		
	}
	
	/**
	 * @param null
	 * @return la r�solution de l'�cran
	 */
	private static Rectangle getMaxBounds(){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		return gc.getBounds();
	}
	
	public void  switchPanel(int a){
		window.remove(panelDisplayer[currentPanel]); // !!!!!!!!!!!!!
		currentPanel +=a;
		window.add((panelDisplayer[currentPanel]));
	}
	/**
	 * @param null
	 * @return un conteneur qui contient le menu principal
	 */
	private static JPanel createMenu(){
		JButton buttonPlay = new JButton("Jouer");
		buttonPlay.addActionListener(new SwitchListenner(window));
		JPanel panel=new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		panel.add(buttonPlay , c);    //Place le buttonPlay au centre du conteneur
		return panel;
	}
	
	private static JPanel createModSelect(){
		JButton classicMod = new JButton("Mode Classic");
		JButton previousButton = new JButton("Retour");
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(classicMod, BorderLayout.CENTER);
		panel.add(previousButton, BorderLayout.WEST);
		return panel;
	}
	

}
