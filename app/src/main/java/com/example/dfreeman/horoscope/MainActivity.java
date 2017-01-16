package com.example.dfreeman.horoscope;

import android.app.ListActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView = getListView();

        //Get the horoscope names and set them in the activity_main
        ArrayAdapter<Horoscope> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Horoscope.horoscopes);
        listView.setAdapter(listAdapter);

        //Make an onClickListener for the main menu items
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View v, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                    intent.putExtra(CategoryActivity.EXTRA_HOROSCOPENO, (int) id);
                    startActivity(intent);
            }
        };

        //Add listener to list view
        listView.setOnItemClickListener(itemClickListener);
    }
}
