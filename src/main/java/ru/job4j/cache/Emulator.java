package ru.job4j.cache;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Emulator {
    private static DirFileCache dirFileCache;
    private static Scanner scanner = new Scanner(System.in);
    private static boolean flag = true;
    private static boolean dir = false;
    private static String lines = "========================================";

    public static void main(String[] args) {
        while (flag) {
            work();
        }
    }

    private static void work() {
        showMenu();
        System.out.println("Выбор действий: ");
        String task = scanner.nextLine();
        switch (task) {
            case "1":
                getCacheDir();
                break;
            case "2":
                loadFileToCache();
                break;
            case "3":
                getDataByName();
                break;
            case "4":
                exit();
                break;
            default:
                System.out.println("Выберите задачу от 1 до 4\n");
                break;
        }
    }

    private static void exit() {
        flag = false;
        System.out.println("Exit");
    }

    private static void getDataByName() {
        if (dir) {
            System.out.print("Введите название файла, который необходимо вывести на экран: ");
            String file = scanner.next();
            System.out.println(dirFileCache.get(file));
            System.out.println();
            scanner.nextLine();
        } else {
            System.out.println(lines);
            System.out.println("Кешируемая директория не указана");
            System.out.println(lines);
        }
    }

    private static void loadFileToCache() {
        if (dir) {
            System.out.print("Введите название файла для кеширования: ");
            String file = scanner.next();
            String data = dirFileCache.load(file);
            dirFileCache.put(file, data);
            System.out.println(lines);
            System.out.println("Кеширование завершено");
            System.out.println(lines);
        } else {
            System.out.println(lines);
            System.out.println("Кешируемая директория не указана");
            System.out.println(lines);
        }
        scanner.nextLine();
    }

    private static DirFileCache getCacheDir() {
        System.out.println("Укажите кешируемую директорию: ");
        String directory = scanner.nextLine();
        File file = Path.of(directory).toFile();
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(
                    String.format("Not directory %s", file.getAbsolutePath()));
        }
        dirFileCache = new DirFileCache(directory);
        System.out.println(lines);
        System.out.println("Кеширование завершено");
        System.out.println(lines);
        dir = true;
        return dirFileCache;
    }

    private static void showMenu() {
        List.of(
                "1.Указать кэшируемую директорию",
                "2.Загрузить содержимое файла в кэш",
                "3.Получить содержимое файла из кэша",
                "4.Выход"
        ).forEach(System.out::println);
    }
}
