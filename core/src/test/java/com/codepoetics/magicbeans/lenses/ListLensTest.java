package com.codepoetics.magicbeans.lenses;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import java.util.List;

import org.junit.Test;

import com.codepoetics.magicbeans.lists.EasyList;

public class ListLensTest {

    @Test public void
    create_a_lens_indexed_into_a_list() {
        List<String> strings = EasyList.of("foo", "bar", "baz");
        
        ListLens<String> secondItem = ListLens.atIndex(1);
        
        assertThat(secondItem.apply(strings).get(), equalTo("bar"));
        assertThat(secondItem.apply(strings).apply("rab"), hasItems("foo", "rab", "baz"));
    }
}
