package cunliffe.jack.acw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayPuzzleActivity extends AppCompatActivity {

    TextView timerTextView;
    long startTime = 0;

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            String timer = getString(R.string.timer) + String.format("%d:%02d", minutes, seconds);
            timerTextView.setText(timer);

            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play_puzzle);

        timerTextView = (TextView) findViewById(R.id.timerTextView);

        PuzzleFragment content = PuzzleFragment.newInstance(0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content, content, "0");
        content = PuzzleFragment.newInstance(1);
        fragmentTransaction.add(R.id.content2, content, "1");
        content = PuzzleFragment.newInstance(2);
        fragmentTransaction.add(R.id.content3, content, "2");
        content = PuzzleFragment.newInstance(3);
        fragmentTransaction.add(R.id.content4, content, "3");
        content = PuzzleFragment.newInstance(4);
        fragmentTransaction.add(R.id.content5, content, "4");
        content = PuzzleFragment.newInstance(5);
        fragmentTransaction.add(R.id.content6, content, "5");
        content = PuzzleFragment.newInstance(6);
        fragmentTransaction.add(R.id.content7, content, "6");
        content = PuzzleFragment.newInstance(7);
        fragmentTransaction.add(R.id.content8, content, "7");
        content = PuzzleFragment.newInstance(8);
        fragmentTransaction.add(R.id.content9, content, "8");
        content = PuzzleFragment.newInstance(9);
        fragmentTransaction.add(R.id.content10, content, "9");
        content = PuzzleFragment.newInstance(10);
        fragmentTransaction.add(R.id.content11, content, "10");
        content = PuzzleFragment.newInstance(11);
        fragmentTransaction.add(R.id.content12, content, "11");
        content = PuzzleFragment.newInstance(12);
        fragmentTransaction.add(R.id.content13, content, "12");
        content = PuzzleFragment.newInstance(13);
        fragmentTransaction.add(R.id.content14, content, "13");
        content = PuzzleFragment.newInstance(14);
        fragmentTransaction.add(R.id.content15, content, "14");
        content = PuzzleFragment.newInstance(15);
        fragmentTransaction.add(R.id.content16, content, "15");
        content = PuzzleFragment.newInstance(16);
        fragmentTransaction.add(R.id.content17, content, "16");
        content = PuzzleFragment.newInstance(17);
        fragmentTransaction.add(R.id.content18, content, "17");
        content = PuzzleFragment.newInstance(18);
        fragmentTransaction.add(R.id.content19, content, "18");
        content = PuzzleFragment.newInstance(19);
        fragmentTransaction.add(R.id.content20, content, "19");
        content = PuzzleFragment.newInstance(20);
        fragmentTransaction.add(R.id.content21, content, "20");
        content = PuzzleFragment.newInstance(21);
        fragmentTransaction.add(R.id.content22, content, "21");
        content = PuzzleFragment.newInstance(22);
        fragmentTransaction.add(R.id.content23, content, "22");
        content = PuzzleFragment.newInstance(23);
        fragmentTransaction.add(R.id.content24, content, "23");
        content = PuzzleFragment.newInstance(24);
        fragmentTransaction.add(R.id.content25, content, "24");
        content = PuzzleFragment.newInstance(25);
        fragmentTransaction.add(R.id.content26, content, "25");
        content = PuzzleFragment.newInstance(26);
        fragmentTransaction.add(R.id.content27, content, "26");
        content = PuzzleFragment.newInstance(27);
        fragmentTransaction.add(R.id.content28, content, "27");
        content = PuzzleFragment.newInstance(28);
        fragmentTransaction.add(R.id.content29, content, "28");
        content = PuzzleFragment.newInstance(29);
        fragmentTransaction.add(R.id.content30, content, "29");
        content = PuzzleFragment.newInstance(30);
        fragmentTransaction.add(R.id.content31, content, "30");
        content = PuzzleFragment.newInstance(31);
        fragmentTransaction.add(R.id.content32, content, "31");
        content = PuzzleFragment.newInstance(32);
        fragmentTransaction.add(R.id.content33, content, "32");
        content = PuzzleFragment.newInstance(33);
        fragmentTransaction.add(R.id.content34, content, "33");
        content = PuzzleFragment.newInstance(34);
        fragmentTransaction.add(R.id.content35, content, "34");
        content = PuzzleFragment.newInstance(35);
        fragmentTransaction.add(R.id.content36, content, "35");
        content = PuzzleFragment.newInstance(36);
        fragmentTransaction.add(R.id.content37, content, "36");
        content = PuzzleFragment.newInstance(37);
        fragmentTransaction.add(R.id.content38, content, "37");
        content = PuzzleFragment.newInstance(38);
        fragmentTransaction.add(R.id.content39, content, "38");
        content = PuzzleFragment.newInstance(39);
        fragmentTransaction.add(R.id.content40, content, "39");
        fragmentTransaction.commit();

        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);

        Log.i("Activity Lifecycle", "onCreate");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Activity Lifecycle", "onPause");
        //timerHandler.removeCallbacks(timerRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Activity Lifecycle", "onResume");
        //startTime = System.currentTimeMillis();
        //timerHandler.postDelayed(timerRunnable, 0);

    }

    @Override
    public void finish() {
        Intent data = new Intent();
        TextView flipCounterText = findViewById(R.id.tilesFlippedEndText);
        TextView timerText = findViewById(R.id.timerEndText);
        TextView longestSequenceText = findViewById(R.id.sequenceCounterEndText);

        String flipCounterString = flipCounterText.getText().toString();
        flipCounterString = flipCounterString.substring(14);
        int flipCounter = Integer.parseInt(flipCounterString);

        String timerString = timerText.getText().toString();
        timerString = timerString.substring(6);
        timerString = timerString.replace(":", "");
        int timer = Integer.parseInt(timerString);

        String longestSequenceString = longestSequenceText.getText().toString();
        longestSequenceString = longestSequenceString.substring(17);
        int longestSequence = Integer.parseInt(longestSequenceString);

        data.putExtra("FlipCounter", flipCounter);
        data.putExtra("Timer", timer);
        data.putExtra("LongestSequence", longestSequence);
        setResult(RESULT_OK, data);

        super.finish();
    }

}