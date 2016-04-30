package com.herokuapp.jeffruder.perkaapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.herokuapp.jeffruder.perkaapplication.services.PerkaApiService;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private static String FILE_NAME = "resume.pdf";
    private String mEncoded;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            mEncoded = encodeAssetToBase64Binary();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        mButton = (Button) findViewById(R.id.submitButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerkaApiService service = new PerkaApiService(MainActivity.this, mEncoded);
                String json = service.buildJson();
                try{
                    String result = service.post(json);
                    Log.i("RESULT", result);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        });
    }

    private String encodeAssetToBase64Binary() throws IOException {
        InputStream is = this.getAssets().open(FILE_NAME);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }
}
