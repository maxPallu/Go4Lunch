package com.maxpallu.go4lunch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AutocompleteResult {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("matched_substrings")
    @Expose
    private List<PlaceAutocompleteResponse.MatchedSubstring> matchedSubstrings = null;
    @SerializedName("place_id")
    @Expose
    private String placeId;
    @SerializedName("reference")
    @Expose
    private String reference;
    @SerializedName("structured_formatting")
    @Expose
    private PlaceAutocompleteResponse.StructuredFormatting structuredFormatting;
    @SerializedName("terms")
    @Expose
    private List<PlaceAutocompleteResponse.Term> terms = null;
    @SerializedName("types")
    @Expose
    private List<String> types = null;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PlaceAutocompleteResponse.MatchedSubstring> getMatchedSubstrings() {
        return matchedSubstrings;
    }

    public void setMatchedSubstrings(List<PlaceAutocompleteResponse.MatchedSubstring> matchedSubstrings) {
        this.matchedSubstrings = matchedSubstrings;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public PlaceAutocompleteResponse.StructuredFormatting getStructuredFormatting() {
        return structuredFormatting;
    }

    public void setStructuredFormatting(PlaceAutocompleteResponse.StructuredFormatting structuredFormatting) {
        this.structuredFormatting = structuredFormatting;
    }

    public List<PlaceAutocompleteResponse.Term> getTerms() {
        return terms;
    }

    public void setTerms(List<PlaceAutocompleteResponse.Term> terms) {
        this.terms = terms;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

}
