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

public class InfiniteExpandableListItem {
	private Object data;
	private int position = 0;
	private int level = 0;
	private boolean isExpanding;
	private View view;
	private InfiniteExpandableListViewHolder viewHolder;

	/**
	 * constructor to create new item using it's view,viewHolder data and the view layout
	 *
	 * @param data       data used like User,Student
	 * @param viewHolder can be used to modify into the view data dynamically
	 * @param view       is the view itself created from passed layout
	 */
	public InfiniteExpandableListItem ( Object data, InfiniteExpandableListViewHolder viewHolder, View view ) {
		this.data = data;
		this.view = view;
		this.viewHolder = viewHolder;
	}

	/**
	 * @param position current position of this item in his own level
	 */
	public void setPosition ( int position ) {
		this.position = position;
	}

	/**
	 * current level of this item in the whole InfiniteExpandableList
	 *
	 * @param level indicates the level of depth of this item
	 */
	public void setLevel ( int level ) {
		this.level = level;
	}

	/**
	 * set listener to this item
	 *
	 * @param listener internal listener
	 * @see InfiniteExpandableList
	 */
	void setListener ( View.OnClickListener listener ) {
		view.setOnClickListener(listener);
		view.setTag(new InfiniteExpandableListViewItemTag(position, level, isExpanding, data, viewHolder));
	}

	/**
	 * tag to keep reaching data of this view much faster and more simple
	 */
	class InfiniteExpandableListViewItemTag {
		int position, level;
		Object data;
		InfiniteExpandableListViewHolder viewHolder;
		boolean isExpanding;

		InfiniteExpandableListViewItemTag ( int position, int level, boolean isExpanding, Object data, InfiniteExpandableListViewHolder viewHolder ) {

			this.position = position;
			this.level = level;
			this.data = data;
			this.viewHolder = viewHolder;
			this.isExpanding = isExpanding;
		}
	}

	/**
	 *
	 * @return saved view for this item
	 */
	public View getView () {
		return view;
	}
}
