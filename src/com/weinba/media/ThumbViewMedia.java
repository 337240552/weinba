package com.weinba.media;

import com.weinba.ThumbView;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Map;

public class ThumbViewMedia extends ThumbView {

    public ThumbViewMedia(Context paramContext, Map<String, String> paramMap, String paramString)
    {
      super(paramContext, paramMap, paramString);
    }

    protected String getText1()
    {
      return (String)this.m_map.get("desc");
    }

    protected String getTextTitle()
    {
      return (String)this.m_map.get("title");
    }

    protected String getThumbFieldName()
    {
      return "thumb";
    }
    
}
