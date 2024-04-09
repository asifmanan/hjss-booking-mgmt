package com.hjss.models;

import com.hjss.utilities.Rating;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;

public class RatingAndReview {
    private final static int maxReviewLength = 250;
    private Rating rating;
    private String review;
    public RatingAndReview(){

    }
    public RatingAndReview(Rating rating){
        this.rating = rating;
    }
    public RatingAndReview(Rating rating, String review){
        this.rating = rating;
        this.review = review;
    }
    public void setRating(Rating rating){
        this.rating = rating;
    }
    public boolean setRating(String ratingString){
        try{
            this.rating = Rating.fromString(ratingString);
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }
    }
    public boolean setRating(int ratingInt){
        try{
            this.rating = Rating.fromInt(ratingInt);
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }
    }
    public Rating getRating(){
        return this.rating;
    }
    public boolean setReview(String reviewString){
        boolean response = validateReview(reviewString);
        if (response) {
            this.review = reviewString;
        }
        return response;
    }
    public String getReview(){
        return this.review;
    }
    public static Rating getValidatedRating(String ratingString){
        try{
            return Rating.fromString(ratingString);
        } catch (IllegalArgumentException e){
            return null;
        }
    }
    public static boolean validateReview(String reviewString){
        return !reviewString.isEmpty() && reviewString.length() <= maxReviewLength;
    }
    public static Rating getRatingInput(Terminal terminal, LineReader lineReader, String message){
        String leftMargin = " ".repeat(3);

        String helpText = leftMargin+ message+
                "\n"+leftMargin+"Please provide your feedback,"+
                "\n"+leftMargin.repeat(2)+"1 for Very Dissatisfied"+
                "\n"+leftMargin.repeat(2)+"2 for Dissatisfied"+
                "\n"+leftMargin.repeat(2)+"3 for Ok"+
                "\n"+leftMargin.repeat(2)+"4 for Satisfied"+
                "\n"+leftMargin.repeat(2)+"5 for Very Satisfied"+
                "\n\n";

        String ratingPrompt = "Give Rating: ";
        while (true){
            terminal.puts(InfoCmp.Capability.clear_screen);
            terminal.writer().print(helpText);
            String input = lineReader.readLine(ratingPrompt);
            Rating rating = getValidatedRating(input);
            if (rating!=null){
                return rating;
            }
        }
    }
    public static String getReviewInput(Terminal terminal, LineReader lineReader){
        String leftMargin = " ".repeat(3);
        String helpText =
                "\n"+leftMargin+"Please share your thoughts about your experience\n";
        String reviewPrompt = leftMargin+"Write a Review (Max 250 Characters): ";
        while (true){
            terminal.puts(InfoCmp.Capability.clear_screen);
            terminal.writer().println(helpText);
            String input = lineReader.readLine(reviewPrompt);
            if(validateReview(input)){
                terminal.puts(InfoCmp.Capability.clear_screen);
                terminal.writer().print(leftMargin+"Thank you for your feedback.\n");
                return input;
            }
        }

    }
}
