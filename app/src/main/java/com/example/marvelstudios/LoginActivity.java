package com.example.marvelstudios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

  EditText edtUsuario, edtContraseña;
  Button btnLogin, btnRegister;
  SharedPreferences sharedPreferences;
  String savedUser, savedPassword;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

//      Traemos los datos
    edtUsuario = findViewById(R.id.edtUsuario);
    edtContraseña = findViewById(R.id.edtPassword);
    btnLogin = findViewById(R.id.btnLogin);
    btnRegister = findViewById(R.id.btnRegister);
    sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
    savedUser = sharedPreferences.getString("email", "");
    savedPassword = sharedPreferences.getString("password", "");

    btnLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//                Validamos los datos
        // Validamos los datos
        String user = edtUsuario.getText().toString();
        String password = edtContraseña.getText().toString();

        if (user.isEmpty() || password.isEmpty()) {
          Toast.makeText(LoginActivity.this, "Por favor llenar los campos ", Toast.LENGTH_SHORT).show();
        } else {
          sendLoginDataToApi(user, password);
          Intent i = new Intent(LoginActivity.this, MainActivity.class);
          startActivity(i);
          finish();
        }
      }
    });

    btnRegister.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
        finish();
      }
    });
  }
  public void sendLoginDataToApi(String user, String password) {
    String url = "http://10.0.2.2:3100/api/login"; // Cambia localhost a 10.0.2.2 si usas un emulador

    JSONObject jsonBody = new JSONObject();
    try {
      jsonBody.put("user", user);
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
                // Puedes agregar lógica adicional para manejar la respuesta
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                // Manejar el error del servidor
                Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
              }
            }
    );

    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(jsonObjectRequest);
  }
}