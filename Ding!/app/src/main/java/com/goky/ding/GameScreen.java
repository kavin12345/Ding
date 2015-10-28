package com.goky.ding;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameScreen extends Activity implements View.OnTouchListener {

    float x, y;
    CircularAnimation circularAnimation;
    boolean match_correct = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        circularAnimation = new CircularAnimation(this);
        circularAnimation.setOnTouchListener(this);
        setContentView(circularAnimation);
    }

    @Override
    protected void onPause() {
        super.onPause();
        circularAnimation.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        circularAnimation.resume();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                match_correct = circularAnimation.matchCorrect();
                checkLose(v);
                Log.d("ACTION_DOWN", "ACTION_DOWN");
                x = event.getX();
                y = event.getY();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_screen, menu);
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

    public void checkLose(View view) {
        if (match_correct == false) {
            MediaPlayer.create(this, R.raw.lose).start();
            Intent intent = new Intent(this, LoseScreen.class);
            startActivity(intent);
            finish();
        } else {
            MediaPlayer.create(this, R.raw.ding).start();
        }
    }
}
