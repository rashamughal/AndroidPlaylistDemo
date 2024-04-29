package com.example.tictactoe;

import static com.example.tictactoe.R.drawable.line;
import static com.example.tictactoe.R.drawable.line_vertical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.stream.IntStream; /// Please search later
import java.util.Arrays;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.VideoView;
import android.view.SurfaceView;
public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    boolean gameActive=true; /// game is active / inactive

    public static final int MAX_NO_OF_GAME_STATES = 9; // Defines the maximum number of game states

    enum Player {
        O, // Player 1
        X  // Player 2
    }

    // Represents the current state of the game board
    // Each element can have the values: Player.O (O), Player.X (X), null (Empty)
    Player[] gameState = {null, null, null, null, null, null, null, null, null};

    // Represents the active player: Player.O for O, Player.X for X
    Player activePlayer = Player.X; // Player.O: O, Player.X: X

    // Represents the winning combinations on the game board
    int[][] winingPositions = {       // +---+---+---+
            // Rows                col |   | 0 | 1 | 2 |
            {0, 1, 2},           //    |   +---+---+---+
            {3, 4, 5},           //    |   | 3 | 4 | 5 |
            {6, 7, 8},           //    |   +---+---+---+
                                 //    |   | 6 | 7 | 8 |
            // Columns           //     +---+---+---+ row
            {0, 3, 6},           //   Winning Combinations:
            {1, 4, 7},           //    1. Rows:
            {2, 5, 8},           //     - Row 1: [0, 1, 2]
                                 //     - Row 2: [3, 4, 5]
            // Diagonals         //     - Row 3: [6, 7, 8]
            {0, 4, 8},           //    2. Columns:
            {2, 4, 6}            //    - Column 1: [0, 3, 6]
                                 //    - Column 2: [1, 4, 7]
                                 //    - Column 3: [2, 5, 8]
    };
    /*

      3. Diagonals:
            - Diagonal 1: [0, 4, 8]
            - Diagonal 2: [2, 4, 6]

    Diagonals:
            0 |   | 8
            -----------
              | 4 |
            -----------
            2 |   | 6

     */
    //

    int index_winning_combination_row = 0;

    // This is checkWinCondition Function
    // int[] positions is a combination,an array,passed to function and a player X or O
    private boolean checkWinCondition(int[] positions, Player player) { // this function checks the gamestate on indexes for e.g{0,3,6}(combination

//        Log.d("TicTacToe", "Checking win condition for player " + player);
//        Log.d("TicTacToe", "Positions: " + Arrays.toString(positions));
//        Log.d("TicTacToe", "Game state: " + Arrays.toString(gameState));
        //if values on all of the 3 indexes are equal and if all on the positions it is PlayerX,
        //it returns true anf if not then it will return false
        return (gameState[positions[0]] != null &&
                gameState[positions[0]] == gameState[positions[1]] &&
                gameState[positions[1]] == gameState[positions[2]]&& gameState[positions[0]]==player);
    }

    //  playerTap function
    public void playerTap(View view){
        ImageView img=(ImageView) view;
        int tappedImage=Integer.parseInt(img.getTag().toString());
        // this line is converting the tag of an ImageView to an integer,
        // and the resulting value is stored in the tappedImage variable.
        //"img.getTag()" it is retrieving tag associated with imageView  means "img" object here
        //toString() is called to make its String representation
        //Integer.parseInt is converting the String representation of Tag to Integer and it is then stored in tappedImage variable
        ////This variable now holds the integer value extracted from the tag associated with the ImageView.

        if(!gameActive){
            gameReset(view);
            return;
        }
        //tappedImage is here used to represent index of gamestate array
        if(gameState[tappedImage]== null){
            gameState[tappedImage]=activePlayer;
            img.setTranslationY(-1000f); //This line immediately sets the Y-translation property of the ImageView (img) to -1000 pixels.
            // It means the image is moved vertically upwards by 1000 pixels instantly,
            // without any animation. This kind of operation results in an immediate change of the view's position.
            if(activePlayer==Player.X){
                img.setImageResource(R.drawable.x);
                activePlayer=Player.O;
                TextView Status= findViewById(R.id.Status);
                Status.setText("O's turn-Tap To Play");
            }
            else{
                //this part will execute when activePlayer is Player.O ( O)
                img.setImageResource(R.drawable.o);
                activePlayer=Player.X;
                TextView Status= findViewById(R.id.Status);
                Status.setText("X's turn-Tap To Play");
            }
            img.animate().translationYBy(1000f).setDuration(300);// this line is creating an animation for the ImageView (img) to move
            // it upwards by 1000 pixels with a duration of 300 milliseconds. It's a smooth transition that gives the appearance of the image sliding or fading in from the top of the screen.
            //the key difference is that the first line sets the position immediately, while the second line creates an animation for a gradual transition over a specified duration.
        }

        // Check if the game is a draw
        boolean isDraw = Arrays.stream(gameState).allMatch(position -> position != null);

        index_winning_combination_row = 0;

        // winPosition is an array of size 3
        int[] winPosition = new int[3];

        // here int[] combination is a variable,combination represents one of the winning combinations.
        // winingPositions is a multidimensional array of all winning combinations {0,1,2},{3,4,5},..................{6,7,8}

        for (int[] combination : winingPositions) { // This loop will iterate through all the winning combinations.

            //The line System.arraycopy(combination, 0, winPosition, 0, 3); is using the System.arraycopy method to copy elements
            //from one array (combination) to another array (winPosition).
            //'combination' : The source array from which elements will be copied.
            //'0' : The starting index in the combination array from where the copying will begin.
            //'winPosition: The destination array where elements will be copied.
            //'3: The number of elements to copy.
            System.arraycopy(combination, 0, winPosition, 0, 3);

            //checkWinCondition is function call, with passing two arguments,1 is winning combination for e.g{0,1,2} and 2nd is Player.X
            if (checkWinCondition(winPosition, Player.X)) {
                displayWin(winPosition, "x", index_winning_combination_row);// DisplayWin is also function call here, with arguments , it is passing the combination and a String "X"
                                                            // checkWinCondition function returns true or false here
                
            } else if (checkWinCondition(winPosition, Player.O)) {
                displayWin(winPosition, "O", index_winning_combination_row);

            }
            index_winning_combination_row++;
        }

        // Check for a draw
        if (isDraw) {
            gameActive = false;
            TextView Status = findViewById(R.id.Status);
            Status.setText("It's a Draw! Tap to Play Again");
        }

    }



    // this is a DisplayWin function and winning combination is passed to this function and a String is passed
    // int [] winPosition is a parameter of displayWin
    private void displayWin(int[] winPosition, String winner, int param_index_winning_combination_row) {
        gameActive = false;
        String winnerStr = winner + " has won";
        TextView status = findViewById(R.id.Status);
        status.setText(winnerStr);

        for (int i = 0; i < winPosition.length; i++) {
            if ( param_index_winning_combination_row == 0 ) { //FOR win positions {0,1,2}, row 1
                findViewById(R.id.line_horizontal).setVisibility(View.VISIBLE);
            }else if (param_index_winning_combination_row == 1 ) { // For win positions {3,4,5}, row 2
                findViewById(R.id.line_horizontal2).setVisibility(View.VISIBLE);
            }
            else if ( param_index_winning_combination_row == 2) {             //FOR win position {6,7,8}, row 3
                findViewById(R.id.line_horizontal3).setVisibility(View.VISIBLE);
            }
            else if ( param_index_winning_combination_row == 3 ) {  //FOR win positions {0,3,6}, column 1
                findViewById(R.id.line_vertical).setVisibility(View.VISIBLE);
            }
            else if ( param_index_winning_combination_row == 4 ){    //FOR win positions {1,4,7} , column 2
                findViewById(R.id.line_vertical2).setVisibility(View.VISIBLE);
            }
            else if(param_index_winning_combination_row==5){     //FOR win position {2,5,8}, column 3
                findViewById(R.id.line_vertical3).setVisibility(View.VISIBLE);
            }
            else if(param_index_winning_combination_row==6){    //FOR win position {0,4,8}, diagonal 1 (top left to bottom right)
                findViewById(R.id.line_diagonal).setVisibility(View.VISIBLE); // this setVisibility(View.Visible) is passed
                                                                              // in DiagonalLineView class's constructor parameter "AttributeSet"
            }else if(param_index_winning_combination_row==7){   //FOR win position {2,4,6}, diagonal 2 (top right to bottom left)
                findViewById(R.id.line_diagonal4).setVisibility(View.VISIBLE);//this setVisibility(View.Visible) is passed
                // in DiagonalLineView1 class's constructor parameter "AttributeSet"
            }

        }
    }

    //Game Reset function
    public void gameReset(View view){
        gameActive=true;
        activePlayer=Player.X;
        //We can only use Arrays.fill , if we want to change the elements of an array
        // which already exists and it has elements already.
        Arrays.fill(gameState, null);
        //setting all the image resources to 0, means there would be no image of X or O on gameboard
        ((ImageView)findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView8)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView9)).setImageResource(0);

        //Setting all the winning lines to INVISIBLE
        findViewById(R.id.line_horizontal).setVisibility(View.INVISIBLE);
        findViewById(R.id.line_vertical).setVisibility(View.INVISIBLE);
        findViewById(R.id.line_horizontal2).setVisibility(View.INVISIBLE);
        findViewById(R.id.line_horizontal3).setVisibility(View.INVISIBLE);
        findViewById(R.id.line_vertical2).setVisibility(View.INVISIBLE);
        findViewById(R.id.line_vertical3).setVisibility(View.INVISIBLE);
        findViewById(R.id.line_diagonal).setVisibility(View.INVISIBLE);
        findViewById(R.id.line_diagonal4).setVisibility(View.INVISIBLE);

        //setting the status of the text to again , "X's turn-Tap To Play"
        // as we are resetting the game
        TextView Status= findViewById(R.id.Status);
        Status.setText("X's turn-Tap To Play");

    }
    //The Bundle is a container for key-value pairs, often used for passing data between activities.

    //VideoView backgroundVideo;
    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private SurfaceHolder surfaceHolder;
    private int currentPosition = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //This line calls the onCreate method of the superclass (in this case, the AppCompatActivity).
        // It ensures that the necessary setup defined by the superclass is executed before your specific setup.
        super.onCreate(savedInstanceState);
        //This line sets the content view of the activity to the layout defined in the XML file named "activity_main.xml.
        setContentView(R.layout.activity_main);

//        backgroundVideo=findViewById(R.id.bgVideo1);
//
//        //setVideoPath is a function which helps in attaching an  android resource to this videoview
//        backgroundVideo.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ict);
//        backgroundVideo.start();

        //Surface data refers to the graphical content that is displayed on the surface of a SurfaceView.
        // A SurfaceView is a special type of View in Android that is used for displaying graphics and video efficiently.
        // Initialize SurfaceView for video playback
        surfaceView = findViewById(R.id.surfaceView);  //is used to get a reference to the SurfaceView defined in the layout XML file with the ID surfaceView
        //surface holder is an object that holds the display surface and provides access to its underlying surface
        surfaceHolder = surfaceView.getHolder();
        //SurfaceHolder.Callback interface is implemented to handle events related to the SurfaceView (e.g., surface creation, changes, and destruction).
        surfaceHolder.addCallback(this);

        //Initialize MediaPlayer
        //mediaPlayer is an instance of MediaPlayer, which is used for playing audio and video, here it is playing video "ict".
        //The create method sets up the MediaPlayer for the specified resource "ict" video.
        mediaPlayer = MediaPlayer.create(this, R.raw.ict); //The current class (this) implements the SurfaceHolder.Callback interface, so it will handle the events.
        mediaPlayer.setLooping(true); // Loop the video
        //sets the MediaPlayer to loop the video indefinitely, so it will automatically restart from the beginning when it reaches the end.
    }

    //The onPause() method in Android is called when the activity is about to enter the paused state.
    // During this state transition, the system may pause certain activities to prioritize resources
    // for the foreground activity or other critical system operations.

    protected void onPause() {
        super.onPause();
        // Save current position and pause the video when the activity is paused
        currentPosition = mediaPlayer.getCurrentPosition();
        //here, explicitly video playback is paused, using mediaPlayer.pause().
        mediaPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Resume video playback from the saved position when the activity is resumed
        mediaPlayer.seekTo(currentPosition);
        mediaPlayer.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mediaPlayer.setDisplay(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // No implementation needed
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // No implementation needed
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer resources when the activity is destroyed
        mediaPlayer.release();
    }



}