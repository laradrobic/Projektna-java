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
        new Shape(new int[][] {{0, 0}, {0, 1}, {0, 2}, {1, 0}, {1 , 1}, {1, 2}, {2, 0}, {2, 1}, {2, 2}}, Color(rgb(218, 108, 108))), // 3*3
        new Shape(new int[][] {{0,0}}, Color(rgb(251, 243, 185))), //kocka

        new Shape(new int[][] {{0,0}, {1, 0}, {2, 0}, {2, 1}, {2, 2}}, Color(rgb(204, 213, 174))),  // L
        new Shape(new int[][] {{0,0}, {0, 1}, {0, 2}, {1, 1}, {2, 2}}, Color(rgb(224, 229, 182))), // l + 90
        new Shape(new int[][] {{0, 2}, {1, 2}, {2, 0}, {2, 1}, {2, 2}}, Color(rgb(250, 237, 206))), // L + 180
        new Shape(new int[][] {{0, 0}, {0, 1}, {0, 2}, {1, 0}, {2, 0}}, Color(rgb(254, 250, 224))),

        new Shape(new int[][] {{0, 0}, {1, 0}, {1, 1}, {1, 2}}, Color(rgb(159, 179, 223))), //Ležeči L 1*3
        new Shape(new int[][] {{0, 1}, {1 ,1}, {2, 0}, {2, 1}}, Color(rgb(158, 198, 243))),
        new Shape(new int[][] {{0, 0}, {0, 1}, {0, 2}, {1, 2}}, Color(rgb(179,235,242))),
        new Shape(new int[][] {{0, 0}, {0, 1}, {1, 0}, {1, 2}}, Color(rgb(218, 240, 247))),

        new Shape(new int[][] {{0, 0}, {0, 1}, {0, 2}}, Color(rgb(127, 85, 177))), //ravna črta
        new Shape(new int[][] {{0, 0}, {1 ,0}, {2, 1}}, Color(rgb(183, 177, 242))), //pokončna ravna črte

        new Shape(new int[][] {{0, 0}, {0, 1}, {1, 0}, {1, 1}, {2, 0}, {2, 1}}, Color(rgb(253, 183, 234))), //2*3
        new Shape(new int[][] {{0, 0}, {0, 1}, {0, 2}, {1, 0}, {1, 1}, {1, 2}}, Color(rgb(247, 207, 216))), // 3 * 2

        new Shape(new int[][] {{0, 0}, {0, 1}, {1, 0}, {1, 1}}, Color(rgb(166, 214, 214))), //2*2

        new Shape(new int[][] {{0, 2}, {1, 1}, {2, 0}}, Color(rgb(254, 237, 228))), //diagonala
        new Shape(new int[][] {{0, 0}, {1, 1}, {2, 2}}, Color(rgb(248, 214, 203))),

        new Shape(new int[][] {{0, 1}, {1, 0}, {1, 1}, {2, 1}}, Color(rgb(255, 205, 178))), 
        new Shape(new int[][] {{0, 1}, {0, 2}, {1, 0}, {1, 1}}, Color(rgb(255, 180, 162))),
        new Shape(new int[][] {{0, 1}, {1, 0}, {1, 1}, {2, 0}}, (Color(rgb(229, 152, 155)))),
        new Shape(new int[][] {{0, 0}, {0, 1}, {1, 1}, {1, 2}}, Color(rgb(181, 130, 140))),

    }

}




