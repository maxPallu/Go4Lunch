package com.maxpallu.go4lunch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

class Northeast {

        @SerializedName("lat")
        @Expose
        private Double lat;
        @SerializedName("lng")
        @Expose
        private Double lng;

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }

    }

class PlusCode {

        @SerializedName("compound_code")
        @Expose
        private String compoundCode;
        @SerializedName("global_code")
        @Expose
        private String globalCode;

        public String getCompoundCode() {
            return compoundCode;
        }

        public void setCompoundCode(String compoundCode) {
            this.compoundCode = compoundCode;
        }

        public String getGlobalCode() {
            return globalCode;
        }

        public void setGlobalCode(String globalCode) {
            this.globalCode = globalCode;
        }

    }

    public class Restaurants {

        @SerializedName("html_attributions")
        @Expose
        private List<Object> htmlAttributions = null;
        @SerializedName("next_page_token")
        @Expose
        private String nextPageToken;
        @SerializedName("results")
        @Expose
        private List<Result> results = null;
        @SerializedName("status")
        @Expose
        private String status;

        public List<Object> getHtmlAttributions() {
            return htmlAttributions;
        }

        public void setHtmlAttributions(List<Object> htmlAttributions) {
            this.htmlAttributions = htmlAttributions;
        }

        public String getNextPageToken() {
            return nextPageToken;
        }

        public void setNextPageToken(String nextPageToken) {
            this.nextPageToken = nextPageToken;
        }

        public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

class Southwest {

        @SerializedName("lat")
        @Expose
        private Double lat;
        @SerializedName("lng")
        @Expose
        private Double lng;

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }

    }

    class Viewport {

        @SerializedName("northeast")
        @Expose
        private Northeast northeast;
        @SerializedName("southwest")
        @Expose
        private Southwest southwest;

        public Northeast getNortheast() {
            return northeast;
        }

        public void setNortheast(Northeast northeast) {
            this.northeast = northeast;
        }

        public Southwest getSouthwest() {
            return southwest;
        }

        public void setSouthwest(Southwest southwest) {
            this.southwest = southwest;
        }

    }