package br.edu.leaosampaio.CityCare.Modelo;

import android.app.Application;
import android.util.Log;

/**
 * Created by lenilson on 11/06/17.
 */

public class UsuarioAplication extends Application {

    private static Usuario usuario;
    private static final String TAG = "UsuárioApplication";
    private static UsuarioAplication instance = null;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public static UsuarioAplication getInstance(){
        return instance;
    }

    public void onCreate(){
        super.onCreate();
        Log.d(TAG, "UsuarioApplication.onCreate()");
        instance = this;
    }

    public void onTerminate(){
        super.onTerminate();
        Log.d(TAG,"UsuarioApplication.onTerminate");
    }


}
