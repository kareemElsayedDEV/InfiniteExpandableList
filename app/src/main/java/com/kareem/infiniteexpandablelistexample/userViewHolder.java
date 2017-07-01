package com.kareem.infiniteexpandablelistexample;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kareem.infiniteexpandablelist.InfiniteExpandableListViewHolder;

/**
 * Created by kareem on 6/30/2017 - InfiniteExpandableListView.
 * <br></br>
 * description goes here
 *
 * @author kareem
 * @version %I%
 */

public class userViewHolder extends InfiniteExpandableListViewHolder {

	public userViewHolder ( View view, Object data ) {
		super(view, data);
		TextView name = (TextView) view.findViewById(R.id.name);
		TextView location = (TextView) view.findViewById(R.id.location);
		name.setText(((User) data).getName());
		location.setText(((User) data).getLocation());
	}

	@Override
	public void onInfiniteExpandableListViewItemIsCollapsing ( Object data, int level, int position ) {
		Toast.makeText(getView().getContext(), "Collapsed User", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onInfiniteExpandableListViewItemIsExpanding ( Object data, int level, int position ) {
		Toast.makeText(getView().getContext(), "Expanded User", Toast.LENGTH_SHORT).show();
	}
}
