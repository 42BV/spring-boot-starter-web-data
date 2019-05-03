package nl._42.boot.web.data.paging;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Pages {

  private Pages() {
  }

  public static <I, O> Page<O> map(Page<I> source, Function<I, O> transformer) {
    Pageable found = PageRequest.of(source.getNumber(), source.getSize(), source.getSort());
    List<O> results = source.stream().map(transformer).collect(Collectors.toList());
    return new PageImpl<>(results, found, source.getTotalElements());
  }

}
