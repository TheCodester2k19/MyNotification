package com.example.mynotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText bp,temp;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bp = findViewById(R.id.bpBox);
        temp = findViewById(R.id.tempBox);
        btn = findViewById(R.id.submitBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                int bp_int = Integer.parseInt(bp.getText().toString());
                int temp_int = Integer.parseInt(temp.getText().toString());
                String msg = (bp_int >= 60 && bp_int <= 100) || temp_int == 98 ? "Your Vitals are Fine" : "You need to Consult a Doctor";

                NotificationManager manager = getSystemService(NotificationManager.class);
                NotificationChannel channel = new NotificationChannel("my_ch","my_channel",NotificationManager.IMPORTANCE_HIGH);
                manager.createNotificationChannel(channel);

                Intent intent = new Intent(MainActivity.this, NotificationResult.class);
                intent.putExtra("Message", msg);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "my_ch")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentIntent(pendingIntent)
                        .setContentTitle("Vital Conditions")
                        .setContentText("BP: " + bp_int + "\nTemperature: " + temp_int);

                manager.notify(100, builder.build());
            }
        });
    }
}