/*
 * JestWrapper
 *
 * March 15, 2018
 *
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

package ca.ualbert.cs.tasko.data;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;
import java.util.concurrent.TimeUnit;

/**
 * Provides a few simple methods to work with the Jest Client
 *
 * @author Chase Buhler
 * @version 1
 * @see ca.ualbert.cs.tasko.Commands.DataCommands
 */
public class JestWrapper {
    private final static String index =
            "cmput301w18t14"; //Our elasticSearch index
    private final static String database =
            "http://cmput301.softwareprocess.es:8080"; // Our elasticSearch url
    private static JestDroidClient client;

    /**
     * Will return the single JestDroidClient using our server url or create
     * it if it is not yet made
     * @return A Jest Droid Client
     */
    public static JestDroidClient getClient(){
        verifySettings();
        return client;
    }

    /**
     * Will get the final String that is the elastic search index currently in
     * use.
     * @return the Elastic Search index for this project
     */
    public static final String getIndex(){
        return index;
    }

    /**
     * Verify the JestDroidClient is created and build it if it is not
     */
    public static void verifySettings(){
        /*
        Retrieved on 04-03-2018
        https://github.com/7FeiW/lonelyTwitter/blob/lab5_base/app/src/main/java/ca/ualberta/cs/lonelytwitter/ElasticsearchTweetController.java
         */
        if(client == null){
            DroidClientConfig.Builder builder = new DroidClientConfig
                    .Builder(database)
                    .discoveryEnabled(true)
                    .discoveryFrequency(101, TimeUnit.SECONDS)
                    .multiThreaded(true);
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
