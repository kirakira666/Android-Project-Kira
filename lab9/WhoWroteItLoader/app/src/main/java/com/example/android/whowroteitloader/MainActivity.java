/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.whowroteitloader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The WhoWroteIt app queries the Book Search API for books based
 * on a user's search.  It uses an AsyncTask to run the search task in
 * the background.
 */
public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String> {

    private EditText mBookInput;
    private TextView mTitleText;
    private TextView mAuthorText;
    private TextView mScrollText;
    private  String mhttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBookInput = findViewById(R.id.bookInput);
        mTitleText = findViewById(R.id.titleText);
        mAuthorText = findViewById(R.id.authorText);
        mScrollText = findViewById(R.id.scrollText);


        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] languages = getResources().getStringArray(R.array.languages);
                mhttp=languages[pos];
//                Toast.makeText(MainActivity.this, "你点击的是:"+languages[pos], 2000).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    /**
     * onClick handler for the "Search Books" button.
     *
     * @param view The view (Button) that was clicked.
     */
    public void searchBooks(View view) {
        // Get the search string from the input field.
        String queryString = mBookInput.getText().toString();
        queryString = mhttp+queryString+"/";
        System.out.println("9999999999999999999999999999999999999999999999999999999999999999999999999999");
        System.out.println(queryString);

        // Hide the keyboard when the button is pushed.
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        // Check the status of the network connection.
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        // If the network is available, connected, and the search field
        // is not empty, start a BookLoader AsyncTask.
        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) {

            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);

            mAuthorText.setText("");
            mTitleText.setText("");
        }
        // Otherwise update the TextView to tell the user there is no
        // connection, or no search term.
        else {
            if (queryString.length() == 0) {
                mAuthorText.setText("");
                mTitleText.setText(R.string.no_search_term);
            } else {
                mAuthorText.setText("");
                mTitleText.setText(R.string.no_network);
            }
        }
    }


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";

        if (args != null) {
            queryString = args.getString("queryString");
        }

        return new BookLoader(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            // Convert the response into a JSON object.
            String data1  = data.toString();
            System.out.println(data1);
            mScrollText.setText(data1);
            JSONObject jsonObject = new JSONObject(data);

            // Get the JSONArray of book items.
            JSONArray itemsArray = jsonObject.getJSONArray("data");

            // Initialize iterator and results fields.
            int i = 0;
            JSONObject title1 = null;
            String authors = null;
            String title = null;

            // Look for results in the items array, exiting when both the
            // title and author are found or when all items have been checked.
//            while (i < itemsArray.length() &&
//                    (authors == null && title == null)) {
//                // Get the current item information.
//                JSONObject book = itemsArray.getJSONObject(i);
//                JSONObject volumeInfo = book;
//                System.out.println(book.toString());
//                System.out.println("99999999999999999999999999999999999999999999999999999999999999999999999999999999");
////                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
//
//                // Try to get the author and title from the current item,
//                // catch if either field is empty and move on.
//                try {
//                    title1 = volumeInfo.getJSONObject("Author");
//                    title=title1.getString("Name ");
//                    System.out.println(title);
//                    authors = volumeInfo.getString("authors");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                // Move to the next item.
//                i++;
//            }

            JSONObject book = itemsArray.getJSONObject(0);
            JSONObject volumeInfo = book;
            System.out.println(book.toString());
            System.out.println("99999999999999999999999999999999999999999999999999999999999999999999999999999999");

            try {
//              title1 = volumeInfo.getString("Author");
                title = volumeInfo.getString("Name");
                authors = volumeInfo.getString("Author");
                System.out.println(title);
                System.out.println("99999999999999999999999999999999999999999999999999999999999999999999999999999999");
                System.out.println(authors);
                System.out.println("99999999999999999999999999999999999999999999999999999999999999999999999999999999");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // If both are found, display the result.
            if (title != null && authors != null) {
                mTitleText.setText("");
                mAuthorText.setText("");
                //mBookInput.setText("");
            } else {
                // If none are found, update the UI to show failed results.
                mTitleText.setText("");
                mAuthorText.setText("");
            }

        } catch (Exception e) {
            // If onPostExecute does not receive a proper JSON string,
            // update the UI to show failed results.
            mTitleText.setText("");
            mAuthorText.setText("");
            e.printStackTrace();
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        // Do nothing.  Required by interface.
    }
}
