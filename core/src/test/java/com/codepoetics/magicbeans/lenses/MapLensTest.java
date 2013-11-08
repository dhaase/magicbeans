package com.codepoetics.magicbeans.lenses;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;

import java.util.Map;

import org.junit.Test;

import com.codepoetics.magicbeans.maps.EasyMap;

public class MapLensTest {
    @Test public void
    create_a_lens_indexed_into_a_map() {
        Map<String, String> map = EasyMap.of(
                "a", "apple",
                "b", "banana",
                "c", "carrot");
        
        MapLens<String, String> bIsFor = MapLens.atKey("b");
        
        assertThat(bIsFor.apply(map).get(), equalTo("banana"));
        assertThat(bIsFor.apply(map).apply("broccoli"), hasEntry("b",  "broccoli"));
    }
}
