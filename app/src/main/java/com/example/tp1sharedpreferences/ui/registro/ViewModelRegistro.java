package com.example.tp1sharedpreferences.ui.registro;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tp1sharedpreferences.model.Usuario;
import com.example.tp1sharedpreferences.request.ApiClient;

import java.util.List;

public class ViewModelRegistro extends AndroidViewModel {
    private MutableLiveData<Usuario> userLiveData;
    private Usuario user;
    private Context context;

    public ViewModelRegistro(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        user = new Usuario();
        userLiveData = new MutableLiveData<Usuario>();
    }

    public LiveData<Usuario> getUser(){
       if (userLiveData == null){
            userLiveData = new MutableLiveData<Usuario>();
        }

        return userLiveData;
    }

    public void leer(){
        user = ApiClient.leer(context);
        userLiveData.setValue(user);
    }

    public boolean guardar(Long dni, String apellido, String nombre, String email, String pass) {
          boolean guardarOk = false;
        if (validarDni(dni)) {
            if (!email.equals("") && !pass.equals("") ) {
                Usuario usuario = new Usuario(dni, apellido, nombre, email, pass);
                ApiClient.guardar(context, usuario);
                guardarOk = true;
            }
        } else  guardarOk = false;
    return guardarOk;
    }

    private boolean validarDni(Long dni) {
        String dnis = dni.toString();
        if (dnis.length() != 8) return false;
        int i=0;
        boolean valido = true;

        while (i<7 && valido)
        {
            if (dnis.charAt(i) < '0' || dnis.charAt(i) > '9')
                valido = false;
            else i++;
        }
        return (valido && dnis.charAt(i)>='0' && dnis.charAt(i)<='9');
    }

}
