package com.charlesdrews.firebaselab;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String CHAT_PREFS_KEY = "chat_prefs_key";
    private static final String USERNAME_KEY = "username_key";

    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getUsername();

        final Firebase firebaseMessages = new Firebase("https://glaring-fire-165.firebaseio.com/messages");

        final ListView listView = (ListView) findViewById(R.id.list_view);

        final FirebaseListAdapter<Message> adapter = new FirebaseListAdapter<Message>(
                this, Message.class, android.R.layout.simple_list_item_2, firebaseMessages) {
            @Override
            protected void populateView(View view, Message message, int i) {
                TextView user = (TextView) view.findViewById(android.R.id.text1);
                TextView text = (TextView) view.findViewById(android.R.id.text2);

                user.setText(message.getUsername());
                text.setText(message.getMessageText());

                listView.setSelection(i);
            }
        };
        listView.setAdapter(adapter);

        final EditText editText = (EditText) findViewById(R.id.edit_text);
        Button sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseMessages.push().setValue(new Message(mUsername, editText.getText().toString()));
                editText.setText(null);
                editText.requestFocus();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void getUsername() {
        SharedPreferences preferences = getApplication().getSharedPreferences(CHAT_PREFS_KEY, MODE_PRIVATE);
        mUsername = preferences.getString(USERNAME_KEY, null);
        if (mUsername == null) {
            Random random = new Random(System.currentTimeMillis());
            mUsername = "User" + random.nextInt(10000);
            preferences.edit().putString(USERNAME_KEY, mUsername).commit();
        }
    }
}
