package obause.example.dreigewinnt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int activePlayer = 0;
    boolean gameActive = true;
    //0: Gelb, 1: Rot, 2: Leer
    int[] gameState = {2,2,2,2,2,2,2,2,2};

    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    Button playAgainButton;
    TextView winnerTextView;

    public void dropIn (View view) {

        ImageView counter = (ImageView) view;
        Log.i("TAG", counter.getTag().toString());

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameActive == true) {

            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1500);

            if(activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            }
            else if (activePlayer == 1) {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1500).rotation(3600).setDuration(300);

            for (int[] winningPosition: winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {

                    String winner = "";
                    gameActive = false;

                    if(activePlayer == 1) {
                        winner = "Gelb";
                    }
                    else if (activePlayer == 0) {
                        winner = "Rot";
                    }

                    playAgainButton.setVisibility(View.VISIBLE);

                    winnerTextView.setText(winner + " hat gewonnen!");
                    winnerTextView.setVisibility(View.VISIBLE);
                }
                else {
                    gameActive = false;

                    for (int counterState: gameState) {
                        if (counterState == 2) gameActive = true;
                    }

                    if (!gameActive) {
                        playAgainButton.setVisibility(View.VISIBLE);

                        winnerTextView.setText("Unentschieden!");
                        winnerTextView.setVisibility(View.VISIBLE);
                    }
                }
            }
        }

    }

    public void playAgain(View view) {
        playAgainButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {

            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }

        activePlayer = 0;
        gameActive = true;

        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playAgainButton = findViewById(R.id.playAgainButton);
        winnerTextView = findViewById(R.id.textView);
    }
}