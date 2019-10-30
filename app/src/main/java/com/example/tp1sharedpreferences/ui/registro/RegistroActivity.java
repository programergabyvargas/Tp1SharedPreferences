package com.example.tp1sharedpreferences.ui.registro;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tp1sharedpreferences.R;
import com.example.tp1sharedpreferences.model.Usuario;

public class RegistroActivity extends AppCompatActivity {
  private EditText etDni, etApellido, etNombre, etMail, etPassword;
  private Button btnGuardar;
  private TextView tvMsj;
  private ViewModelRegistro viewModelRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        configView();
    }

    private void configView(){

        etDni = findViewById(R.id.etDni);
        etApellido = findViewById(R.id.etApellido);
        etNombre = findViewById(R.id.etNombre);
        etMail = findViewById(R.id.etMail);
        etPassword = findViewById(R.id.etPassword);
        tvMsj = findViewById(R.id.tvMsj);
        btnGuardar = findViewById(R.id.btnGuardar);
        viewModelRegistro = ViewModelProviders.of(this).get(ViewModelRegistro.class);
        String clave = getIntent().getStringExtra("clave");

        Log.d("calve = ", clave);
       if(clave.equals("l") ){
            viewModelRegistro.leer();
       }


        final Observer<Usuario> observerUsuario = new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                etDni.setText(""+ usuario.getDni());
               // etDni.setTextColor(Integer.parseInt("red")); probando
                etApellido.setText(usuario.getApellido());
                etNombre.setText(usuario.getNombre());
                etMail.setText(usuario.getMail());
                etPassword.setText(usuario.getPassword());
            }
        };

        viewModelRegistro.getUser().observe(this,observerUsuario); //asigno el observador



        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //guardar objet user en preferencias
                Long dni = (Long.parseLong(etDni.getText().toString()));
                String apellido = etApellido.getText().toString();
                String nombre = etNombre.getText().toString();
                String mail = etMail.getText().toString();
                String password = etPassword.getText().toString();
               if (viewModelRegistro.guardar(dni,apellido,nombre,mail,password)){
                   Toast.makeText(getApplicationContext(), "Los datos se han guardado Correctamente", Toast.LENGTH_LONG).show();
                    tvMsj.setText("Los datos se han guardado Correctamente");
                   //setearFormulario();
               }else {
                   Toast.makeText(getApplicationContext(), "No se pudo guardar los datos", Toast.LENGTH_LONG).show();
                   tvMsj.setText("No se pudo guardar los datos \n- Dni ocho digitos (solo números) \n- Mail no puede ser vacio \n- Password no puede ser vacio ");
               }
            }
        });

        // Otra forma de asignar el (relacionar) el observador al observado
        /*viewModelRegistro.getUser().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                etDni.setText(""+ usuario.getDni());
              //  etDni.setTextColor(Color.parseColor("#ffff0000")); Probando
                etApellido.setText(usuario.getApellido());
              //  etApellido.setTextColor(Color.parseColor("#ffff0000"));
                etNombre.setText(usuario.getNombre());
                etMail.setText(usuario.getMail());
                etPassword.setText(usuario.getPassword());
               }
        });*/


      /*  btnObtener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario user = new Usuario();
                user = viewModelRegistro.leer();
                etDni.setText(""+user.getDni());
                etApellido.setText(""+user.getApellido());
                etNombre.setText(""+user.getNombre());
                etMail.setText(""+user.getMail());
                etPassword.setText(""+user.getPassword());
            }
        });  Para probar si los datos se guardaron en las preferences*/
    }

    private void setearFormulario(){
        etDni.setText("");
        etApellido.setText("");
        etNombre.setText("");
        etMail.setText("");
        etPassword.setText("");

    }
}
