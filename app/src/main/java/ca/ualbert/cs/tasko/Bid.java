
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

/**
 * Created by Chase on 2/23/2018.
 * Represents a bid Object containing the name of the bidder and the value they
 * bidded.
 *
 * @author Chase Buhler
 * @see Task
 */
public class Bid implements Comparable<Bid>{

    private User taskProvider;
    private float value;

    public Bid(User taskProvider, float value){
        this.taskProvider = taskProvider;
        this.value = value;
    }

    public User getTaskProvider() {
        return taskProvider;
    }

    public void setTaskProvider(User taskProvider) {
        this.taskProvider = taskProvider;
    }

    public float getValue() {

        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

//    I dont know exactly what this method does but I do know that it is fucking up my testing lol
//    @Override
//    public boolean equals(Object o){
//        if(!(o instanceof Bid)){
//            return false;
//        }
//        //Chase you wrote this as return false aswell... I assume that was a mistake?
//        return true;
//    }

    @Override
    public int compareTo(Bid bid){
        return Float.compare(this.value, bid.getValue());
        // return this.value.compareTo(bid.getValue());
    }
}
