package br.edu.leaosampaio.CityCare.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lenilson on 22/04/17.
 */

public class Categoria implements Parcelable {

    private Long id;
    private String descricao;

    public Categoria(){

    }

    protected Categoria(Parcel in) {
        descricao = in.readString();
    }

    public static final Creator<Categoria> CREATOR = new Creator<Categoria>() {
        @Override
        public Categoria createFromParcel(Parcel in) {
            return new Categoria(in);
        }

        @Override
        public Categoria[] newArray(int size) {
            return new Categoria[size];
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

    public String toString(){
        return this.descricao;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(descricao);
    }
}
