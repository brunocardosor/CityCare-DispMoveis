package br.edu.leaosampaio.CityCare.Modelo;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Created by lenilson on 22/04/17.
 */

public class Denuncia implements Parcelable {

    private Long id;
    private String descricao;
    private String cidade;
    private double latitude;
    private double longitude;
    private Categoria categoria;
    private Usuario usuario;
    private String dataHora;

    public Denuncia(){

    }

    protected Denuncia(Parcel in) {
        descricao = in.readString();
        cidade = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        categoria =  in.readParcelable(Categoria.class.getClassLoader());
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

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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
        dest.writeString(cidade);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeParcelable(categoria, flags);
        dest.writeString(dataHora);
    }

    public String getEndereco(Context context){
        double latitude = getLatitude();
        double longitude = getLongitude();
        Geocoder geocoder = new Geocoder(context);
        try {
            List<Address> enderecos = geocoder.getFromLocation(latitude, longitude, 1);
            String endereco = enderecos.get(0).getAddressLine(0);
            return  endereco;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
