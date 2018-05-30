package project1;


//PLEASE GRADE THIS ONE!
/**
 * A custom linked list that stores user info. Each node in the list is of type
 * UserNode.
 * 
 * @author okarpenko
 *
 */

import java.io.*;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class UsersList {
	private UserNode head = null; // head of the list
	private UserNode tail = null; // tail of the list
	private int num = 0;

	/** A default constructor */
	public UsersList() {
	}

	/** A getter for the head */
	public UserNode head() {
		return head;
	}

	/** A getter for the tail */
	public UserNode tail() {
		return tail;
	}

	/** Add a new node to the end of list */
	public void append(UserNode newNode) {
		// FILL IN CODE
		if (head == null) {
			head = newNode;
			tail = newNode;
			num++;
		}
		else {
			tail.setNext(newNode);
			tail = tail.next();
			num++;
		}
		

	}

	/** Return a UserNode that contains a given userId */
	public UserNode get(int userId) {
		UserNode curr = head;
		while (curr != null) {
			if (curr.getId() == userId) {
				return curr;
			}
			curr = curr.next();
		}
		return null;
	}

	/**
	 * Read user ratings from the file and save data for each user in this list.
	 * For each user, the movie ratings list will be sorted by rating (from
	 * largest to smallest).
	 */
	public void loadUserInfo(String filename) {
		Scanner fileScan, userScan;
		String userInfo, movieName;
		int userID = 0, Ratings, movieRating;
		MovieRatingsList newList = new MovieRatingsList();

		try {
			fileScan = new Scanner(new File(filename));
			while (fileScan.hasNext()) {
				userInfo = fileScan.nextLine();
				if (!userInfo.equals("")) {
					userScan = new Scanner(userInfo);
					userScan.useDelimiter(" ");
					userID = userScan.nextInt();
					Ratings = userScan.nextInt();
					userInfo = fileScan.nextLine();
					newList = new MovieRatingsList();
					while (!userInfo.equals("")) {
						userScan = new Scanner(userInfo);
						userScan.useDelimiter("; ");
						movieName = userScan.next().trim();
						movieRating = userScan.nextInt();
						newList.insertByRating(movieName, movieRating);
						try {
							userInfo = fileScan.nextLine();
						}
						catch(NoSuchElementException e) {
							break;
						}
						
					}
				}

				UserNode newUser = new UserNode(userID, newList);
				append(newUser);

			}

			fileScan.close();
		}

		catch(FileNotFoundException e)
		{
			System.out.println("Cannot open file.");
		}

	}

	/**
	 * Compute the similarity between the user with the given userId and all the
	 * other users. Finds the maximum similarity and returns the
	 * "most similar user".
	 */
	public UserNode findMostSimilarUser(int userid) {
		UserNode curr = head;
		UserNode user = get(userid);
		int currID = -1, mostSimilarID = -1;
		double mostSimilar = 0, similar = 0;


		while (curr != null) {
			currID = curr.getId();
			if (userid != currID) {
				similar = user.computeSimilarity(curr);
				if (similar > mostSimilar) {
					mostSimilar = similar;
					mostSimilarID = currID;
				}
			}
			curr = curr.next();
		}

		UserNode mostSimUser = get(mostSimilarID);

		return mostSimUser;

	}

	/**
	 * Compute up to num movie recommendations for the user with the given user
	 * id and print these movie titles to the file with the given name. First
	 * calls findMostSimilarUser and then getFavoriteMovies(num) method on the
	 * "most similar user" to get up to num recommendations.
	 */
	public void findRecommendations(int userid, int num, String filename) {
		// -Compute similarity between userid and all the other users
		// -Find the most similar user and recommend movies that the most similar
		// user rated as 5. 
		// - Recommend only the movies that userid has not seen (has not
		// rated).

		UserNode mostsimilar = findMostSimilarUser(userid);
		String[] recommendations = mostsimilar.getFavoriteMovies(num);

		try {
			PrintWriter pw = new PrintWriter(new File(filename));
			UserNode user = get(userid);

			for (int i = 0; i < recommendations.length; i++){
				if (user.getRating(recommendations[i]) == -1) {
					pw.println(recommendations[i]);
				}
			}

			pw.close();
		}

		catch(IOException e)
		{
			e.printStackTrace();
		}


	}

	/**
	 * Compute up to num movie anti-recommendations for the user with the given
	 * user id and print these movie titles to the given file. These are the
	 * movies the user should avoid. First calls findMostSimilarUser and then
	 * getLeastFavoriteMovies(num) method on the "most similar user" to get up
	 * to num movies the most similar user strongly disliked.
	 */
	public void findAntiRecommendations(int userid, int num, String filename) {
		// FILL IN CODE

		UserNode mostsimilar = findMostSimilarUser(userid);
		String[] AR = mostsimilar.getLeastFavoriteMovies(num);

		try {
			PrintWriter pw = new PrintWriter(new File(filename));
			UserNode user = get(userid);

			for (int i = 0; i < AR.length; i++){
				if (user.getRating(AR[i]) == -1) {
					pw.println(AR[i]);
				}
			}

			pw.close();
		}

		catch(IOException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Return an array that contains the highest rated movie for each user.
	 * Since the MovieRatingsList is sorted from highest rated movie to lowest, to
	 * implement this method, you just need to traverse the list of user nodes,
	 * and for each user, get the first movie from their ratings list.
	 * 
	 * @return
	 */
	public String[] findFavoriteMoviesForAll() {
		String[] fave = new String[num]; 
		int num = 0;
		UserNode curr = head;
		String[] movienames;

		while (curr != null) {
			movienames = curr.getMovies();
			fave[num] = movienames[0];
			num++;
			curr = curr.next();
		}
		
		return fave;

	}

}
