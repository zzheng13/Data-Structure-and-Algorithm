package project1;

//PLEASE GRADE THIS ONE!
/** The "driver" class for the project. Creates UsersList and
 * calls several methods. 
 */
import java.util.Arrays;

public class MovieRecommender {
	public static void main(String[] args) {
		UsersList users = new UsersList(); 
		users.loadUserInfo("movieRatings"); 
		UserNode user = users.get(0);
		System.out.println("Getting recommendations...");
		users.findRecommendations(0, 5, "recommendations");
		System.out.println("Getting antiRecommendations...");
		users.findAntiRecommendations(0, 5, "antiRecommendations");
		System.out.println("Check files for recommendations.");
		System.out.println("--------------------------------------------------");
		int mostSim = users.findMostSimilarUser(0).getId();
		System.out.println("The user most similar to user 0 is: " + mostSim);
		//System.out.println("--------------------------------------------------");
		
		// I commented methods that result in long prints. Uncomment to test.

		//System.out.println("Here are everyone's favorite movies:");
		//System.out.println(Arrays.toString(users.findFavoriteMoviesForAll()));
		//System.out.println("--------------------------------------------------");
		//System.out.println("Here's what's stored in userID 0:");
		//user.print();
		System.out.println("--------------------------------------------------");
		System.out.println("The user's ID should be 0. GetID gets: " + user.getId());
		System.out.println("--------------------------------------------------");
		System.out.println("Here's the user's favorite 2 movies: ");
		System.out.println(Arrays.toString(user.getFavoriteMovies(2)));
		System.out.println("--------------------------------------------------");
		System.out.println("Here's the user's least favorite 2 movies: ");
		System.out.println(Arrays.toString(user.getLeastFavoriteMovies(2)));
		System.out.println("--------------------------------------------------");
		System.out.println("Asking least favorite movies that is out of bounds");
		System.out.println(Arrays.toString(user.getLeastFavoriteMovies(273)));
		System.out.println("--------------------------------------------------");
		System.out.println("Current rating for Desperado: " + user.getRating("Desperado"));
		user.setRating("Desperado", 1);
		System.out.println("After changing rating to 1: " + user.getRating("Desperado"));
		System.out.println("--------------------------------------------------");
		System.out.println("Trying to get rating & change rating for movie not in list: ");
		System.out.println("Current rating for 'Fall': " + user.getRating("Fall"));
		user.setRating("Fall", 5);
	}
}
