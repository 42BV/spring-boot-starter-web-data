# Spring Boot Starter Web Data

Provides additional helpers between Spring Web and Data.

## Default sorts on entity

```java
@DefaultSort({"name", "id"})
public class User {
    private Long id;
    private String name;
}
```
