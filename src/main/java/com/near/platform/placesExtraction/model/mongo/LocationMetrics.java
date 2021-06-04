package com.near.platform.placesExtraction.model.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "location_metrics")
public class LocationMetrics {

    @Id
    private ObjectId id;
    private long currentPOICount;
    private long extractedPOICount;
    private long ingestCount;
    private long updateCount;
    private long deleteCount;
    private Date dateOfExtraction;
    private long poiListId;
    private String country;
    private long groundTruth;
    private String source;

    public LocationMetrics(long currentPOICount, long extractedPOICount, long ingestCount, long updateCount, long deleteCount, Date dateOfExtraction, long poiListId, String country, long groundTruth, String source) {
        this.currentPOICount = currentPOICount;
        this.extractedPOICount = extractedPOICount;
        this.ingestCount = ingestCount;
        this.updateCount = updateCount;
        this.deleteCount = deleteCount;
        this.dateOfExtraction = dateOfExtraction;
        this.poiListId = poiListId;
        this.country = country;
        this.groundTruth = groundTruth;
        this.source = source;
    }

    public ObjectId get_id() {
        return id;
    }

    public void set_id(ObjectId id) {
        this.id = id;
    }

    public long getCurrentPOICount() {
        return currentPOICount;
    }

    public void setCurrentPOICount(long currentPOICount) {
        this.currentPOICount = currentPOICount;
    }

    public long getExtractedPOICount() {
        return extractedPOICount;
    }

    public void setExtractedPOICount(long extractedPOICount) {
        this.extractedPOICount = extractedPOICount;
    }

    public long getIngestCount() {
        return ingestCount;
    }

    public void setIngestCount(long ingestCount) {
        this.ingestCount = ingestCount;
    }

    public long getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(long updateCount) {
        this.updateCount = updateCount;
    }

    public long getDeleteCount() {
        return deleteCount;
    }

    public void setDeleteCount(long deleteCount) {
        this.deleteCount = deleteCount;
    }

    public Date getDateOfExtraction() {
        return dateOfExtraction;
    }

    public void setDateOfExtraction(Date dateOfExtraction) {
        this.dateOfExtraction = dateOfExtraction;
    }

    public long getPoiListId() {
        return poiListId;
    }

    public void setPoiListId(long poiListId) {
        this.poiListId = poiListId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getGroundTruth() {
        return groundTruth;
    }

    public void setGroundTruth(long groundTruth) {
        this.groundTruth = groundTruth;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
