package com.kareem.infiniteexpandablelist.Exceptions;

/**
 * Created by kareem on 6/30/2017 - InfiniteExpandableList.
 * <br></br>
 * description goes here
 *
 * @author kareem
 * @version %I%
 */

public class LayoutContainerDifferentType extends Exception{
	@Override
	public String getMessage () {
		return "container layout must be of type ViewGroup";
	}
}
