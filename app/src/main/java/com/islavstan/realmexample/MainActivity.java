package com.islavstan.realmexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lapism.searchview.SearchView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    RecyclerAdapter adapter;
    RecyclerView recyclerView;
    EditText editName, editPage;
    Button saveBtn;
    Realm realm;
    List<Book> list = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getInstance(this);
        getAllBooks();
        setUI();


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewBook();
            }
        });

    }

    UserActionsListener listener = new UserActionsListener() {
        @Override
        public void deleteBook(String title, int position) {
            deleteBookFromRealm(title, position);
        }
    };

    private void setUI() {
        searchView = (SearchView) findViewById(R.id.searchView);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        editName = (EditText) findViewById(R.id.edit_name);
        editPage = (EditText) findViewById(R.id.edit_page);
        saveBtn = (Button) findViewById(R.id.savBtn);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new RecyclerAdapter(list, listener);
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                findBook(newText);


                return false;
            }
        });
    }


    private void saveNewBook() {
        String name = editName.getText().toString().trim();
        int page = Integer.parseInt(editPage.getText().toString().trim());
        realm.beginTransaction();
        Book book = realm.createObject(Book.class);
        book.setTitle(name);
        book.setPage(page);
        realm.commitTransaction();
        adapter.notifyItemInserted(list.size() - 1);
        editName.setText("");
        editPage.setText("");
    }


    private void getAllBooks() {
        list = realm.where(Book.class).findAll();
    }

    private void deleteBookFromRealm(String title, int position) {
        realm.beginTransaction();
        RealmResults<Book> books = realm.where(Book.class).equalTo("title", title).findAll();
        if (!books.isEmpty()) {
            for (int i = books.size() - 1; i >= 0; i--) {
                books.get(i).removeFromRealm();
            }
        }
        realm.commitTransaction();
        adapter.notifyItemRemoved(position);
    }

    private void findBook(String title) {
        realm.beginTransaction();
        RealmResults<Book> books = realm.where(Book.class).contains("title", title).findAll();
        adapter.newList(books);
        realm.commitTransaction();
        adapter.notifyDataSetChanged();
    }

}


