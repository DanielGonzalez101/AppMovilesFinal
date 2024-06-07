package com.example.marvelstudios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
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

  EditText edtUser, edtPassword;
  Button btnLogin, btnRegister;
  SharedPreferences sharedPreferences;
  String savedUser, savedPassword;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

//      Traemos los datos
    edtUser = findViewById(R.id.edtUsuario);
    edtPassword = findViewById(R.id.edtContraseña);
    btnLogin = findViewById(R.id.btnLogin);
    btnRegister = findViewById(R.id.btnRegister);
    sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
    savedUser = sharedPreferences.getString("email", "");
    savedPassword = sharedPreferences.getString("password", "");

    btnLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Validamos los datos
        String email = edtUser.getText().toString();
        String password = edtPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
          Toast.makeText(LoginActivity.this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
          return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
          Toast.makeText(LoginActivity.this, "Correo electrónico inválido", Toast.LENGTH_SHORT).show();
          return;
        }

        sendLoginDataToApi(email, password);
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

  // Método para enviar los datos de inicio de sesión a la API
  public void sendLoginDataToApi(String email, String password) {
    String url = "http://10.0.2.2:3100/api/login"; // Cambia localhost a 10.0.2.2 si usas un emulador

    JSONObject jsonBody = new JSONObject();
    try {
      jsonBody.put("email", email); // Ajuste de 'user' a 'email'
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
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                // Manejar el error del servidor
                if (error.networkResponse != null) {
                  if (error.networkResponse.statusCode == 400) {
                    try {
                      String responseBody = new String(error.networkResponse.data, "UTF-8");
                      JSONObject errorResponse = new JSONObject(responseBody);
                      String errorMessage = errorResponse.getString("msg");

                      if ("El Usuario no esta registrado".equals(errorMessage)) {
                        Toast.makeText(LoginActivity.this, "El Usuario no está registrado", Toast.LENGTH_LONG).show();
                      } else if ("Contraseña Incorrecta".equals(errorMessage)) {
                        Toast.makeText(LoginActivity.this, "Contraseña Incorrecta", Toast.LENGTH_LONG).show();
                      } else {
                        Toast.makeText(LoginActivity.this, "Error desconocido", Toast.LENGTH_LONG).show();
                      }
                    } catch (Exception e) {
                      e.printStackTrace();
                    }
                  } else {
                    Toast.makeText(LoginActivity.this, "Error del servidor", Toast.LENGTH_LONG).show();
                  }
                } else {
                  Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_LONG).show();
                }
              }
            }
    );

    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(jsonObjectRequest);
  }

}