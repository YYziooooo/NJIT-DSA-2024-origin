package oy.tol.tira.books;

import java.io.*;

public class MyBook_HashTable implements Book {
    private String bookFilePath;
    private String ignoreFilePath;
    private KeyValueHashTable<String, Integer> wordCounts;
    private WordFilter wordFilter;
    private int totalWordCount = 0;
    private int uniqueWordCount = 0;
    private int ignoredWordsTotal = 0;

    public MyBook_HashTable() {
        this.wordCounts = new KeyValueHashTable<>(); // Initialize with default capacity
        this.wordFilter = new WordFilter(); // Initialize WordFilter
    }

    @Override
    public void setSource(String fileName, String ignoreWordsFile) throws FileNotFoundException {
        this.bookFilePath = fileName;
        this.ignoreFilePath = ignoreWordsFile;
        verifyFileExists(bookFilePath);
        verifyFileExists(ignoreFilePath);
        try {
            wordFilter.readFile(ignoreFilePath); // Load ignored words into WordFilter
        } catch (IOException e) {
            throw new FileNotFoundException("Could not read ignore words file: " + ignoreWordsFile);
        }
    }

    private void verifyFileExists(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException("File not found: " + filePath);
        }
    }

    @Override
    public void countUniqueWords() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(bookFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line);
            }
        }
        uniqueWordCount = wordCounts.size(); // Update unique word count after processing the file
    }

    private void processLine(String line) {
        for (String word : line.split("\\P{IsAlphabetic}+")) {
            word = word.toLowerCase();
            if (word.length() > 1) {
                if (!wordFilter.shouldFilter(word)) {
                    Integer count = wordCounts.find(word);
                    if (count == null) {
                        uniqueWordCount++; // Increment unique word count for each new word
                    }
                    count = (count == null) ? 1 : count + 1;
                    wordCounts.add(word, count);
                    totalWordCount++;
                } else {
                    ignoredWordsTotal++; // Increment ignored words count for each filtered word
                }
            }
        }
    }

    @Override
    public void report() {
        Pair<String, Integer>[] sortedWords = wordCounts.toSortedArray();
        for (int i = 0; i < Math.min(sortedWords.length, 100); i++) {
            String word = String.format("%-20s", sortedWords[i].getKey()).replace(' ', '.');
            System.out.format("%4d. %s %6d%n", i + 1, word, sortedWords[i].getValue());
        }

        System.out.println("\nStatistics:");
        System.out.println("Total number of words: " + totalWordCount);
        System.out.println("Number of unique words: " + uniqueWordCount);
        System.out.println("Number of words ignored: " + wordFilter.ignoreWordCount());
        System.out.println("Ignored words count in the book file: " + ignoredWordsTotal);
    }

    @Override
    public void close() {
        wordCounts = new KeyValueHashTable<>(); // Reset word counts
        wordFilter.close(); // Reset WordFilter
        totalWordCount = 0;
        uniqueWordCount = 0; // Reset unique word count
        ignoredWordsTotal = 0; // Reset ignored words total
    }

    @Override
    public int getUniqueWordCount() {
        return uniqueWordCount;
    }

    @Override
    public int getTotalWordCount() {
        return totalWordCount;
    }

    @Override
    public String getWordInListAt(int position) {
        Pair<String, Integer>[] sortedWords = wordCounts.toSortedArray();
        if (position >= 0 && position < sortedWords.length) {
            return sortedWords[position].getKey();
        }
        return null;
    }

    @Override
    public int getWordCountInListAt(int position) {
        Pair<String, Integer>[] sortedWords = wordCounts.toSortedArray();
        if (position >= 0 && position < sortedWords.length) {
            return sortedWords[position].getValue();
        }
        return -1;
    }
}