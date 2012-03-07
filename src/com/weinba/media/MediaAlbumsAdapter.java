package com.weinba.media;

import com.weinba.R;
import com.weinba.ViewText;
import com.weinba.R.string;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MediaAlbumsAdapter extends BaseAdapter {

    protected Context mContext;
    protected List<Map<String, String>> mListAlbums;
    protected List<View> mListViews;
    
    public MediaAlbumsAdapter(Context context, Object[] objs){
        this.mContext = context;
        this.mListAlbums = new ArrayList();
        for (int i = 0; i < objs.length; i++){
            this.mListAlbums.add((Map)objs[i]);
        }
        initViews();
    }
    
    public String getAlbumNameRaw(int paramInt)
    {
      return (String)((Map)this.mListAlbums.get(paramInt)).get("Title");
    }
    
    @Override
    public int getCount() {
        
        return mListAlbums.size();
    }

    @Override
    public Object getItem(int position) {

        return mListAlbums.get(position);
    }

    public String getAlbumId(int paramInt)
    {
      return (String)((Map)this.mListAlbums.get(paramInt)).get("Id");
    }
    
    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        if ((position < 0) || (position >= this.mListViews.size()))
            view = new ViewText(this.mContext, getAlbumName(position));
        else
            view = (View)this.mListViews.get(position);
        return (View)view;
    }
    
    public String getAlbumName(int paramInt)
  {
    String str;
    if ((paramInt >= 0) && (paramInt < this.mListAlbums.size()))
    {
      Map localMap = (Map)this.mListAlbums.get(paramInt);
      Integer localInteger = new Integer((String)localMap.get("Num"));
      if (localInteger.intValue() <= 0)
      {
        str = (String)localMap.get("Title");
      }
      else
      {
        str = this.mContext.getString(R.string.media_album_name_num);
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = localMap.get("Title");
        arrayOfObject[1] = localInteger;
        str = String.format(str, arrayOfObject);
      }
    }
    else
    {
      str = "Out of range";
    }
    return str;
  }
    protected void initViews()
    {
      this.mListViews = new ArrayList<View>();
      for (int i = 0; i < this.mListAlbums.size(); i++){
          this.mListViews.add(i, getView(i, null, null));
      }
        
    }
}
