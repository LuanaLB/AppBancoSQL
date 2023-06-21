package com.example.appbancosql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BdSqlite extends SQLiteOpenHelper {
    public static final String BD_NAME = "bdsqlite";
    //If you change the database schema, you must increment the database version.
    public static final int BD_VERSAO = 1;

    public BdSqlite(@Nullable Context context) {

        /**
         * super(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
         * @param context to use for locating paths to the the database This value may be null.
         * @param name of the database file, or null for an in-memory database This value may be null.
         * @param factory SQLiteDatabase.CursorFactory: to use for creating cursor objects, or null for the default This value may be null.
         * @param version int: number of the database (starting at 1); if the database is older, onUpgrade(SQLiteDatabase, int, int)
         * will be used to upgrade the database; if the database is newer, onDowngrade(SQLiteDatabase, int, int) will be used to downgrade the database
         */
        super(context, BD_NAME, null, BD_VERSAO);
    }

    /**
     * Responsável por GERAR A TABELA TB_PESSOA NO BANCO
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE TB_PESSOA(");
        query.append(" ID INTEGER PRIMARY KEY AUTOINCREMENT,");
        query.append(" NOME TEXT NOT NULL,");
        query.append(" IDADE INT NOT NULL)");

        db.execSQL(query.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Responsável por INSERIR DADOS NO BANCO
     * Ao executar o método getWritableDatabase(), o método onCreate() é executado.
     * O método getWritableDatabase() é utilizado para as operações de insert, delete e update.
     */
    public void inserirDados(String nome, int idade){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("NOME", nome); // COLUNA / VALOR
        values.put("IDADE", idade);
        db.insert("TB_PESSOA",null,values);

    }

    /**
     * Responsável por CONSULTAR DADOS NO BANCO
     * Para consultar dados, utilizamos o método getReadableDatabase()
     */
    @SuppressLint("Range")
    public List<Pessoa> consultarDados(){
        SQLiteDatabase dbselec = getReadableDatabase();

        String[] colunas = {
                "ID",
                "NOME",
                "IDADE"
        };
        Cursor cursor = dbselec.query(
                "TB_PESSOA",   // The table to query
                colunas,       // The array of columns to return (pass null to get all)
                null,          // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,          // don't group the rows
                null,          // don't filter by row groups
                null           // The sort order
        );

        List<Pessoa> pessoas = new ArrayList<>();
        while(cursor.moveToNext()) {
            Pessoa p=new Pessoa();
            p.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            p.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
            p.setIdade(Integer.parseInt(cursor.getString(cursor.getColumnIndex("IDADE"))));
            pessoas.add(p);
        }

        cursor.close();
        return pessoas;
    }

    /**
     * Responsável por EXCLUIR DADOS NO BANCO
     */
    public void excluir(int id){
        SQLiteDatabase db = getReadableDatabase();
        // Define 'where' part of query.
        String selection = "ID LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(id) };
        // Issue SQL statement.
        db.delete("TB_PESSOA", selection, selectionArgs);
    }

    /**
     * Responsável por ATUALIZAR DADOS NO BANCO
     */
    public void update(int id, String coluna, String valor){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(coluna, valor);

        // Which row to update, based on the title
        String selection = "ID LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = db.update(
                "TB_PESSOA",
                values,
                selection,
                selectionArgs);
    }
}
