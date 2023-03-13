package cunliffe.jack.acw;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_DIALOG_RESPONSE = 1;
    int flipResponse;
    int timerResponse;
    int sequenceResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    // Sets layout when application is started

    public void openPuzzle(View view) {
        Intent openPuzzleIntent = new Intent(getApplicationContext(), PlayPuzzleActivity.class);
        startActivityForResult(openPuzzleIntent, REQUEST_DIALOG_RESPONSE);
    }
    // Opens puzzle

    public void openHighScores(View view) {
        Intent openHighScoresIntent = new Intent(getApplicationContext(), HighScoresActivity.class);
        openHighScoresIntent.putExtra("FlipCounter", flipResponse);
        openHighScoresIntent.putExtra("Timer", timerResponse);
        openHighScoresIntent.putExtra("LongestSequence", sequenceResponse);
        startActivity(openHighScoresIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUEST_DIALOG_RESPONSE) {
            flipResponse = data.getExtras().getInt("FlipCounter");
            timerResponse = data.getExtras().getInt("Timer");
            sequenceResponse = data.getExtras().getInt("LongestSequence");
        }
    } // Use to get results from puzzle activity
}