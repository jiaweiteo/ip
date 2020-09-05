package seedu.duke;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class that store, read and write data to local txt file.
 */
public class Storage {
    private static final String DIRECTORY_PATH = "data";
    private static final String STORAGE_PATH = "data/duke.txt";
    private static final String TODO_SYMBOL = "T";
    private static final String EVENT_SYMBOL = "E";
    private static final String DEADLINE_SYMBOL  = "D";
    private static final String DONE = "1";
    private static final String NOT_DONE = "0";

    /**
     * Initializes an instance of Storage class.
     * Creates new folder and file if directory did not exist.
     *
     * @throws IOException If invalid path name given.
     */
    public Storage() throws IOException {
        File folderDirectory = new File(DIRECTORY_PATH);
        if (!folderDirectory.exists()) {
            folderDirectory.mkdir();
        }
        File txt = new File(STORAGE_PATH);
        if (!txt.exists()) {
            txt.createNewFile();
        }

    }

    /**
     * Erases and wipe out all data in the txt file.
     *
     * @throws IOException If invalid path name given.
     */
    private static void clearTasks() throws IOException {
        FileWriter storageWriter = new FileWriter(STORAGE_PATH, false);
        storageWriter.write("");
        storageWriter.close();
    }

    /**
     * Writes new task to local txt file.
     *
     * @param task Name of the task to be written.
     * @throws IOException If invalid path name given.
     */
    public static void addTask(String task) throws IOException {
        File data = new File(STORAGE_PATH);
        FileWriter writer = new FileWriter(data, true);
        writer.write(task);
        writer.write('\n');
        writer.flush();
        writer.close();
    }

    /**
     * Loads up existing data in a txt file stored in locally when Duke program starts.
     *
     * @param listOfTasks of tasks stored in ArrayList.
     *
     * @return Arraylist storing Tasks.
     * @throws FileNotFoundException If no file is found on the local directory.
     * @throws IOException If invalid input given.
     */
    public static ArrayList<Task> loadFromStorage(ArrayList<Task> listOfTasks) throws FileNotFoundException, IOException {
        File data = new File(STORAGE_PATH);
        FileReader fr = new FileReader(data);
        BufferedReader br = new BufferedReader(fr);
        String tasks = "";
        while (true) {
            tasks = br.readLine();
            if (tasks == null) {
                break;
            }
            String[] arrTasks = tasks.split(" ~ ");
            assert arrTasks.length >= 3; //Must be a valid Task string
            String typeOfTask = arrTasks[0];
            assert typeOfTask.equals(TODO_SYMBOL) || typeOfTask.equals(DEADLINE_SYMBOL)
                    || typeOfTask.equals(EVENT_SYMBOL); //Valid Symbol
            String isDone = arrTasks[1];
            String nameOfTask = arrTasks[2];
            if (typeOfTask.equals(TODO_SYMBOL)) {
                Todo tempTodo;
                if (isDone.equals(DONE)) {
                    tempTodo = new Todo(nameOfTask, true);
                } else {
                    tempTodo = new Todo(nameOfTask, false);
                }
                listOfTasks.add(tempTodo);
            } else if (typeOfTask.equals(DEADLINE_SYMBOL)) {
                String date = arrTasks[3];
                Deadline tempDeadline;
                if (isDone.equals(DONE)) {
                    tempDeadline = new Deadline(nameOfTask, true, date);
                } else {
                    tempDeadline = new Deadline(nameOfTask, false, date);
                }
                listOfTasks.add(tempDeadline);
            } else if (typeOfTask.equals(EVENT_SYMBOL)) {
                String date = arrTasks[3];
                Event tempEvent;
                if (isDone.equals(DONE)) {
                    tempEvent = new Event(nameOfTask, true, date);
                } else {
                    tempEvent = new Event(nameOfTask, false, date);
                }
                listOfTasks.add(tempEvent);
            }
        }
        return listOfTasks;
    }

    /**
     * Makes task as complete in a local txt file.
     *
     * @param taskNo Index of the task.
     * @param size Size of tasklist.
     */
    public static void completeTaskOnFile(int taskNo, int size) {
        try {
            File data = new File(STORAGE_PATH);
            FileReader fr = new FileReader(data);
            BufferedReader br = new BufferedReader(fr);
            ArrayList<String> tempArr = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                String task = br.readLine();
                if (i == taskNo) {
                    assert task.contains(NOT_DONE); //Must contain an uncompleted task
                    String tempTask = task.replaceFirst(NOT_DONE, DONE);
                    task = tempTask;
                }
                tempArr.add(task);
            }
            Storage.clearTasks();
            for (int i = 0; i < size; i++) {
                Storage.addTask(tempArr.get(i));
            }
        } catch (IOException ee) {
            System.out.println(ee.getMessage());
        }
    }

    /**
     * Deletes a task locally from the txt file.
     *
     * @param index Index of the task.
     * @param size Size of the tasklist.
     */
    public static void deleteTaskOnFile(int index, int size) {
        try {
            File data = new File(STORAGE_PATH);
            FileReader fr = new FileReader(data);
            BufferedReader br = new BufferedReader(fr);
            ArrayList<String> tempArr = new ArrayList<>();
            for (int i = 0; i <= size; i++) {
                String task = br.readLine();
                if (i != index) {
                    tempArr.add(task);
                }
            }
            Storage.clearTasks();
            for (int i = 0; i < tempArr.size(); i++) {
                Storage.addTask(tempArr.get(i));
            }
        } catch (IOException ee) {
            System.out.println(ee.getMessage());
        }
    }

}
