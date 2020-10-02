package br.com.developers.github.http.resource.developer;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Developer {
  @JsonProperty("total_count")
  private int total;

  @JsonProperty("incomplete_results")
  private boolean incompleteResults;

  @JsonProperty("items")
  private List<Items> items;
}
