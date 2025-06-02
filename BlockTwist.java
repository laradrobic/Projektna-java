import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class BlockTwist {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(()-> {
			JFrame frame = new JFrame("BLOCK TWIST");
			frame.setSize(1024, 768);
			frame.setMinimumSize(new Dimension(800, 600));
			frame.setResizable(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			GamePanel gamePanel = new GamePanel();
			frame.add(gamePanel);
			
			frame.setVisible(true);
		});
	}

}

@SuppressWarnings("serial")
class GamePanel extends JPanel{
	private final int ROWS = 8;
	private final int COLS = 8;
	private int cell_size = 60;
	private int grid_width = COLS * cell_size;
	private int grid_height = ROWS * cell_size;
	private int grid_x = 100; //premik leve
	private int grid_y = 100; // premik od vrha
	private final int SHAPE_NUM = 3;
	private final int SHAPE_SIZE = 3;
	private final int TABLE_ROWS = SHAPE_SIZE * SHAPE_NUM + SHAPE_NUM - 1;
	private final int TABLE_COLS = SHAPE_SIZE;
	private int table_width = TABLE_COLS * cell_size;
	private int table_height = TABLE_ROWS * cell_size;
	private int table_x = 2 * grid_x + grid_width;
	private int table_y = grid_y - cell_size;
	private int chosen_shape = -1;
	private int chosen_row = -1;
	private int chosen_col = -1;
	
	private Color[][] grid = new Color[ROWS][COLS]; // 2D matrika (null- prazno)
	private Shape[] table = new Shape[SHAPE_NUM];
	private int score = 0;
	private JLabel scoreLabel;
	private Random random = new Random();
	
	public GamePanel() {
		setLayout(null);
		setBackground(new Color(240, 240, 240));
		
		scoreLabel = new JLabel("Score: 0");
		scoreLabel.setBounds(grid_x, 20, 200, 30);
		scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
		add(scoreLabel);
		
		generateShapes();
		
		// MouseListener za klik
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				
					
				if (chosen_shape < 0) {
					if (x > table_x && y > table_y) {
						int row = (y - table_y) / cell_size;
						int col = (x- table_x) / cell_size;
						
						if (row < TABLE_ROWS && col < TABLE_COLS) {
							int ind = row / (SHAPE_SIZE + 1);
							if (row - ind * (SHAPE_SIZE + 1) < SHAPE_SIZE) {
								chosen_col = col;
								chosen_row = row - ind * (SHAPE_SIZE + 1);
								if (contains(table[ind], chosen_row, chosen_col) == true) {
									chosen_shape = ind;
									repaint();
								}
							}
						}
					}	
					System.out.println(chosen_row +" "+chosen_col);
				}
				else {
					if (x < grid_width + grid_x && grid_x < x && y < grid_height + grid_y && grid_y < y) {
					
						// izračunamo na katerem mestu je bil klik
						int col = (x - grid_x)/ cell_size;
						int row = (y - grid_y)/cell_size;
						
						// če je klik znotraj mreže in polje prazno
						if (col >= 0 && col < COLS && row >= 0 && row < ROWS && canPlace(table[chosen_shape], row, col)){
							/*if (free(table[chosen_shape],row, col ) == true) {
							}*/
							placeShape(table[chosen_shape], row, col);
							table[chosen_shape] = null;
							chosen_shape = -1;
							checkAndClearLines();
							repaint();
							
							// če so vsi liki uporabljeni generiramo nove
							if (allUsed()) {
								generateShapes();
							
							/*
							if (grid[row][col] == null) {
								grid[row][col] = 1; //postavi blok
								repaint();
								checkAndClearLines(); //preveri in izbriše polne vrstice
							}
							*/
						}
						
					}
						System.out.println(row+" "+col);
				}
				
			};
		
		// tajmer za animacijo, lahko tudi a premikanje kasnej
		/*Timer timer = new Timer(100, e -> repaint()); // lahko še probaš dat notr hasGridChanged
		timer.start();*/
		}});
	}
		
	private void generateShapes() {
		Shape[]options = Shape.getAllShapes();
		for (int i = 0; i < SHAPE_NUM; i++) {
			table[i] = options[random.nextInt(options.length)];
		}
	}
	
	private boolean canPlace(Shape shape, int row, int col) {
		for (int[] block : shape.shapeMatrix) {
			int newRow = row + block[0] - chosen_row;
			int newCol = col + block[1] - chosen_col;
			if (newRow < 0 || newRow >= ROWS || newCol < 0 || newCol >= COLS || grid[newRow][newCol] != null) {
				return false;
			}
		}
		return true;
	}
	
	private boolean allUsed() {
		for (Shape s : table) {
			if (s != null) {
				return false;		
			}
		}
		return true;
	}
	
	private void placeShape(Shape shape, int row, int col) {
		for (int[]block : shape.shapeMatrix) {
			int newRow = row + block[0] - chosen_row;
			int newCol = col + block[1] - chosen_col;
			grid[newRow][newCol] = shape.color;
		}
	}
	public boolean contains(Shape shape, int row, int col) {
		for (int i = 0; i < shape.shapeMatrix.length; i++) {
			if (col == shape.shapeMatrix[i][1] && row == shape.shapeMatrix[i][0])
				return true;
		}
		return false;
	}
	/*
	public boolean free(Shape shape, int row, int col) {
		for (int i = 0; i < shape.shapeMatrix.length; i++) {
			int newRow = row + shape.shapeMatrix[i][0] - chosen_row;
			int newCol = col + shape.shapeMatrix[i][1] - chosen_col;
			if (newRow < 0 || newCol < 0 || newRow < ROWS || newCol < COLS) {
				continue;
			}
			if (grid[newRow][newCol] != null)
				return false;
		}
		return true;
	} */
	
	public void polni() {
		for(int i = 0; i < SHAPE_NUM; i++) {
			table[i] = Shape.LIKI[(int)(Math.random()*SHAPE_NUM)];
		}
	}
	private void checkAndClearLines() {
		// preverja vrstice
		for (int row = 0; row < ROWS; row++) {
			boolean full = true;
			
			for (int col = 0; col < COLS; col++) {
				if (grid[row][col] == null) {
					full = false;
					break;
				}
			}
			if (full) {
				// počisti vrstico
				for (int col = 0; col < COLS; col++) {
					grid[row][col] = null;
				}
				score += 10;
			}
		}
		// preverja stolpce
		for (int col = 0; col < COLS; col++) {
			boolean full = true;
			for (int row = 0; row < ROWS; row++) {
				if (grid[row][col] == null) {
					full = false;
					break;
				}
			}
			if (full) {
				//počisti stolpec
				for (int row = 0; row < ROWS; row++) {
					grid[row][col] = null;
				}
				score += 10;
			}
		}
		updateScore();
	}
	private void updateScore() {
		scoreLabel.setText("Score: " + score);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//okvir igralnega polja
		for (int row = 0; row < ROWS ; row++) {
			for (int col = 0; col < COLS; col++) {
				int x = grid_x + col * cell_size;
				int y = grid_y+ row * cell_size;
				
				if (grid[row][col] != null) {
					g.setColor(grid[row][col]);
				}else {
					g.setColor(Color.LIGHT_GRAY);
				}
				g.fillRect(x, y, cell_size, cell_size);
				g.setColor(Color.GRAY);
				g.drawRect(x, y, cell_size, cell_size);
			}
		}
		//nriše like na desni strani v tabeli
		for (int i = 0; i < SHAPE_NUM; i++) {
			Shape shape = table[i];
			if (shape == null) continue;
			
			int shapeRowOffset = i * (SHAPE_SIZE +1);
			
			for (int[] block : shape.shapeMatrix) {
				int row = block[0];
				int col = block[1];
				int x = table_x + col * cell_size;
				int y = table_y + (row + shapeRowOffset) * cell_size;
				
				g.setColor(shape.getColor());
				g.fillRect(x, y, cell_size, cell_size);
				g.setColor(Color.DARK_GRAY);
				g.drawRect(x, y, cell_size, cell_size);
			}
		}
		if (chosen_shape >= 0) {
			int col = table_x + chosen_col * cell_size + cell_size / 2;
			int row = table_y + (chosen_row + (SHAPE_SIZE + 1) * chosen_shape)*cell_size + cell_size / 2;
			int length = 10;
			
			g.drawLine(col - length, row - length, col + length, row + length);
			g.drawLine(col - length, row + length, col + length, row - length);
			
			System.out.println(row+" "+col);
		}
	}
}