//Done by Abhinand p(abhinand.po@gmail.com) and Adithya Krishnan K(k.adi.krish@gmail.com)
package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity
{
    private static final String COMPUTER_TURN = "My turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean yourTurn;
    private Random random = new Random();
    private String displayText="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        try
        {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new FastDictionary(inputStream);
        }
        catch (IOException e)
        {
            Toast toast = Toast.makeText(this, "Dictionary Load Failed!", Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putBoolean("yourTurn",yourTurn);
        savedInstanceState.putString("displayText",displayText);
        savedInstanceState.putString("ghostText",(String)((TextView) findViewById(R.id.ghostText)).getText());
        savedInstanceState.putString("gameStatus",(String)((TextView) findViewById(R.id.gameStatus)).getText());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        displayText = savedInstanceState.getString("displayText");
        yourTurn=savedInstanceState.getBoolean("yourTurn");
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText(savedInstanceState.getString("ghostText"));
        TextView label = (TextView) findViewById(R.id.gameStatus);
        label.setText(savedInstanceState.getString("gameStatus"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
       int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onStart(View view)
    {
        displayText = "";
        Button challenge = (Button) findViewById(R.id.b_challenge);
        challenge.setClickable(true);
        yourTurn = random.nextBoolean();
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);

        if (yourTurn)
        {
            label.setText(USER_TURN);
        }
        else
        {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }

    }

    private void computerTurn()
    {
        if (displayText.length() >= 4 && dictionary.isWord(displayText))
        {
            TextView label = (TextView) findViewById(R.id.gameStatus);
            label.setText("This is a word. Computer Wins");
            Button challenge = (Button) findViewById(R.id.b_challenge);
            challenge.setClickable(false);
        }
        else if(dictionary.getGoodWordStartingWith(displayText) == null)
        {
            TextView label = (TextView) findViewById(R.id.gameStatus);
            label.setText("Cannot lead to a word. Computer Wins");
            Button challenge = (Button) findViewById(R.id.b_challenge);
            challenge.setClickable(false);
        }
        else
        {
            String word = dictionary.getGoodWordStartingWith(displayText);
            displayText = displayText + word.charAt(displayText.length());
            TextView text = (TextView) findViewById(R.id.ghostText);
            text.setText(displayText);
            TextView label = (TextView) findViewById(R.id.gameStatus);
            yourTurn = true;
            label.setText(USER_TURN);
        }
    }


    private void userFunct(char pressedKey)
    {
        displayText = displayText + pressedKey;
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText(displayText);
        yourTurn = false;
        TextView label = (TextView) findViewById(R.id.gameStatus);
        label.setText(USER_TURN);
        computerTurn();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        char pressedKey = (char) event.getUnicodeChar();
        if (Character.isLetter(pressedKey))
        {
            userFunct(pressedKey);
            return true;
        }
        else
        {
            return super.onKeyUp(keyCode, event);
        }
    }


    public void challengeHandler(View view)
    {
        if (displayText.length() >= 4 && dictionary.isWord(displayText))
        {
            TextView label = (TextView) findViewById(R.id.gameStatus);
            label.setText("This is a word. User Wins");
        }
        else if (!(dictionary.getGoodWordStartingWith(displayText)== null))
        {
            TextView label = (TextView) findViewById(R.id.gameStatus);
            label.setText("This will lead to a word '"+ dictionary.getAnyWordStartingWith(displayText)+"' computer Wins");
        }
        else
        {
            TextView label = (TextView) findViewById(R.id.gameStatus);
            label.setText("This is a word. User Wins");
        }
        Button challenge = (Button) findViewById(R.id.b_challenge);
        challenge.setClickable(false);
    }


}
