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
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationHandler;
import ca.ualbert.cs.tasko.NotificationArtifacts.NotificationType;
import ca.ualbert.cs.tasko.data.DataManager;
import ca.ualbert.cs.tasko.data.NoInternetException;

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
    private NotificationHandler nh;

    private DataManager dm = DataManager.getInstance();

    /**
     * Constructor for the Adapter, Takes in the context which designates the activity that will use
     * the adapter and a BidList which represents the Bids on a Tasks that will be displayed.
     * @param context The context for the activity using the adapter.
     * @param dmbids The BidList represnting the Bids on a Tasks to be displayed, from the DataManager.
     */
    public ViewBidsAdapter(Context context, BidList dmbids){
        thiscontext = context;
        nh = new NotificationHandler(thiscontext);
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
        View view = inflater.inflate(R.layout.bid_cardview_layout, parent, false);
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
        DataManager dm = DataManager.getInstance();
        User biduser = new User();
        
        try {
            biduser = dm.getUserById(currentTask.getUserID());
        } catch (NoInternetException e) {
            e.printStackTrace();
        }

        holder.bidTitle.setText("Posted by: " + biduser.getUsername());

        String myBid = Float.toString(currentTask.getValue());
        holder.Bid.setText("Bid: " + myBid);
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
    class BidViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {

        TextView bidTitle;
        TextView Bid;

        public BidViewHolder(View itemView) {
            super(itemView);

            //itemView.setOnClickListener(this);

            bidTitle = (TextView) itemView.findViewById(R.id.bidTitle);
            Bid = (TextView) itemView.findViewById(R.id.LowBid);
            Button acceptButton = (Button) itemView.findViewById(R.id.acceptButton);
            Button rejectButton = (Button) itemView.findViewById(R.id.rejectButton);

            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //prints to debug
                    Log.d("ButtonClick", "Accept Button Clicked");
                    try {
                        //gets the current task
                        Task thisTask = dm.getTask((bids.get(getAdapterPosition())).getTaskID());
                        if (thisTask.getStatus() == TaskStatus.ASSIGNED) {
                            //tell the user that task is already assigned
                            CharSequence toasttext = "Task already Assigned";
                            Toast toast = Toast.makeText(thiscontext, toasttext, Toast.LENGTH_SHORT);
                            toast.show();

                            Log.d("Error", "Task already assigned");

                            //testing stuff
                            if ((bids.get(getAdapterPosition())).getStatus() == BidStatus.ACCEPTED ) {
                                Log.d("Message", "Bid status is ACCEPTED!");
                            } else if ((bids.get(getAdapterPosition())).getStatus() == BidStatus.REJECTED ) {
                                Log.d("Message", "Bid status is REJECTED!");
                            } else if ((bids.get(getAdapterPosition())).getStatus() == BidStatus.PENDING) {
                                Log.d("Message", "Bid status is PENDING!");
                            }
                        } else if ((bids.get(getAdapterPosition())).getStatus() == BidStatus.REJECTED) {

                            //tell the user that the bid is already rejected
                            CharSequence toasttext = "Bid already Rejected";
                            Toast toast = Toast.makeText(thiscontext, toasttext, Toast.LENGTH_SHORT);
                            toast.show();

                        } else {
                            //Make all other bids rejected
                            for(int i = 0; i < bids.getSize(); i++){
                                bids.get(i).setStatus(BidStatus.REJECTED);
                            }
                            //Make accepted bid status accepted
                            (bids.get(getAdapterPosition())).setStatus(BidStatus.ACCEPTED);
                            if (bids.get(getAdapterPosition()).getStatus() == BidStatus.ACCEPTED) {
                                Log.d("Msg", "Bid is accepted");
                            }

                            //assigns it to the appropriate provider
                            thisTask.assign((bids.get(getAdapterPosition())).getUserID());

                            //updates the task
                            dm.putTask(thisTask);
                            dm.addBid(bids.get(getAdapterPosition()));

                            //send the notification
                            nh.newNotification(thisTask.getId(), NotificationType.TASK_PROVIDER_BID_ACCEPTED);

                            //task assigned and bid accepted.
                            //brings the user back to view my task details
                            ((ViewBidsOnTaskActivity)thiscontext).finish();

                        }
                    } catch (NullPointerException e) {
                        Log.i("Error", "TaskID not properly passed");
                    } catch (NoInternetException e) {
                        Log.d("Error", "TaskID not properly passed");
                        e.printStackTrace();
                    }
                }
            });

            rejectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //prints to debug
                    Log.d("ButtonClick", "Reject Button Clicked");

                    try {
                        //gets the current task
                        Task thisTask = dm.getTask((bids.get(getAdapterPosition())).getTaskID());
                        if (thisTask.getStatus() == TaskStatus.ASSIGNED) {
                            //tell the user that task is already assigned
                            CharSequence toasttext = "Task already Assigned";
                            Toast toast = Toast.makeText(thiscontext, toasttext, Toast.LENGTH_SHORT);
                            toast.show();

                            Log.d("Error", "Task already assigned");

                            //testing stuff
                            if ((bids.get(getAdapterPosition())).getStatus() == BidStatus.ACCEPTED ) {
                                Log.d("Message", "Bid status is ACCEPTED!");
                            } else if ((bids.get(getAdapterPosition())).getStatus() == BidStatus.REJECTED ) {
                                Log.d("Message", "Bid status is REJECTED!");
                            } else if ((bids.get(getAdapterPosition())).getStatus() == BidStatus.PENDING) {
                                Log.d("Message", "Bid status is PENDING!");
                            }
                        } else {
                            //Reject a bid
                            Bid currentbid = bids.get(getAdapterPosition());
                            bids.removeBid(currentbid);
                            dm.deleteBid(currentbid);
                            notifyDataSetChanged();
                         
                            nh.newBidDeletedNotification(thisTask.getId(), bids.get(getAdapterPosition()).getUserID());


                        }
                    } catch (NullPointerException e) {
                        Log.i("Error", "TaskID not properly passed");
                    } catch (NoInternetException e) {
                        Log.d("Error", "TaskID not properly passed");
                        e.printStackTrace();
                    }
                }
            });
        }
    }


}
