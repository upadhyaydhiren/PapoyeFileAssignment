package com.papoye.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This is class that scan local director given by user.
 * 
 * @author Dhiren
 *
 */
public class FileTest {
	private static String PATH;

	/**
	 * This method take Folder path that is get all Text file of folder.Each
	 * file last modified time compare with current time. After that show last
	 * modified 5 file name and size.
	 * 
	 * @param path
	 */
	public static void UpdatedFiles(String path) {
		File file = new File(path);
		File[] textFiles = file.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				if (name.endsWith(".txt"))
					return true;
				return false;
			}
		});

		Map<File, Long> lastModifiedFiles = new HashMap<File, Long>();
		for (File file2 : textFiles) {
			lastModifiedFiles.put(file2,
					(System.currentTimeMillis() - file2.lastModified()));
		}
		SortedSet<Entry<File, Long>> data = entriesSortedByValues(lastModifiedFiles);
		int i = 0;
		for (Entry<File, Long> entry : data) {
			i++;
			if (i > 5)
				break;
			System.out.println(entry.getKey().getName() + " "
					+ entry.getKey().length() + "bytes");
		}
	}

	/**
	 * This is sorting method that sort a map based on values.
	 * 
	 * @param map
	 *            It takes Map for sorting
	 * @return It return sorted map.
	 */
	static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> entriesSortedByValues(
			Map<K, V> map) {
		SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(
				new Comparator<Map.Entry<K, V>>() {
					@Override
					public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
						int res = e1.getValue().compareTo(e2.getValue());
						return res != 0 ? res : 1;
					}
				});
		sortedEntries.addAll(map.entrySet());
		return sortedEntries;
	}

	/**
	 * This is main static method that is execute Updated files function. In
	 * this method Updated files continue scanning of give path interval 1
	 * minutes.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.print("Enter Path for file scanning:");
		PATH = s.next();
		while (true) {
			try {
				UpdatedFiles(PATH);
				Thread.sleep(60000);
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
		s.close();
	}
}
