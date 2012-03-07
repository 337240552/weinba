package com.weinba;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Map;

public class ThumbView extends LinearLayout
{
    protected Context m_context;
    protected Map<String, String> m_map;
    protected String m_sUsername;
    protected LoaderImageView m_viewImageThumb;
    protected LinearLayout m_viewInfoContainer;
    protected TextView m_viewText1;
    protected TextView m_viewText2;
    protected TextView m_viewTitle;

    public ThumbView(Context paramContext, Map<String, String> paramMap, String paramString)
    {
      super(paramContext);
      this.m_viewInfoContainer = (LinearLayout) LayoutInflater.from(paramContext).inflate(R.layout.video_file_item_view, this, true);
      this.m_context = paramContext;
      this.m_sUsername = paramString;
      this.m_map = paramMap;
      this.m_viewImageThumb = ((LoaderImageView)findViewById(R.id.load_img_view));
      this.m_viewTitle = ((TextView)findViewById(R.id.thumb_info_text));
      this.m_viewText1 = ((TextView)findViewById(R.id.text1));
      this.m_viewText2 = ((TextView)findViewById(R.id.text2));
      addControls();
      setControlsData();
    }

    protected void addControls()
    {
    }

    protected String getImageUrl()
    {
      return (String)this.m_map.get(getThumbFieldName());
    }

    protected String getText1()
    {
      Object localObject1;
      if (this.m_map.get("Age") == null)
        localObject1 = (String)this.m_map.get("age");
      else
        localObject1 = (String)this.m_map.get("Age");
      Object localObject2;
      if (this.m_map.get("Sex") == null)
        localObject2 = (String)this.m_map.get("sex");
      else
        localObject2 = (String)this.m_map.get("Sex");
      String str;
      if ((((String)localObject1).length() <= 0) || (((String)localObject1).equalsIgnoreCase("0")) || (((String)localObject2).length() <= 0))
      {
        if ((((String)localObject1).length() <= 0) || (((String)localObject1).equalsIgnoreCase("0")))
        {
          if (((String)localObject2).length() <= 0)
            localObject1 = "";
          else
            localObject1 = localObject2;
        }
        else
        {
          str = this.m_context.getString(2130968583);
          Object[] obj = new Object[1];
          obj[0] = localObject1;
          localObject1 = String.format(str, obj);
        }
      }
      else
      {
        str = this.m_context.getString(2130968583);
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = localObject1;
        localObject1 = String.format(str, arrayOfObject) + " " + (String)localObject2;
      }
      return (String)(String)localObject1;
    }

    protected String getText2()
    {
      return "";
    }

    protected String getTextTitle()
    {
      return this.m_sUsername;
    }

    protected String getThumbFieldName()
    {
      return "Thumb";
    }

    public String getUsername()
    {
      return this.m_sUsername;
    }

    protected void setControlsData()
    {
      this.m_viewImageThumb.setImageDrawable(getImageUrl());
      this.m_viewTitle.setText(getTextTitle());
      this.m_viewText1.setText(getText1());
      this.m_viewText2.setText(getText2());
    }
  }

  /* Location:           D:\tools\new-version-dex2jar\dex2jar-0.0.9.7\classes_dex2jar.jar
   * Qualified Name:     com.boonex.oo.ThumbView
   * JD-Core Version:    0.6.0
   */