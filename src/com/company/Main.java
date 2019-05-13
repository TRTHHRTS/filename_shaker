package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class Main {

    private static String FIRST_SYMBOLS = "abcdefghijklmnopqrstuvwxyz";

    private static String CHARS_TO_REMOVE = " 1234567890',.-\\(\\)";

    private static String OLD_DIR = "D:\\55ЛЕТ_МУЗЫКа";
    private static String NEW_DIR = "D:\\55ЛЕТ_МУЗЫКА_final";

    private static Random rnd = new Random();

    public static void main(String[] args) {
        final File curFolder = Paths.get(OLD_DIR).toFile();
        listFilesForFolder(curFolder);
        finalProcessing();
    }

    private static void listFilesForFolder(final File folder) {
        int total = folder.listFiles().length;
        int cursor = 0;
        for (final File fileEntry : folder.listFiles()) {
            String newName = fileEntry.getName();
            newName = newName.substring(0, newName.indexOf(".mp3"));
            for (int i = 0; i < CHARS_TO_REMOVE.length(); i++) {
                newName = newName.replace(String.valueOf(CHARS_TO_REMOVE.charAt(i)), "");
            }
            newName = newName + ".mp3";
            Path resultPath = Paths.get(NEW_DIR, "/" + newName);

            try {
                Files.copy(fileEntry.toPath(), resultPath);
                cursor++;
            } catch (FileAlreadyExistsException faee) {
                System.out.println("Повтор: " + newName + "(" + fileEntry.getName() + ")");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Скопировано " + cursor + " из " + total);
    }

    private static void finalProcessing() {
        int count = 0;
        for (final File fileEntry : Paths.get(NEW_DIR).toFile().listFiles()) {
            String newName = fileEntry.getName();
            boolean success = fileEntry.renameTo(new File(fileEntry.getParent() + "\\" + FIRST_SYMBOLS.charAt(rnd.nextInt(FIRST_SYMBOLS.length())) + "_" + newName));
            if (success) count++;
        }
        System.out.println("Переименовано: " + count);
    }
}
