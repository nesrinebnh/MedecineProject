import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

	private boolean play = false;
	private int score ;
	private int totalBricks;
	private Timer time;
	private int delay = 8;
	private int playerX = 310;

	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;
	private MapGenerateurL2 mapL2;
	private MapGenerateur map;
	private MapGenerateurL3 mapL3;

	private int level;
	private JFrame obj;

	public GamePlay(JFrame obj,int level,int score){
		this.score = score;
		this.obj = obj;
		this.level = level;
		if(this.level == 1){
			totalBricks = 21;
			map = new MapGenerateur(3,7);
		}
		else if(this.level == 2){
			totalBricks = 24;
			mapL2 = new MapGenerateurL2(4,7);
		}else{
			totalBricks = 24;
			mapL3 = new MapGenerateurL3(4,7);
		}
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		time = new Timer(delay,this);
		time.start();



	}

	public void paint(Graphics g){

		//background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);

		//drawing map
		if(level == 1)
			map.draw((Graphics2D)g);
		else if(level == 2) mapL2.draw((Graphics2D)g);
		else mapL3.draw((Graphics2D)g);
		//borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);


		//score
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString(""+score, 592, 30);

		//level
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString("level "+this.level, 10, 30);

		//the paddle

		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);

		//the ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 20, 20);

		if(totalBricks <=0){
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("You Won", 260, 300);

			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press Enter to restart", 230, 350);


		}
		if(ballposY >570){
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Game Over, Score: "+score, 190, 300);

			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press Enter to restart", 230, 350);
		}

		g.dispose();


	}


	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

		if(arg0.getKeyCode() == KeyEvent.VK_RIGHT){
			System.out.println("start");
			if(playerX>=600){
				playerX = 600;
			}else{
				moveRight();
			}


		}
		if(arg0.getKeyCode() == KeyEvent.VK_LEFT){
			if(playerX < 10){
				playerX = 10;
			}else{
				moveLeft();
			}
		}
		if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
			if(!play){
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 310;
				//score = 0;
				if(totalBricks == 0 && this.level == 1){
					JFrame f = new JFrame();
					f.setBounds(10,10,700,600);
					f.setTitle("Brickout Ball");
					f.setResizable(false);
					f.setVisible(true);
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					f.getContentPane().setBackground( Color.black );
					obj.setVisible(false);

					GamePlay gp = new GamePlay(obj,2,this.score);
					f.add(gp);

				}else if(totalBricks == 0 && this.level == 2){
					JFrame f = new JFrame();
					f.setBounds(10,10,700,600);
					f.setTitle("Brickout Ball");
					f.setResizable(false);
					f.setVisible(true);
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					f.getContentPane().setBackground( Color.black );
					obj.setVisible(false);

					GamePlay gp = new GamePlay(obj,3,this.score);
					f.add(gp);

				}else{

					JFrame f = new JFrame();
					f.setBounds(10,10,700,600);
					f.setTitle("Brickout Ball");
					f.setResizable(false);
					f.setVisible(true);
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					f.getContentPane().setBackground( Color.black );
					f.setLayout(new FlowLayout(FlowLayout.CENTER));
					obj.setVisible(false);
					Menu gp = new Menu(obj);
					f.add(gp);
				}

				/*if(this.level == 1){
					totalBricks = 21;
					map = new MapGenerateur(3,7);
				}else{
					totalBricks = 24;
					mapL2 = new MapGenerateurL2(4,7);
				}*/


				repaint();
			}
		}

	}

	private void moveLeft() {
		// TODO Auto-generated method stub
		play = true;
		playerX -= 20;

	}

	private void moveRight() {
		// TODO Auto-generated method stub
		play = true;
		playerX += 20;

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		time.start();

		if(play == true){
			//detecting the intersaction betwenn the ball and brich
			if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))){
				ballYdir = -ballYdir;
			}
			if(level == 1){
				A: for(int i = 0; i < map.map.length; i++){
					for(int j=0;j<map.map[0].length;j++){
						if(map.map[i][j] ==1){
							int brickX = j*map.brickwidth+80;
							int brickY = i * map.brickheight + 50;

							int brickwidth = map.brickwidth;
							int brickheight = map.brickheight;

							Rectangle rect = new Rectangle(brickX,brickY, brickwidth,brickheight);
							Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);

							Rectangle brickrect  = rect;

							if(ballRect.intersects(brickrect)){
								map.setBrickValue(0, i, j);
								totalBricks --;
								score += 5;

								if(ballposX + 19 <= brickrect.x || ballposX +1 >= brickrect.width){
									ballXdir = - ballXdir;
								}else{
									ballYdir = - ballYdir;
								}

								break A;
							}
						}
					}
				}
			}else if(level == 2){
				A: for(int i = 0; i < mapL2.map.length; i++){
					for(int j=0;j<mapL2.map[0].length;j++){
						if(mapL2.map[i][j] ==1){
							int brickX = j*mapL2.brickwidth+80;
							int brickY = i * mapL2.brickheight + 50;

							int brickwidth = mapL2.brickwidth;
							int brickheight = mapL2.brickheight;

							Rectangle rect = new Rectangle(brickX,brickY, brickwidth,brickheight);
							Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);

							Rectangle brickrect  = rect;

							if(ballRect.intersects(brickrect)){
								mapL2.setBrickValue(0, i, j);
								totalBricks --;
								score += 5;

								if(ballposX + 19 <= brickrect.x || ballposX +1 >= brickrect.width){
									ballXdir = - ballXdir;
								}else{
									ballYdir = - ballYdir;
								}

								break A;
							}
						}else if(mapL2.map[i][j] ==2){

							int brickX = j*mapL2.brickwidth+80;
							int brickY = i * mapL2.brickheight + 50;

							int brickwidth = mapL2.brickwidth;
							int brickheight = mapL2.brickheight;

							Rectangle rect = new Rectangle(brickX,brickY, brickwidth,brickheight);
							Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);

							Rectangle brickrect  = rect;

							if(ballRect.intersects(brickrect)){
								System.out.println("intersect");

								ballYdir = -ballYdir;


								break A;
							}
						}
					}
				}
			}else{
				A: for(int i = 0; i < mapL3.map.length; i++){
					for(int j=0;j<mapL3.map[0].length;j++){
						if(mapL3.map[i][j] ==1){
							int brickX = j*mapL3.brickwidth+80;
							int brickY = i * mapL3.brickheight + 50;

							int brickwidth = mapL3.brickwidth;
							int brickheight = mapL3.brickheight;

							Rectangle rect = new Rectangle(brickX,brickY, brickwidth,brickheight);
							Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);

							Rectangle brickrect  = rect;

							if(ballRect.intersects(brickrect)){
								mapL3.setBrickValue(0, i, j);
								totalBricks --;
								score += 5;

								if(ballposX + 19 <= brickrect.x || ballposX +1 >= brickrect.width){
									ballXdir = - ballXdir;
								}else{
									ballYdir = - ballYdir;
								}

								break A;
							}
						}else if(mapL3.map[i][j] ==2){

							int brickX = j*mapL3.brickwidth+80;
							int brickY = i * mapL3.brickheight + 50;

							int brickwidth = mapL3.brickwidth;
							int brickheight = mapL3.brickheight;

							Rectangle rect = new Rectangle(brickX,brickY, brickwidth,brickheight);
							Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);

							Rectangle brickrect  = rect;

							if(ballRect.intersects(brickrect)){
								System.out.println("intersect");

								ballYdir = -ballYdir;


								break A;
							}
						}
					}
				}
			}


			ballposX += ballXdir;
			ballposY += ballYdir;
			//left border
			if(ballposX < 0){
				ballXdir = - ballXdir;
			}
			//top border
			if(ballposY < 0){
				ballYdir = - ballYdir;
			}
			//right border
			if(ballposX > 670 ){
				ballXdir = - ballXdir;
			}
		}

		repaint();
	}

}
