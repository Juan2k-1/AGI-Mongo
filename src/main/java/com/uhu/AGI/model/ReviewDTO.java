package com.uhu.AGI.model;

import java.time.LocalDateTime;

/**
 *
 * @author juald
 */
public class ReviewDTO
{

    private String reviewId;
    private String userId;
    private String businessId;
    private String businessName;
    private String reviewText;
    private int stars;
    private LocalDateTime date;

    public String getReviewId()
    {
        return reviewId;
    }

    public void setReviewId(String reviewId)
    {
        this.reviewId = reviewId;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getBusinessId()
    {
        return businessId;
    }

    public void setBusinessId(String businessId)
    {
        this.businessId = businessId;
    }

    public String getBusinessName()
    {
        return businessName;
    }

    public void setBusinessName(String businessName)
    {
        this.businessName = businessName;
    }

    public String getReviewText()
    {
        return reviewText;
    }

    public void setReviewText(String reviewText)
    {
        this.reviewText = reviewText;
    }

    public Integer getStars()
    {
        return stars;
    }

    public void setStars(Integer stars)
    {
        this.stars = stars;
    }

    public LocalDateTime getDate()
    {
        return date;
    }

    public void setDate(LocalDateTime date)
    {
        this.date = date;
    }

}
