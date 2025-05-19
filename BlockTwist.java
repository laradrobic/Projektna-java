import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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

    Shape[]liki = {
        new Shape(new int[][] {{0, 0}, {0, 1}, {0, 2}, {1, 0}, {1 , 1}, {1, 2}, {2, 0}, {2, 1}, {2, 2}}, Color.RED), // 3*3
        new Shape(new int[][] {{0,0}}, ), //kocka

        new Shape(new int[][] {{0,0}, {1, 0}, {2, 0}, {2, 1}, {2, 2}}),  // L
        new Shape(new int[][] {{0,0}, {0, 1}, {0, 2}, {1, 1}, {2, 2}}), // l + 90
        new Shape(new int[][] {{0, 2}, {1, 2}, {2, 0}, {2, 1}, {2, 2}}), // L + 180
        new Shape(new int[][] {{0, 0}, {0, 1}, {0, 2}, {1, 0}, {2, 0}}),

        new Shape(new int[][] {{0, 0}, {1, 0}, {1, 1}, {1, 2}}), //Ležeči L 1*3
        new Shape(new int[][] {{0, 1}, {1 ,1}, {2, 0}, {2, 1}}),
        new Shape(new int[][] {{0, 0}, {0, 1}, {0, 2}, {1, 2}}),
        new Shape(new int[][] {{0, 0}, {0, 1}, {1, 0}, {1, 2}}),

        new Shape(new int[][] {{0, 0}, {0, 1}, {0, 2}}), //ravna črta
        new Shape(new int[][] {{0, 0}, {1 ,0}, {2, 1}}), //pokončna ravna črte

        new Shape(new int[][] {{0, 0}, {0, 1}, {1, 0}, {1, 1}, {2, 0}, {2, 1}}), //2*3
        new Shape(new int[][] {{0, 0}, {0, 1}, {0, 2}, {1, 0}, {1, 1}, {1, 2}}), // 3 * 2

        new Shape(new int[][] {{0, 0}, {0, 1}, {1, 0}, {1, 1}}), //2*2

        new Shape(new int[][] {{0, 2}, {1, 1}, {2, 0}}), //diagonala
        new Shape(new int[][] {{0, 0}, {1, 1}, {2, 2}}),

        new Shape(new int[][] {{0, 1}, {1, 0}, {1, 1}, {2, 1}}), 
        new Shape(new int[][] {{0, 1}, {0, 2}, {1, 0}, {1, 1}}),
        new Shape(new int[][] {{0, 1}, {1, 0}, {1, 1}, {2, 0}}),
        new Shape(new int[][] {{0, 0}, {0, 1}, {1, 1}, {1, 2}}),

    }

}




