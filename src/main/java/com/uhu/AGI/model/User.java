package com.uhu.AGI.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author Juan Alberto Domínguez Vázquez
 */
@Document(collection = "user")
public class User
{

    @Id
    private String id;

    @Field("user_id")
    @NotBlank
    private String userId;
    
    @Field("name")
    @NotBlank
    private String name;
    
    @Field("review_count")
    @Min(value = 0, message = "El contador de visitas ha de ser minimo 0.")
    private int reviewCount;
    
    @Field("yelping_since")
    @NotBlank
    private String yelpingSince;
    
    @Field("useful")
    @Min(value = 0, message = "Useful ha de ser minimo 0.")
    private int useful;
    
    @Field("funny")
    @Min(value = 0, message = "Funny ha de ser minimo 0.")
    private int funny;
    
    @Field("cool")
    @Min(value = 0, message = "Cool ha de ser minimo 0.")
    private int cool;

    private List<String> elite;
    private List<String> friends;

    @Field("fans")
    @Min(value = 0, message = "Los fans han de ser minimo 0.")
    private int fans;
    
    @Field("average_stars")
    @Min(value = 0, message = "Las estrellas deben estar entre 0 y 5.")
    @Max(value = 5, message = "Las estrellas deben estar entre 0 y 5.")    
    private double averageStars;

    @Field("compliment_hot")
    @Min(value = 0, message = "Los complimentHot han de ser minimo 0.")
    private int complimentHot;
    
    @Field("compliment_more")
    @Min(value = 0, message = "Los complimentMore han de ser minimo 0.")
    private int complimentMore;
    
    @Field("compliment_profile")
    @Min(value = 0, message = "Los complimentProfile han de ser minimo 0.")
    private int complimentProfile;
    
    @Field("compliment_cute")
    @Min(value = 0, message = "Los complimentCute han de ser minimo 0.")
    private int complimentCute;
    
    @Field("compliment_list")
    @Min(value = 0, message = "Los complimentList han de ser minimo 0.")
    private int complimentList;
    
    @Field("compliment_note")
    @Min(value = 0, message = "Los complimentNote han de ser minimo 0.")
    private int complimentNote;
    
    @Field("compliment_plain")
    @Min(value = 0, message = "Los complimentPlain han de ser minimo 0.")
    private int complimentPlain;
    
    @Field("compliment_cool")
    @Min(value = 0, message = "Los complimentCool han de ser minimo 0.")
    private int complimentCool;
    
    @Field("compliment_funny")
    @Min(value = 0, message = "Los complimentFunny han de ser minimo 0.")
    private int complimentFunny;
    
    @Field("compliment_writer")
    @Min(value = 0, message = "Los complimentWriter han de ser minimo 0.")
    private int complimentWriter;
    
    @Field("compliment_photos")
    @Min(value = 0, message = "Los complimentPhotos han de ser minimo 0.")
    private int complimentPhotos;

    /**
     *
     */
    public User()
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getReviewCount()
    {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount)
    {
        this.reviewCount = reviewCount;
    }

    public String getYelpingSince()
    {
        return yelpingSince;
    }

    public void setYelpingSince(String yelpingSince)
    {
        this.yelpingSince = yelpingSince;
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

    public List<String> getElite()
    {
        return elite;
    }

    public void setElite(List<String> elite)
    {
        this.elite = elite;
    }

    public List<String> getFriends()
    {
        return friends;
    }

    public void setFriends(List<String> friends)
    {
        this.friends = friends;
    }

    public int getFans()
    {
        return fans;
    }

    public void setFans(int fans)
    {
        this.fans = fans;
    }

    public double getAverageStars()
    {
        return averageStars;
    }

    public void setAverageStars(double averageStars)
    {
        this.averageStars = averageStars;
    }

    public int getComplimentHot()
    {
        return complimentHot;
    }

    public void setComplimentHot(int complimentHot)
    {
        this.complimentHot = complimentHot;
    }

    public int getComplimentMore()
    {
        return complimentMore;
    }

    public void setComplimentMore(int complimentMore)
    {
        this.complimentMore = complimentMore;
    }

    public int getComplimentProfile()
    {
        return complimentProfile;
    }

    public void setComplimentProfile(int complimentProfile)
    {
        this.complimentProfile = complimentProfile;
    }

    public int getComplimentCute()
    {
        return complimentCute;
    }

    public void setComplimentCute(int complimentCute)
    {
        this.complimentCute = complimentCute;
    }

    public int getComplimentList()
    {
        return complimentList;
    }

    public void setComplimentList(int complimentList)
    {
        this.complimentList = complimentList;
    }

    public int getComplimentNote()
    {
        return complimentNote;
    }

    public void setComplimentNote(int complimentNote)
    {
        this.complimentNote = complimentNote;
    }

    public int getComplimentPlain()
    {
        return complimentPlain;
    }

    public void setComplimentPlain(int complimentPlain)
    {
        this.complimentPlain = complimentPlain;
    }

    public int getComplimentCool()
    {
        return complimentCool;
    }

    public void setComplimentCool(int complimentCool)
    {
        this.complimentCool = complimentCool;
    }

    public int getComplimentFunny()
    {
        return complimentFunny;
    }

    public void setComplimentFunny(int complimentFunny)
    {
        this.complimentFunny = complimentFunny;
    }

    public int getComplimentWriter()
    {
        return complimentWriter;
    }

    public void setComplimentWriter(int complimentWriter)
    {
        this.complimentWriter = complimentWriter;
    }

    public int getComplimentPhotos()
    {
        return complimentPhotos;
    }

    public void setComplimentPhotos(int complimentPhotos)
    {
        this.complimentPhotos = complimentPhotos;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
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
        final User other = (User) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "User{" + "id=" + id + ", userId=" + userId + ", name=" + name + 
                ", reviewCount=" + reviewCount + ", yelpingSince=" + yelpingSince + 
                ", useful=" + useful + ", funny=" + funny + ", cool=" + cool + 
                ", elite=" + elite + ", friends=" + friends + ", fans=" + fans + 
                ", averageStars=" + averageStars + ", complimentHot=" + complimentHot + 
                ", complimentMore=" + complimentMore + ", complimentProfile=" + complimentProfile + 
                ", complimentCute=" + complimentCute + ", complimentList=" + complimentList + 
                ", complimentNote=" + complimentNote + ", complimentPlain=" + complimentPlain + 
                ", complimentCool=" + complimentCool + ", complimentFunny=" + complimentFunny + 
                ", complimentWriter=" + complimentWriter + ", complimentPhotos=" + complimentPhotos + '}';
    }
       
}
