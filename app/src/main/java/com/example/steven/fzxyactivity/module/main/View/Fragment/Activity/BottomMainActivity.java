package com.example.steven.fzxyactivity.module.main.View.Fragment.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.steven.fzxyactivity.R;
import com.example.steven.fzxyactivity.module.main.View.Fragment.DiscoverFragment;
import com.example.steven.fzxyactivity.module.main.View.Fragment.HomeFragment;
import com.example.steven.fzxyactivity.module.main.View.Fragment.MeFragment;
import com.example.steven.fzxyactivity.module.main.View.Fragment.MessageFrament;

public class BottomMainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{
    private BottomNavigationBar mBottomNavigationBar;
    int mlastSelectedPosition = 0;
    private HomeFragment homeFragment;
    private MessageFrament messageFrament;
    private DiscoverFragment discoverFragment;
    private MeFragment meFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_main);
        mBottomNavigationBar= (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar_container);
        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.tabbar_home,"主页"))
                .addItem(new BottomNavigationItem(R.mipmap.tabbar_message_center,"消息"))
                .addItem(new BottomNavigationItem(R.mipmap.tabbar_discover,"发现"))
                .addItem(new BottomNavigationItem(R.mipmap.tabbar_profile,"中心"))
                .setFirstSelectedPosition(mlastSelectedPosition)
                .initialise();
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        mBottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();

    }

    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        homeFragment = HomeFragment.newInstance("","");
        transaction.replace(R.id.rl_container,homeFragment);
        transaction.commit();
    }

    @Override
    public void onTabSelected(int position) {
        FragmentManager fm=this.getFragmentManager();
        //开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position){
            case 0:
                if (homeFragment == null) {
                    homeFragment = homeFragment.newInstance("主页","");
                }
                transaction.replace(R.id.rl_container, homeFragment);
                break;
            case 1:
                if (messageFrament == null) {
                    messageFrament = messageFrament.newInstance("信息","");
                }
                transaction.replace(R.id.rl_container, messageFrament);
                break;
            case 2:
                if (discoverFragment == null) {
                    discoverFragment = discoverFragment.newInstance("发现","");
                }
                transaction.replace(R.id.rl_container, discoverFragment);
                break;
            case 3:
                if (meFragment == null) {
                    meFragment = meFragment.newInstance("中心","");
                }
                transaction.replace(R.id.rl_container, meFragment);
                break;
            default:
                break;
        }
        //事务提交
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
