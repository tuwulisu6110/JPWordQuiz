package com.example.wordquiz;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import FunctionalTab.AddNewWordTab;
import java.util.ArrayList;

import FunctionalTab.FunctionalTab;

/**
 * Created by tuwulisu on 2015/2/13.
 */
public class MainActivity extends FragmentActivity
{
    private FragmentTabHost mTabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tabs);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.addTab(mTabHost.newTabSpec("newWord").setIndicator("New Word"), AddNewWordTab.class, getIntent().getExtras());
        //TabManager tm = new TabManager(this,mTabHost,R.id.realtabcontent);
//        mTabHost.addTab(mTabHost.newTabSpec("functions").setIndicator("Functions"),
//                FunctionalTab.class, null);

    }

}
