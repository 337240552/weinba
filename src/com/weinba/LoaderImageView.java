package com.weinba;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class LoaderImageView extends LinearLayout
{
  private static final int COMPLETE = 0;
  private static final int FAILED = 1;
  private final Handler imageLoadedHandler = new Handler(new Handler.Callback()
  {
    public boolean handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default:
        LoaderImageView.this.m_layoutImageLoader.setGravity(17);
        LoaderImageView.this.m_spinner.setVisibility(8);
        LoaderImageView.this.m_viewImage.setImageResource(LoaderImageView.this.m_iNoImageResource);
        LoaderImageView.this.m_viewImage.setVisibility(0);
        break;
      case 0:
        LoaderImageView.this.m_viewImage.setImageDrawable(LoaderImageView.this.m_drawable);
        LoaderImageView.this.m_viewImage.setVisibility(0);
        LoaderImageView.this.m_spinner.setVisibility(8);
      }
      return true;
    }
  });
  protected Context m_context;
  protected Drawable m_drawable;
  protected int m_iNoImageResource;
  protected LoaderImageView m_layoutImageLoader;
  protected ProgressBar m_spinner;
  protected ImageView m_viewImage;

  public LoaderImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    instantiate(paramContext, paramAttributeSet.getAttributeValue(null, "image"));
  }

  public LoaderImageView(Context paramContext, String paramString)
  {
    super(paramContext);
    instantiate(paramContext, paramString);
  }

  private static Drawable getDrawableFromUrl(String paramString)
    throws IOException, MalformedURLException
  {
    return Drawable.createFromStream((InputStream)new URL(paramString).getContent(), "name");
  }

  private void instantiate(Context paramContext, String paramString)
  {
    this.m_context = paramContext;
    this.m_layoutImageLoader = this;
    this.m_iNoImageResource = 2130837534;
    this.m_viewImage = new ImageView(this.m_context);
    this.m_viewImage.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
    this.m_spinner = new ProgressBar(this.m_context);
    this.m_spinner.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
    this.m_spinner.setIndeterminate(true);
    addView(this.m_spinner);
    addView(this.m_viewImage);
    if (paramString != null)
      setImageDrawable(paramString);
  }

  public void setImageDrawable(final String paramString)
  {
      
    this.m_spinner.setVisibility(0);
    this.m_viewImage.setVisibility(8);
    new Thread()
    {
      public void run()
      {
        try
        {
          LoaderImageView.this.m_drawable = LoaderImageView.getDrawableFromUrl(paramString);
          LoaderImageView.this.imageLoadedHandler.sendEmptyMessage(0);
          return;
        }
        catch (MalformedURLException localMalformedURLException)
        {
          while (true)
            LoaderImageView.this.imageLoadedHandler.sendEmptyMessage(1);
        }
        catch (IOException localIOException)
        {
          while (true)
            LoaderImageView.this.imageLoadedHandler.sendEmptyMessage(1);
        }
      }
    }
    .start();
  }

  public void setNoImageResource(int paramInt)
  {
    this.m_iNoImageResource = paramInt;
  }
}