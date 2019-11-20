package id.ac.its.juwita.moving;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener{
	private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 60;
    private final int DELAY = 10;
    private Timer timer;
    private SpaceShip spaceShip;
    private List<Asteroid> asteroid;
    private boolean ingame;

    private final int[][] pos = {
            {2380, 29}, {2500, 59}, {1380, 89},
            {780, 109}, {580, 139}, {680, 239},
            {790, 259}, {760, 50}, {790, 150},
            {980, 209}, {560, 45}, {510, 70},
            {930, 159}, {590, 80}, {530, 60},
            {940, 59}, {990, 30}, {920, 200},
            {900, 259}, {660, 50}, {540, 90},
            {810, 220}, {860, 20}, {740, 180},
            {820, 128}, {490, 170}, {700, 30}
        };
    
    public Board() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);
        ingame = true;

        spaceShip = new SpaceShip(ICRAFT_X, ICRAFT_Y);

        initAsteroid();
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public void initAsteroid() {
        
        asteroid = new ArrayList<>();

        for (int[] p : pos) {
            asteroid.add(new Asteroid (p[0], p[1]));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
            drawObjects(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics g) {

        if (spaceShip.isVisible()) {
            g.drawImage(spaceShip.getImage(), spaceShip.getX(), spaceShip.getY(),
                    this);
        }

        List<Missile> ms = spaceShip.getMissiles();

        for (Missile missile : ms) {
            if (missile.isVisible()) {
                g.drawImage(missile.getImage(), missile.getX(), 
                        missile.getY(), this);
            }
        }

        for (Asteroid asteroid : asteroid) {
            if (asteroid.isVisible()) {
                g.drawImage(asteroid.getImage(), asteroid.getX(), asteroid.getY(), this);
            }
        }

        g.setColor(Color.WHITE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	
        updateMissiles();
        updateSpaceShip();
        updateAsteroid();
        
        
        repaint();
    }

    private void updateMissiles() {

        List<Missile> missiles = spaceShip.getMissiles();

        for (int i = 0; i < missiles.size(); i++) {

            Missile missile = missiles.get(i);

            if (missile.isVisible()) {

                missile.move();
            } else {

                missiles.remove(i);
            }
        }
    }

    private void updateSpaceShip() {

    	if (spaceShip.isVisible()) {
            
    		spaceShip.move();
        }
        
    }
    private void updateAsteroid() {

        if (asteroid.isEmpty()) {

            ingame = false;
            return;
        }

        for (int i = 0; i < asteroid.size(); i++) {

            Asteroid a = asteroid.get(i);
            
            if (a.isVisible()) {
                a.move();
            } else {
            	asteroid.remove(i);
            }
        }
    }


    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            spaceShip.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            spaceShip.keyPressed(e);
        }
    }

}
