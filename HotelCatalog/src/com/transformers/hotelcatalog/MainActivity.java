package com.transformers.hotelcatalog;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends Activity {

	private ListView listview;
	private List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateListView();
    }


    private void populateListView() {
		// TODO Auto-generated method stub
		String[] items = {"Hotel1", "Hotel2", "Hotel3", "Hotel4"}; 
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.hotel_view, items); 
		
		this.listview = (ListView)this.findViewById(R.id.listViewHotels);
		listview.setAdapter(adapter);
	} 


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
