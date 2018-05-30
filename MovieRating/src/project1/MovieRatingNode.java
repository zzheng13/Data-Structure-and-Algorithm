package project1;
//PLEASE GRADE THIS ONE!

/** The class that represents a node in the MOvieRatingsList */

public class MovieRatingNode {
	
	private String movieTitle; // title of the movie
	private int movieRating; // rating, ranges from 1 to 5
	private MovieRatingNode next;  // reference to the "next" node

	/** A constructor for MovieRatingNode. 
	 * @param title
	 * @param rating
	 */
	public MovieRatingNode(String title, int rating) {
		movieTitle = title;
		next = null;

		if (rating < 1 || rating > 5) {
			System.out.println("Invalid rating. Using a default value of 3.");
			rating = 3;
		}
		else	
			movieRating = rating;
	}

	/** A constructor for MovieRating node. 
	 * @param title
	 * @param rating
	 * @param next
	 */
	public MovieRatingNode(String title, int rating, MovieRatingNode next) {
		this(title, rating);
		this.next = next;
	}
	
	/** Advance to the next node in the list. 
	 *  Return the next node */ 
	public MovieRatingNode next() {
		return next;
	}

	/** Set the next to point at the given node
	 * 
	 * @param anotherNode
	 */
	public void setNext(MovieRatingNode anotherNode) {
		this.next = anotherNode;
	}

	/** Return the title of the movie stored in this node
	 * @return
	 */
	public String getMovieTitle() {
		return movieTitle;
	}

	/** Set the title of the movie stored in the node
	 * 
	 * @param s  - A new title.
	 */
	public void setMovieTitle(String s) {
		movieTitle = s;
	}

	/** A getter for the movie rating */
	public int getMovieRating() {
		return movieRating;
	}

	/** A setter for the movie rating. 
	 *  Change the rating to newRating. 
	 * */
	public void setMovieRating(int newRating) {
		movieRating = newRating;
	}
	
	/** Return a string with the movie title and rating */
	public String toString() {
		return movieTitle + ", " + movieRating;
	}
}
