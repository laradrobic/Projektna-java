package projektnaJava;
import java.awt.*;

public class Shape{
    protected int[][] shapeMatrix;
    protected Color color;

    public Shape(int[][] shapeMatrix, Color color){
        this.shapeMatrix = shapeMatrix;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public static final Shape[]LIKI = {
        new Shape(new int[][] {{0, 0}, {0, 1}, {0, 2}, {1, 0}, {1 , 1}, {1, 2}, {2, 0}, {2, 1}, {2, 2}}, new Color(218, 108, 108)), // 3*3
        new Shape(new int[][] {{0,0}}, new Color(251, 243, 185)), //kocka
        new Shape(new int[][] {{0,0}}, new Color(251, 243, 185)), //kocka
        new Shape(new int[][] {{0,0}}, new Color(251, 243, 185)), //kocka 
        new Shape(new int[][] {{0,0}}, new Color(251, 243, 185)), //kocka
        
        new Shape(new int[][] {{0, 0}, {1, 0}, {1 ,1}}, new Color(177, 188, 190)),
        new Shape(new int[][] {{0, 1}, {1, 0}, {1 ,1}}, new Color(217, 213, 200)),
        new Shape(new int[][] {{0, 0}, {0, 1}, {1, 0}}, new Color(235, 217, 209)),
        new Shape(new int[][] {{0, 0}, {1, 0}, {1 ,1}}, new Color(172, 181, 175)),
        

        new Shape(new int[][] {{0,0}, {1, 0}, {2, 0}, {2, 1}, {2, 2}}, new Color(204, 213, 174)),  // L
        new Shape(new int[][] {{0,0}, {0, 1}, {0, 2}, {1, 2}, {2, 2}}, new Color(224, 229, 182)), // l + 90
        new Shape(new int[][] {{0, 2}, {1, 2}, {2, 0}, {2, 1}, {2, 2}}, new Color(250, 237, 206)), // L + 180
        new Shape(new int[][] {{0, 0}, {0, 1}, {0, 2}, {1, 0}}, new Color(254, 250, 224)),

        new Shape(new int[][] {{0, 0}, {1, 0}, {1, 1}, {1, 2}}, new Color(159, 179, 223)), //Ležeči L 1*3
        new Shape(new int[][] {{0, 1}, {1 ,1}, {2, 0}, {2, 1}}, new Color(158, 198, 243)),
        new Shape(new int[][] {{0, 0}, {0, 1}, {0, 2}, {1, 0}}, new Color(179,235,242)),
        new Shape(new int[][] {{0, 0}, {1, 0}, {2, 0}, {2, 1}}, new Color(218, 240, 247)),

        new Shape(new int[][] {{0, 0}, {0, 1}, {0, 2}}, new Color(127, 85, 177)), //ravna črta
        new Shape(new int[][] {{0, 0}, {1 ,0}, {2, 0}}, new Color(183, 177, 242)), //pokončna ravna črte

        new Shape(new int[][] {{0, 0}, {0, 1}, {1, 0}, {1, 1}, {2, 0}, {2, 1}}, new Color(253, 183, 234)), //2*3
        new Shape(new int[][] {{0, 0}, {0, 1}, {0, 2}, {1, 0}, {1, 1}, {1, 2}}, new Color(247, 207, 216)), // 3 * 2

        new Shape(new int[][] {{0, 0}, {0, 1}, {1, 0}, {1, 1}}, new Color(166, 214, 214)), //2*2

        new Shape(new int[][] {{0, 2}, {1, 1}, {2, 0}}, new Color(254, 237, 228)), //diagonala
        new Shape(new int[][] {{0, 0}, {1, 1}, {2, 2}}, new Color(248, 214, 203)),

        new Shape(new int[][] {{0, 1}, {1, 0}, {1, 1}, {2, 1}}, new Color(255, 205, 178)), 
        new Shape(new int[][] {{0, 0}, {0, 1}, {0, 2}, {1, 1}}, new Color(255, 180, 162)),
        new Shape(new int[][] {{0, 1}, {1, 0}, {1, 1}, {1, 2}}, new Color(247, 178, 245)),
        new Shape(new int[][] {{0, 0}, {1, 0}, {1, 1}, {2, 0}}, new Color(235, 136, 177)),
        
        
        new Shape(new int[][] {{0, 1}, {1, 0}, {1, 1}, {2, 0}}, new Color(229, 152, 155)), //Z
        new Shape(new int[][] {{0, 0}, {0, 1}, {1, 1}, {1, 2}}, new Color(181, 130, 140)), //Z

    };
    
    public static Shape[] getAllShapes() {
    	return LIKI;		
	}

}


