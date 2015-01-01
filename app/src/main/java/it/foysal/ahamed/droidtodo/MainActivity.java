package it.foysal.ahamed.droidtodo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private Boolean greetingChanged = false;
    private Button changeGreetingButton;
    private EditText customGreeting;

    private ListView customGreetingListView;
    private ArrayList<String> customGreetingsList = new ArrayList<String>();
    private ArrayAdapter<String> customGreetingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView greetingTextView = (TextView) findViewById(R.id.greetingTextView);
        changeGreetingButton = (Button) findViewById(R.id.buttonChangeGreeting);
        customGreeting = (EditText) findViewById(R.id.customGreetingInput);

        customGreetingListView = (ListView) findViewById(R.id.customGreetingsList);
        customGreetingsList.add("Greeting default");
        customGreetingListAdapter = new ArrayAdapter<String>(this, R.layout.custom_greeting_list_text, customGreetingsList);
        customGreetingListView.setAdapter(customGreetingListAdapter);

        changeGreetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeGreeting(greetingTextView);

                Notes NoteService = new Notes(MainActivity.this);
                //NoteService.getAll();
                NoteService.execute("test");
            }
        });
    }

    public String getCustomGreetingInput() {
        return String.valueOf(customGreeting.getText());
    }

    public Boolean hasCustomGreeting() {
        return getCustomGreetingInput().length() > 0;
    }

    public void changeGreeting(TextView greetingView) {
        String newString;

        if (hasCustomGreeting()) {
            newString = getCustomGreetingInput();
            customGreetingsList.add(newString);
            customGreetingListAdapter.notifyDataSetChanged();
        }else if (greetingChanged) {
            newString = getString(R.string.greeting_text_budu_budu);
        } else {
            newString = getString(R.string.greeting_text_fish_lips);
        }

        greetingView.setText(newString);
        greetingChanged = !greetingChanged;

        Toast.makeText(this, "Greeting changed!", Toast.LENGTH_LONG).show();
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
}
