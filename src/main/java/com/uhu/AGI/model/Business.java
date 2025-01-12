package com.uhu.AGI.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import jakarta.validation.constraints.*;

/**
 *
 * @author Juan Alberto Domínguez Vázquez
 */
@Document(collection = "business")
public class Business
{

    @Id
    private String id;
    
    @NotBlank(message = "La id del negocio es obligatorio.")
    @Field(name = "business_id")
    private String businessId;

    @NotBlank(message = "El nombre del negocio es obligatorio.")
    private String name;

    @NotBlank(message = "La direccion es obligatoria  y debe ser una cadena.")
    private String address;

    @NotBlank(message = "La ciudad es obligatoria y debe ser una cadena.")
    private String city;

    @NotBlank(message = "El estado es obligatorio y debe ser una cadena.")
    private String state;

    @NotBlank(message = "El codigo postal es obligatorio y debe ser una cadena.")
    @Field("postal_code")
    private String postalCode;

    @Min(value = -90, message = "La latitud debe estar entre -90 y 90.")
    @Max(value = 90, message = "La latitud debe estar entre -90 y 90.")
    private double latitude;

    @Min(value = -180, message = "La longitud debe estar entre -180 y 180.")
    @Max(value = 180, message = "La longitud debe estar entre -180 y 180.")
    private double longitude;

    @Min(value = 0, message = "Las estrellas deben estar entre 0 y 5.")
    @Max(value = 5, message = "Las estrellas deben estar entre 0 y 5.")
    private double stars;

    @Min(value = 0, message = "El numero de reseñas debe ser un entero no negativo.")
    @Field("review_count")
    private int reviewCount;

    @NotNull(message = "El estado de apertura es obligatorio.")
    @Min(value = 0, message = "El valor de is_open debe ser 0 o 1.")
    @Max(value = 1, message = "El valor de is_open debe ser 0 o 1.")
    @Field("is_open")
    private int isOpen;

    private Map<String, Object> attributes;

    private List<String> categories;

    private Map<String, String> hours;

    /**
     *
     */
    public Business()
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
    public String getName()
    {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getAddress()
    {
        return address;
    }

    /**
     *
     * @param address
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     *
     * @return
     */
    public String getCity()
    {
        return city;
    }

    /**
     *
     * @param city
     */
    public void setCity(String city)
    {
        this.city = city;
    }

    /**
     *
     * @return
     */
    public String getState()
    {
        return state;
    }

    /**
     *
     * @param state
     */
    public void setState(String state)
    {
        this.state = state;
    }

    /**
     *
     * @return
     */
    public String getPostalCode()
    {
        return postalCode;
    }

    /**
     *
     * @param postalCode
     */
    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }

    /**
     *
     * @return
     */
    public double getLatitude()
    {
        return latitude;
    }

    /**
     *
     * @param latitude
     */
    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     */
    public double getLongitude()
    {
        return longitude;
    }

    /**
     *
     * @param longitude
     */
    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    /**
     *
     * @return
     */
    public double getStars()
    {
        return stars;
    }

    /**
     *
     * @param stars
     */
    public void setStars(double stars)
    {
        this.stars = stars;
    }

    /**
     *
     * @return
     */
    public Integer getReviewCount()
    {
        return reviewCount;
    }

    /**
     *
     * @param reviewCount
     */
    public void setReviewCount(Integer reviewCount)
    {
        this.reviewCount = reviewCount;
    }

    /**
     *
     * @return
     */
    public int getIsOpen()
    {
        return isOpen;
    }

    /**
     *
     * @param isOpen
     */
    public void setIsOpen(int isOpen)
    {
        this.isOpen = isOpen;
    }

    /**
     *
     * @return
     */
    public Map<String, Object> getAttributes()
    {
        return attributes;
    }

    /**
     *
     * @param attributes
     */
    public void setAttributes(Map<String, Object> attributes)
    {
        this.attributes = attributes;
    }

    /**
     *
     * @return
     */
    public List<String> getCategories()
    {
        return categories;
    }

    /**
     *
     * @param categories
     */
    public void setCategories(List<String> categories)
    {
        this.categories = categories;
    }

    /**
     *
     * @return
     */
    public Map<String, String> getHours()
    {
        return hours;
    }

    /**
     *
     * @param hours
     */
    public void setHours(Map<String, String> hours)
    {
        this.hours = hours;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
     *
     * @param obj
     * @return
     */
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
        final Business other = (Business) obj;
        return Objects.equals(this.id, other.id);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString()
    {
        return "Business{" + "id=" + id + "businessId=" + businessId
                + ", name=" + name + ", address=" + address + ", city=" + city
                + ", state=" + state + ", postalCode=" + postalCode
                + ", latitude=" + latitude + ", longitude=" + longitude
                + ", stars=" + stars + ", reviewCount=" + reviewCount
                + ", isOpen=" + isOpen + ", attributes=" + attributes
                + ", categories=" + categories + ", hours=" + hours + '}';
    }

}
