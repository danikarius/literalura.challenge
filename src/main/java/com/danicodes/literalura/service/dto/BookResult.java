package com.danicodes.literalura.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookResult {
    public Integer id;
    public String title;
    public List<String> languages;
    public Integer download_count;
    public List<Person> authors;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Person {
        public String name;
        public Integer birth_year;
        public Integer death_year;
    }
}
