package com.example.dfreeman.horoscope;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Make an onClickListener for the main menu items
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View v, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                    intent.putExtra(CategoryActivity.EXTRA_HOROSCOPENO, (int) id);
                    startActivity(intent);
            }
        };

        //Add listener to list view
        ListView listView = (ListView) findViewById(R.id.main_list);
        listView.setOnItemClickListener(itemClickListener);
    }
}
