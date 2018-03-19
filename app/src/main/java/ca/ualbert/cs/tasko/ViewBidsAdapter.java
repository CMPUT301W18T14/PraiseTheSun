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

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * The class represents a Adapter that is specifically designed to display bid of a particular
 * task in an AdapterView. Allowing the user to accept or reject a particular bid.
 *
 * @author Alden Tan
 */
public class ViewBidsAdapter extends RecyclerView.Adapter<ViewBidsAdapter.BidViewHolder> {

    private LayoutInflater inflater;
    private BidList bids;
    private Context thiscontext;

    /**
     * Constructor for the Adapter, Takes in the context which designates the activity that will use
     * the adapter and a BidList which represents the Bids on a Tasks that will be displayed.
     * @param context The context for the activity using the adapter.
     * @param dmbids The BidList represnting the Bids on a Tasks to be displayed, from the DataManager.
     */
    public ViewBidsAdapter(Context context, BidList dmbids){
        thiscontext = context;
        inflater = LayoutInflater.from(context);
        bids = dmbids;
    }

    /**
     * Creates the ViewHolder object that will display the Objects, in this case Bids on a
     * Tasks matching a search query.
     * @param parent parent means the View can contain other views
     * @param viewType an enumeration that tracks the type of views, in this case since we only have
     *                one view should be a constant value.
     * @return The ViewHolder object that will be used.
     */
    @Override
    public BidViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.bid_view, parent, false);
        BidViewHolder holder = new BidViewHolder(view);

        return holder;
    }

    /**
     * Binds The appropriate Data to the ViewHolder.
     * @param holder The ViewHolder data will be bound too.
     * @param position The position within the RecyclerView.
     */
    @Override
    public void onBindViewHolder(BidViewHolder holder, int position) {
        Bid currentTask = bids.get(position);

        holder.bidTitle.setText("Posted by: " + currentTask.getUserID());

        String myBid = Float.toString(currentTask.getValue());
        holder.Bid.setText("My Bid: " + myBid);
    }

    /**
     * Used to determine the number of ViewHolders the recyclerview will need to display.
     * @return An int representing the number of objects to be Displayed.
     */
    @Override
    public int getItemCount() {
        return bids.getSize();
    }

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


        //TODO: Use case 15 & 16
        @Override
        public void onClick(View view) {
        }
    }

}
