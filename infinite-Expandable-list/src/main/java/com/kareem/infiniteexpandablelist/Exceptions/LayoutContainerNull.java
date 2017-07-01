package com.kareem.infiniteexpandablelist.Exceptions;

/**
 * Created by kareem on 6/30/2017 - InfiniteExpandableList.
 * <br></br>
 * description goes here
 *
 * @author kareem
 * @version %I%
 */

public class LayoutContainerNull extends Exception {
	@Override
	public String getMessage () {
		return "container id initialized in getLayoutContainer() method could not be found!";
	}
}
