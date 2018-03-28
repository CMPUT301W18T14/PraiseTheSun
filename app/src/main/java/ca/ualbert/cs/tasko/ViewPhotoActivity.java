/*
 * Copyright © 2018 Chase Buhler, Imtihan Ahmed, Thomas Lafrance, Ryan Romano, Stephen Packer,
 * Alden Emerson Ern Tan
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.ualbert.cs.tasko;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewPhotoActivity extends AppCompatActivity {

    private ImageSwitcher switcher;
    private ImageView imageView;
    private Task currentTask;
    private int position = 0;
    private ArrayList<Bitmap> photos;
    private Float initialX;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);
        switcher = (ImageSwitcher) findViewById(R.id.viewPhotoImageSwitcher);
        imageView = (ImageView) findViewById(R.id.viewPhotoImageView);
        Intent intent = getIntent();
        currentTask = (Task) intent.getSerializableExtra("photos");

        if (currentTask.hasPhoto()) {
            photos = currentTask.getPhotos();
            imageView.setImageBitmap(photos.get(0));
        }
        else {
            TextView imageText = (TextView) findViewById(R.id.viewPhotoTextView);
            imageText.setText(R.string.view_photo_message);
        }
    }

    /*
     * https://developer.android.com/training/gestures/detector.html
     * http://www.androprogrammer.com/2014/02/add-gesture-in-image-switcher-to-swipe.html
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float finalX;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                finalX = event.getX();
                /*
                Log.i("Not Error", Integer.toString(position));
                Log.i("Not Error", Integer.toString(photos.size()));
                Log.i("Not Error", Float.toString(initialX));
                Log.i("Not Error", Float.toString(finalX));
                */
                if (initialX > finalX) {
                    if (position < (photos.size() - 1)) {
                        position++;
                        imageView.setImageBitmap(photos.get(position));
                        switcher.showNext();
                        Toast.makeText(getApplicationContext(), "Next Image",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "No More Images To Swipe",
                                Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    if(position > 0)
                    {
                        position--;
                        imageView.setImageBitmap(photos.get(position));
                        switcher.showNext();
                        Toast.makeText(getApplicationContext(), "previous Image",
                                Toast.LENGTH_LONG).show();
                        switcher.showPrevious();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "No More Images To Swipe",
                                Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
        return false;
    }
}
