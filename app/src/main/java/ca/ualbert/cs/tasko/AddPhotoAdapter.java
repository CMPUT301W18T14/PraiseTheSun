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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Thomas on 2018-03-25.
 *
 */
public class AddPhotoAdapter extends RecyclerView.Adapter<AddPhotoAdapter.ImageViewHolder> {
    String[] mDataset;

    public AddPhotoAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    @Override
    public AddPhotoAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView v = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.add_image_view,
                parent, false);
        ImageViewHolder vh = new ImageViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        //holder.mImageView.setImageBitmap(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }


    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public ImageViewHolder(ImageView v) {
            super(v);
            mImageView = v;
        }
    }
}
