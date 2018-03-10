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

package ca.ualbert.cs.tasko.Commands.DataCommands;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import ca.ualbert.cs.tasko.Task;
import ca.ualbert.cs.tasko.TaskList;
import ca.ualbert.cs.tasko.User;
import ca.ualbert.cs.tasko.data.JestWrapper;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by chase on 3/10/2018.
 */

public class SearchTasksCommand extends GetCommand<TaskList> {

    private String searchTerm;

    public SearchTasksCommand(String searchTerm){
        this.searchTerm = searchTerm;
    }

    @Override
    public void execute() {
        String query = "{\"query\":{\"match\":{\"description\": { \"query\" : \"" + searchTerm +
                "\", \"operator\" : \"and\" } } } }";
        SearchTasks searchTask = new SearchTasks();
        searchTask.execute(query);
        try{
            TaskList tasks = searchTask.get();
            setResult(tasks);
        } catch (Exception e){
            Log.i("Error", "Failed to get the user by username from the async object");
        }
    }

    private static class SearchTasks extends AsyncTask<String, Void, TaskList> {

        @Override
        protected TaskList doInBackground(String... searchTerms){
            TaskList tasks = new TaskList();
            JestWrapper.verifySettings();

            //Build the search query
            Search search = new Search.Builder(searchTerms[0]).addIndex(JestWrapper.getIndex())
                    .addType("task").build();
            try{
                SearchResult sr = JestWrapper.getClient().execute(search);
                if(sr.isSucceeded() && sr.getTotal() > 0){
                    List<SearchResult.Hit<Task, Void>> hits = sr.getHits(Task.class);
                    for(SearchResult.Hit hit : hits){
                        tasks.addTask((Task) hit.source);
                    }
                }

            } catch (IOException e){
                Log.i("Error", e.getMessage());
            }

            return tasks;
        }
    }
}
