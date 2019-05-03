package nl._42.boot.web.data.paging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl._42.boot.web.data.domain.User;
import nl._42.boot.web.data.domain.UserResult;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;

public class PagesTest {

  @Test
  public void map() {
    User user = new User(1L, "Some name");
    Pageable pageable = PageRequest.of(1, 20);

    Page<User> users = new PageImpl<>(Arrays.asList(user), pageable, 100);
    Page<UserResult> results = Pages.map(users, this::map);

    Assert.assertEquals(1, results.getContent().size());
    Assert.assertEquals(user.getName(), results.getContent().get(0).name);
  }

  private UserResult map(User user) {
    return new UserResult(user.getName());
  }

}
