package br.edu.leaosampaio.CityCare.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by lenilson on 22/04/17.
 */

public class Denuncia implements Parcelable {

    private Long id;
    private String descricao;
    private String localizacao;
    private Categoria categoria;
    private Usuario usuario;
    private String dataHora;

    public Denuncia(){

    }

    protected Denuncia(Parcel in) {
        descricao = in.readString();
        localizacao = in.readString();
        categoria = (Categoria) in.readParcelable(Categoria.class.getClassLoader());
        usuario = (Usuario) in.readParcelable(Usuario.class.getClassLoader());
        dataHora = in.readString();
    }

    public static final Creator<Denuncia> CREATOR = new Creator<Denuncia>() {
        @Override
        public Denuncia createFromParcel(Parcel in) {
            return new Denuncia(in);
        }

        @Override
        public Denuncia[] newArray(int size) {
            return new Denuncia[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora() {
        Calendar c = Calendar.getInstance();
        dataHora = String.valueOf(c.get(Calendar.DAY_OF_MONTH))+ "/" +
                String.valueOf(c.get(Calendar.MONTH))+ "/" +
                String.valueOf(c.get(Calendar.YEAR))+ "  " +
                String.valueOf(c.get(Calendar.HOUR_OF_DAY))+ ":" +
                String.valueOf(c.get(Calendar.MINUTE));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(descricao);
        dest.writeString(localizacao);
        dest.writeParcelable(categoria, flags);
        dest.writeParcelable(usuario, flags);
        dest.writeString(dataHora);
    }

}
