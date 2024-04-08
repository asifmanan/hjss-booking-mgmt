package com.hjss.models;

import com.hjss.utilities.HelpText;
import com.hjss.utilities.Rating;
import io.consolemenu.TerminalManager;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;

public class RatingAndReview {
    private final int maxReviewLength = 150;
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
        if(!reviewString.isEmpty() && reviewString.length() < 151){
            this.review = reviewString;
            return true;
        } else {
            return false;
        }
    }
    public String getReview(){
        return this.review;
    }
    public static Rating validateRating(String ratingString){
        try{
            Rating rating = Rating.fromString(ratingString);
            return rating;
        } catch (IllegalArgumentException e){
            return null;
        }
    }
    public void getAndSetRating(Terminal terminal, LineReader lineReader, String message){
        String leftMargin = " ".repeat(3);

        String helpText = leftMargin+ message+
                                        "\n"+leftMargin+"Please provide your feedback,"+
                                        "\n"+leftMargin+"1 for Very Dissatisfied"+
                                        "\n"+leftMargin+"2 for Very Dissatisfied"+
                                        "\n"+leftMargin+"3 for Very Dissatisfied"+
                                        "\n"+leftMargin+"4 for Very Dissatisfied"+
                                        "\n"+leftMargin+"5 for Very Dissatisfied"+
                                        "\n\n";

        String ratingPrompt = "Enter Rating: ";
        while (true){
            terminal.puts(InfoCmp.Capability.clear_screen);
            terminal.writer().print(helpText);
            String input = lineReader.readLine(ratingPrompt);
            if(this.setRating(input)){
                break;
            }
        }
//        String reviewPrompt = String.format("Provide Review (Max %s characters): ",maxReviewLength);
//        String review = lineReader.readLine(reviewPrompt);
    }
}
