package syksy2024.backend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name="breed")
@Entity
public class Breed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "breed_name", nullable = false)
    private String name;

    @JsonProperty("bred_for")
    @Column(name = "bred_for")
    private String bredFor;

    @JsonProperty("breed_group")
    @Column(name = "breed_group")
    private String breedGroup;

    @JsonProperty("life_span")
    @Column(name = "life_span")
    private String lifeSpan;

    @Column(name = "temperament")
    private String temperament;

    @Column(name = "origin")
    private String origin;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "metric", column = @Column(name = "weight_metric"))
    })
    private Weight weight;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "metric", column = @Column(name = "height_metric"))
    })
    private Height height;

    @JsonProperty("reference_image_id")
    @Column(name = "reference_image_id")
    private String referenceImageId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "breed")
    @JsonIgnore
    private List<Dog> dogs;

    public List<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(List<Dog> dogs) {
        this.dogs = dogs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBredFor() {
        return bredFor;
    }

    public void setBredFor(String bredFor) {
        this.bredFor = bredFor;
    }

    public String getBreedGroup() {
        return breedGroup;
    }

    public void setBreedGroup(String breedGroup) {
        this.breedGroup = breedGroup;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(String lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public String getTemperament() {
        return temperament;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
    

    public String getReferenceImageId() {
        return referenceImageId;
    }
public Weight getWeight() {
    return weight;
}

public void setWeight(Weight weight) {
    this.weight = weight;
}

public Height getHeight() {
    return height;
}

public void setHeight(Height height) {
    this.height = height;
}


    public void setReferenceImageId(String referenceImageId) {
        this.referenceImageId = referenceImageId;
    }

    public String getImageUrl() {
        if (referenceImageId != null && !referenceImageId.isEmpty()) {
            return "https://cdn2.thedogapi.com/images/" + referenceImageId + ".jpg";
        }
        return null;
    }

    

    

}
