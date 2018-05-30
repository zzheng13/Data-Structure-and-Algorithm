package project1;
//PLEASE GRADE THIS ONE!


/** UserNode represents a node in the UsersList. 
 *  Stores a userId, a reference to a MovieRatingsList,
 *  and a reference to the "next" user in the list. */
public class UserNode {
	private int userId;
	private MovieRatingsList movieRatings;
	private UserNode nextUser;
	
	/** A constructor for the UserNode.
	 * @param id 	User id
	 * @param list 	A list of ratings
	 * */
	public UserNode(int id, MovieRatingsList list) {
		userId = id;
		movieRatings = list;
		nextUser = null;
	}

	/** A getter for the nextUser variable.
	 * 
	 * @return
	 */
	public UserNode next() {
		return nextUser;
	}

	/** Set the next pointer to a given node
	 * 
	 * @param anotherUserNode
	 */
	public void setNext(UserNode anotherUserNode) {
		this.nextUser = anotherUserNode;
	}
	
	/** Return a userId stored in this node */
	public int getId() {
		return userId;
	}

	/** Print info contained in this node: 
	 *  userId and a list of ratings */
	public void print() {
		System.out.println(userId + "\n ");
		movieRatings.print();

	}

	/** Return an array of the movie titles from the movieRatings list.
	 *  These are the titles of all the movies that the user has seen.
	 */
	public String[] getMovies() {
		return movieRatings.getTitles();
		
	}
	
	/**
	 * Return an array of user's favorite movies (up to n). These are the
	 * movies that this user gave the rating of 5.
	 * 
	 * @param n
	 * @return
	 */
	public String[] getFavoriteMovies(int n) {
		MovieRatingsList best = movieRatings.getNBestRankedMovies(n).sublist(5, 5);
		// best.print();
		if (best != null)
			return best.getTitles();
		else
			return null;
	}

	/**
	 * Returns an array of movie titles the user likes the least (up to n). These
	 * are the movie titles that this user rated as 1.
	 * 
	 * @param n
	 * @return
	 */
	public String[] getLeastFavoriteMovies(int n) {
		MovieRatingsList worst = movieRatings.getNWorstRankedMovies(n).sublist(1, 1);
		if (worst != null)
			return worst.getTitles();
		else
			return null;
	}

	/**
	 * Compute the cosine similarity of this user with the given "other" user.
	 */
	public double computeSimilarity(UserNode otherUser) {
		return movieRatings.computeSimilarity(otherUser.movieRatings);
	}

	/** Change the rating for the given movie to newRating */
	public void setRating(String movieTitle, int newRating) {
		movieRatings.setRating(movieTitle, newRating);
	}

	/** Returns this user's rating for a given movie */
	public int getRating(String movieTitle) {
		return movieRatings.getRating(movieTitle);
	}
}
