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

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.awt.font.TextAttribute;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import ca.ualbert.cs.tasko.Commands.Command;
import ca.ualbert.cs.tasko.Commands.DataCommands.DeleteTaskCommand;
import ca.ualbert.cs.tasko.Commands.DataCommands.GetTaskCommand;
import ca.ualbert.cs.tasko.Commands.DataCommands.GetUserTasksCommand;
import ca.ualbert.cs.tasko.Commands.DataCommands.PutTaskCommand;
import ca.ualbert.cs.tasko.TaskList;

/**
 * Created by Chase on 4/4/2018.
 */

public class LocalDataManager {

    private static final String TASK_FILE = "userTasks.sav";
    private static final String COMMAND_QUEUE = "commandQueue.sav";
    private static ArrayList<Command> queue = new ArrayList<>();

    public static TaskList getLocalTasks(Context context){
        TaskList localTasks = new TaskList();
        try {
            FileInputStream fis = context.openFileInput(TASK_FILE);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            Type listtype = new TypeToken<TaskList>(){}.getType();
            localTasks = gson.fromJson(in, listtype);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return localTasks;
    }

    public static void saveLocalTasks(TaskList localTasks, Context context){
        try{
            FileOutputStream fos = context.openFileOutput(TASK_FILE, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(localTasks, out);
            out.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
    https://jansipke.nl/serialize-and-deserialize-a-list-of-polymorphic-objects-with-gson/
    April 4, 2018
     */
    public static void addCommandToQueue(Command command, Context context){
        queue.add(command);
        saveQueue(context);
    }

    public static void executeAllPendingCommands(Context context){
        try{
            FileInputStream fis = context.openFileInput(COMMAND_QUEUE);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = setupGsonForCommands();
            Type listtype = new TypeToken<ArrayList<Command>>(){}.getType();
            ArrayList<Command> queueLocal = gson.fromJson(in, listtype);
            in.close();
            for(Command c: queueLocal){
                Log.d("OFFLINE", "Looping");
                DataCommandManager.getInstance().invokeCommand(c);
            }
            queue.clear();
            saveQueue(context);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void saveQueue(Context context){
        try {
            FileOutputStream fos = context.openFileOutput(COMMAND_QUEUE, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = setupGsonForCommands();
            gson.toJson(queue, out);
            out.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Gson setupGsonForCommands(){
        RuntimeTypeAdapterFactory<Command> factory =
                RuntimeTypeAdapterFactory.of(Command.class, "type")
                .registerSubtype(PutTaskCommand.class, "PutTaskCommand")
                .registerSubtype(GetTaskCommand.class, "GetTaskCommand")
                .registerSubtype(GetUserTasksCommand.class, "GetUserTasksCommand")
                .registerSubtype(DeleteTaskCommand.class, "DeleteTaskCommand");
        return new GsonBuilder().registerTypeAdapterFactory(factory).create();
    }
}
