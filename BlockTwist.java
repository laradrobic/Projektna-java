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
		scoreLabel.setFont(new Font("Algerian", Font.BOLD, 28));
		add(scoreLabel);
		
		//generiramo tri like na desnem polju
		generateShapes();
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				adjustLayout(getWidth(), getHeight());
				revalidate();
			}
			
			@Override
			public void componentShown(ComponentEvent e) {
				adjustLayout(getWidth(), getHeight());
				revalidate();
				repaint();
			}
		});	

		
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
										Shape shape = table[ind];
										if (shape != null) {
											int minRow = Integer.MAX_VALUE;
											int maxRow = Integer.MIN_VALUE;
											
											for (int[] block: shape.shapeMatrix) {
												minRow = Math.min(minRow, block[0]);
												maxRow = Math.max(maxRow, block[0]);
											}
											int shapeHeight = maxRow - minRow + 1;
											int centerOffset = (SHAPE_SIZE - shapeHeight) / 2;
											
											int blockRow = row - ind * (SHAPE_SIZE + 1) - centerOffset + minRow;
											
											if(contains(shape, blockRow, col)) {
												chosen_col = col;
												chosen_row = blockRow;
												chosen_shape = ind;
												repaint();
											}
											
										}
									}
								}
							}	
							System.out.println(chosen_row +" "+chosen_col);
						}
						else {
							int ind = chosen_shape;
							int minRow = Integer.MAX_VALUE;
							int maxRow = Integer.MIN_VALUE;
							Shape shape = table[ind];
							
							for (int[] block : shape.shapeMatrix) {
								minRow = Math.min(minRow, block[0]);
								maxRow = Math.max(maxRow, block[0]); 
							}
							int shapeHeight = maxRow - minRow + 1;
							int centerOffset = (SHAPE_SIZE - shapeHeight) / 2;
							
							//izračuna pozicijo klika
							int shapeRowOffset = ind * (SHAPE_SIZE + 1);
							int relativeRow = (y - table_y) / cell_size - shapeRowOffset;
							int relativeCol = (x - table_x) / cell_size;
							
							int blockRow = relativeRow - centerOffset + minRow;
							
							if (contains(shape, blockRow, relativeCol)) {
								//odoznači klik na že označenm liku
								chosen_shape = -1;
								chosen_col = -1;
								chosen_row = -1;
								repaint();
							}								
							else if (x < grid_width + grid_x && grid_x < x && y < grid_height + grid_y && grid_y < y) {
							
								// izračunamo na katerem mestu je bil klik
								int col = (x - grid_x)/ cell_size;
								int row = (y - grid_y)/cell_size;
								
								// če je klik znotraj mreže in polje prazno
								if (col >= 0 && col < COLS && row >= 0 && row < ROWS && canPlace(table[chosen_shape], row, col)){
									placeShape(table[chosen_shape], row, col);
									table[chosen_shape] = null;
									chosen_shape = -1;
									checkAndClearLines();
									repaint();
									
									// če so vsi liki uporabljeni generiramo nove
									if (allUsed()) {
										generateShapes();
									
								}
									// če v matriki na levi ni prostora da postavimo katerikoli lik iz desne se igra 'zaključi'
									if (!canPlaceShape()) {
										gameOver();
									}
								
							}
								System.out.println(row+" "+col);
						}
						
					};
				}});
			}
	private void adjustLayout(int newWidth, int newHeight) {
		int padding = 110;
		cell_size = Math.max(30, Math.min((newWidth - 2 * padding) / COLS, (newHeight - 2 * padding) / ROWS));
		grid_width = COLS * cell_size;
		grid_height = ROWS * cell_size;
		grid_x = padding;
		grid_y = padding;
		
		table_x = grid_x + grid_width + padding;
		table_y = grid_y + (grid_height - table_height) / 2;
		table_width = TABLE_COLS * cell_size;
		table_height = TABLE_ROWS  * cell_size;
		
		scoreLabel.setBounds(grid_x, grid_y - 60 ,200, 30);
		
		repaint();
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
	//preveri če smo porabili vse tri like iz desne
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
	
	private boolean canPlaceShape() {
		for(Shape shape: table) {
			if (shape == null) continue;
			
			for(int row = 0; row < ROWS; row++) {
				for(int col = 0; col < COLS; col++) {
					boolean canPlaceBoolean = true;
					for (int[] block: shape.shapeMatrix) {
						int newRow = row + block[0];
						int newCol = col + block[1];
						
						if (newRow < 0 || newRow >= ROWS || newCol < 0 || newCol >= COLS || grid[newRow][newCol] != null) {
							canPlaceBoolean= false;
							break;
						}
					}
					if (canPlaceBoolean) return true;
				}
			}
		}
	return false; //ne moremo nič več postaviti
}
	
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
	
	private void gameOver() {
		JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		GameOver dialog = new GameOver(topFrame, score, this::resetGame);
		dialog.setVisible(true);
	}
	
	private void resetGame() {
		for(int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				grid[row][col] = null;
			}
		}
		score = 0;
		updateScore();
		chosen_shape = -1;
		chosen_col = -1;
		chosen_row = -1;
		generateShapes();
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//Gradiantno ozadje
		GradientPaint gradient = new GradientPaint(0, 0, new Color(239, 226, 243), getWidth(), getHeight(), new Color(226, 243, 243));
		g2d.setPaint(gradient);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		//okvir igralnega polja
		for (int row = 0; row < ROWS ; row++) {
			for (int col = 0; col < COLS; col++) {
				int x = grid_x + col * cell_size;
				int y = grid_y+ row * cell_size;
				
				
				if (grid[row][col] != null) {
					//senca
					g2d.setColor(grid[row][col].darker());
					g2d.fillRoundRect(x + 3, y + 3, cell_size, cell_size, 12, 12);
					// osnovni blok
					g2d.setColor(grid[row][col]);
					g2d.fillRoundRect(x, y, cell_size, cell_size, 12, 12);
				}else {
					g2d.setColor(new Color(220, 220, 220));
				}
				//obroba
				g2d.setColor(new Color(180, 180, 180));
				g2d.drawRoundRect(x, y, cell_size, cell_size, 10, 10);

			}
		}
		
		//Nariše like na desni strani v tabeli
		for (int i = 0; i < SHAPE_NUM; i++) {
			Shape shape = table[i];
			if (shape == null) continue;
			
			int minRow = Integer.MAX_VALUE;
			int maxRow = Integer.MIN_VALUE;

			for (int[] block: shape.shapeMatrix) {
				minRow = Math.min(minRow, block[0]);
				maxRow = Math.max(maxRow, block[0]);
				}
			
			int shapeHeight = maxRow - minRow + 1;
			int shapeRowOffset = i * (SHAPE_SIZE +1);
			int centerOffset = (SHAPE_SIZE - shapeHeight) / 2;
			
			for (int[] block : shape.shapeMatrix) {
				int row = block[0] - minRow + centerOffset;
				int col = block[1];
				
				int x = table_x + col * cell_size;
				int y = table_y + (row + shapeRowOffset) * cell_size;
				
				//senca
				g2d.setColor(shape.getColor());
				g2d.fillRoundRect(x, y, cell_size, cell_size, 12, 12);
				
				// obroba
				g2d.setColor(Color.DARK_GRAY);
				g2d.drawRoundRect(x, y, cell_size, cell_size, 12, 12);

			}
		}
		
		//Nariše x kot indikator i
		if (chosen_shape >= 0) {
			int col = table_x + chosen_col * cell_size + cell_size / 2;
			int row = table_y + (chosen_row + (SHAPE_SIZE + 1) * chosen_shape)*cell_size + cell_size / 2;
			int length = 10;
			
			g2d.setColor(Color.BLACK);
			g2d.drawLine(col - length, row - length, col + length, row + length);
			g2d.drawLine(col - length, row + length, col + length, row - length);
			
			System.out.println(row+" "+col);
		}
	}
}