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

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//Todo: It would be nice to make the adapter more modular, can be reused but needs slight modifications each time

/**
 * The class represents a Adapter that is specifically designed to display search results in an
 * AdapterView. Clicking on an element in the view will send the user to that tasks details where
 * they can place a Bid if they wish.
 */
public class ViewBidsAdapter extends RecyclerView.Adapter<ViewBidsAdapter.BidViewHolder> {

    private LayoutInflater inflater;
    private BidList bids;
    private Context thiscontext;

    public ViewBidsAdapter(Context context, BidList dmbids){
        thiscontext = context;
        inflater = LayoutInflater.from(context);
        bids = dmbids;
    }

    @Override
    public BidViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.bid_view, parent, false);
        BidViewHolder holder = new BidViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(BidViewHolder holder, int position) {
        Bid currentTask = bids.get(position);

        holder.bidTitle.setText(currentTask.getBidID());
        //holder.taskDescription.setText(currentTask.getDescription());
        //Needs more information then I currently have/ dont know how to implement.
        //holder.taskBid.setText();
        //holder.taskPhoto.setImageResource();

    }

    @Override
    public int getItemCount() {
        return bids.getSize();
    }

    //@Override
    //public int getItemCount(){
        //return bids.getSize();
    //}

    /**
     * The clickabale view holder that will get displayed in the recylcerview which displays
     * relevant information about a task.
     */
    class BidViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView bidTitle;
        TextView Bid;

        public BidViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            bidTitle = (TextView) itemView.findViewById(R.id.bidTitle);
            Bid = (TextView) itemView.findViewById(R.id.LowBid);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(thiscontext, ViewBidsOnTaskActivity.class);
            //intent.putExtra("UserID", bids.get(getAdapterPosition()).get());
            thiscontext.startActivity(intent);

        }
    }

}
