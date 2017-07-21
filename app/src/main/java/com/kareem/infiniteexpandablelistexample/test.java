package com.kareem.infiniteexpandablelistexample;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kareem.infiniteexpandablelist.InfiniteExpandableList;
import com.kareem.infiniteexpandablelist.InfiniteExpandableListInterface;
/**
 * Created by kareem on 6/30/2017 - InfiniteExpandableListView.
 * <br></br>
 * description goes here
 *
 * @author kareem
 * @version %I%
 */

public class test extends AppCompatActivity implements InfiniteExpandableListInterface  {
	public static InfiniteExpandableList getAdapter () {
		return infiniteExpandableList;
	}

	static InfiniteExpandableList infiniteExpandableList;

	@Override
	protected void onCreate ( @Nullable Bundle savedInstanceState ) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.container);
		infiniteExpandableList= new InfiniteExpandableList(this);
		infiniteExpandableList.createLevel(new String[]{ "Saturday", "Sunday", "Monday" }, dayViewHolder.class, R.layout.day_view);
	}

	@Override
	public Activity getActivity () {
		return this;
	}

	@Override
	public int getLayoutContainer () {
		return R.id.containerLayout;
	}
}
