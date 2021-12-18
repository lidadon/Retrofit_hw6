package ru.geekbrains.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@With
@ToString
public class Product {
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("title")
    public String title;
    @JsonProperty("price")
    public Integer price;
    @JsonProperty("categoryTitle")
    public String categoryTitle;
}
