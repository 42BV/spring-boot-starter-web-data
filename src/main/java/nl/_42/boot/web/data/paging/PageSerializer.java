package nl._42.boot.web.data.paging;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Defines the serialization to a JSON String from a Page object.
 * @author bas
 *
 * @param <T> Type of entity in Page content list.
 */
@JsonComponent
public final class PageSerializer<T> extends JsonObjectSerializer<Page<T>>{
    
    /**
     * Serializes a Page object to a JSON String with one-based page number.
     */
    @Override
    protected void serializeObject(Page<T> page, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        provider.defaultSerializeField("content", page.getContent(), jgen);
        provider.defaultSerializeField("last", page.isLast(), jgen);
        provider.defaultSerializeField("totalPages", page.getTotalPages(), jgen);
        provider.defaultSerializeField("totalElements", page.getTotalElements(), jgen);
        provider.defaultSerializeField("sort", page.getSort(), jgen);
        provider.defaultSerializeField("numberOfElements", page.getNumberOfElements(), jgen);
        provider.defaultSerializeField("first", page.isFirst(), jgen);
        provider.defaultSerializeField("size", page.getSize(), jgen);
        provider.defaultSerializeField("number", page.getNumber() + 1, jgen);
    }

}
