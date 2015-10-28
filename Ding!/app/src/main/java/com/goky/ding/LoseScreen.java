package com.goky.ding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Type;


public class LoseScreen extends Activity {

    SharedPreferences sp;
    int curr_score, high_score;
    TextView loseTextView, scoreTextView, score, highScoreTextView, highScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose_screen);

        Typeface feenaCasualFont = Typeface.createFromAsset(getAssets(), "fonts/feenacasual.ttf");

        sp = getSharedPreferences("ding", Context.MODE_PRIVATE);
        curr_score = sp.getInt("curr_score", 0);
        high_score = sp.getInt("high_score", 0);

        loseTextView = (TextView) findViewById(R.id.lose_text);
        loseTextView.setTypeface(feenaCasualFont);

        scoreTextView = (TextView) findViewById(R.id.score_text);
        scoreTextView.setTypeface(feenaCasualFont);

        score = (TextView) findViewById(R.id.curr_score);
        score.setText("" + curr_score);
        score.setTypeface(feenaCasualFont);

        highScoreTextView = (TextView) findViewById(R.id.high_score_text);
        highScoreTextView.setTypeface(feenaCasualFont);

        highScore = (TextView) findViewById(R.id.high_score);
        highScore.setText("" + high_score);
        highScore.setTypeface(feenaCasualFont);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lose_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(this, MainMenu.class));
        finish();

    }

    public void goToMainMenu(View view) {
        startActivity(new Intent(this, MainMenu.class));
        finish();
    }

    public void restartGame(View view) {
        startActivity(new Intent(this, GameScreen.class));
        finish();
    }
}
