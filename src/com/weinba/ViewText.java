package com.weinba;

import com.weinba.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewText extends LinearLayout
{
  protected LinearLayout mLayout;
  protected TextView mViewText;

  public ViewText(Context paramContext, String paramString)
  {
    super(paramContext);
    LayoutInflater.from(paramContext).inflate(R.layout.video_folder_name_view, this, true);
    this.mLayout = ((LinearLayout)findViewById(R.id.fold_name_layout));
    this.mViewText = ((TextView)findViewById(R.id.folder_name));
    this.mViewText.setText(paramString);
  }
}