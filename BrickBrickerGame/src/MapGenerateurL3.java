import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class MapGenerateurL3 {

	public int map[][] ;
	public int brickwidth;
	public int brickheight;
	public MapGenerateurL3(int row,int col){
		map = new int[row][col];

		for(int i = 0;i<map.length;i++){
			for(int j = 0; j < map[0].length ; j++){
				map[i][j] = 1;



			}
		}
		for(int i = 0;i<6;i++){
			Random rand = new Random();
			int k = rand.nextInt(3);
			int j = rand.nextInt(7);
			map[k][j] = 2;
		}

		brickwidth = 540/col;
		brickheight = 150/row;


	}

	public void draw(Graphics2D g){
		for(int i = 0;i<map.length;i++){
			for(int j = 0; j < map[0].length ; j++){
				if(map[i][j]==1){
					g.setColor(Color.white);
					g.fillRect(j*brickwidth+80, i*brickheight + 50, brickwidth, brickheight);
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect(j*brickwidth+80, i*brickheight + 50, brickwidth, brickheight);
				}else if(map[i][j]==2){
					g.setColor(Color.red);
					g.fillRect(j*brickwidth+80, i*brickheight + 50, brickwidth, brickheight);
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect(j*brickwidth+80, i*brickheight + 50, brickwidth, brickheight);
				}
			}
		}
	}

	public void setBrickValue(int value, int row, int col){
		map[row][col] = value;
	}

}
