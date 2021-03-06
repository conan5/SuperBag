package com.example.k.superbag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.k.superbag.Fragment.FirstPageFragment;
import com.example.k.superbag.Fragment.MemoFragment;
import com.example.k.superbag.activity.EditActivity;
import com.example.k.superbag.activity.SettingsActivity;
import com.example.k.superbag.others.Constant;
import com.example.k.superbag.utils.GetImageUtils;
import com.example.k.superbag.utils.SuperbagDatabaseHelper;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ImageView navHeaderIV;
    private TextView signatureTV;
    private LinearLayout navHeaderLL;

    private Fragment[] fragments = new Fragment[3];
    private android.support.v4.app.FragmentManager fm;
    private int currentIndex = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("执行到了","main的onCreate()");
        initView();
        initListener();
//        initDatabase();
        fm  = getSupportFragmentManager();
        showFragment(0);

    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        //获取抽屉头像和背景的方法
        View view = navigationView.getHeaderView(0);
        navHeaderIV = (ImageView)view.findViewById(R.id.nav_header_iv);
        navHeaderLL = (LinearLayout)view.findViewById(R.id.nav_header_LL);
        signatureTV = (TextView) view.findViewById(R.id.nav_header_tv);
        //-----
        toolbar.getBackground().setAlpha(0);
    }

    private void initListener(){
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EditActivity.class));
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    //用于显示
    private void showFragment(int index){
        if (currentIndex == index){
            return;
        }
        currentIndex = index;
        FragmentTransaction transaction = fm.beginTransaction();
        hideAllFragment(transaction);
        switch (index){
            case 0:
                if (fragments[0] == null){
                    fragments[0] = new FirstPageFragment();
                    transaction.add(R.id.main_ll,fragments[0]);
                }
                transaction.show(fragments[0]);
                transaction.commit();
                break;
            case 1:
                if (fragments[1] == null){
                    fragments[1] = new MemoFragment();
                    transaction.add(R.id.main_ll,fragments[1]);
                }
                transaction.show(fragments[1]);
                transaction.commit();
                break;
            case 2:

                break;
        }
    }

    private void hideAllFragment(FragmentTransaction ft){
        for (Fragment fragment:fragments){
            if (fragment != null){
                ft.hide(fragment);
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_firstPage) {
            showFragment(0);
        } else if (id == R.id.nav_memo) {
            showFragment(1);
        } else if (id == R.id.nav_diary) {

        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        } else if (id == R.id.nav_quit) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //可以在返回该页面时，刷新抽屉头像和背景
    @Override
    public void onStart(){
        //设置抽屉头像
        Bitmap headIcon = GetImageUtils.getBMFromUri(this,"headIconUri");
        if (headIcon != null){
            navHeaderIV.setImageBitmap(headIcon);
        }
        //背景,有问题，图片会被拉伸
        Bitmap backGround = GetImageUtils.getBMFromUri(this,"bgUri");
        if(backGround != null){
            Drawable drawable = new BitmapDrawable(backGround);

            navHeaderLL.setBackground(new BitmapDrawable(backGround));
        }
        //个性签名
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String signature = sp.getString(Constant.SIGNATURE,"唱情歌落俗");
        signatureTV.setText(signature);
        super.onStart();
    }

}
