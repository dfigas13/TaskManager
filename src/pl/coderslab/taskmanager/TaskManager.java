package pl.coderslab.taskmanager;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class TaskManager {
    public static final String FILE_NAME = "task.csv";


    public static void main(String[] args) {
        showChoice();
    }
//show all options to choice
    public static void showChoice() {
        System.out.println(ConsoleColors.BLUE_BOLD + "Please select an option:");
        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "add");
        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "remove");
        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "list");
        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "exit");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        switch (choice) {
            case "add":
                addTask();//This is done
                break;
            case "remove":
                removeTask();//This is done
                break;
            case "list":
                showList();//This is done
            case "exit":
                closeProgram();//This is done
                break;
            default:
                System.out.println("Please select a correct option.");
        }
    }


    public static void closeProgram() {
        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Bye, bye..");
        System.exit(0);
    }

    public static void addTask() {
        System.out.println("Please add task description: ");
        Scanner scanner = new Scanner(System.in);
        String description = scanner.nextLine();
        System.out.println("Please add task due date: ");
        String dueDate = scanner.nextLine();
        System.out.println("Is your task important: true/false ");
        String isImportant = scanner.nextLine();

        ArrayList<String> tasks = new ArrayList<>();
        tasks.add(description);
        tasks.add(dueDate);
        tasks.add(isImportant);

        saveTask(tasks);
    }

    public static void saveTask(ArrayList<String> tasks) {
        try (FileWriter fileWriter = new FileWriter(FILE_NAME, true)) {
            String taskToSave = tasks.toString().replace("[", "").replace("]", "");
            fileWriter.write(taskToSave + "\n");
            System.out.println("Task saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        showChoice();
    }

    public static ArrayList<String> taskListRead() {
        ArrayList<String> taskList = new ArrayList<>();
        File file = new File(FILE_NAME);
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                taskList.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return taskList;
    }

    public static void showList() {
        ArrayList<String> taskList = taskListRead();
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println(i + ":" + taskList.get(i));
        }
        showChoice();
    }


    public static void removeTask() {
        ArrayList<String> taskToRemove = taskListRead();

        System.out.println("Select task number to remove: ");
        Scanner sc = new Scanner(System.in);
        int number = 0;
        if (sc.hasNextInt()) {
            number = sc.nextInt();
            if (number < 0) {
                System.out.println("Incorrect argument. Please give argument greater or equal 0");
                removeTask();
            } else if (number > taskToRemove.size()) {
                System.out.println("Task number not exist! Try again");
                removeTask();
            }
        } else {
            System.out.println("This is not Integer! Please give number greater or equal 0");
            removeTask();
        }

        StringBuilder newArray = new StringBuilder();
        for (int i = 0; i < taskToRemove.size(); i++) {
            if (number == i) {
                taskToRemove.remove(number);
            }
            newArray.append(taskToRemove.get(i)).append("\n");

        }
        try (PrintWriter printWriter = new PrintWriter(FILE_NAME)) {
            printWriter.write(String.valueOf(newArray));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Task number " + number + " is removed!");
        showChoice();
    }


}

