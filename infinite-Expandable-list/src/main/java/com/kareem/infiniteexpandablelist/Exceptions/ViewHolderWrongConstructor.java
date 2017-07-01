package com.kareem.infiniteexpandablelist.Exceptions;

/**
 * Created by kareem on 7/1/2017 - InfiniteExpandableList.
 * <br></br>
 * description goes here
 *
 * @author kareem
 * @version %I%
 */

public class ViewHolderWrongConstructor extends Exception {
	@Override
	public String getMessage () {
		return "Wrong View Holder Constructor, constructor needed with View,Data";
	}
}
