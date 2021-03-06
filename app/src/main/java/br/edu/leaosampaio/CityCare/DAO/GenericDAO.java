package br.edu.leaosampaio.CityCare.DAO;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import br.edu.leaosampaio.CityCare.R;

/**
 * Created by lenilson on 21/04/17.
 */

public abstract class GenericDAO<T> extends SQLiteOpenHelper{

    private static final String NOME_BANCO = "CityCareDB6";
    private static final int VERSAO = 6;
    private String tabela[] = {"usuario", "categoria", "denuncia", "estado", "cidade"};
    private String categorias[] = {"Categoria","Buraco nas vias","Entulho na calçada/via pública","Limpeza urbana","Esgoto a céu aberto","Bloqueio na via","Estacionamento irregular","Ocupação irregular de área pública","Lâmpada apagada à noite","Mato alto","Bueiro entupido","Ponto de alagamento","Condição sanitária irregular","Calçada irregular","Falta de rampa de acessibilidade","Foco de dengue","Poluição sonora","Equipamento público danificado","Poda/retirada de árvore","Faixa de pedestre inexistente","Imóvel abandonado","Falta de energia","Falta de água","Iluminação pública irregular","Fiação irregular","Emissão de fumaça preta","Transporte Público Irregular","Placa de sinalização quebrada","Vazamento de água","Queimada irregular","Infestação de roedores","Ponto de assalto/roubo","Recuperação de estradas","Lâmpada acesa de dia","Estação de ônibus/trem/metrô danificada","Falta de assentos na sala de embarque","Estabelecimento sem saída de emergência","Aterro sanitário irregular","Ar-condicionado com defeito","Ciclovia/ciclofaixa mal sinalizada","Ponto de tráfico de drogas","Demora na fila do check-in","Pesca ilegal","Serviço de Assistência Técnica","Cercamento de reserva legal","Ponto de ônibus danificado","Patrimônio histórico em risco","Tomada com defeito no aeroporto","Falta de táxi","Bicicletário danificado","Demora na entrega de bagagens em aeroporto","Lixeira quebrada","Estabelecimento sem nota fiscal","Calçada inexistente","Estádio danificado","Aeroporto com instalações danificadas","Semáforo quebrado","Metrô/trem danificado","Praia suja","Caça ilegal","Falta de carrinhos de bagagem","Estabelecimento sem alvará","Ônibus danificado","Desmatamento irregular","Aeroporto superlotado","Demora na fila do Raio X","Maus tratos a animais","Estabelecimento com acessibilidade irregular","Sanitário sujo no aeroporto","Painel de informação de vôos desligado/danificado","Acesso problemático ao estádio","Ônibus/trem/metrô superlotado","Desmatamento Ilegal","Caixa eletrônico com defeito","Veículo abandonado","Recuperação de Ponte","Poluição","Bueiro sem tampa","Atraso excessivo em voo","Estádio com acessibilidade irregular","Bagagem danificada","Presença de cambista em estádio","Sujeira no aeroporto","Ponto de prostituição de menores","Demora na entrega de bagagem","Utilização irregular de agrotóxico","Caça predatória","Passarela irregular"};
    private Context context;

    public GenericDAO(Context context){
        super(context,NOME_BANCO,null,VERSAO);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + tabela[0] + "(" +
                "id_"+tabela[0] +" INTEGER primary key autoincrement," +
                "nome_usuario VARCHAR(100) not null," +
                "email_usuario VARCHAR(100) unique not null," +
                "senha_usuario VARCHAR(100)," +
                "estado_usuario VARCHAR(45) not null," +
                "cidade_usuario VARCHAR(45) not null," +
                "status_usuario boolean not null);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + tabela[1] + "(" +
                "id_"+tabela[1]+" INTEGER primary key autoincrement," +
                "descricao_categoria VARCHAR(45) not null);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + tabela[2] +"(" +
                "id_" + tabela[2] + " INTEGER primary key autoincrement," +
                "denuncia_id_usuario INTEGER not null," +
                "denuncia_id_categoria INTEGER not null," +
                "denuncia_descricao VARCHAR(400) not null," +
                "denuncia_latitude double(20) not null," +
                "denuncia_longitude double(20) not null," +
                "denuncia_cidade VARCHAR(200) not null," +
                "denuncia_data_hora TEXT not null," +
                "FOREIGN KEY(denuncia_id_usuario) REFERENCES "+ tabela[0] + "(id_"+tabela[0]+")," +
                "FOREIGN KEY(denuncia_id_categoria) REFERENCES " + tabela[1] +"(id_"+tabela[1]+"));");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + tabela[3] + "(" +
                "id_" + tabela[3] +" INTEGER primary key autoincrement," +
                "estado VARCHAR(45) not null);");

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ tabela[4] +"(" +
                "id_"+tabela[4] + " INTEGER primary key autoincrement," +
                "cidade_id_estado INTEGER not null," +
                "cidade VARCHAR(45) not null," +
                "FOREIGN KEY(cidade_id_estado) REFERENCES " + tabela[3] + "(id_"+tabela[3]+"));");
        try {
            inserirEstados(db);
            inserirMunicipios(db);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < categorias.length; i++){
            db.execSQL("INSERT INTO " + tabela[1] + " (descricao_categoria) values('"+categorias[i]+"')");
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public abstract boolean salvar(T t, Context c);

    public abstract boolean delete(T t, Context c);

    public void inserirMunicipios(SQLiteDatabase db) {
        final Resources res = context.getResources();
        InputStream is = res.openRawResource(R.raw.estado);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] strings = TextUtils.split(linha, ";");
                db.execSQL(linha);
                Log.i("TableCidade", "Erro : " + strings[0].trim());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void inserirEstados(SQLiteDatabase db) throws IOException {
        final Resources res = context.getResources();
        InputStream is = res.openRawResource(R.raw.cidade);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] strings = TextUtils.split(linha, ";");
                db.execSQL(linha);
                Log.e("Teste", "Erro : " + strings[0].trim());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

