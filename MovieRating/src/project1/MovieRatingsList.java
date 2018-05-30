package project1;

//PLEASE GRADE THIS ONE!

/**
 * A class that stores movie ratings for a user in a custom singly linked list of
 * MovieRatingNode objects. Has various methods to manipulate the list. Stores
 * only the head of the list (no tail!). The list should be sorted by
 * rating (from highest to smallest).
 */
import java.util.Iterator;
import java.util.HashMap;
import java.util.Arrays;

public class MovieRatingsList implements Iterable<MovieRatingNode> {

	
	private MovieRatingNode head = null;

	/** A getter for the head variable */
	public MovieRatingNode head() {
		return head; 
	}

	/**
	 * Return the reference to the node that contains the given movie title or
	 * null if such node does not exit.
	 * 
	 * @param movieTitle
	 * @return
	 */
	public MovieRatingNode find(String movieTitle) {
		MovieRatingNode curr = head;
		while (curr != null) {
			if (curr.getMovieTitle().equals(movieTitle)) {
				return curr; // found
			}
			curr = curr.next();
		}

		return null; // did not find
	}

	/**
	 * Return the rating for a given movie. If the movie is not in the list,
	 * return -1.
	 */
	public int getRating(String movieTitle) {
		// FILL IN CODE
				MovieRatingNode curr = head;
				while(curr!= null){
					if(curr.getMovieTitle().equals(movieTitle)){
						return curr.getMovieRating();
					}else{
						curr = curr.next();
					}
				}
				//System.out.println("The movie is not in the list!");
				return -1; // change
	}

	/**
	 * Create a new node with the given movie and rating, and appends it at the
	 * end of the array
	 * 
	 * @param movie
	 * @param rating
	 */
	public void append(String movie, int rating) {
		MovieRatingNode newnode = new MovieRatingNode(movie, rating);
		MovieRatingNode curr = head;

		if (head == null) {
			head = newnode;
		}
		else {
			while (curr.next() != null) {
				curr = curr.next();
			}
			curr.setNext(newnode);
		}	
		
	}

	/**
	 * Insert a new node (with a given movie and a given rating) into the list.
	 * Insert it in the right place based on the value of the rating. Assume the
	 * list is sorted by the value of ratings, from highest to smallest. The
	 * list should remain sorted after this insert operation.
	 */
	public void insertByRating(String movie, int rating) {
		// insert a node into the sorted list
		// FILL IN CODE
		MovieRatingNode newnode = new MovieRatingNode(movie,rating);
		if(head==null){
			head = newnode;
		}
		else if(head.next()==null){
			if(head.getMovieRating()> rating){
				head.setNext(newnode);
			}else{
				newnode.setNext(head);
				head = newnode;
			}
		}
		else if(head!=null && head.next()!=null){
			MovieRatingNode curr = head;
			MovieRatingNode currnext = head.next();
			//check head first
			if (newnode.getMovieRating()>=head.getMovieRating()) {
			  newnode.setNext(head);
			  head = newnode;
			}
			//check body
			else {
			  while(true){
			    if(newnode.getMovieRating() < currnext.getMovieRating()&& currnext.next()== null){
			      currnext.setNext(newnode);
			      break; 
			    }   
			    else if(newnode.getMovieRating() < curr.getMovieRating() &&  newnode.getMovieRating()>=currnext.getMovieRating()){
			    	newnode.setNext(currnext);
			    	curr.setNext(newnode);
			      break;
			    }
			    curr = curr.next();
			    currnext = currnext.next();
			  }
			 
			}
		}
		}



	/**
	 * Change the rating for a given movie to newRating.The position of the node
	 * within the list should be changed accordingly, so that the list remains
	 * sorted by rating (from largest to smallest).
	 * 
	 * @param movieTitle
	 * @param newRating
	 */
	public void setRating(String movieTitle, int newRating) {
		MovieRatingNode curr = head;

		if (movieTitle.equals(curr.getMovieTitle()) && curr == head) {
			head = curr.next();
			insertByRating(movieTitle, newRating);
		}

		else {
			while (curr.next() != null) {
				if (curr.next().getMovieTitle().equals(movieTitle)) {
					curr.setNext(curr.next().next());
					insertByRating(movieTitle, newRating);
					return; 
				}
				curr = curr.next();
			}
			System.out.println(movieTitle + " is not in list");
		}
	}

	/**
	 * Compute the cosine similarity between two lists of ratings. Note: You are
	 * allowed to use a HashMap for this method.
	 */
	public double computeSimilarity(MovieRatingsList otherList) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		Iterator<MovieRatingNode> it = iterator();
		MovieRatingNode curr = null;
		
		int dotproduct = 0,vectorleng1squre = 0, vectorleng2squre = 0;
		double similar;
		
		while (it.hasNext()) {
			curr = it.next();
			vectorleng1squre = vectorleng1squre + curr.getMovieRating()*curr.getMovieRating();
			map.put(curr.getMovieTitle(), curr.getMovieRating());

		}

		Iterator<MovieRatingNode> otherIt = otherList.iterator();

		while (otherIt.hasNext()) {
			curr = otherIt.next();
			vectorleng2squre = vectorleng2squre + curr.getMovieRating()*curr.getMovieRating();
			if (map.get(curr.getMovieTitle()) != null) {
				dotproduct += (curr.getMovieRating() * map.get(curr.getMovieTitle()));
			}
		}

		 similar = dotproduct / (Math.sqrt(vectorleng1squre) * Math.sqrt(vectorleng2squre));

		return similar;
	}

	/**
	 * Return a sublist of this list where the rating values are in the range
	 * from begRating to endRating, inclusive.
	 */
	public MovieRatingsList sublist(int begRating, int endRating) {
		MovieRatingsList res = new MovieRatingsList();
		MovieRatingNode curr = head;

		while (curr != null) {
			if (curr.getMovieRating() >= begRating && curr.getMovieRating() <= endRating) {
				res.append(curr.getMovieTitle(), curr.getMovieRating());
			}
			curr = curr.next();
		}
		return res;
	}

	/**
	 * Return an array of movie titles from the ratings list. The movie title
	 * should be in the array in the same order in which they were in the
	 * ratings list. (ordered by ratings, not titles)
	 */
	public String[] getTitles() {

		int numnodes = 0,number = 0;
		Iterator<MovieRatingNode> it = iterator();

		while (it.hasNext()) {	
			it.next();
			numnodes++;
		}
		String[] titles = new String[numnodes];

		it = iterator(); 
		while (it.hasNext()) {
			titles[number] = it.next().getMovieTitle();
			number++;
		}

		return titles;
	}

	/** Traverse the list and print info about each node */
	public void print() {

		MovieRatingNode curr = head;
		while (curr != null){
			System.out.print(curr.getMovieTitle());
			System.out.print(":");
			System.out.print(curr.getMovieRating());
			System.out.print(" ");
			curr = curr.next();
		}

	}

	/**
	 * Returns the middle node in the list - the one half way into the list.
	 * Needs to have the running time O(n), should be done in one pass. Hint:
	 * Use slow and fast pointers.
	 * 
	 * @return
	 */
	public MovieRatingNode getMiddleNode() {
		MovieRatingNode fast = head;
		MovieRatingNode slow = head;

		while ((fast != null) && (fast.next() != null) && (slow != null)) {
			slow = slow.next();
			if (fast.next() != null) {
				fast = fast.next().next();
			}
		}
		return slow; 
	}

	/**
	 * Return the median rating (the number that is halfway into the sorted
	 * list). To compute it, find the middle node and return it's rating. If the
	 * middle node is null, return -1.
	 */
	public int getMedianRating() {
		int middlerating = -1;
		MovieRatingNode median = getMiddleNode();
		if (median != null) {
			middlerating = median.getMovieRating();
		}

		return middlerating;
	}

	/**
	 * Return a MovieRatingsList that contains n best rated movies. These are
	 * the first n movies from the beginning of the list. If the list is
	 * shorter than size n, it will return the whole list.
	 */
	public MovieRatingsList getNBestRankedMovies(int n) {
		MovieRatingsList result = new MovieRatingsList();
		MovieRatingNode curr = head;
		int num = 0;
		while (curr != null && num < n) {
			result.append(curr.getMovieTitle(), curr.getMovieRating());
			curr = curr.next();
			num++;
		}
		
		return result;
	}

	/**
	 * Return a MovieRatingsList that contains n worst rated movies for this user.
	 * These are the last n movies from the end of the list. Use slow & fast
	 * pointers to find the n-th node from the end (required). Note: This method
	 * should compute the result in one pass. Do NOT use reverse().
	 */
	public MovieRatingsList getNWorstRankedMovies(int n) {
		MovieRatingsList worstMovies = new MovieRatingsList();
		MovieRatingNode slow = head, fast = head;
		int num = 0;
		while (num < n-1) {
			fast = fast.next();
			if (fast == null) 
				return worstMovies; 
			num++;
		}
		while (fast.next() != null) {
			fast = fast.next();
			slow = slow.next();
		}
		while (slow != null) {
			worstMovies.append(slow.getMovieTitle(), slow.getMovieRating());
			slow = slow.next();
		}
		return worstMovies;
	}

	/**
	 * Return a new list that is the reverse of the original list.
	 */
	public MovieRatingsList reverse(MovieRatingNode h) {
		MovieRatingsList r = new MovieRatingsList();
		MovieRatingNode last = h, curr = h;
		while (last.next() != null) {
			last = last.next();
		}
		r.append(last.getMovieTitle(), last.getMovieRating());
		while (!last.getMovieTitle().equals(h.getMovieTitle())) {
			curr = h;
			while (!curr.next().getMovieTitle().equals(last.getMovieTitle())) {
				curr = curr.next();
			}
			r.append(curr.getMovieTitle(), curr.getMovieRating());
			last = curr;
		}

		return r;
	}

	/** Return an iterator that allows to traverse the list */
	@Override
	public Iterator<MovieRatingNode> iterator() {
		return new RatingsListIterator(0);
	}

	/**
	 * The inner class that represents the iterator for the movie ratings list.
	 * Iterates over the MovieRatingNode-s of the list.
	 */
	private class RatingsListIterator implements Iterator<MovieRatingNode> {

		MovieRatingNode nextnode = head; 
		MovieRatingNode curr = null; 

		public RatingsListIterator(int index) {
			curr = null;
			for (int i = 0; i < index; i++) {
					curr = nextnode;
					nextnode = nextnode.next();
				}
			}

		@Override
		public boolean hasNext() {
			if (curr == null && nextnode != null) {
				return true;
			}
			else {
				return curr != null && nextnode != null; 
			}
		}

		@Override
		public MovieRatingNode next() {
			if (curr == null) { 
				curr = nextnode;
				nextnode = nextnode.next();
				return curr;
			}
			if (!hasNext()) {
				System.out.println("No next element");
				return null;
			}
			else {
				curr = nextnode;
				nextnode = nextnode.next();
				return curr;
			}
		}
	}
}