package com.hjss.models;

import com.hjss.utilities.Rating;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RatingAndReviewTest {
        // Test constructors and getter methods
    @Test
    public void testConstructorAndGetter() {
        RatingAndReview rar = new RatingAndReview(Rating.VerySatisfied, "Excellent service.");
        assertEquals(Rating.VerySatisfied, rar.getRating());
        assertEquals("Excellent service.", rar.getReview());
    }

    // Test setting and getting ratings
    @Test
    public void testSetAndGetRating() {
        RatingAndReview rar = new RatingAndReview();
        assertTrue(rar.setRating("4")); // Using string input
        assertEquals(Rating.Satisfied, rar.getRating());

        assertTrue(rar.setRating(1)); // Using integer input
        assertEquals(Rating.VeryDissatisfied, rar.getRating());
    }

    // Test setting and getting reviews
    @Test
    public void testSetAndGetReview() {
        RatingAndReview rar = new RatingAndReview();
        assertTrue(rar.setReview("Good job."));
        assertEquals("Good job.", rar.getReview());

        assertFalse(rar.setReview("")); // Testing empty review
    }

    // Test review validation
    @Test
    public void testReviewValidation() {
        String longReview = "a".repeat(251); // Generate a string longer than 250 characters
        assertFalse(RatingAndReview.validateReview(longReview));
        assertTrue(RatingAndReview.validateReview("a".repeat(250)));
    }

    // Test rating validation from strings
    @Test
    public void testRatingFromString() {
        assertNotNull(RatingAndReview.getValidatedRating("5"));
        assertNull(RatingAndReview.getValidatedRating("invalid"));
    }

    // Additional tests to check edge cases, such as boundary values for rating
    @Test
    public void testRatingBoundaries() {
        assertNull(RatingAndReview.getValidatedRating("0")); // Below minimum
        assertNotNull(RatingAndReview.getValidatedRating("1")); // Minimum valid rating
        assertNotNull(RatingAndReview.getValidatedRating("5")); // Maximum valid rating
        assertNull(RatingAndReview.getValidatedRating("6")); // Above maximum
    }
}
