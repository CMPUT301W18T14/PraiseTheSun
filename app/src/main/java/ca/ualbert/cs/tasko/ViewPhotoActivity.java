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
import android.graphics.BitmapFactory;
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
    private TextView imageText;
    private int position = 0;
    private int numImages;
    private ArrayList<Bitmap> photos;
    private ArrayList<byte[]> imageBytes;
    private Float initialX;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);
        switcher = (ImageSwitcher) findViewById(R.id.viewPhotoImageSwitcher);
        imageView = (ImageView) findViewById(R.id.viewPhotoImageView);
        imageText = (TextView) findViewById(R.id.viewPhotoTextView);
        Intent intent = getIntent();
        imageBytes = (ArrayList<byte[]>) intent.getSerializableExtra("photos");
        photos = new ArrayList<Bitmap>();
        numImages = imageBytes.size();

        if (numImages > 0) {
            for (int i = 0; i < numImages; i++) {
                Bitmap image = BitmapFactory.decodeByteArray(imageBytes.get(i), 0,
                        imageBytes.get(i).length);
                photos.add(image);
            }
            imageView.setImageBitmap(photos.get(0));
            imageText.setText("Swipe to view other photos.\n Viewing photo 1" + "/" + Integer
                    .toString(numImages));
        }
        else {
            imageText.setText(R.string.view_photo_message);
        }
    }

    /*
     * https://developer.android.com/training/gestures/detector.html
     * http://www.androprogrammer.com/2014/02/add-gesture-in-image-switcher-to-swipe.html
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (numImages > 0) {
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
                        if (position < (numImages - 1)) {
                            position++;
                            imageView.setImageBitmap(photos.get(position));
                            switcher.showNext();
                            imageText.setText("Swipe to view other photos.\n Viewing photo " +
                                    Integer.toString(position + 1) + "/" + Integer.toString
                                    (numImages));
                        } else {
                            Toast.makeText(getApplicationContext(), "No More Images To Swipe",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    else if (initialX < finalX) {
                        if (position > 0) {
                            position--;
                            imageView.setImageBitmap(photos.get(position));
                            switcher.showNext();
                            switcher.showPrevious();
                            imageText.setText("Swipe to view other photos.\n Viewing photo " +
                                    Integer.toString(position + 1) + "/" + Integer.toString
                                    (numImages));
                        } else {
                            Toast.makeText(getApplicationContext(), "No More Images To Swipe",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
            }
            return false;
        }
        else {
            return true;
        }
    }
}
