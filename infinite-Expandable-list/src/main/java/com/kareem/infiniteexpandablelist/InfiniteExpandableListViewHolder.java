package com.kareem.infiniteexpandablelist;

import android.view.View;

/**
 * Created by kareem on 6/30/2017 - InfiniteExpandableList.
 * <br></br>
 * description goes here
 *
 * @author kareem
 * @version %I%
 */

public abstract class InfiniteExpandableListViewHolder {
	private View view;
	private Object data;

	/**
	 * constructor of the viewHolder
	 *
	 * @param view passed view that can be modified on
	 * @param data data passed that contains info
	 */
	public InfiniteExpandableListViewHolder ( View view, Object data ) {
		this.view = view;
		this.data = data;
	}

	/**
	 * this method is called whenever item is collapsed
	 *
	 * @param data     data passed that contains info to control collapsing change
	 * @param level    level of this item
	 * @param position position of this item
	 */
	public abstract void onInfiniteExpandableListViewItemIsCollapsing ( Object data, int level, int position );

	/**
	 * this method is called whenever item is expanded
	 *
	 * @param data     data passed that contains info to control expansion changes
	 * @param level    level of this item
	 * @param position position of this item
	 */
	public abstract void onInfiniteExpandableListViewItemIsExpanding ( Object data, int level, int position );

	/**
	 * public method that can be accessed from childs to reach the passed view
	 * @return the saved view
	 */
	public View getView () {
		return view;
	}
	/**
	 * public method that can be accessed from childs to reach the passed data
	 * @return the saved data
	 */
	public Object getData () {
		return data;
	}
}
