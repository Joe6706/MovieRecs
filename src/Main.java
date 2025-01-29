package src;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;


class MovieRecSystem
{
    public static void main(String[] args)
    {

        //Section 1: Determines if the user wants to look for a movie, and what genres they would like
        Scanner input = new Scanner(System.in);

        System.out.println("Are you looking for a movie to watch? (y/n) ");
        String intention = input.nextLine();

        if (intention.equals("n"))
        {
            System.out.println("Ok, Bye!");
            input.close();
            return;
        }

        String genres = """
Action, Adventure, Animation, Comedy, Crime, Documentary,
Drama, Family, Fantasy, History, Horror, Music, Mystery, 
Science Fiction, Thriller, War, Western""";

        System.out.println("Ok sounds great!. What genres do you feel in the mood for? ( " + genres + " ) ");
        String wantedGenres = input.nextLine(); 

        //Parses Each Genre into an array
        String[] parsedGenres = wantedGenres.split(" ");

        //Paths to files containing relevant information
        String names_Path = "Resources\\Names.txt";
        String duration_Path = "Resources\\Duration.txt";
        String genres_Path = "Resources\\Genres.txt";
        String ratings_Path = "Resources\\Ratings.txt";
       
        //Initialized List Variables for relevant information
        List<String> nameLines = null;
        List<String> genreLines = null;
        List<String> durationLines = null;
        List<String> ratingLines = null;
        ArrayList<Movie> goodMovies = new ArrayList<>();

        //Attempt to read relevant information into respective list variables
        try
        {
            nameLines = Files.readAllLines(Paths.get(names_Path));
            genreLines = Files.readAllLines(Paths.get(genres_Path));
            durationLines = Files.readAllLines(Paths.get(duration_Path));
            ratingLines = Files.readAllLines(Paths.get(ratings_Path));
        }
        catch (IOException e) 
        {
            System.out.println("An error occurred while reading the file:");
            e.printStackTrace();
            input.close();
            return;
        }

        //Creates movie object of movies matching the wanted genres, adds them to previously initialized array list variable
        for (int i=0; i<genreLines.size(); i++)
        {
            int containing = 0;
            for (int j=0; j<parsedGenres.length; j++)
            {
                if (genreLines.get(i).contains(parsedGenres[j]))
                {
                    containing++;
                }
            }
            if (containing != parsedGenres.length) { continue; }

            double theRating;
            int theDuration;
            try
            {
                theRating = Double.parseDouble(ratingLines.get(i));
                theDuration = Integer.parseInt(durationLines.get(i));
            }
            catch (NumberFormatException e) {
                theRating = 0.0;
                theDuration = 0;
            } 
            catch (Exception e) {
                e.printStackTrace();
                input.close();
                return;
            }

            String[] theGenres = genreLines.get(i).split(", ");
            
            if (theDuration == 0) { continue; }

            Movie movie = new Movie(nameLines.get(i), theRating, theDuration, theGenres);
            goodMovies.add(movie);
            
        }

        //Sorts 'goodMovies' by rating, highest to lowest
        goodMovies.sort((one, two) -> Double.compare(two.getRating(), one.getRating()));

        //Checks if movies were found
        if (goodMovies.size() == 0)
        {
            System.out.println("No movies found.");
            input.close();
            return;
        }

        //Asks user for top ? # of movies they would like to see
        int top = 0;
        while (top < 1 || top > goodMovies.size())
        {
            System.out.println("Top # of Movies? ");
            top = input.nextInt();
            if (top >= goodMovies.size())
            {
                System.out.println("Too Many!");
            }
        }

        //Prints 'top' number of movies that match the wanted genre, along with their duration and rating
        for (int i=0; i<top; i++)
        {
            System.out.println("Name: " + goodMovies.get(i).getName() + "   Rating: " + goodMovies.get(i).getRating() + "   Duration: " + goodMovies.get(i).getDuration() + "   Genres: " + goodMovies.get(i).getGenres());
        }
        input.close();
        return;
    }
}