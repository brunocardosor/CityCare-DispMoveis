package br.edu.leaosampaio.CityCare.Modelo;

/**
 * Created by lenilson on 21/04/17.
 */

public class UsuarioSingleton {
    private static Usuario usuario = null;

    public Usuario getInstance() {
        if (usuario == null) {
            usuario = new Usuario();
        }
        return usuario;
        }

    public void setInstance(Usuario usuario){
        this.usuario = usuario;
        }
}