package com.example.databaseupdater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener{

    private EditText etMarket;
    private EditText etName;
    private EditText etPrice;
    private EditText etLat;
    private EditText etLong;
    private EditText etBar;

    private Button btnMap;
    private Button btnBar;
    private Button btnSend;

    private String market = null;
    private String name = null;
    private int price = 0;
    private Double latitute = null;
    private Double longitute = null;
    private int barcode = 0;

    private FirebaseDatabase database = null;
    private DatabaseReference myRef = null;

    final static int RESULT_FROM_BARCODE = 0;
    final static int RESULT_FROM_MAP = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMarket = (EditText)findViewById(R.id.etMarket);
        etName = (EditText)findViewById(R.id.etName);
        etPrice = (EditText)findViewById(R.id.etPrice);
        etLat = (EditText)findViewById(R.id.etLat);
        etLong = (EditText)findViewById(R.id.etLong);
        etBar = (EditText)findViewById(R.id.etBar);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        btnMap = (Button)findViewById(R.id.btnMap);
        btnMap.setOnClickListener(this);
        btnSend = (Button)findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        btnBar = (Button)findViewById(R.id.btnBar);
        btnBar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnMap:
                Log.d("check", "btnMap");
                Intent intentMap = new Intent(getApplicationContext(), MapActivity.class);
                startActivityForResult(intentMap, RESULT_FROM_MAP);
                break;
            case R.id.btnSend:
                Log.d("check", "btnSend");
                market = etMarket.getText().toString();
                name = etName.getText().toString();
                price = Integer.parseInt(etPrice.getText().toString());
                myRef.child(market).child(name).setValue(price);
                break;
            case R.id.btnBar:
                Log.d("check", "btnBar");
                Intent intentBar = new Intent(getApplicationContext(), BarcodeActivity.class);
                startActivityForResult(intentBar, RESULT_FROM_BARCODE);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("check", "main onResume");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this, "Result: " + data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
                etBar.setText(data.getStringExtra("result"));
            } else {   // RESULT_CANCEL
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
          } else if (requestCode == RESULT_FROM_MAP) {

        }
    }
}
