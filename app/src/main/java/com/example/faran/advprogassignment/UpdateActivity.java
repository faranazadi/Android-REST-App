package com.example.faran.advprogassignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //Get the intent
        Bundle extras = getIntent().getExtras();
        Intent intent = getIntent();

        //Grab all of the extras sent over from the previous intent
        //Doing it the long way because I was unable to create a Student object due to casting issues
        String fullName = intent.getStringExtra("name");
        String gender = intent.getStringExtra("gender");
        String DOB = intent.getStringExtra("dob");
        String address = intent.getStringExtra("address");
        String postCode = intent.getStringExtra("postcode");
        String studentNo = intent.getStringExtra("studentNumber");
        //int studentNo = intent.getIntExtra("studentNumber", 0);
        String courseTitle = intent.getStringExtra("courseTitle");
        String startDate = intent.getStringExtra("startDate");
        //int bursary = intent.getIntExtra("bursary", 0);
        String bursary = intent.getStringExtra("bursary");
        String email = intent.getStringExtra("email");

        System.out.println("Received from the intent: " + fullName);

        //Get references to the objects
        final EditText nameEditText = (EditText) findViewById(R.id.nameText);
        final EditText genderEditText = (EditText) findViewById(R.id.genderText);
        final EditText dobEditText = (EditText) findViewById(R.id.dobText);
        final EditText addressEditText = (EditText) findViewById(R.id.addressText);
        final EditText postcodeEditText = (EditText) findViewById(R.id.postcodeText);
        final EditText stuNoEditText = (EditText) findViewById(R.id.studentNoText);
        final EditText courseEditText = (EditText) findViewById(R.id.courseText);
        final EditText startDateEditText = (EditText) findViewById(R.id.startDateText);
        final EditText bursaryEditText = (EditText) findViewById(R.id.bursaryText);
        final EditText emailEditText = (EditText) findViewById(R.id.emailText);
        final Button saveButton = (Button) findViewById(R.id.saveButton);

        //Load the student's details into all of the text boxes
        nameEditText.setText(fullName);
        genderEditText.setText(gender);
        dobEditText.setText(DOB);
        addressEditText.setText(address);
        postcodeEditText.setText(postCode);
        stuNoEditText.setText(studentNo);
        courseEditText.setText(courseTitle);
        startDateEditText.setText(startDate);
        bursaryEditText.setText(bursary);
        emailEditText.setText(email);

        final HashMap<String, String> params = new HashMap<>();

        saveButton.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                String name = nameEditText.getText().toString();
                String gender = genderEditText.getText().toString();
                String DOB = dobEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String postCode = postcodeEditText.getText().toString();
                //int studentNo = Integer.parseInt(stuNoEditText.getText().toString());
                String studentNo = stuNoEditText.getText().toString();
                String courseTitle = courseEditText.getText().toString();
                String startDate = startDateEditText.getText().toString();
                //int bursary = Integer.parseInt(postcodeEditText.getText().toString());
                String bursary = postcodeEditText.getText().toString();
                String email = emailEditText.getText().toString();

                //Create student object with values used from above
                Student stu = new Student(name, gender, DOB, address, postCode, studentNo, courseTitle, startDate, bursary, email);

                //Create JSON object from student object
                String studentJSON = gson.toJson(stu);

                params.put("json", studentJSON);
                params.put("apikey", "d7dbbbd1c3");
                String url = "http://radikaldesign.co.uk/sandbox/studentapi/add.php";
                performPostCall(url, params);
            }
        });
    }

    public String performPostCall(String requestURL, HashMap<String, String> postDataParams)
    {
      URL url;
      String response = "";
      try {
          url = new URL(requestURL);

          //Create the connection object
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setReadTimeout(15000);
          conn.setConnectTimeout(15000);
          conn.setRequestMethod("POST");
          conn.setDoInput(true);
          conn.setDoOutput(true);

          //POST data to the connection using output stream and buffered writer
          OutputStream os = conn.getOutputStream();
          BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

          //POST key/value data (URL encoded) to the server
          writer.write(getPostDataString(postDataParams));

          //Clear the writer
          writer.flush();
          writer.close();

          //Close output stream
          os.close();

          //Get the server response code to determine what to do next (i.e. success/error)
          int responseCode = conn.getResponseCode();
          System.out.println("responseCode = " + responseCode);

          if (responseCode == HttpsURLConnection.HTTP_OK) {
              Toast.makeText(this, "Student added to database.", Toast.LENGTH_LONG).show();
              String line;
              BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
              while((line = br.readLine()) != null) {
                  response+=line;
              }
          }
          else {
              Toast.makeText(this, "Failed to add student.", Toast.LENGTH_LONG).show();
              response = "";
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
      System.out.println("response = " + response);
      return response;
    }

    //This method converts a hashmap to a URL query string of key/value pairs
    //e.g. name=faran&email=faranazadi@mail.com&...)
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
