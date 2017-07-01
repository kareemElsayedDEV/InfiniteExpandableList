package com.kareem.infiniteexpandablelist;

import android.app.Activity;

/**
 * Created by kareem on 6/30/2017 - InfiniteExpandableList.
 * <br></br>
 * description goes here
 *
 * @author kareem
 * @version %I%
 */

public interface InfiniteExpandableListInterface {
	/**
	 * @return parent activity
	 */
	public Activity getActivity ();

	/**
	 * @return viewGroup layout container that is desired to insert the InfiniteExpandableList in
	 * @see android.view.ViewGroup
	 */
	public int getLayoutContainer ();

}
