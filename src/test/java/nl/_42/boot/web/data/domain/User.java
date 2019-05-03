package nl._42.boot.web.data.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {

  private final Long id;
  private final String name;

}
