package cunliffe.jack.acw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class HighScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores_activity);

        Bundle extras = getIntent().getExtras();
        int tilesFlipped = extras.getInt("FlipCounter");
        int timer = extras.getInt("Timer");
        int longestSequence = extras.getInt("LongestSequence");

        int previousTilesFlipped = retrieveTilesFlipped();
        int previousTimer = retrieveTimer();
        int previousLongestSequence = retrieveLongestSequence();

        TextView tilesFlippedText = findViewById(R.id.flipCounterHighScore);
        TextView timerTextView = findViewById(R.id.timerHighScore);
        TextView longestSequenceText = findViewById(R.id.sequenceHighScore);

        if(tilesFlipped == 0 && (previousTilesFlipped == 999999 || previousTilesFlipped ==0))
        {
            tilesFlippedText.setText(R.string.tilesFlipped);
        }
        else if(tilesFlipped == 0)
        {
            String tileFlippedString = getString(R.string.tilesFlipped) + Integer.toString(previousTilesFlipped);
            tilesFlippedText.setText(tileFlippedString);
        }
        else if(previousTilesFlipped > tilesFlipped)
        {
            String tileFlippedString = getString(R.string.tilesFlipped) + Integer.toString(tilesFlipped);
            tilesFlippedText.setText(tileFlippedString);
            storeTilesFlipped(tilesFlipped);
        }
        else
        {
            String tileFlippedString = getString(R.string.tilesFlipped) + Integer.toString(previousTilesFlipped);
            tilesFlippedText.setText(tileFlippedString);
        }

        if(timer == 0 && (previousTimer == 9999999 || previousTimer == 0))
        {
            timerTextView.setText("");
        }
        else if(timer == 0)
        {
            timerTextView.setText(Integer.toString(previousTimer));
        }
        else if(previousTimer > timer)
        {
            timerTextView.setText(Integer.toString(timer));
            storeTimer(timer);
        }
        else{
            timerTextView.setText(Integer.toString(previousTimer));
        }

        if(longestSequence == 0 && (previousLongestSequence == -1 || previousLongestSequence == 0))
        {
            longestSequenceText.setText(R.string.sequenceCounter);
        }
        else if(longestSequence == 0){
            String longestSequenceString = getString(R.string.sequenceCounter) + Integer.toString(previousLongestSequence);
            longestSequenceText.setText(longestSequenceString);
        }
        else if(previousLongestSequence < longestSequence)
        {
            String longestSequenceString = getString(R.string.sequenceCounter) + Integer.toString(longestSequence);
            longestSequenceText.setText(longestSequenceString);
            storeLongestSequence(longestSequence);
        }
        else {
            String longestSequenceString = getString(R.string.sequenceCounter) + Integer.toString(previousLongestSequence);
            longestSequenceText.setText(longestSequenceString);
        }

    }

    private void storeTilesFlipped(int pTilesFlipped){
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("cunliffe.jack.acw", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("tilesFlipped", pTilesFlipped);
        editor.commit();
    }

    private int retrieveTilesFlipped() {
        int tilesFlipped = 0;
        SharedPreferences sharedPreferences = this.getApplication().getSharedPreferences("cunliffe.jack.acw", Context.MODE_PRIVATE);
        tilesFlipped = sharedPreferences.getInt("tilesFlipped", 999999);
        return tilesFlipped;
    }

    private void storeTimer(int pTimer){
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("cunliffe.jack.acw", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("timer", pTimer);
        editor.commit();
    }

    private int retrieveTimer() {
        int timer = 0;
        SharedPreferences sharedPreferences = this.getApplication().getSharedPreferences("cunliffe.jack.acw", Context.MODE_PRIVATE);
        timer = sharedPreferences.getInt("timer", 9999999);
        return timer;
    }

    private void storeLongestSequence(int pLongestSequence){
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("cunliffe.jack.acw", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("longestSequence", pLongestSequence);
        editor.commit();
    }

    private int retrieveLongestSequence() {
        int longestSequence = 0;
        SharedPreferences sharedPreferences = this.getApplication().getSharedPreferences("cunliffe.jack.acw", Context.MODE_PRIVATE);
        longestSequence = sharedPreferences.getInt("longestSequence", -1);
        return longestSequence;
    }
}