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

package ca.ualbert.cs.tasko.data;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

/**
 * Created by chase on 3/4/2018.
 *
 */
public class JestWrapper {

    private static JestDroidClient client;

    private final static String index = "cmput301w18t14";

    private final static String database = "http://cmput301.softwareprocess.es:8080";

    public static JestDroidClient getClient(){
        verifySettings();
        return client;
    }

    public static void verifySettings(){
        /*
        Retrieved on 04-03-2018
        https://github.com/7FeiW/lonelyTwitter/blob/lab5_base/app/src/main/java/ca/ualberta/cs/lonelytwitter/ElasticsearchTweetController.java
         */
        if(client == null){
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder(database);
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

    public static final String getIndex(){
        return index;
    }
}
