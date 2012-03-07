
package com.weinba.media;

import com.weinba.Connector;
import com.weinba.ListActivityBase;
import com.weinba.R;
import com.weinba.Connector.Callback;
import com.weinba.R.layout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class MediaAlbumsActivity extends ListActivityBase {
    private static final String TAG = "ImagesAlbumsActivity";

    MediaAlbumsAdapter albumsAdapter;
    Object[] m_aAlbums;
    protected Class<?> m_classFilesActivity;
    Connector m_oConnector;
    protected String m_sMethodXMLRPC;
    String m_sUsername;

    protected MediaAlbumsAdapter getAdapterInstance(Context paramContext,
            Object[] paramArrayOfObject) {
        return null;
    }

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.video_albulms_listview);
        this.m_sUsername = getIntent().getExtras().getString("username");
        this.m_oConnector = Connector.restoreConnector();
        reloadRemoteData();
    }

    protected void onListItemClick(ListView paramListView, View paramView, int paramInt,
            long paramLong) {
        super.onListItemClick(paramListView, paramView, paramInt, paramLong);
        String str = this.albumsAdapter.getAlbumId(paramInt);
        if (str != null) {
            Log.d("ImagesAlbumsActivity", "class: " + this.m_classFilesActivity);
            Intent localIntent = new Intent(this, this.m_classFilesActivity);
            localIntent.putExtra("username", this.m_sUsername);
            localIntent.putExtra("album_id", str);
            localIntent.putExtra("album_name", this.albumsAdapter.getAlbumNameRaw(paramInt));
            startActivityForResult(localIntent, 0);
        }
    }

    protected void reloadRemoteData()
  {
    Object[] params = new Object[3];
    params[0] = this.m_oConnector.getUsername();
    params[1] = this.m_oConnector.getPassword();
    params[2] = this.m_sUsername;
    Log.d(TAG, params[0] + " " + params[1] + " " + params[2] + " " + this.m_sMethodXMLRPC);
    this.m_oConnector.execAsyncMethod(this.m_sMethodXMLRPC, params, new Connector.Callback()
    {
      public void callFinished(Object paramObject)
      {
        Log.d("ImagesAlbumsActivity", MediaAlbumsActivity.this.m_sMethodXMLRPC + " result: " + paramObject.toString());
        
        MediaAlbumsActivity.this.m_aAlbums = ((Object[])paramObject);
        Log.d("ImagesAlbumsActivity", MediaAlbumsActivity.this.m_sMethodXMLRPC + " num: " + MediaAlbumsActivity.this.m_aAlbums.length);
       
        MediaAlbumsActivity.this.albumsAdapter = MediaAlbumsActivity.this.getAdapterInstance(MediaAlbumsActivity.this.m_actThis, MediaAlbumsActivity.this.m_aAlbums);
        MediaAlbumsActivity.this.setListAdapter(MediaAlbumsActivity.this.albumsAdapter);
      }
    }
    , this);
  }
}
