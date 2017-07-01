package com.kareem.infiniteexpandablelist;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.kareem.infiniteexpandablelist.Exceptions.LayoutContainerDifferentType;
import com.kareem.infiniteexpandablelist.Exceptions.LayoutContainerNull;
import com.kareem.infiniteexpandablelist.Exceptions.ViewHolderWrongConstructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by kareem on 6/30/2017 - InfiniteExpandableList.
 * <br></br>
 * description goes here
 *
 * @author kareem
 * @version %I%
 */

public class InfiniteExpandableList implements View.OnClickListener {
	// referes to the parent/container that hosts the list
	private InfiniteExpandableListInterface parent;
	//tracks the current reached level of items
	private int currentLevel = 0;
	//tracks user requirements whether item click closes others or keep them open
	private boolean clearOnClick = true;
	//tracks the current position of the last item added
	private int currentPosition = 0;
	//=====> holds the preferred values for the padding
	private int pixelsPaddingTop = 5;
	private int pixelsPaddingBottom = 5;
	private int pixelsPaddingLeft = 20;
	private int pixelsPaddingRight = 5;
	//<=====

	/**
	 * constructor to start initializing the ExpandableListView
	 *
	 * @param parent is the interface that holds the required data such as context and layout container
	 */
	public InfiniteExpandableList ( InfiniteExpandableListInterface parent ) {
		this.parent = parent;
		if (parent.getActivity() != null) {

			try {
				//if passed layout could not be found, an exception is thrown
				if (parent.getActivity().findViewById(parent.getLayoutContainer()) == null)
					throw new LayoutContainerNull();
				//if passed layout is not viewHolder, an exception is thrown is notify user.
				if (! (parent.getActivity().findViewById(parent.getLayoutContainer()) instanceof ViewGroup))
					throw new LayoutContainerDifferentType();
				//starts tracking the passed container already have childs or not
				// if it contains the counter will increment and keep track of
				// the positions not to interface with the already existed components
				currentPosition = ((ViewGroup) parent.getActivity().findViewById(parent.getLayoutContainer())).getChildCount();
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("InfiniteExpandable", e.getMessage());
			}
		}
	}

	/**
	 * @return the parent container that will host the InfiniteExpandableList
	 */
	private ViewGroup getLayoutContainer () {
		return (ViewGroup) parent.getActivity().findViewById(parent.getLayoutContainer());
	}

	/**
	 * is used to insert a new level inside the InfiniteExpandableList
	 * and this level is added below the clicked item
	 *
	 * @param items a list of items that needs to be added as childs to current item
	 */
	private void createLevel ( ArrayList<InfiniteExpandableListItem> items ) {

		for (int i = 0; i < items.size(); i++) {
			InfiniteExpandableListItem item = items.get(i);
			//update level of the item to help track it's location
			item.setLevel(currentLevel);
			//update position of the item to help track it's location
			item.setPosition(i);
			//update internal listener to the item to trigger the customed listeners
			item.setListener(this);
			//edit the required final design of this item such as add padding to the item
			finalizeViewDesign(item.getView(), currentLevel);
			//if item is required to be placed after the last item
			//using addView(view,position) will result in RunTimeError of Container outOfBounds
			if (currentPosition >= getLayoutContainer().getChildCount())
				//add item to the last position of the container
				getLayoutContainer().addView(item.getView());
			else
				//add item to a specific position that is been tracked
				getLayoutContainer().addView(item.getView(), currentPosition);
			//increment the current position tracker to help add
			// the items in order behind the pressed parent-item
			currentPosition++;
		}
	}

	/**
	 * this is the method used to edit the final design
	 * of the view before adding to the container
	 *
	 * @param view  is the view that will have changes
	 * @param level the level of the view to calculate the
	 *              value of the passed padding
	 */
	private void finalizeViewDesign ( View view, int level ) {
		//get the ratio of the device between density pixels(DP) and pixels
		float ratio = parent.getActivity().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT;
		//calculate the required value to be passed to make a padding of a certain pxs
		view.setPadding((int) (pixelsPaddingLeft / ratio * level), (int) (pixelsPaddingTop / ratio * level), (int) (pixelsPaddingRight / ratio * level), (int) (pixelsPaddingBottom / ratio * level));
	}

	/**
	 * create items for the InfiniteExpandableList and assign views,viewHolders,Layout to each one
	 * also initialize the viewHolder class for each item
	 *
	 * @param data       data that holds the info about the item
	 * @param viewHolder view holder that holds the view of the item
	 * @param layout     is the resource file of the view
	 */
	public void createLevel ( Object[] data, Class<? extends InfiniteExpandableListViewHolder> viewHolder, int layout ) {
		if (data == null)
			return;
		View view;
		ArrayList<InfiniteExpandableListItem> listOfData = new ArrayList<>();
		//get constructor of the passed Type Class
		Constructor<? extends InfiniteExpandableListViewHolder> constructor;
		//for each item in the list of data
		//a new ListView item is created
		for (Object dataItem : data) {
			view = View.inflate(parent.getActivity(), layout, null);
			try {
				//viewHolder class is created for each item to hold it's views
				constructor = viewHolder.getConstructor(View.class, Object.class);

				listOfData.add(new InfiniteExpandableListItem(dataItem, constructor.newInstance(view, dataItem), view));
			} catch (IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
				e.printStackTrace();
				ViewHolderWrongConstructor VHE = new ViewHolderWrongConstructor();
				Log.e("InfiniteExpandableList", VHE.getMessage());
			}
		}
		createLevel(listOfData);
	}

	@SuppressWarnings("unused")
	public int getCurrentLevel () {
		return currentLevel;
	}

	/**
	 * can be used to insert items to a certain level in the list
	 *
	 * @param currentLevel
	 */
	@SuppressWarnings("unused")
	public void setCurrentLevel ( int currentLevel ) {
		this.currentLevel = currentLevel;
	}

	/**
	 * called when ever the viewItem is clicked/asked to collapse/expand
	 *
	 * @param v is the view clicked
	 */
	@Override
	public void onClick ( View v ) {
		//get tag of the saved data
		InfiniteExpandableListItem.InfiniteExpandableListViewItemTag tag = (InfiniteExpandableListItem.InfiniteExpandableListViewItemTag) v.getTag();
		//update current level reached
		currentLevel = tag.level + 1;
		//control call to expansion/collapsing
		triggerExpansion(tag, v);

	}

	/**
	 * @param tag tag of the view that holds data
	 * @param v   view itself
	 */
	private void triggerExpansion ( InfiniteExpandableListItem.InfiniteExpandableListViewItemTag tag, View v ) {
		//change the expansion state of the item
		tag.isExpanding = ! tag.isExpanding;
		//if item is now expanding call for clear other views and call onExpansion() trigger method
		if (tag.isExpanding) {
			//controlled by the user(library user)
			if (isClearOnClick()) {
				clearPageFromOtherItems(v);
			}
			//update the last recorded position to the pressed item
			//added items will be added as incremental to this value
			//means items will be positioned after this value
			//check it's location first very carefully!!!!
			currentPosition = getLayoutContainer().indexOfChild(v) + 1;

			tag.viewHolder.onInfiniteExpandableListViewItemIsExpanding(tag.data, tag.level, tag.position);
		} else {
			//if item is requested to collapse, inside items will be removed
			//is not controlled by the user
			clearPageFromOtherItems(v);
			//update the last recorded position to the pressed item
			currentPosition = getLayoutContainer().indexOfChild(v) + 1;
			tag.viewHolder.onInfiniteExpandableListViewItemIsCollapsing(tag.data, tag.level, tag.position);
		}
	}

	/**
	 * @param v is the view which other views will be compared to
	 */
	private void clearPageFromOtherItems ( View v ) {
		//get parent container
		ViewGroup parent = getLayoutContainer();
		//get level of the pressed view
		int level = ((InfiniteExpandableListItem.InfiniteExpandableListViewItemTag) v.getTag()).level;
		//get list of our-type views
		for (View item :
				  getListOfInfiniteItemsInView()) {
			//if the item has higher level, means its a child of a neighbour
			// and need to be removed to allow only pressed childs
			try {
				if (((InfiniteExpandableListItem.InfiniteExpandableListViewItemTag) item.getTag()).level > level)
					//remove that view
					parent.removeView(item);
					//if the item has the same level and is not the clicked one
					//means it's a neighbour and a collapsed call trigger will be activated
				else if (((InfiniteExpandableListItem.InfiniteExpandableListViewItemTag) item.getTag()).level == level && ! item.equals(v))
					triggerCollapsing(item);

			} catch (ClassCastException e) {
				e.printStackTrace();
				Log.e("InfiniteExpandable", e.getMessage());
			}
		}
	}

	/**
	 * @param v view that will get it's collapsing trigger activated
	 */
	private void triggerCollapsing ( View v ) {
		InfiniteExpandableListItem.InfiniteExpandableListViewItemTag tag = ((InfiniteExpandableListItem.InfiniteExpandableListViewItemTag) v.getTag());
		tag.isExpanding = false;
		tag.viewHolder.onInfiniteExpandableListViewItemIsCollapsing(tag.data, tag.level, tag.position);
	}

	/**
	 * @return array list of our-type items
	 * means that if the passed container has different items than ours
	 * //this other views will not be affected
	 * //also viewGroup doesn't return list of views, it only returns views at certain positions
	 * //this method is used to get this list of -propper-childs
	 */
	private ArrayList<View> getListOfInfiniteItemsInView () {
		ArrayList<View> listOfChilds = new ArrayList<>();
		View item;
		//get parent container
		ViewGroup parent = getLayoutContainer();
		for (int i = 0; i < parent.getChildCount(); i++) {
			item = parent.getChildAt(i);
			//check if this item is our type or not
			//our type is InfiniteExpandableListItem
			if (item.getTag() instanceof InfiniteExpandableListItem.InfiniteExpandableListViewItemTag)
				listOfChilds.add(item);
		}
		return listOfChilds;
	}

	@SuppressWarnings("WeakerAccess")
	public boolean isClearOnClick () {
		return clearOnClick;
	}

	/**
	 * @param clearOnClick controls if the clicked views should clear all other child views or not
	 */
	@SuppressWarnings("unused")
	public void setClearOnClick ( boolean clearOnClick ) {
		this.clearOnClick = clearOnClick;
	}

	/**
	 * set padding values of the final editing design for the
	 * view before adding to the container.
	 * all values are in pixels.
	 *
	 * @param pixelsPaddingRight  padding value for right
	 * @param pixelsPaddingLeft   padding value for left
	 * @param pixelsPaddingTop    padding value for top
	 * @param pixelsPaddingBottom padding value for bottom
	 */
	public void setPixelsPadding ( int pixelsPaddingRight, int pixelsPaddingLeft, int pixelsPaddingTop, int pixelsPaddingBottom ) {
		this.pixelsPaddingRight = pixelsPaddingRight;
		this.pixelsPaddingLeft = pixelsPaddingLeft;
		this.pixelsPaddingTop = pixelsPaddingTop;
		this.pixelsPaddingBottom = pixelsPaddingBottom;
	}
}
