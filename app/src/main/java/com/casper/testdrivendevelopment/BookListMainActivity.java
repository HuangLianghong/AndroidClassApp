package com.casper.testdrivendevelopment;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
//haha
public class BookListMainActivity extends AppCompatActivity {

    private ListView listViewBook;
    private ArrayList<Book> theBooks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_main);

        listViewBook = (ListView)findViewById(R.id.list_view_books);

        Init();

        GoodsArrayAdapter theAdapter = new GoodsArrayAdapter(this,R.layout.list_book,theBooks);
        listViewBook.setAdapter(theAdapter);


    }

    private void Init() {
        theBooks = new ArrayList<Book>();
        theBooks.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
        theBooks.add(new Book ("创新工程实践", R.drawable.book_no_name));
        theBooks.add(new Book("信息安全数学基础（第2版）", R.drawable.book_1));
    }


    protected class GoodsArrayAdapter extends ArrayAdapter<Book> {

        private int resourceId;
        public GoodsArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Book> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //渲染器生成视图
            LayoutInflater mInflater = LayoutInflater.from(this.getContext());
            View item = mInflater.inflate(this.resourceId,null);

            ImageView img = (ImageView)item.findViewById(R.id.image_view_book_cover);
            TextView name = (TextView)item.findViewById(R.id.text_view_book_title);


            //将商品信息放入对象中
            Book good_item=this.getItem(position);
            img.setImageResource(good_item.getCoverResourceId());
            name.setText(good_item.getTitle());

            return item;
        }
    }

    public List<Book> getListBooks(){
        return theBooks;
    }
}
