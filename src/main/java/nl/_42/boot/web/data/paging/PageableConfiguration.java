package nl._42.boot.web.data.paging;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * This component defines the de-serialization to a Pageable object.
 * @author bas
 */
@Configuration
public class PageableConfiguration {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;
    private static final String DEFAULT_SORT = "id";
    private static final int MAX_SIZE = 200;

    @Autowired
    private PageableHandlerMethodArgumentResolver resolver;

    @Autowired
    private PageableProperties properties;

    /**
     * Configures the PageableArgumentResolver to de-serialize to a Pageable object with one-based page number.
     * This is the configuration of de-serialization from url params.
     */
    @PostConstruct
    public void configure() {
        Sort sort = Sort.by(properties.getDefaultSort());
        resolver.setFallbackPageable(PageRequest.of(DEFAULT_PAGE, properties.getDefaultSize(), sort));
        resolver.setMaxPageSize(properties.getMaxSize());
        resolver.setOneIndexedParameters(true);
    }

    @Getter
    @Setter
    @Component
    @ConfigurationProperties(prefix = "pageable")
    public static final class PageableProperties {

        /** Default page size **/
        private int defaultSize = DEFAULT_SIZE;

        /** Default sorting property **/
        private String[] defaultSort = new String[] { DEFAULT_SORT };

        /** Maximum amount of entities to retrieve at once **/
        private int maxSize = MAX_SIZE;

    }

}
