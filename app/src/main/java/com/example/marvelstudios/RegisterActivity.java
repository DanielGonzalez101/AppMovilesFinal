package com.example.marvelstudios;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.regex.Pattern;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class RegisterActivity extends AppCompatActivity {

  EditText edtName, edtEmail, edtDate, edtPassword;
  Button btnRegister;
  SharedPreferences sharedPreferences;
  SharedPreferences.Editor editor;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    edtName = findViewById(R.id.edtName);
    edtEmail = findViewById(R.id.edtEmail);
    edtDate = findViewById(R.id.edtDate);
    edtPassword = findViewById(R.id.edtPassword);
    btnRegister = findViewById(R.id.btnRegister);
    edtDate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showDatePickerDialog();
      }
    });

    btnRegister.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        String date = edtDate.getText().toString();
        String password = edtPassword.getText().toString();

        if (name.isEmpty() || email.isEmpty() || date.isEmpty() || password.isEmpty())
          Toast.makeText(RegisterActivity.this, "Por favor completar los campos ", Toast.LENGTH_SHORT).show();
        else {
          sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
          editor = sharedPreferences.edit();
          editor.putString("name", name);
          editor.putString("email", email);
          editor.putString("date", date);
          editor.putString("password", password);
          editor.apply();

          sendDataToApi(name,email,date,password);

          if (!validEmail(email))
            Toast.makeText(RegisterActivity.this, "Correo inválido", Toast.LENGTH_SHORT).show();
          else {
            Toast.makeText(RegisterActivity.this, "Usuario registrado con exito", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
          }

        }
      }
    });
  }

  public void sendDataToApi(String name, String email, String date, String password) {
    String url = "http://10.0.2.2:3100/api/registrar"; // Cambia localhost a 10.0.2.2 si usas un emulador

    JSONObject jsonBody = new JSONObject();
    try {
      jsonBody.put("name", name);
      jsonBody.put("email", email);
      jsonBody.put("date", date);
      jsonBody.put("password", password);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonBody,
            new Response.Listener<JSONObject>() {
              @Override
              public void onResponse(JSONObject response) {
                // Manejar la respuesta del servidor
                Toast.makeText(RegisterActivity.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                // Manejar el error del servidor
                Toast.makeText(RegisterActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();
              }
            }
    );

    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(jsonObjectRequest);
  }

  private void showDatePickerDialog() {
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);

    DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        edtDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
      }
    }, year, month, day);
    datePickerDialog.show();
  }

  private boolean validEmail(String correo) {
    String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    Pattern pattern = Pattern.compile(regex);
    return pattern.matcher(correo).matches();
  }


}