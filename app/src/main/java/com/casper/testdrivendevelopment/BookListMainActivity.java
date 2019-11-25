package com.casper.testdrivendevelopment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.casper.testdrivendevelopment.data.EditBookActivity;
import com.casper.testdrivendevelopment.data.FileDataSource;
import com.casper.testdrivendevelopment.data.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookListMainActivity extends AppCompatActivity {


    private ArrayList<Book> theBooks;
    private GoodsArrayAdapter theAdapter;
    private FileDataSource fileDataSource;

    public static final int CONTEXT_MENU_ITEM_NEW = 1;
    public static final int CONTEXT_MENU_ITEM_DELETE = 2;
    public static final int CONTEXT_MENU_ITEM_UPDATE= 3;
    public static final int CONTEXT_MENU_ITEM_ABOUT = 4;
    public static final int REQUEST_CODE_NEW_GOOD = 901;
    public static final int REQUEST_CODE_UPDATE_GOOD = 902;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_main);

        Init();
       theAdapter=new GoodsArrayAdapter(this,R.layout.list_book,theBooks);

        BookFragmentAdapter myPageAdapter = new  BookFragmentAdapter(getSupportFragmentManager());

        ArrayList<Fragment> datas = new ArrayList<Fragment>();
        datas.add(new BookListFragment(theAdapter));
        datas.add(new WebFragment());
        datas.add(new MapFragment());
        datas.add(new GameFragment());
        myPageAdapter.setData(datas);

        ArrayList<String> titles = new ArrayList<String>();
        titles.add("商品");
        titles.add("新闻");
        titles.add("商家");
        titles.add("游戏");
        myPageAdapter.setTitles(titles);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        viewPager.setAdapter(myPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
/*

        theAdapter = new GoodsArrayAdapter(this,R.layout.list_book,theBooks);

        */
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v==findViewById(R.id.list_view_books)){
            int itemPosition= ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
            menu.setHeaderTitle(theBooks.get(itemPosition).getTitle());//场景菜单标题
            menu.add(0, CONTEXT_MENU_ITEM_NEW, 0, "New");
            menu.add(0,CONTEXT_MENU_ITEM_UPDATE,0,"Update");
            menu.add(0, CONTEXT_MENU_ITEM_DELETE, 0, "Delete");
            menu.add(0, CONTEXT_MENU_ITEM_ABOUT, 0, "About...");
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    protected void onStop() {
        super.onStop();
        fileDataSource.save();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case REQUEST_CODE_NEW_GOOD:
                if(resultCode==RESULT_OK)
                {
                    int position=data.getIntExtra("edit_position",0);
                    String name=data.getStringExtra("good_name");
                    //double price =data.getDoubleExtra("good_price",0);
                    theBooks.add(position, new Book(name,R.drawable.book_1));
                    theAdapter.notifyDataSetChanged();

                    Toast.makeText(this, "Create successfully", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_UPDATE_GOOD:
                if(resultCode==RESULT_OK)
                {
                    int position=data.getIntExtra("edit_position",0);
                    String name=data.getStringExtra("good_name");
                   // double price =data.getDoubleExtra("good_price",0);

                    Book book=theBooks.get(position);
                    book.setTitle(name);
                    //good.setPrice(price);
                    theAdapter.notifyDataSetChanged();

                    Toast.makeText(getApplicationContext(), "Update successfully", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //实现事件菜单监听
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case CONTEXT_MENU_ITEM_NEW:{
                AdapterView.AdapterContextMenuInfo menuinfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                Intent intent = new Intent(BookListMainActivity.this,EditBookActivity.class);
                intent.putExtra("edit_position",menuinfo.position);
                startActivityForResult(intent, REQUEST_CODE_NEW_GOOD);
                break;
            }
            case CONTEXT_MENU_ITEM_DELETE: {
                AdapterView.AdapterContextMenuInfo menuinfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                menuinfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                //修改数据,删除视图控件
                final int itemPosition = menuinfo.position;
                    Boolean deleteOrNot = false;

                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Confirm")
                        .setMessage("Sure？") // 设置显示的view
                        .setPositiveButton("yes,just do it!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                theBooks.remove(itemPosition);
                                theAdapter.notifyDataSetChanged();//通知Adapter修改界面，否则界面不会更新
                                Toast.makeText(BookListMainActivity.this, "Roger that", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create().show();

                break;
            }
            case CONTEXT_MENU_ITEM_UPDATE:{
                AdapterView.AdapterContextMenuInfo menuinfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

                Book book=theBooks.get(menuinfo.position);

                Intent intent = new Intent(BookListMainActivity.this,EditBookActivity.class);
                intent.putExtra("edit_position",menuinfo.position);
                intent.putExtra("good_name",book.getTitle());
                startActivityForResult(intent, REQUEST_CODE_UPDATE_GOOD);
                break;
            }
            case CONTEXT_MENU_ITEM_ABOUT: {
                Toast.makeText(this, "Copyright reserved by lighuang", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        return super.onContextItemSelected(item);
    }

    private void Init() {
        fileDataSource = new FileDataSource(this);
        theBooks = fileDataSource.load();

        if(theBooks.size() == 0) {
            theBooks.add(new Book("no book for now", R.drawable.book_2));
        }
    }


    protected class GoodsArrayAdapter extends ArrayAdapter<Book>
    {
        private  int resourceId;
        public GoodsArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Book> objects) {
            super(context, resource, objects);
            resourceId=resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater mInflater= LayoutInflater.from(this.getContext());
            View item = mInflater.inflate(this.resourceId,null);

            ImageView img = (ImageView)item.findViewById(R.id.image_view_book_cover);
            TextView name = (TextView)item.findViewById(R.id.text_view_book_title);


            Book good_item= this.getItem(position);
            img.setImageResource(good_item.getCoverResourceId());
            name.setText(good_item.getTitle());


            return item;
        }
    }
}
