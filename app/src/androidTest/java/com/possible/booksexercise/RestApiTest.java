package com.possible.booksexercise;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.possible.booksexercise.domain.Book;
import com.possible.booksexercise.rest.RestClient;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by korji on 7/30/17.
 */
@RunWith(AndroidJUnit4.class)
public class RestApiTest {

    @Test
    public void getBooksTest() throws Exception {
        RestClient restClient = new RestClient();
        Call<List<Book>> call = restClient.getApiService().getBooks();
        Response<List<Book>> booksResponse = call.execute();

        assertTrue("Call successful", booksResponse.isSuccessful());
        assertEquals("Response code 200", 200, booksResponse.code());
        List<Book> books = booksResponse.body();
        assertNotNull("Books list should not be null", books);
        assertTrue("Books list not empty", !books.isEmpty());

    }
}
