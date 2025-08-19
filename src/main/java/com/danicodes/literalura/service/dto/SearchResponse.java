package com.danicodes.literalura.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponse {
    public Integer count;
    public List<BookResult> results;
}
