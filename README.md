# InfiniteExpandableList
the idea of infiniteExpandableList is that allows you to create infinite number of expandable lists with many levels
the views can be customized and a viewholder can be used to more interact with clicks and customed views

<img src="https://github.com/kareemElsayedDEV/InfiniteExpandableList/blob/master/Screenshot_2017-07-01-04-42-37.png" width="320" height="568" />
<img src="https://media.giphy.com/media/kjFmsXNUyX8Zi/giphy.gif" width="320" height="568" />

## Requirements
Min SDK Version of 19

## Installation

### Gradle
make sure to add this to your root build.gradle
```
allprojects {
 repositories {
  ...
  maven { url 'https://jitpack.io' }
  }
}
```
Add the dependency
```
dependencies {
  compile 'com.github.kareemElsayedDEV:InfiniteExpandableList:0.1.0'
}
```

### Maven
```
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```
Add the dependency
```
<dependency>
  <groupId>com.github.kareemElsayedDEV</groupId>
  <artifactId>InfiniteExpandableList</artifactId>
  <version>0.1.0</version>
</dependency>
```
## How to use
### first
make your main Activity/fragment <strong>implements</strong> library interface
```
extends AppCompatActivity implements InfiniteExpandableListInterface{
```
### Then
create new level by passing the required data
<strong>Object[] dataHolders</strong> , <strong>Class< ? extends InfiniteExpandableListViewHolder> viewHolders</strong> , <strong>int layout</strong>
```
InfiniteExpandableList infiniteList = new InfiniteExpandableList(this);
User[] users = new User[]{new User(),new User()};
infiniteList.createLevel(users, UserViewHolder.class, R.layout.user_item);
```
### Later
modify this method to return this id of the ViewGroup container that will cotain the InfiniteExpandableList
```
@Override
public int getLayoutContainer () {
  return R.id.containerLayout;
}
  ```
that's it , now we have first level of the items
  <h3>customize ViewHolder</h3>
  
  in the ViewHolder class contructor has 2 passed values , ```View view``` for the view reference and  ```Object data``` for the data reference 
```  
public userViewHolder ( View view, Object data ) {
  super(view, data);
  TextView name = (TextView) view.findViewById(R.id.name);
  TextView location = (TextView) view.findViewById(R.id.location);
  name.setText(((User) data).getName());
  location.setText(((User) data).getLocation());
}
```
  
  <h3>Expansion and collapsing events</h3>
  two events are triggered when expanded,collapsed
  
```
@Override
public void onInfiniteExpandableListViewItemIsCollapsing ( Object data, int level, int position ) {
}

@Override
public void onInfiniteExpandableListViewItemIsExpanding ( Object data, int level, int position ) {
}
```
that can eventually be used to create a new level

```
@Override
public void onInfiniteExpandableListViewItemIsExpanding ( Object data, int level, int position ) {
  //method defined in the Activity/fragment to pass the initialized InfiniteExpandableList;
  InfiniteExpandableList infiniteExpandableList = test.getInfiniteExpandableList();
  if (infiniteExpandableListVi == null)
    throw new NullPointerException("Null InfiniteListView is detected, this may happend because of activity destruction");
  infiniteExpandableListVi.createLevel(new User[]{ new User("name of user 1", "location of user 1"), new User("name of user 2", "location of user 2"), new User("name of user 3", "location of user 3") }, UserViewHolder.class, R.layout.user_item);
  }
}
```
and <strong>finally</strong>
there are 2 methods that can be used to access the data,view in each viewHolder class

```
getView();
getData();
```
