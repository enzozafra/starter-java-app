package com.zafra.starterapp.models;

import java.util.Optional;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Model {
  public String id;
  public String name;
  public Optional<String> owner;
}
