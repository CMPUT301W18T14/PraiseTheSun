package ca.ualbert.cs.tasko;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

public class MainActivity extends RootActivity {

    private MainActivity activity = this;
    private DataManager dm = DataManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        * The following implements the menu bar here
        * reference:
        * http://shockingandroid.blogspot.ca/2017/04/navigation-drawer-in-every-activity.html
        * accessed 2018-03-11
        * */

        //FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        //NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        //navigationView.getMenu().getItem(0).setChecked(true);
        //Referenced: https://www.c-sharpcorner.com/article/navigation-drawer-activity-in-android/
        //on 03-17-2018

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_main, null, false);
        drawerLayout.addView(contentView, 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button postTaskButton = (Button)findViewById(R.id.postTaskButton);
        Button searchTaskButton = (Button)findViewById(R.id.SearchButton);
        final EditText searchQuery = (EditText)findViewById(R.id.searchQuery);


        // Go to the AddTaskActivity to create a new task
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
                try {
                    TaskList temptasks = dm.searchTasks(keywords);
                    if(temptasks.getSize() == 0){
                        searchQuery.setError("This Search Found No Results");
                    }else{
                        Intent intent = new Intent(activity, SearchResultsActivity.class);
                        intent.putExtra("SearchKeywords", keywords);
                        startActivity(intent);
                    }
                } catch (NoInternetException e) {
                    Toast.makeText(MainActivity.this, "Can not search offline", Toast.LENGTH_SHORT)
                            .show();
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
