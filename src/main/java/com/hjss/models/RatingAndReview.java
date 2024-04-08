package com.hjss.models;

import com.hjss.utilities.Rating;

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
}
