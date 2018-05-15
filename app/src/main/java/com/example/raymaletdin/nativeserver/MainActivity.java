package com.example.raymaletdin.nativeserver;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final String PREFIX = "TAG_";
    private static final String TAG = PREFIX + MainActivity.class.getSimpleName();

    /**
     *  Used to load the 'native-lib' library on application startup.
     */
    static {
        System.loadLibrary("native-lib");
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native int startServer(String databasePath);
    public native int destroyServer();

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private AdapterRecycler mRecyclerAdapter;
    private LinearLayoutManager mLayoutManager;
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    @BindView(R.id.sample_text)
    TextView g;
    int s ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerAdapter = new AdapterRecycler(new ArrayList<>(), new ArrayList<>());
        mRecycler.setAdapter(mRecyclerAdapter);
        mRecycler.setLayoutManager(mLayoutManager);
    }

    @OnClick(R.id.startServer)
    public void onClickStart() {
        Runnable r = () -> startServer(database.getPath());
        new Thread(r).start();
    }

    @OnClick(R.id.destroyServer)
    public void onClickDestroy() {
        Runnable r = () -> destroyServer();
        new Thread(r).start();
    }

    @OnClick(R.id.refresh)
    public void onClickRefresh() {
        Cursor c = database.query(DBHelper.TABLE, null, null, null, null, null, null);
        log(c);
    }


    void log(Cursor cursor) {
        ArrayList<String> first = new ArrayList<>();
        ArrayList<String> second = new ArrayList<>();
        if (cursor.moveToFirst()) {
            int firstNameIndex = cursor.getColumnIndex(DBHelper.FIRST_NAME);
            int lastNameIndex = cursor.getColumnIndex(DBHelper.LAST_NAME);

            first.clear();
            second.clear();

            do {
                Log.i(TAG,
                        "firstName: " + cursor.getString(firstNameIndex)
                                + " lastName: " +  cursor.getString(lastNameIndex));
                first.add(cursor.getString(firstNameIndex));
                second.add(cursor.getString(lastNameIndex));
            } while (cursor.moveToNext());
            mRecyclerAdapter.add(first, second);
        } else
            Log.i(TAG, "0 rows");
    }
}
