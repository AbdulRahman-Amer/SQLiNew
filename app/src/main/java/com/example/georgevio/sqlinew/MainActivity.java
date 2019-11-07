package com.example.georgevio.sqlinew;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //George
    DBHelper mydb;
    int s;
    Button bttnshow1;
    Button bttnshowall;
    Button bttnadd;

    EditText editTextName;
    EditText editTextPhone;
    EditText editTextEmail;
    EditText editTextStreet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DBHelper(this);

        editTextName = (EditText)findViewById(R.id.editName);
        editTextPhone = (EditText)findViewById(R.id.editPhone);
        editTextEmail = (EditText)findViewById(R.id.editEmail);
        editTextStreet=(EditText)findViewById(R.id.editStreet);
        bttnadd = (Button) findViewById(R.id.bttnAdd);
        bttnshow1 = (Button) findViewById(R.id.bttnShow1);
        bttnshowall = (Button) findViewById(R.id.bttnShowAll);

        bttnadd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //remove the following toast...
                Toast.makeText(getApplicationContext(),
                        "bttnOnClick Pressed", Toast.LENGTH_SHORT).show();

                String getName = editTextName.getText().toString();
                String getPhone = editTextPhone.getText().toString();
                String getEmail = editTextEmail.getText().toString();
                String getStreet=editTextStreet.getText().toString();

                if (getName.isEmpty()||getPhone.isEmpty()||getEmail.isEmpty()||getStreet.isEmpty()){
                    Toast.makeText(getApplicationContext(),"must be insert",Toast.LENGTH_SHORT).show();
                }
                else
                if (!Patterns.EMAIL_ADDRESS.matcher(getEmail).matches()){
                    Toast.makeText(getApplicationContext(),"not correct email",Toast.LENGTH_SHORT).show();

                }else

                if (mydb.insertContact(getName, getPhone, getEmail,getStreet)) {
                    Log.v("georgeLog", "Successfully inserted record to db");
                    Toast.makeText(getApplicationContext(),
                            "Inserted:" + getName + ", " + getPhone + "," + getEmail+","+getStreet, Toast.LENGTH_SHORT).show();


                } else
                    Toast.makeText(getApplicationContext(), "DID NOT insert to db :-(", Toast.LENGTH_SHORT).show();
            }

        });
        bttnshow1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("georgeLog", "clicked on fetch");
                Cursor getData=mydb.getData(1); //specific record (id=1)

                if (getData.moveToNext()) {// data?
                    Log.v("georgeLog", "data found in DB...");
                    String dName = getData.getString(getData.getColumnIndex("name"));
                    String dPhone = getData.getString(getData.getColumnIndex("phone"));
                    String dEmail = getData.getString(getData.getColumnIndex("email"));
                    String dStreet = getData.getString(getData.getColumnIndex("street"));
                    Toast.makeText(getApplicationContext(),
                            "rec: " + dName + ", " + dPhone + ", " + dEmail+","+dStreet, Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(),
                            "did not get any data...:-(", Toast.LENGTH_LONG).show();
                getData.close();
            }
        });

        bttnshowall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("georgeLog", "clicked on Result Button");
                ArrayList<String> fetchAll = new ArrayList<String>();
                fetchAll=mydb.getAllContacts();
                for (String a:fetchAll)
                    Log.v("georgeLog:", a.toString());
                Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
                Log.v("georgeLog:", "intent executed");
                intent.putStringArrayListExtra("fetchAll",fetchAll);
                Log.v("georgeLog:","fetchALL executed");
                startActivity(intent);
                Log.v("georgeLog:", "startActivity executed");
            }
        });

        // to delete a record
        //find the record you want, get its id, and use the following
        //mydb.deleteContact (id);

    }
}
