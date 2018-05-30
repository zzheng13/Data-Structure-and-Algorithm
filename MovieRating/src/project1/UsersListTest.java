package project1;

//PLEASE GRADE THIS ONE!

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A tester file for UsersList in project 1. Note that this class provides the
 * minimum tests. You are responsible for thoroughly testing your code on your
 * own.
 */
public class UsersListTest {

	public static void main(String[] args) {

		System.out.println("Testing UsersList class ---------");
		UsersList users = new UsersList();
		users.loadUserInfo("movieRatings");

		// check data for a couple of users
		UserNode user8 = users.get(8); // get the user with id 8
		String[] movies8 = user8.getMovies();
		UserNode user672 = users.get(672); // get the user with id 672
		String[] movies672 = user672.getMovies();

		if (movies8.length != 22) {
			System.out.println("Error in loadUserInfo in UsersList: The user with id=8 is expected to have 22 movies. "
					+ "			Exited the test file. Did not test other methods of UsersList");
			return;
		}
		if (movies672.length != 35) {
			System.out
					.println("Error in loadUserInfo in UsersList: The user with id=672 is expected to have 35 movies. "
							+ "			Exited the test file. Did not test other methods of UsersList");
			return;
		}

		else { // check whether movie list is sorted correctly - check a couple
				// random movies
			if (!movies8[0].equals("True Lies") || !movies8[5].equals("Shanghai Triad") || !movies8[20].equals("Gandhi")
					|| !movies672[0].equals("Fugitive, The") || !movies672[30].equals("Air Force One")) {
				System.out.println("------Error in loadUserInfo in UsersList: Movie titles are not in the right order"
						+ "for the user id=8 or user id = 30. Exited the test file");
				return;
			}
		}
		System.out.println(
				"------ Good news: It looks like the data is loaded correctly from movieRatings file into the UsersList.");

		int similarId1 = users.findMostSimilarUser(0).getId();
		int similarId2 = users.findMostSimilarUser(50).getId();
		if (similarId1 != 915 || similarId2 != 631) {
			System.out.print("------Error in findMostSimilarUser: the most similar user for the user with id = 0 "
					+ "is the user with id = 915 \n      and the most similar user for id = 50 is id = 631. ");
			System.out.println("Exiting the test");
			return;
		} else
			System.out.println("------ Good news: findMostSimilarUser seems to work correctly. ");

		List<String> expectedRecommendations60 = new ArrayList<String>();
		expectedRecommendations60.add("Titanic");
		expectedRecommendations60.add("Good Will Hunting");
		testFindRecommendations(users, 60, 5, "recommendationsUser60", expectedRecommendations60);

		List<String> expectedAntiRecommendations60 = new ArrayList<String>();
		expectedAntiRecommendations60.add("Dangerous Beauty");
		expectedAntiRecommendations60.add("Kull the Conqueror");
		expectedAntiRecommendations60.add("Excess Baggage");
		expectedAntiRecommendations60.add("Wings of the Dove, The");
		testFindAntiRecommendations(users, 60, 5, "antiRecommendationsUser60", expectedAntiRecommendations60);

	}

	/**
	 * Test findRecommendations method for a particular user
	 * 
	 * @param users
	 * @param userid
	 * @param maxNumOfRecommendations
	 * @param filename
	 * @param expectedMovies
	 */
	public static void testFindRecommendations(UsersList users, int userid, int maxNumOfRecommendations,
			String filename, List<String> expectedMovies) {

		users.findRecommendations(userid, maxNumOfRecommendations, filename);
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			List<String> recom = new ArrayList<String>();
			while ((line = br.readLine()) != null) {
				recom.add(line);
			}

			if (recom.size() != expectedMovies.size()) {
				System.out.println("---- Error in findRecommendations. For user" + userid + ", there should be "
						+ expectedMovies.size() + " movies in the list.");
				return;
			} else {
				for (int i = 0; i < recom.size(); i++) {
					if (!(recom.get(i)).equals(expectedMovies.get(i))) {
						System.out.println(
								"Error in findRecommendations - the expected list does not match what is returned by your code");
						System.out.println("Expected: " + expectedMovies);
						System.out.println("Your code returned: " + recom);
						return;
					}
				}
			}
			System.out.println(
					"------ Good news: it looks like findRecommendations returned correct  recommendations for user"
							+ userid);

		} catch (IOException e) {
			System.out.println("Could not read from the file: " + filename + ". Could not test findRecommendations.");
		}

	}

	/**
	 * Test findAntiRecommendations method for a particular user
	 * 
	 * @param users
	 * @param userid
	 * @param maxNumOfRecommendations
	 * @param filename
	 * @param expectedMovies
	 */
	public static void testFindAntiRecommendations(UsersList users, int userid, int maxNumOfRecommendations,
			String filename, List<String> expectedMovies) {

		users.findAntiRecommendations(userid, maxNumOfRecommendations, filename);
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			List<String> antiR = new ArrayList<String>();
			while ((line = br.readLine()) != null) {
				antiR.add(line);
			}

			if (antiR.size() != expectedMovies.size()) {
				System.out.println("---- Error in findAntiRecommendations. For user" + userid + ", there should be "
						+ expectedMovies.size() + " movies in the list.");
				return;
			} else {
				for (int i = 0; i < antiR.size(); i++) {
					if (!(antiR.get(i)).equals(expectedMovies.get(i))) {
						System.out.println(
								"Error in findAntiRecommendations - the expected list does not match what is returned by your code");
						System.out.println("Expected: " + expectedMovies);
						System.out.println("Your code returned: " + antiR);
						return;
					}
				}
			}
			System.out.println(
					"------ Good news: it looks like findAntiRecommendations returned correct  anti-recommendations for user"
							+ userid);

		} catch (IOException e) {
			System.out.println("Could not read from the file: " + filename + ". Could not test findAntiRecommendations.");
		}
	}
}

