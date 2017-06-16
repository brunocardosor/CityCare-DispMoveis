package br.edu.leaosampaio.CityCare.Modelo;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;

/**
 * Created by lenilson on 21/04/17.
 */

public class Usuario implements Parcelable{

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String estado;
    private String cidade;
    private boolean status;

    public Usuario(){

    }

    protected Usuario(Parcel in) {
        nome = in.readString();
        email = in.readString();
        senha = in.readString();
        estado = in.readString();
        cidade = in.readString();
        status = in.readByte() != 0;
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String toString(){
        return this.nome;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nome);
        parcel.writeString(email);
        parcel.writeString(senha);
        parcel.writeString(estado);
        parcel.writeString(cidade);
        parcel.writeByte((byte) (status ? 1 : 0));
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (senha != null ? senha.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        result = 31 * result + (cidade != null ? cidade.hashCode() : 0);
        result = 31 * result + (status ? 1 : 0);
        return result;
    }
}