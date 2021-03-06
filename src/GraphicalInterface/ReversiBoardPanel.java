package GraphicalInterface;

import Board.ReversiBoard;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import javax.swing.Timer;

import Game.Game;

/**
 * @author Thundermoose
 * @version 1
 * 
 */
public class ReversiBoardPanel extends JPanel implements ActionListener, Observer {
	// All private instance variable here
	private ReversiBoard board;
	private Color[] colors;

	// All constructors here
	public ReversiBoardPanel(){
        super(true); // sets the JPanel to be double buffered, do not know if necessary
        this.board = new ReversiBoard();
        colors = new Color[3];
        colors[0] = Color.GREEN;
        colors[1] = Color.BLACK;
        colors[2] = Color.WHITE;
	}

	// All private methods here
	private void drawReversiBoard(Graphics g){

        int[][] boardLayout = board.getBoardInformation();
        int side = this.getWidth();
        int xstart=0,ystart=0;
        if (side>this.getHeight()){
            side = this.getHeight();
            xstart=(this.getWidth()-side)/2;
        }else{
            ystart=side/16;
        }
        side/=8;

        // The traditional colour of a Reversi board is green
        // So I stick with that
        g.setColor(Color.GREEN);
        g.fillRect(xstart,ystart,side*8,side*8);
        for (int i = 0; i<8; i++){
            for (int j = 0; j<8; j++){
            if (boardLayout[i][j] !=0){
                g.setColor(colors[boardLayout[i][j]]);
                g.fillOval(xstart+i*side,ystart+j*side,side,side);
            }
            }
        }

	}
	// All public methdos here

	// All overriden methods here
	@Override
	public void paintComponent(Graphics g){
	super.paintComponent(g);
	drawReversiBoard(g);
	}

	@Override
	public void actionPerformed(ActionEvent e){
	repaint();
	}

	@Override
	public Dimension getPreferredSize() {
		Dimension d = super.getPreferredSize();
		Container c = getParent();
		if (c != null) {
			d = c.getSize();
		} else {
			return new Dimension(10, 10);
		}
		int w = (int) d.getWidth();
		int h = (int) d.getHeight();
		int s = (w < h ? w : h);
		return new Dimension(s, s);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Game){
			this.board = ((Game) o).getGameState().getBoard();
            this.repaint();
            

		}
	}
}
