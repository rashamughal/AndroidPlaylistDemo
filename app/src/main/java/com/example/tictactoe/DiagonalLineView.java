package com.example.tictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class DiagonalLineView extends View {


    //here paint is an object of Class Paint, which will define how the line will be drawn with a color and a stroke
    private Paint paint;

    int[][] winPositions={{0,1,2},{3,4,5},{6,7,8},{0,3,6},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}};


    // these are the three constructors in which a method init() will be called

    // In DiagonalLineView class, "context" is used to set up the Paint object with a specific color.
    //The Context isn't directly involved in this operation, but it's often used when dealing with
    // resources or system services. In this case, it's part of the general Android programming environment.
    public DiagonalLineView(Context context) {
        super(context);
        init();
    }
    //"AttributeSet attrs" ,  It allows you to read and interpret the XML attributes set in the layout file.
    // For example, you might use it to customize the behavior or appearance of the custom view.
    //"DiagonalLineView" is the custom view defined in activity_main.xml , the attributes of this view defined in the xml layout file
    //is passed through parameter " AttributeSet attrs ". The custom attributes starting with app: in activity_main.xml are
    // accessible to your custom view through the AttributeSet parameter in the constructor of DiagonalLineView1.
    public DiagonalLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    //The defStyleAttr is used to apply a default style from the theme if specified. if there is no style specified
    // in layout XML file, this parameter will allow the View object ( means when view is created from layout xml in inflation process)
    // to inherit style defined in current theme
    public DiagonalLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    // in this init() method an object of paint is created
    //and the attributes of color and stroke are setting here by calling
    // setColor and setStrokeWidth
    private void init() {
        paint = new Paint();
        paint.setColor(0xFF00FF00); // Red color
        paint.setStrokeWidth(6); // Line width
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Draw a diagonal line from top-left to bottom-right
        canvas.drawLine(0, 0,getWidth(), getHeight(), paint); // these get functions will return the height of canvas
    }
}
/*
0,0 __________________________ (getWidth(), 0)
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
   |                           |
(0, getHeight()) _____________ (getWidth(), getHeight())

The line starts from the top-left corner (0, 0) and goes to the bottom-right corner (getWidth(), getHeight()).
This results in a diagonal line that spans the entire width and height of the canvas.
The paint object defines the color and style of the line.
 */

