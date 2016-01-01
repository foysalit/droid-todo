package it.foysal.ahamed.droidtodo;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import it.foysal.ahamed.droidtodo.notes.NotesApi;


public class MainActivity extends ActionBarActivity {
    private Boolean greetingChanged = false;
    private Button changeGreetingButton;
    private EditText customGreeting;

    private ListView customGreetingListView;
    private ArrayList<String> customGreetingsList = new ArrayList<>();
    private ArrayAdapter<String> customGreetingListAdapter;
    public TextView greetingTextView;
    private TextView greetingView;

    private ListView drawerNavList;
    private ArrayAdapter<String> drawerListAdapter;
    private ActionBarDrawerToggle drawerNavToggle;
    private DrawerLayout drawerNavLayout;
    private String activityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        greetingTextView = (TextView) findViewById(R.id.greetingTextView);
        changeGreetingButton = (Button) findViewById(R.id.buttonChangeGreeting);
        customGreeting = (EditText) findViewById(R.id.customGreetingInput);

        customGreetingListView = (ListView) findViewById(R.id.customGreetingsList);
        customGreetingsList.add("Greeting default");
        customGreetingListAdapter = new ArrayAdapter<>(this, R.layout.custom_greeting_list_text, customGreetingsList);

        customGreetingListView.setAdapter(customGreetingListAdapter);

        drawerNavList = (ListView)findViewById(R.id.drawerNavList);
        drawerNavLayout = (DrawerLayout)findViewById(R.id.main_drawer_layout);
        activityTitle = getTitle().toString();
        addDrawerItems();

        changeGreetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeGreeting(greetingTextView);

                NotesApi NoteService = new NotesApi(MainActivity.this);
                NoteService.getAll();
                NoteService.execute("test");
            }
        });
    }

    private void addDrawerItems() {
        String[] osArray = { "Notes", "iOS", "Windows", "OS X", "Linux" };
        drawerListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, osArray);
        drawerNavList.setAdapter(drawerListAdapter);
        final ActionBarActivity selfActivity = this;

        drawerNavList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(selfActivity, NotesActivity.class);
                startActivity(intent);
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

        if (id == R.id.action_notes) {
            Intent intent = new Intent(this, NotesActivity.class);
            startActivity(intent);
        }

        if (drawerNavToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawer() {
        drawerNavToggle = new ActionBarDrawerToggle(this, drawerNavLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(activityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        drawerNavToggle.setDrawerIndicatorEnabled(true);
        drawerNavLayout.setDrawerListener(drawerNavToggle);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerNavToggle.onConfigurationChanged(newConfig);
    }
}
