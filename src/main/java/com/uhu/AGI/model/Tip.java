package com.uhu.AGI.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author Juan Alberto Domínguez Vázquez
 */
@Document(collection = "tip")
public class Tip
{

    @Id
    private String id;

    @Field("user_id")
    @NotBlank
    private String userId;

    @Field("business_id")
    @NotBlank
    private String businessId;

    @Field("text")
    @NotBlank
    private String text;

    @Field("date")
    @NotNull
    private LocalDateTime date;

    @Field("compliment_count")
    @Min(value = 0, message = "Los complimentCount han de ser minimo 0.")
    private int complimentCount;

    public Tip()
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

    public int getComplimentCount()
    {
        return complimentCount;
    }

    public void setComplimentCount(int complimentCount)
    {
        this.complimentCount = complimentCount;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.id);
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
        final Tip other = (Tip) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "Tip{" + "id=" + id + ", userId=" + userId
                + ", businessId=" + businessId + ", text=" + text
                + ", date=" + date + ", complimentCount=" + complimentCount + '}';
    }

}
