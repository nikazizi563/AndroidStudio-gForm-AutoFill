package com.ggnik.keluarharian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    Button btn1;
    EditText nik0EditText, nik1EditText, nik2EditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn1);
        nik0EditText  = findViewById(R.id.nik0EditText);
        nik1EditText  = findViewById(R.id.nik1EditText);
        nik2EditText = findViewById(R.id.nik2EditText);

        // Load NIK value from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String nik0Value = preferences.getString("nik0Value", "");
        nik0EditText.setText(nik0Value);
        String nik1Value = preferences.getString("nik1Value", "");
        nik1EditText.setText(nik1Value);
        String nik2Value = preferences.getString("nik2Value", "");
        nik2EditText.setText(nik2Value);
        Toast.makeText(getApplicationContext(), "Values loaded from the Last Time", Toast.LENGTH_SHORT).show();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://docs.google.com/forms/d/e/1FAIpQLSelLXeS4W1UUKLKD1BGPENcNpLHOxl-QqEgsnkRd9EI18HpUw/viewform?usp=pp_url&entry.2089497473=[nik0Value]&entry.971786495=[nik1Value]&entry.1122259075=[nik2Value]&entry.1013074252=KELUAR+HARIAN&entry.671190071=[currentTime]&entry.776041985=[currentDate]&entry.443561527=[currentTimePlus2Hours]";

                // Format current time as "15:01"
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                String currentTime = timeFormat.format(new Date());

                // Format current date as "2023-02-24"
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String currentDate = dateFormat.format(new Date());

                // Add 2 hours to current time
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.HOUR_OF_DAY, 2);
                String currentTimePlus = timeFormat.format(calendar.getTime());

                // Get the EMAIL value from the TextView
                String nik0Value = nik0EditText.getText().toString();

                // Get the NAME value from the TextView
                String nik1Value = nik1EditText.getText().toString();

                // Get the ROOM value from the TextView
                String nik2Value = nik2EditText.getText().toString();

                // Encode the EMAIL value for use in the URL
                try {
                    nik0Value = URLEncoder.encode(nik0Value, StandardCharsets.UTF_8.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Encode the NAME value for use in the URL
                try {
                    nik1Value = URLEncoder.encode(nik1Value, StandardCharsets.UTF_8.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Encode the ROOM value for use in the URL
                try {
                    nik2Value = URLEncoder.encode(nik2Value, StandardCharsets.UTF_8.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Replace [currentTime] with current time in the URL
                url = url.replace("[currentTime]", currentTime);

                // Replace [currentDate] with current date in the URL
                url = url.replace("[currentDate]", currentDate);

                // Replace [currentTimePlus2Hours] with current time plus 2 hours in the URL
                url = url.replace("[currentTimePlus2Hours]", currentTimePlus);

                // Replace [nikValue] with the encoded EMAIL value in the URL
                url = url.replace("[nik0Value]", nik0Value);

                // Replace [nikValue] with the encoded NAME value in the URL
                url = url.replace("[nik1Value]", nik1Value);

                // Replace [nikValue] with the encoded ROOM value in the URL
                url = url.replace("[nik2Value]", nik2Value);

                // Save NIK value to SharedPreferences
                SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("nik0Value", nik0EditText.getText().toString());
                editor.apply();
                editor.putString("nik1Value", nik1EditText.getText().toString());
                editor.apply();
                editor.putString("nik2Value", nik2EditText.getText().toString());
                editor.apply();

                // Create an intent with the ACTION_VIEW action and the URL
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));

                // Start the activity with the intent
                startActivity(i);
            }
        });
    }

}