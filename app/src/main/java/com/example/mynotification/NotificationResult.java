package com.example.mynotification;

import android.app.NotificationManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NotificationResult extends AppCompatActivity {
    TextView res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_result);

        res = findViewById(R.id.result);
        displayResult();
    }

    private void displayResult() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("Message")) {
            String msg = extras.getString("Message");
            res.setText("Report: " + msg);
        }

        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.cancel(100);
    }
}