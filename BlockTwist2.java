package projektna;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class BlockTwist {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
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
class GamePanel extends JPanel {
    private final int ROWS = 8;
    private final int COLS = 8;
    private int cell_size = 60;
    private int grid_x = 100;
    private int grid_y = 100;
    private final int SHAPE_NUM = 3;
    private final int SHAPE_SIZE = 3;
    private final int TABLE_ROWS = SHAPE_SIZE * SHAPE_NUM + SHAPE_NUM - 1;
    private final int TABLE_COLS = SHAPE_SIZE;
    private int table_x, table_y;
    private int chosen_shape = -1;
    private int chosen_row = -1;
    private int chosen_col = -1;
    private boolean dragging = false;
    private int dragX, dragY;

    private Color[][] grid = new Color[ROWS][COLS];
    private Shape[] table = new Shape[SHAPE_NUM];
    private int score = 0;
    private JLabel scoreLabel;
    private Random random = new Random();

    public GamePanel() {
        setLayout(null);
        setBackground(new Color(240, 240, 240));

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(scoreLabel);

        generateShapes();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustLayout(getWidth(), getHeight());
                revalidate();
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                if (chosen_shape < 0) {
                    selectShape(x, y);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (dragging && chosen_shape >= 0) {
                    dragging = false;
                    placeDraggedShape(e.getX(), e.getY());
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (chosen_shape >= 0) {
                    dragging = true;
                    dragX = e.getX();
                    dragY = e.getY();
                    repaint();
                }
            }
        });

        adjustLayout(getWidth(), getHeight());
    }

    private void adjustLayout(int newWidth, int newHeight) {
        int padding = 50;
        cell_size = Math.max(40, Math.min((newWidth - 2 * padding) / COLS, (newHeight - 2 * padding) / ROWS));

        grid_x = padding;
        grid_y = padding;

        table_x = grid_x + COLS * cell_size + padding;
        table_y = grid_y;

        scoreLabel.setBounds(grid_x, 10, 200, 30);

        repaint();
    }

    private void generateShapes() {
        Shape[] options = Shape.getAllShapes();
        for (int i = 0; i < SHAPE_NUM; i++) {
            table[i] = options[random.nextInt(options.length)];
        }
    }

    private void selectShape(int x, int y) {
        if (x > table_x && y > table_y) {
            int row = (y - table_y) / cell_size;
            int col = (x - table_x) / cell_size;

            if (row < TABLE_ROWS && col < TABLE_COLS) {
                int ind = row / (SHAPE_SIZE + 1);
                if (row - ind * (SHAPE_SIZE + 1) < SHAPE_SIZE) {
                    chosen_col = col;
                    chosen_row = row - ind * (SHAPE_SIZE + 1);
                    if (contains(table[ind], chosen_row, chosen_col)) {
                        chosen_shape = ind;
                    }
                }
            }
        }
    }

    private void placeDraggedShape(int x, int y) {
        int col = (x - grid_x) / cell_size;
        int row = (y - grid_y) / cell_size;

        if (col >= 0 && col < COLS && row >= 0 && row < ROWS && canPlace(table[chosen_shape], row, col)) {
            placeShape(table[chosen_shape], row, col);
            table[chosen_shape] = null;
            chosen_shape = -1;
            checkAndClearLines();
        }
        repaint();
    }

    private boolean contains(Shape shape, int row, int col) {
        for (int[] block : shape.shapeMatrix) {
            if (col == block[1] && row == block[0])
                return true;
        }
        return false;
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

    private void placeShape(Shape shape, int row, int col) {
        for (int[] block : shape.shapeMatrix) {
            int newRow = row + block[0] - chosen_row;
            int newCol = col + block[1] - chosen_col;
            grid[newRow][newCol] = shape.color;
        }
    }

    private void checkAndClearLines() {
        for (int row = 0; row < ROWS; row++) {
            if (isFullRow(row)) clearRow(row);
        }
        for (int col = 0; col < COLS; col++) {
            if (isFullCol(col)) clearCol(col);
        }
        updateScore();
    }

    private boolean isFullRow(int row) {
        for (int col = 0; col < COLS; col++) if (grid[row][col] == null) return false;
        return true;
    }

    private void clearRow(int row) {
        for (int col = 0; col < COLS; col++) grid[row][col] = null;
        score += 10;
    }

    private void updateScore() {
        scoreLabel.setText("Score: " + score);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (dragging && chosen_shape >= 0) {
            Shape shape = table[chosen_shape];
            if (shape != null) {
                for (int[] block : shape.shapeMatrix) {
                    int x = dragX + (block[1] - chosen_col) * cell_size;
                    int y = dragY + (block[0] - chosen_row) * cell_size;
                    g.setColor(shape.getColor());
                    g.fillRect(x, y, cell_size, cell_size);
                    g.setColor(Color.GRAY);
                    g.drawRect(x, y, cell_size, cell_size);
                }
            }
        }
    }
}
