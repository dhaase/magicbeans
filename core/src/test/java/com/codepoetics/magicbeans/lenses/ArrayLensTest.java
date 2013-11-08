package com.codepoetics.magicbeans.lenses;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import com.codepoetics.magicbeans.arrays.EasyArray;

public class ArrayLensTest {

    @Test public void
    create_a_lens_indexed_into_an_array() {
        String[] strings = EasyArray.of("foo", "bar", "baz");
        
        ArrayLens<String> secondItem = ArrayLens.atIndex(1);
        
        assertThat(secondItem.apply(strings).get(), equalTo("bar"));
        assertThat(secondItem.apply(strings).apply("rab"), equalTo(new String[] { "foo", "rab", "baz" }));
    }
}
