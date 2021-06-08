package com.near.platform.placesExtraction.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "places")
public class LocationMetrics {
    @Id
    private Long poiListId;
    private Long currentPOICount;
    private Long extractedPOICount;
    private Long ingestCount;
    private Long updateCount;
    private Long deleteCount;
    private Date dateOfExtraction;
    private String country;
    private Long groundTruth;
    private String source;

    public LocationMetrics(Long poiListId, Long currentPOICount, Long extractedPOICount, Long ingestCount, Long updateCount, Long deleteCount, Date dateOfExtraction, String country, Long groundTruth, String source) {
        this.poiListId = poiListId;
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

    public Long getCurrentPOICount() {
        return currentPOICount;
    }

    public void setCurrentPOICount(Long currentPOICount) {
        this.currentPOICount = currentPOICount;
    }

    public Long getExtractedPOICount() {
        return extractedPOICount;
    }

    public void setExtractedPOICount(Long extractedPOICount) {
        this.extractedPOICount = extractedPOICount;
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
}
