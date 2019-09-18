package android.example.courtcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int score = 0;
    int score2 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void displayOfScoreTeamA(int score){
        TextView scoreView = findViewById(R.id.tv_score);
        scoreView.setText(String.valueOf(score));
    }
    public void threePoints(View view){
        score = score + 3;
        displayOfScoreTeamA(score);
    }
    public void twoPoints(View view){
        score = score + 2;
        displayOfScoreTeamA(score);
    }
    public void freeThrow(View view){
        score = score + 1;
        displayOfScoreTeamA(score);
    }
    private void displayOfScoreTeamB(int score){
        TextView scoreView = findViewById(R.id.tv_score2);
        scoreView.setText(String.valueOf(score));
    }
    public void threePointsB(View view){
        score2 = score2 + 3;
        displayOfScoreTeamB(score2);
    }
    public void twoPointsB(View view){
        score2 = score2 + 2;
        displayOfScoreTeamB(score2);
    }
    public void freeThrowB(View view){
        score2 = score2 + 1;
        displayOfScoreTeamB(score2);
    }
    public void reset(View view){
        score = 0;
        score2 = 0;
        displayOfScoreTeamA(score);
        displayOfScoreTeamB(score2);

    }

}
