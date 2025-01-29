package src;
import java.util.Arrays;
import java.util.List;

class Movie
{
    private String name;
    private List<String> genres;
    private double rating;
    private int duration;

    public Movie(String name, double rating, int duration, String[] genres)
    {
        this.name = name;
        this.rating = rating;
        this.duration = duration;
        this.genres = Arrays.asList(genres);
    }

    public String getName()
    {
        return name;
    }

    public double getRating()
    {
        return rating;
    }

    public int getDuration()
    {
        return duration;
    }

    public List<String> getGenres()
    {
        return genres;
    }

    public boolean checkRating(double minRating)
    {
        return minRating <= rating;
    }
}