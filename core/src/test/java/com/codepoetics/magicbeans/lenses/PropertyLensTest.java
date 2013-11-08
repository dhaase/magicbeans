package com.codepoetics.magicbeans.lenses;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import com.codepoetics.magicbeans.properties.PropertyLens;

public class PropertyLensTest {

    public static class TestBean {
        private String foo;
        private int bar;
        private boolean baz;
        
        public String getFoo() {
            return foo;
        }
        public void setFoo(String foo) {
            this.foo = foo;
        }
        public int getBar() {
            return bar;
        }
        public void setBar(int bar) {
            this.bar = bar;
        }
        public boolean isBaz() {
            return baz;
        }
        public void setBaz(boolean baz) {
            this.baz = baz;
        }
    }
    
    @Test public void
    creates_a_lens_indexed_into_a_bean() {
        TestBean instance = new TestBean();
        instance.setFoo("foo");
        instance.setBar(12);
        instance.setBaz(true);
        
        Lens<TestBean, Integer> barLens = PropertyLens.forProperty("bar", TestBean.class);
        
        assertThat(barLens.apply(instance).get(), equalTo(12));
        assertThat(barLens.apply(instance).apply(42).getBar(), equalTo(42));
    }
}
