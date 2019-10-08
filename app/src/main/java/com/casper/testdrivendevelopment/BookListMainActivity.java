package com.casper.testdrivendevelopment;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
//haha
public class BookListMainActivity extends AppCompatActivity {

    private ListView listViewBook;
    private ArrayList<Book> theBooks;
    private GoodsArrayAdapter theAdapter;

    public static final int CONTEXT_MENU_ITEM_NEW = 1;
    public static final int CONTEXT_MENU_ITEM_DELETE = 2;
    public static final int CONTEXT_MENU_ITEM_ABOUT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_main);

        listViewBook = (ListView)findViewById(R.id.list_view_books);

        Init();

        theAdapter = new GoodsArrayAdapter(this,R.layout.list_book,theBooks);
        listViewBook.setAdapter(theAdapter);

        this.registerForContextMenu(listViewBook);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v==listViewBook){
            int itemPosition= ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
            menu.setHeaderTitle(theBooks.get(itemPosition).getTitle());//场景菜单标题
            menu.add(0, CONTEXT_MENU_ITEM_NEW, 0, "New");
            menu.add(0, CONTEXT_MENU_ITEM_DELETE, 0, "Delete");
            menu.add(0, CONTEXT_MENU_ITEM_ABOUT, 0, "About...");
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    //实现事件菜单监听
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case 1:
                AdapterView.AdapterContextMenuInfo menuinfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                theBooks.add(menuinfo.position,new Book("new book", R.drawable.book_1));

                theAdapter.notifyDataSetChanged();

                Toast.makeText(this, "You have chosen to create a new object.", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                menuinfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
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
            case 3:
                Toast.makeText(this, "Copyright reserved by lighuang",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
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
