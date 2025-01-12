package com.uhu.AGI.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Juan Alberto Domínguez Vázquez
 */
@Document(collection = "review")
public class Review
{

    @Id
    private String id;

    @Field("review_id")
    @NotBlank(message = "El review_id es obligatorio.")
    private String reviewId;

    @Field("user_id")
    @NotBlank(message = "El user_id es obligatorio.")
    private String userId;

    @Field("business_id")
    @NotBlank(message = "El business_id es obligatorio.")
    private String businessId;

    @Min(value = 1, message = "Las estrellas deben ser al menos 1.")
    @Max(value = 5, message = "Las estrellas no pueden ser mayores a 5.")
    private float stars;

    @Min(value = 0, message = "El valor de useful debe ser 0 o mayor.")
    private int useful;

    @Min(value = 0, message = "El valor de funny debe ser 0 o mayor.")
    private int funny;

    @Min(value = 0, message = "El valor de cool debe ser 0 o mayor.")
    private int cool;

    @NotBlank(message = "El texto de la reseña no puede estar vacío.")
    private String text;

    @NotNull(message = "La fecha es obligatoria.")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    public Review()
    {
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

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

    public float getStars()
    {
        return stars;
    }

    public void setStars(float stars)
    {
        this.stars = stars;
    }

    public int getUseful()
    {
        return useful;
    }

    public void setUseful(int useful)
    {
        this.useful = useful;
    }

    public int getFunny()
    {
        return funny;
    }

    public void setFunny(int funny)
    {
        this.funny = funny;
    }

    public int getCool()
    {
        return cool;
    }

    public void setCool(int cool)
    {
        this.cool = cool;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public LocalDateTime getDate()
    {
        return date;
    }

    public void setDate(LocalDateTime date)
    {
        this.date = date;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Review other = (Review) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "Review{" + "id=" + id + ", reviewId=" + reviewId
                + ", userId=" + userId + ", businessId=" + businessId
                + ", stars=" + stars + ", useful=" + useful
                + ", funny=" + funny + ", cool=" + cool + ", text="
                + text + ", date=" + date + '}';
    }

}
