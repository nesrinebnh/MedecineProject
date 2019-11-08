import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;


public class Menu extends JPanel implements KeyListener, ActionListener {
	private JPanel jp2;

	private JButton btnLevel1,btnLevel2,btnLevel3;
	private JFrame obj ;

	public Menu(JFrame obj){
		this.obj = obj;
		//Add panel for buttons
		jp2 = new JPanel();
		jp2.setLayout(new GridBagLayout());
		jp2.setBackground(Color.black);

		// set General Layout for the General Panel Menu
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(Box.createVerticalStrut(100));
		//add text
		JLabel label1 = new JLabel("Brickout Ball",JLabel.CENTER);
		label1.setForeground(Color.yellow);
		label1.setFont(new Font("Serif", Font.PLAIN, 30));
		//attche to general panel
		add(label1,gbc);
		//add space
		add(Box.createVerticalStrut(150));

		//create button
		setBackground(Color.black);
		btnLevel1 = new JButton("Start");
		btnLevel1.setBackground(Color.white);
		btnLevel1.addActionListener(this);
		//attach button to panel jp2
		jp2.add(btnLevel1,gbc);


		//attch jp2 to general panel
		add(jp2,gbc);

		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("src/logo.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		add(picLabel,gbc);

		//add Listener
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);



	}




	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		if(arg0.getSource() == btnLevel1){
			setVisible(false);

			JFrame f = new JFrame();
			f.setBounds(10,10,700,600);
			f.setTitle("Brickout Ball");
			f.setResizable(false);
			f.setVisible(true);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.getContentPane().setBackground( Color.black );
			obj.setVisible(false);
			GamePlay gp = new GamePlay(obj,1,0);
			f.add(gp);

		}

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
