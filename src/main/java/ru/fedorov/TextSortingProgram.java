package ru.fedorov;


import java.io.*;
import java.util.*;

import static ru.fedorov.ProgramUtil.*;

public class TextSortingProgram {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Необходимые аргументы: <inputFile> <outputFile> <sortCriterion>");
            System.out.println("Критерии сортировки:");
            System.out.println("1. По алфавиту;");
            System.out.println("2. По количеству символов в строке;");
            System.out.println("3. По слову в строке заданному аргументом программы в виде порядкового номера.");
            return;
        }

        // Программа принимает следующие аргументы командной строки:
        File inputFile = new File(args[0]);  //1. Путь к исходному файлу с текстом для сортировки.
        File outputFile = new File(args[1]);  //2. Путь к файлу, в который будет сохранен отсортированный текст.
        int sortCriterion = Integer.parseInt(args[2]);  //3. Критерий сортировки (1 для сортировки по алфавиту, 2 для сортировки по количеству символов в строке, 3 для сортировки по указанному слову).

        List<String> lines = readLinesFromFile(inputFile);

        switch (sortCriterion) {
            case 1:
                Collections.sort(lines);
                break;
            case 2:
                Collections.sort(lines, Comparator.comparingInt(String::length));
                break;
            case 3:
                if (args.length < 4) {
                    System.out.println("Укажите слово в качестве критерия сортировки.");
                    return;
                }
                String word = args[3];  //4. Слово, по которому будет выполняться сортировка (только для sortCriterion=3).
                Collections.sort(lines, (s1, s2) -> {
                    int wordCount1 = countWordOccurrences(s1, word);
                    int wordCount2 = countWordOccurrences(s2, word);
                    if (wordCount1 == wordCount2) {
                        return 0;
                    } else if (wordCount1 > wordCount2) {
                        return 1;
                    } else {
                        return -1;
                    }
                });
                break;
            default:
                System.out.println("Некорректный критерий для сортировки.");
                return;
        }

        writeLinesToFile(lines, outputFile);
    }
}