package ca.ualbert.cs.tasko;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

public class MainActivity extends AppCompatActivity {

    private MainActivity activity = this;
    private DataManager dm = DataManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button postTaskButton = (Button)findViewById(R.id.postTaskButton);
        Button searchTaskButton = (Button)findViewById(R.id.SearchButton);
        final EditText searchQuery = (EditText)findViewById(R.id.searchQuery);

        /**
         * Go to the AddTaskActivity to create a new task
         */
        postTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddTaskActivity.class));
            }
        });

        searchTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keywords = searchQuery.getText().toString();
                //I realise this is not the best design, dont know how to check for empty results
                //Without making the search occur here, would prefer not to pass the tasklist to
                //SearchResultsActivity... Definately needs refinement
                try {
                    TaskList temptasks = dm.searchTasks(keywords, activity);
                    if(temptasks.getSize() == 0){
                        searchQuery.setError("This Search Found No Results");
                    }else{
                        Intent intent = new Intent(activity, SearchResultsActivity.class);
                        intent.putExtra("SearchKeywords", keywords);
                        startActivity(intent);
                    }
                } catch (NoInternetException e) {
                    e.printStackTrace();
                }

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
}
