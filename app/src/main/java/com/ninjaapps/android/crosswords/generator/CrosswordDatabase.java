package com.ninjaapps.android.crosswords.generator;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CrosswordDatabase {
    private SQLiteDatabase database;

    public static CrosswordDatabase getDatabase(Context c) {
        String dbName = "Dictionary.db";
        File dbFile = new File(c.getFilesDir() + File.separator + dbName);
        if (!dbFile.exists()) {
            AssetManager am = c.getAssets();
            try {
                BufferedInputStream is = new BufferedInputStream(am.open(dbName));
                BufferedOutputStream fo = new BufferedOutputStream(new FileOutputStream(dbFile));
                int size = 10 * 1024;
                byte[] buffer = new byte[size];
                int read;
                while ((read = is.read(buffer)) > 0) {
                    fo.write(buffer, 0, read);
                }
                is.close();
                fo.flush();
                fo.close();
            } catch (IOException e) { }
        }
        CrosswordDatabase db = new CrosswordDatabase(dbFile.getAbsolutePath());
        return db;
    }

    private CrosswordDatabase(String dbPath) {
        database = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    public ArrayList<String> getWordsMatchingLengths(ArrayList<Integer> wordLengths) {
        if (database != null) {
            StringBuilder selectionClause = new StringBuilder();
            int idx = 0;
            for (int len: wordLengths) {
                selectionClause.append("length(word) = ");
                selectionClause.append(len);
                if (++idx != wordLengths.size()) {
                    selectionClause.append(" or ");
                }
            }
            Cursor c = database.query("filtered_defs"
                    , new String[] {"word" }
                    , selectionClause.toString()
                    , null
                    , null
                    , null
                    , "length(word)");

            ArrayList<String> words = new ArrayList<>();
            while (c != null && c.moveToNext()) {
                words.add(c.getString(0));
            }
            return words;
        }
        return null;
    }

    public Map<String, String> getDefinitionsForWords(ArrayList<String> words) {
        if (database != null) {
            StringBuilder selectionClause = new StringBuilder("word in (");
            int idx = 0;
            for (String word: words) {
                selectionClause.append("\"");
                selectionClause.append(word);
                selectionClause.append("\"");
                if (++idx != words.size()) {
                    selectionClause.append(",");
                }
            }
            selectionClause.append(")");
            Cursor c = database.query("filtered_defs"
                    , new String[] {"word", "definition" }
                    , selectionClause.toString()
                    , null
                    , null
                    , null
                    , "length(word)");

            Map<String, String> definitions = new HashMap<>();
            while (c != null && c.moveToNext()) {
                definitions.put(c.getString(0), c.getString(1));
            }
            return definitions;
        }
        return null;
    }
}
