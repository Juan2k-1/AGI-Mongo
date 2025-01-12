package com.uhu.AGI.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Juan Alberto Domínguez Vázquez
 */
@Document(collection = "checkin")
public class Checkin
{

    @Id
    private String id;

    @NotBlank(message = "El business_id es obligatorio.")
    @Field("business_id")
    private String businessId;

    @NotNull(message = "La fecha no puede ser nula.")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> date;

    /**
     *
     */
    public Checkin()
    {
    }

    /**
     *
     * @return
     */
    public String getId()
    {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getBusinessId()
    {
        return businessId;
    }

    /**
     *
     * @param businessId
     */
    public void setBusinessId(String businessId)
    {
        this.businessId = businessId;
    }

    /**
     *
     * @return
     */
    public List<LocalDateTime> getDate()
    {
        return date;
    }

    /**
     *
     * @param date
     */
    public void setDate(List<LocalDateTime> date)
    {
        this.date = date;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final Checkin other = (Checkin) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "Checkin{" + "id=" + id + ", businessId=" + businessId + ", date=" + date + '}';
    }

}
