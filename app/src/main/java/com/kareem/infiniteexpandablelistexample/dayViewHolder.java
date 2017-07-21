package com.kareem.infiniteexpandablelistexample;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kareem.infiniteexpandablelist.InfiniteExpandableList;
import com.kareem.infiniteexpandablelist.InfiniteExpandableListViewHolder;

/**
 * Created by kareem on 6/30/2017 - InfiniteExpandableListView.
 * <br></br>
 * description goes here
 *
 * @author kareem
 * @version %I%
 */

public class dayViewHolder extends InfiniteExpandableListViewHolder {
	private ImageView image;
	private TextView dayName;

	public dayViewHolder ( final View view, Object data ) {
		super(view, data);
		image = (ImageView) view.findViewById(R.id.image);
		dayName = (TextView) view.findViewById(R.id.dayName);
		dayName.setText((String) data);
	}

	@Override
	public void onInfiniteExpandableListViewItemIsCollapsing ( Object data, int level, int position ) {
		image.setImageResource(R.mipmap.arrow_right);
		getView();
		getData();
	}

	@Override
	public void onInfiniteExpandableListViewItemIsExpanding ( Object data, int level, int position ) {
		image.setImageResource(R.mipmap.arrow_down);
		InfiniteExpandableList infiniteExpandableListVi = test.getAdapter();
		if (infiniteExpandableListVi == null)
			throw new NullPointerException("Null InfiniteListView is detected, this may happend because of activity destruction");
		if (level == 0) {
			infiniteExpandableListVi.createLevel(new String[]{ "12:30", "1:15", "5:59", "7:00" }, dayViewHolder.class, R.layout.day_view);
		} else {
			infiniteExpandableListVi.createLevel(new User[]{ new User("name of user 1", "location of user 1"), new User("name of user 2", "location of user 2"), new User("name of user 3", "location of user 3") }, userViewHolder.class, R.layout.user_item);
		}
	}
}