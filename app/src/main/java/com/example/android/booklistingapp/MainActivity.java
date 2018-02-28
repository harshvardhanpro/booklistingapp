package com.example.android.booklistingapp;

import android.app.LoaderManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?maxResults=10";
    private static final int BOOK_LOADER_ID = 1;
    private String finalUrl;
    private boolean firstLoaderCreated = false;

/*    private static class myAsyncTask extends AsyncTask<String, Void, List<Book>> {

        @Override
        protected List<Book> doInBackground(String...myString) {
            if (myString[0] != null) {
                List<Book> books = QueryUtils.fetchBookData(myString[0]);
                return books;
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            if (books != null) {
                for (int i = 0; i < books.size(); i++) {
                    Log.v("MainActivity.java", "First book title: " + books.get(i).mTitle);
                }
            } else {
                Log.v("MainActivity.java", "This book doesn't exist in our stock");
            }
        }
    }*/

    // Get a reference to the LoaderManager in order to interact with Loaders
    LoaderManager loaderManager = getLoaderManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a reference to search button
        Button searchButton = (Button) findViewById(R.id.search_button);

        /**
         * This event will be called when user clicks on search button
         */
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get a reference to search bar
                EditText searchBar = (EditText) findViewById(R.id.search_bar);

                // Store keyword typed by the user, i.e, Harry Potter
                String keyword = searchBar.getText().toString();

                finalUrl = getFinalUrl(keyword);

//                new myAsyncTask().execute(finalUrl);

                Log.v("MainActivity.java", "keyword value: " + keyword);
                Log.v("MainActivity.java", "finalUrl value: " + finalUrl);

                if (firstLoaderCreated == false) {
                    firstLoaderCreated = true;
                    // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                    // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                    // because this activity implements the LoaderCallbacks interface).
                    loaderManager.initLoader(BOOK_LOADER_ID, null, MainActivity.this);
                } else {
                    loaderManager.restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
                }
            }
        });
    }

    /**
     * This method defines the final url which will be used to make the request
     * @param keyword is the word that the user entered in the search  box
     * @return the formatted string
     */
    private String getFinalUrl(String keyword) {
        // Create a URI object
        Uri baseUri = Uri.parse(BASE_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter "q" to URI
        uriBuilder.appendQueryParameter("q", keyword);

        // Return URI in String format
        return uriBuilder.toString();

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new BookLoader(this, finalUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        for (int i = 0; i < books.size(); i++) {
            Log.v("MainActivity.java", "Book title is: " + books.get(i).mTitle);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        Log.v("MainActivity.java", "This is onLoaderReset");
    }
}