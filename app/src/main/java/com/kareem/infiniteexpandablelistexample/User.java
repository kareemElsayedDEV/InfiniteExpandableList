package com.kareem.infiniteexpandablelistexample;

/**
 * Created by kareem on 6/30/2017 - InfiniteExpandableListView.
 * <br></br>
 * description goes here
 *
 * @author kareem
 * @version %I%
 */

public class User {
private String name,location;

	public User ( String name, String location ) {
		this.name = name;
		this.location = location;
	}

	public String getName () {
		return name;
	}

	public void setName ( String name ) {
		this.name = name;
	}

	public String getLocation () {
		return location;
	}

	public void setLocation ( String location ) {
		this.location = location;
	}
}
