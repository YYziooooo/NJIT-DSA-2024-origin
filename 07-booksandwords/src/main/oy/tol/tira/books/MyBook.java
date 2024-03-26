package oy.tol.tira.books;

import java.io.*;

public class MyBook implements Book {
    private String bookFilePath;
    private String ignoreFilePath;
    private KeyValueHashTable<String, Integer> wordCounts;
    private WordFilter wordFilter; // Using WordFilter to manage ignored words
    private int totalWordCount = 0;

    public MyBook() {
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
    }

    private void processLine(String line) {
        for (String word : line.split("\\P{IsAlphabetic}+")) {
            word = word.toLowerCase();
            if (word.length() > 1 && !wordFilter.shouldFilter(word)) {
                Integer count = wordCounts.find(word);
                count = (count == null) ? 1 : count + 1;
                wordCounts.add(word, count);
                totalWordCount++;
            }
        }
    }

    @Override
    public void report() {
        Pair<String, Integer>[] sortedWords = wordCounts.toSortedArray();
        System.out.println("Top Words by Occurrence:");
        for (int i = 0; i < Math.min(sortedWords.length, 100); i++) {
            System.out.println((i + 1) + ". " + sortedWords[i].getKey() + " - " + sortedWords[i].getValue());
        }

        System.out.println("\nStatistics:");
        System.out.println("Total number of words: " + totalWordCount);
        System.out.println("Number of unique words: " + wordCounts.size());
        System.out.println("Number of words ignored: " + wordFilter.ignoreWordCount());
        // Additional hash table statistics can be printed here as well

        System.out.println("\nHash Table Statistics:");
        System.out.println(wordCounts.getStatus());
    }

    @Override
    public void close() {
        wordCounts = new KeyValueHashTable<>(); // Reset word counts
        wordFilter.close(); // Reset WordFilter
    }

    @Override
    public int getUniqueWordCount() {
        return wordCounts.size();
    }

    @Override
    public int getTotalWordCount() {
        return totalWordCount;
    }

    @Override
    public String getWordInListAt(int position) {
        // This method will need a way to access a word by position, which might not be straightforward with a hash table.
        // One approach could be to convert the hash table to an array and then access by position.
        Pair<String, Integer>[] sortedWords = wordCounts.toSortedArray(); // Assuming toSortedArray is implemented
        if (position >= 0 && position < sortedWords.length) {
            return sortedWords[position].getKey();
        }
        return null; // If position is out of bounds
    }

    @Override
    public int getWordCountInListAt(int position) {
        Pair<String, Integer>[] sortedWords = wordCounts.toSortedArray(); // Assuming toSortedArray is implemented
        if (position >= 0 && position < sortedWords.length) {
            return sortedWords[position].getValue();
        }
        return -1; // If position is out of bounds
    }
}