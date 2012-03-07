package com.weinba.media;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MediaFilesAdapter extends BaseAdapter
{
  protected Context m_context;
  protected List<Map<String, String>> m_listFiles;
  protected List<View> m_listViews;
  protected String m_sUsername;

  public MediaFilesAdapter(Context paramContext, Object[] paramArrayOfObject, String paramString)
  {
    this.m_context = paramContext;
    this.m_sUsername = paramString;
    this.m_listFiles = new ArrayList();
    for (int i = 0; i < paramArrayOfObject.length; i++)
      this.m_listFiles.add((Map)paramArrayOfObject[i]);
    initViews();
  }

  public int getCount()
  {
    return this.m_listFiles.size();
  }

  public Map<String, String> getItem(int paramInt)
  {
    Map localMap;
    if ((paramInt < 0) || (paramInt >= this.m_listFiles.size()))
      localMap = null;
    else
      localMap = (Map)this.m_listFiles.get(paramInt);
    return localMap;
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public List<Map<String, String>> getListStorage()
  {
    return this.m_listFiles;
  }

  public int getPositionByFileId(String paramString)
  {
    int i = 0;
    while (i < this.m_listFiles.size())
    {
      if (!paramString.equals(((Map)this.m_listFiles.get(i)).get("id")))
      {
        i++;
        continue;
      }
      i = i;
      break;
    }
    i -=1;
    return i;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    for (int i = this.m_listViews.size(); i < paramInt + 1; i++)
      this.m_listViews.add(i, getViewReal(i));
    
    View localView;
    if ((paramInt < 0) || (paramInt >= this.m_listViews.size()))
      localView = null;
    else
      localView = (View)this.m_listViews.get(paramInt);
    return localView;
  }

  public View getViewReal(int paramInt)
  {
    Map localMap = (Map)this.m_listFiles.get(paramInt);
    return new ThumbViewMedia(this.m_context, localMap, this.m_sUsername);
  }

  protected void initViews()
  {
    this.m_listViews = new ArrayList();
  }
}
