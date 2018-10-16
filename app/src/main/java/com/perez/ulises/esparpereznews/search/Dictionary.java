package com.perez.ulises.esparpereznews.search;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Dictionary {

    private Context mContext;

    public Dictionary(Context context) {
        this.mContext = context;
    }

    public List<String> readLine(String path, String word) {
        List<String> mLines = new ArrayList<>();
        if (!word.isEmpty()) {
            AssetManager assetManager = mContext.getAssets();
            try {
                InputStream inputStream = assetManager.open(path);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                System.out.println("Dictionary // palabra " + word);
                while (((line = reader.readLine()) != null)) {
                    try {
                        if (reader.readLine().substring(0, word.length()).compareTo(word) == 0) {
                            mLines.add(line);
                            System.out.println("Dictionary // linea " + line);
                        }
                    } catch (NullPointerException e2) {
                        e2.printStackTrace();
                    } catch (StringIndexOutOfBoundsException e3) {
                        e3.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mLines;
    }
}
