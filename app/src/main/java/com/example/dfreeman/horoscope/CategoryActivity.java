package com.example.dfreeman.horoscope;

import android.os.Bundle;
import android.app.ListActivity;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CategoryActivity extends AppCompatActivity {

    public static final String EXTRA_HOROSCOPENO = "horoscopeNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);

        int horoNo = (Integer) getIntent().getExtras().get(EXTRA_HOROSCOPENO);
        Horoscope horoscope = Horoscope.horoscopes[horoNo];

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(horoscope.getName());

        TextView description = (TextView) findViewById(R.id.desc);
        description.setText(horoscope.getDescription());

        TextView sign = (TextView) findViewById(R.id.sign);
        sign.setText(horoscope.getSign());

        TextView month = (TextView) findViewById(R.id.month);
        month.setText(horoscope.getMonth());

        TextView theeHoroscope = (TextView) findViewById(R.id.horoscope);
        theeHoroscope.setText(horoscope.getHoroscope());
    }
}
