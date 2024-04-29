package com.example.tictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class DiagonalLineView1 extends View {

    private Paint paint;

    int[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};

    public DiagonalLineView1(Context context) {
        super(context);
        init();
    }

    public DiagonalLineView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DiagonalLineView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(0xFF00FF00); // Red color
        paint.setStrokeWidth(6); // Line width
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Draw a diagonal line from top-left to bottom-right
        canvas.drawLine(getWidth(), 0, 0, getHeight(), paint);
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
 */
