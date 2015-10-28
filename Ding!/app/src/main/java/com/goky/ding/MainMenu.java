package com.goky.ding;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainMenu extends Activity {

    TextView gameTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        gameTitle = (TextView) findViewById(R.id.title);
        Typeface feenaCasualText = Typeface.createFromAsset(getAssets(), "fonts/feenacasual.ttf");
        gameTitle.setTypeface(feenaCasualText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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

    public void playGame(View view) {
        startActivity(new Intent(this, GameScreen.class));
        finish();
    }

    public void goToHighScoreMenu(View view) {
        startActivity(new Intent(this, HighScore.class));
        finish();
    }
}
