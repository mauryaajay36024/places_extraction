package com.near.platform.placesExtraction.model.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

@Document(collection = "places")
public class LocationMetrics implements Comparable<LocationMetrics>, Comparator<LocationMetrics> {
    @Id
    private ObjectId _id;
    private Long poiListId;
    private String poiListName;
    private String cat1;
    private String cat2;
    private Long currentPOICount;
    private Long extractedPOICount;
    private Long ingestCount;
    private Long updateCount;
    private Long deleteCount;
    private Date dateOfExtraction;
    private String country;
    private Long groundTruth;
    private String source;

    public LocationMetrics( Long poiListId, String poiListName, String cat1, String cat2, Long currentPOICount, Long extractedPOICount, Long ingestCount, Long updateCount, Long deleteCount, Date dateOfExtraction, String country, Long groundTruth, String source) {
        this.poiListId = poiListId;
        this.poiListName = poiListName;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.currentPOICount = currentPOICount;
        this.extractedPOICount = extractedPOICount;
        this.ingestCount = ingestCount;
        this.updateCount = updateCount;
        this.deleteCount = deleteCount;
        this.dateOfExtraction = dateOfExtraction;
        this.country = country;
        this.groundTruth = groundTruth;
        this.source = source;
    }

    public Long getPoiListId() {
        return poiListId;
    }

    public void setPoiListId(Long poiListId) {
        this.poiListId = poiListId;
    }

    public String getPoiListName() {
        return poiListName;
    }

    public String getCat1() {
        return cat1;
    }


    public String getCat2() {
        return cat2;
    }


    public Long getCurrentPOICount() {
        return currentPOICount;
    }

    public void setCurrentPOICount(Long currentPOICount) {
        this.currentPOICount = currentPOICount;
    }

    public Long getExtractedPOICount() {
        return extractedPOICount;
    }

    public Long getIngestCount() {
        return ingestCount;
    }

    public void setIngestCount(Long ingestCount) {
        this.ingestCount = ingestCount;
    }

    public Long getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(Long updateCount) {
        this.updateCount = updateCount;
    }

    public Long getDeleteCount() {
        return deleteCount;
    }

    public void setDeleteCount(Long deleteCount) {
        this.deleteCount = deleteCount;
    }

    public Date getDateOfExtraction() {
        return dateOfExtraction;
    }

    public void setDateOfExtraction(Date dateOfExtraction) {
        this.dateOfExtraction = dateOfExtraction;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getGroundTruth() {
        return groundTruth;
    }

    public void setGroundTruth(Long groundTruth) {
        this.groundTruth = groundTruth;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }



    @Override
    public String toString() {
        return "Location Metrics Data <br>" +
            "<br> POI List Id           :" + poiListId +
            "<br> POI List Name         :" + poiListName +
            "<br> Cat1                  :" + cat1 +
            "<br> Cat2                  :" + cat2 +
            "<br> Current POI Count     :" + currentPOICount +
            "<br> Extracted POI Count   :" + extractedPOICount +
            "<br> Ingest Count          :" + ingestCount +
            "<br> Update Count          :" + updateCount +
            "<br> Delete Count          :" + deleteCount +
            "<br> Date Of Extraction    :" + dateOfExtraction +
            "<br> Country               :" + country +
            "<br> Ground Truth          :" + groundTruth +
            "<br> Source                :" + source ;
    }

    @Override
    public int compareTo(LocationMetrics other) {
        return this.getDateOfExtraction().compareTo(other.dateOfExtraction);
    }

    @Override
    public int compare(LocationMetrics object1, LocationMetrics object2) {
        return object1.getDateOfExtraction().compareTo(object2.getDateOfExtraction());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        LocationMetrics that = (LocationMetrics) object;
        return poiListId.equals(that.poiListId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(poiListId);
    }
}
