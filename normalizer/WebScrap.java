package javacorrect;

import java.util.*;
import java.sql.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.net.ssl.HttpsURLConnection;

public class WebScrap {

	private final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {

		// Method 1 - Sends get request to google and retrieves the number of results
		// found
		// WebScrap http = new WebScrap();
		//
		// System.out.println("Testing 1 - Send Http GET request");
		// Connection con = getConnection();
		// String word = "Mert";
		// String frequency = http.sendGet(word);
		// addWordToDB(word,frequency);

		// Method 2 - Filters Zargan Turkish word database and retrieves the frequencies
		// of given words
		// Connection con = getConnection();
		BufferedReader br = new BufferedReader(new FileReader("/Users/kuluhan/Desktop/project/Kullanım sıklığı/word_forms_stems_and_frequencies_full.txt"));
		String line = "";
		double[] arr = new double[1337897];
		long sum = 0;
		int count = 0;
		for (int i = 0; i < 7; i++) {
			line = br.readLine();
		}
		line = br.readLine();
		while (line != null) {
			String[] columns = line.split("\t");
			arr[count] = (double) Integer.parseInt(columns[columns.length - 1]);
			count++;
			line = br.readLine();
		}
		double sd = calculateSD(arr);
		System.out.println(calculateSD(arr));

		br = new BufferedReader(new FileReader(
				"/Users/kuluhan/Desktop/project/Kullanım sıklığı/word_forms_stems_and_frequencies_full.txt"));
		for (int i = 0; i < 7; i++) {
			line = br.readLine();
		}
		line = br.readLine();
		double mean = 286.43806511263574;
		while (line != null) {
			String[] columns = line.split("\t");
			System.out.println((double) (Integer.parseInt(columns[columns.length - 1]) - mean) / sd);
			// addWordToDB(columns[0], (double)(Integer.parseInt(columns[columns.length -
			// 1]) - mean) / sd);
			System.out
					.println(columns[0] + " : " + (double) (Integer.parseInt(columns[columns.length - 1]) - mean) / sd);
			line = br.readLine();
		}
	}

	// Method that calculates the standard deviation of the elements in an int array
	public static double calculateSD(double numArray[]) {
		double sum = 0.0, standardDeviation = 0.0;

		for (double num : numArray) {
			sum += num;
		}

		double mean = sum / 10;

		for (double num : numArray) {
			standardDeviation += Math.pow(num - mean, 2);
		}

		return Math.sqrt(standardDeviation / 10);
	}

	// HTTP GET request
	private String sendGet(String str) throws Exception {

		String url = "http://www.google.com/search?q=" + str;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());

		String strResponse = response.toString();

		boolean wordFound = false;
		String target = "";

		for (int i = 0; i < strResponse.length() - 8 && !wordFound; i++) {
			if (strResponse.substring(i, i + 8).equals("Yaklaşık")) {
				// if(strResponse.substring(i, i + 22).equals("<div id=\"resultStats\">")) {
				boolean b = false;
				int start = 0;
				int end = 0;
				for (int j = i + 8; j < strResponse.length() && !wordFound; j++) {
					if (strResponse.charAt(j) == ' ') {
						if (b) {
							end = j;
							wordFound = true;
						} else {
							start = j;
							b = true;
						}
					}
				}
				target = strResponse.substring(start + 1, end);
				break;
			}
		}
		System.out.println(target);
		return target;
	}

	// Method that establishes a connection to the database
	public static Connection getConnection() throws Exception {
		try {
			String driver = "com.mysql.jdbc.Driver";
			// host will need to change
			String url = "jdbc:mysql://localhost:3306/sys";
			// user name and password should be adjustable
			String username = "root";
			String password = "31cce6697";
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected");
			return conn;
		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}

	// Method that adds the chosen word and its frequency in Turkish language to the
	// database
	public static boolean addWordToDB(String wrongWord, String corrected) throws Exception {
		try {
			Connection con = getConnection();

			PreparedStatement insertWord = con.prepareStatement(
					"INSERT INTO wrong_words (word, frequency) VALUES ( '" + wrongWord + "', '" + corrected + "');");

			insertWord.executeUpdate();
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}

		return false;
	}

}
