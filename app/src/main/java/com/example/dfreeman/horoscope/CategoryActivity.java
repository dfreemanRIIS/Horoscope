package com.example.dfreeman.horoscope;

import android.os.Bundle;
import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CategoryActivity extends AppCompatActivity {

    public static final String EXTRA_HOROSCOPENO = "horoscopeNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);

        int horoNo = (Integer)getIntent().getExtras().get(EXTRA_HOROSCOPENO);
        Horoscope horoscope = Horoscope.horoscopes[horoNo];

        TextView name = (TextView)findViewById(R.id.name);
        name.setText(horoscope.getName());

        TextView description = (TextView)findViewById(R.id.desc);
        description.setText(horoscope.getDescription());
    }
}
