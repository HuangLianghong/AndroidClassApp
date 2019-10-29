package com.casper.testdrivendevelopment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookListFragment extends Fragment {
    private BookListMainActivity.GoodsArrayAdapter theAdapter;

    public BookListFragment(BookListMainActivity.GoodsArrayAdapter theAdapter) {
        this.theAdapter = theAdapter;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        ListView listViewBook = (ListView)view.findViewById(R.id.list_view_books);
        listViewBook.setAdapter(theAdapter);
        this.registerForContextMenu(listViewBook);

        return view;
    }

}
