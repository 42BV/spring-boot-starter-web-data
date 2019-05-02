/*
 * (C) 2014 42 bv (www.42.nl). All rights reserved.
 */
package nl._42.boot.web.data;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * Resolves {@link Pageable} instances from HTTP requests.
 *
 * @author Jeroen van Schagen
 * @since Sep 3, 2015
 */
class PageableResolver {
    
    static final String PAGE_PARAMETER = "page";
    static final String SIZE_PARAMETER = "size";
    static final String SORT_PARAMETER = "sort";
    private static final String SORT_DELIMITER = ",";
    
    private static final int FALLBACK_DEFAULT_PAGE = 0;
    private static final int FALLBACK_DEFAULT_SIZE = 10;
    private static final Sort FALLBACK_DEFAULT_SORT = new Sort(Direction.ASC, "id");

    /**
     * Determine if this request has pagination information.
     * 
     * @param request the request
     * @return {@code true} if pagination information is found, else {@code false}
     */
    public static boolean isSupported(HttpServletRequest request) {
        return !StringUtils.isEmpty(request.getParameter(PAGE_PARAMETER));
    }
    
    /**
     * Extract the {@link Pageable} instance from this request.
     * 
     * @param request the request
     * @param entityClass the entity class, used for defaults
     * @return the resolved pageable
     */
    public static Pageable getPageable(HttpServletRequest request, Class<?> entityClass) {
        Sort sort = getSort(request, entityClass);
        return getPageable(request, sort);
    }

    /**
     * Extract the {@link Pageable} instance from this request.
     * 
     * @param request the request
     * @param sort the sort to use
     * @return the resolved pageable
     */
    public static Pageable getPageable(HttpServletRequest request, Sort sort) {
        int page = getParameterAsInteger(request, PAGE_PARAMETER, FALLBACK_DEFAULT_PAGE);
        int size = getParameterAsInteger(request, SIZE_PARAMETER, FALLBACK_DEFAULT_SIZE);
        return new PageRequest(page, size, sort);
    }

    private static int getParameterAsInteger(HttpServletRequest request, String name, int defaultValue) {
        String size = request.getParameter(name);
        if (StringUtils.isEmpty(size)) {
            return defaultValue;
        } else {
            return Integer.parseInt(size);
        }
    }
    
    /**
     * Extract the {@link Sort} instance from this request.
     * 
     * @param request the request
     * @param entityClass the entity class, used for defaults
     * @return the resolved pageable
     */
    public static Sort getSort(HttpServletRequest request, Class<?> entityClass) {
        String[] sorts = request.getParameterValues(SORT_PARAMETER);
        if (sorts == null) {
            return getDefaultSort(entityClass);
        }

        return Stream.of(sorts)
                     .map(PageableResolver::parseSort)
                     .reduce(Sort::and)
                     .get();
    }

    private static Sort parseSort(String sort) {
        Direction direction = Direction.ASC;

        String[] fragments = sort.split(SORT_DELIMITER);
        List<String> properties = Arrays.asList(fragments);
        if (properties.size() > 1) {
            int index = properties.size() - 1;
            direction = Direction.fromString(properties.get(index));
            properties = properties.subList(0, index);
        }
        
        return new Sort(direction, properties);
    }

    private static Sort getDefaultSort(Class<?> entityClass) {
        List<SortingDefault> sorts = findSorts(entityClass);
        if (sorts.isEmpty()) {
            return FALLBACK_DEFAULT_SORT;
        }

        List<Order> orders = new ArrayList<>();
        for (SortingDefault sort : sorts) {
            for (String property : sort.value()) {
                orders.add(new Order(sort.direction(), property));
            }
        }

        return Sort.by(orders);
    }
    
    private static List<SortingDefault> findSorts(Class<?> entityClass) {
        SortingDefaults sorts = AnnotationUtils.findAnnotation(entityClass, SortingDefaults.class);
        if (sorts != null) {
            return Arrays.asList(sorts.value());
        }

        SortingDefault sort = AnnotationUtils.findAnnotation(entityClass, SortingDefault.class);
        if (sort != null) {
            return Collections.singletonList(sort);
        } else {
            return Collections.emptyList();
        }
    }

}
