package com.example.faran.advprogassignment;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Global array to hold the names of students
    String[] studentNames;

    //Global ArrayList to hold all of the students
    ArrayList<Student> allStudents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Run network on main thread - use ASyncTasks for assignment
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Reference to the list of all the students on MainActivity
        ListView studentList = findViewById(R.id.studentList);

        //Making a HTTP call
        HttpURLConnection urlConnection;
        InputStream in = null;
        try {
            //The URL to connect to using API key
            URL url = new URL("http://radikaldesign.co.uk/sandbox/studentapi/getallstudents.php?apikey=xxxxxxx");

            //Open the connection to the specified URL
            urlConnection = (HttpURLConnection) url.openConnection();

            //Get the response from the server in an input stream
            in = new BufferedInputStream(urlConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Convert the input stream to a string
        String response = convertStreamToString(in);

        //Print the response to android monitor/log cat
        System.out.println("Server response = " + response);

        try {
            //Declare a new JSON array and pass it the string response from the server
            //this will convert the string into a JSON array which we can then iterate
            //over using a loop
            JSONArray jsonArray = new JSONArray(response);

            //Instantiate the studentNames array and set the size
            //to the amount of Student objects returned by the server
            studentNames = new String[jsonArray.length()];

            //Use a for loop to iterate over the JSON array
            for (int i=0; i < jsonArray.length(); i++)
            {
                //The following code will get the details of the Student from the
                //current JSON object and store it in appropiate variables
                String fullName = jsonArray.getJSONObject(i).get("name").toString();
                String gender = jsonArray.getJSONObject(i).get("gender").toString();
                String DOB = jsonArray.getJSONObject(i).get("dob").toString();
                String address = jsonArray.getJSONObject(i).get("address").toString();
                String postCode = jsonArray.getJSONObject(i).get("postcode").toString();
                //int studentNo = Integer.parseInt(jsonArray.getJSONObject(i).get("studentNumber").toString());
                String studentNo = jsonArray.getJSONObject(i).get("studentNumber").toString();
                String courseTitle = jsonArray.getJSONObject(i).get("courseTitle").toString();
                String startDate = jsonArray.getJSONObject(i).get("startDate").toString();
                //int bursary = Integer.parseInt(jsonArray.getJSONObject(i).get("bursary").toString());
                String bursary = jsonArray.getJSONObject(i).get("bursary").toString();
                String email = jsonArray.getJSONObject(i).get("email").toString();

                //Print the student's name and email to log cat
                System.out.println("Name = " + fullName);
                System.out.println("Email = " + email);

                //Create Student object with details grabbed from above
                Student stu = new Student(fullName, gender, DOB, address, postCode, studentNo, courseTitle, startDate, bursary, email);

                //Add the Student object to the ArrayList
                allStudents.add(stu);

                //Add the name and email of the current Student to the array
                studentNames [i] = fullName + "\n" + email;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, studentNames);
        studentList.setAdapter(arrayAdapter);

        studentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Determines which item was clicked in the list
                Toast.makeText(MainActivity.this, "You pressed " + allStudents.get(i).getFullName(), Toast.LENGTH_SHORT).show();

                //Declare a new intent, give it the context and
                //specify which activity you want to open/start
                Intent intent = new Intent(getApplicationContext(), UpdateActivity.class);

                //Add/put the selected Student object in to the intent which will
                //be passed over to the activity that is started
                //note we use a KEY:VALUE structure to pass variable/objects
                //between activities. Here the keys are below and the values are
                //the student object from the student array list using the position
                //which is specified by the ‘i’ variable.
                intent.putExtra("name", allStudents.get(i).getFullName());
                intent.putExtra("gender", allStudents.get(i).getGender());
                intent.putExtra("dob", allStudents.get(i).getDOB());
                intent.putExtra("address", allStudents.get(i).getAddress());
                intent.putExtra("postcode", allStudents.get(i).getPostCode());
                intent.putExtra("studentNumber", allStudents.get(i).getStudentNo());
                intent.putExtra("courseTitle", allStudents.get(i).getCourseTitle());
                intent.putExtra("startDate", allStudents.get(i).getStartDate());
                intent.putExtra("bursary", allStudents.get(i).getBursary());
                intent.putExtra("email", allStudents.get(i).getEmail());

                //Launch the activity
                startActivity(intent);
            }
        });
    }

    //Convert the input stream to a string
    public String convertStreamToString(InputStream is)
    {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
