
package com.weinba.media;

import com.weinba.ActivityBase;
import com.weinba.Connector;
import com.weinba.LoaderImageView;
import com.weinba.R;
import com.weinba.Settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.List;
import java.util.Map;

public class ImagesGallery extends ActivityBase {
    private static final String TAG = "ImagesGallery";
    protected Object[] m_aFiles;
    protected ImagesGallery m_actImagesGallery;
    protected ImagesFilesAdapter m_adapterFiles;
    protected Button m_btnNext;
    protected Button m_btnPrev;
    protected Integer m_iIndex;
    protected List<Map<String, String>> m_listImages;
    protected String m_sAlbumId;
    protected String m_sPhotoId;
    protected String m_sUsername;
    protected LoaderImageView m_viewImageLoader;

    protected MediaFilesAdapter getAdapterInstance(Context paramContext,
            Object[] paramArrayOfObject, String paramString) {
        return new ImagesFilesAdapter(paramContext, paramArrayOfObject, paramString);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.media_images_gallery);
        this.m_actImagesGallery = this;
        this.m_viewImageLoader = ((LoaderImageView) findViewById(R.id.media_images_image_view));
        this.m_btnNext = ((Button) findViewById(R.id.media_images_gallery_next));
        this.m_btnPrev = ((Button) findViewById(R.id.media_images_gallery_prev));
        Intent localIntent = getIntent();
        this.m_sUsername = localIntent.getStringExtra("username");
        this.m_listImages = ((List) localIntent.getSerializableExtra("list"));

        Log.d("ImagesGallery", "m_listImages: " + this.m_listImages);
        if (this.m_listImages == null) {
            this.m_sAlbumId = localIntent.getStringExtra("album_id");
            this.m_sPhotoId = localIntent.getStringExtra("photo_id");
            reloadRemoteData();
        } else {
            setImageIndex(Integer.valueOf(localIntent.getIntExtra("index", 0)));
        }

        this.m_btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                ImagesGallery.this.setImageIndex(Integer.valueOf(1 + ImagesGallery.this.m_iIndex
                        .intValue()));
            }
        });

        this.m_btnPrev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                ImagesGallery.this.setImageIndex(Integer.valueOf(ImagesGallery.this.m_iIndex
                        .intValue() - 1));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu paramMenu) {
        Connector localConnector = Connector.restoreConnector();
        boolean bool;
        if (!this.m_sUsername.equalsIgnoreCase(localConnector.getUsername())) {
            bool = super.onCreateOptionsMenu(paramMenu);
        } else {
            getMenuInflater().inflate(2131099651, paramMenu);
            bool = true;
        }
        return bool;
    }

    public boolean onMenuItemSelected(int paramInt, MenuItem paramMenuItem) {
        switch (paramMenuItem.getItemId()) {
            case 2131165253:
                Connector localConnector = Settings.getConnector();
                Map localMap = (Map) this.m_listImages.get(this.m_iIndex.intValue());
                Object[] arrayOfObject = new Object[3];
                arrayOfObject[0] = localConnector.getUsername();
                arrayOfObject[1] = localConnector.getPassword();
                arrayOfObject[2] = localMap.get("id");
                localConnector.execAsyncMethod("dolphin.makeThumbnail", arrayOfObject,
                        new Connector.Callback() {
                            public void callFinished(Object paramObject) {
                                Log.d("ImagesGallery",
                                        "dolphin.makeThumbnail result: " + paramObject.toString());
                                String str;
                                if (!paramObject.toString().equals("ok"))
                                    str = ImagesGallery.this.getString(2130968690);
                                else
                                    str = ImagesGallery.this.getString(2130968689);
                                Toast.makeText(ImagesGallery.this.m_actImagesGallery, str, 0)
                                        .show();
                            }
                        }
                        , this);
        }
        return true;
    }

    protected void reloadRemoteData() {
        Connector localConnector = Settings.getConnector();

        Object[] arrayOfObject = new Object[4];
        arrayOfObject[0] = localConnector.getUsername();
        arrayOfObject[1] = localConnector.getPassword();
        arrayOfObject[2] = this.m_sUsername;
        arrayOfObject[3] = this.m_sAlbumId;

        localConnector.execAsyncMethod("dolphin.getImagesInAlbum", arrayOfObject,
                new Connector.Callback() {
                    public void callFinished(Object paramObject) {
                        Log.d("ImagesGallery",
                                "dolphin.getImagesInAlbum result: " + paramObject.toString());
                        ImagesGallery.this.m_aFiles = null;
                        ImagesGallery.this.m_aFiles = ((Object[]) paramObject);
                        Log.d("ImagesGallery", "dolphin.getImagesInAlbum num: "
                                + ImagesGallery.this.m_aFiles.length);
                        if (ImagesGallery.this.m_aFiles.length >= 1) {
                            ImagesGallery.this.m_adapterFiles = null;
                            ImagesGallery.this.m_adapterFiles = new ImagesFilesAdapter(
                                    ImagesGallery.this.m_actThis, ImagesGallery.this.m_aFiles,
                                    ImagesGallery.this.m_sUsername);
                            ImagesGallery.this.m_listImages = ImagesGallery.this.m_adapterFiles
                                    .getListStorage();
                            ImagesGallery.this.setImageIndex(Integer
                                    .valueOf(ImagesGallery.this.m_adapterFiles
                                            .getPositionByFileId(ImagesGallery.this.m_sPhotoId)));
                        } else {
                            ImagesGallery.this.m_actThis.finish();
                        }
                    }
                }
                , this);
    }

    protected void setImageIndex(Integer paramInteger) {
        int i = this.m_listImages.size();
        if (paramInteger.intValue() < 0)
            paramInteger = Integer.valueOf(0);
        if (paramInteger.intValue() > i - 1)
            paramInteger = Integer.valueOf(i - 1);
        Map localMap = (Map) this.m_listImages.get(paramInteger.intValue());
        this.m_viewImageLoader.setImageDrawable((String) localMap.get("file"));
        if ((1 != i) && (paramInteger.intValue() != 0))
            this.m_btnPrev.setVisibility(0);
        else
            this.m_btnPrev.setVisibility(4);
        if ((1 != i) && (i - 1 != paramInteger.intValue()))
            this.m_btnNext.setVisibility(0);
        else
            this.m_btnNext.setVisibility(4);
        this.m_iIndex = paramInteger;
    }
}

/*
 * Location: D:\tools\new-version-dex2jar\dex2jar-0.0.9.7\classes_dex2jar.jar
 * Qualified Name: com.boonex.oo.media.ImagesGallery JD-Core Version: 0.6.0
 */
