package com.codepoetics.magicbeans.introspection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Test;
import org.pcollections.PMap;

import com.codepoetics.magicbeans.lists.EasyList;
import com.codepoetics.magicbeans.maps.EasyMap;


public class DeepBeanReaderWriterTest {

    public static class Person {
        private String name;
        private int age;
        private List<Address> addresses;
        private Map<String, PhoneNumber> phoneNumbers;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getAge() {
            return age;
        }
        public void setAge(int age) {
            this.age = age;
        }
        public List<Address> getAddresses() {
            return addresses;
        }
        public void setAddresses(List<Address> addresses) {
            this.addresses = addresses;
        }
        public Map<String, PhoneNumber> getPhoneNumbers() {
            return phoneNumbers;
        }
        public void setPhoneNumbers(Map<String, PhoneNumber> phoneNumbers) {
            this.phoneNumbers = phoneNumbers;
        }
        
        @Override
        public boolean equals(Object o) {
            if (o == null || !(o instanceof Person)) return false;
            Person p = (Person) o;
            return Objects.equals(name, p.name)
                    && Objects.equals(age, p.age)
                    && Objects.equals(addresses, p.addresses)
                    && Objects.equals(phoneNumbers, p.phoneNumbers);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(name, age, addresses, phoneNumbers);
        }
        
        @Override
        public String toString() {
            return DeepBeanReader.read(this).toString();
        }
    }
    
    public static class Address {
        public List<String> getAddressLines() {
            return addressLines;
        }
        public void setAddressLines(List<String> addressLines) {
            this.addressLines = addressLines;
        }
        public String getPostcode() {
            return postcode;
        }
        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }
        private List<String> addressLines;
        private String postcode;
        
        @Override
        public boolean equals(Object o) {
            if (o == null || !(o instanceof Address)) return false;
            Address a = (Address) o;
            return Objects.equals(addressLines, a.addressLines)
                    && Objects.equals(postcode, a.postcode);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(addressLines, postcode);
        }
        
        @Override
        public String toString() {
            return DeepBeanReader.read(this).toString();
        }
    }
    
    public static class PhoneNumber {
        public String number;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || !(o instanceof PhoneNumber)) return false;
            PhoneNumber n = (PhoneNumber) o;
            return Objects.equals(number, n.number);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(number);
        }
        
        @Override
        public String toString() {
            return DeepBeanReader.read(this).toString();
        }
    }
    
    private final BeanCreator<PhoneNumber> phoneCreator = BeanCreator.forClass(PhoneNumber.class);
    private final PhoneNumber home = phoneCreator.create("number", "01234 567890");
    private final PhoneNumber work = phoneCreator.create("number", "0208 3123 456");
    private final PhoneNumber mobile = phoneCreator.create("number", "07771 234567");
    
    private final BeanCreator<Address> addressCreator = BeanCreator.forClass(Address.class);
    private final Address current = addressCreator.create("addressLines", EasyList.of("13 Acacia Avenue", "Surbiton"), "postcode", "VB6 5UX");
    private final Address previous = addressCreator.create("addressLines", EasyList.of("42 Adams Way", "Guildford"), "postcode", "RA8 81T");
    
    private final Person person = BeanCreator.create(Person.class,
            "name", "Arthur Putey",
            "age", 42,
            "addresses", EasyList.of(current, previous),
            "phoneNumbers", EasyMap.of("home", home, "work", work, "mobile", mobile));

    private static final PMap<String, Object> personProperties = EasyMap.properties(
            "name", "Arthur Putey",
            "age", 42,
            "addresses", EasyList.of(
                    EasyMap.properties(
                            "addressLines", EasyList.of("13 Acacia Avenue", "Surbiton"),
                            "postcode", "VB6 5UX"),
                    EasyMap.properties(
                            "addressLines", EasyList.of("42 Adams Way", "Guildford"),
                            "postcode", "RA8 81T")),
            "phoneNumbers", EasyMap.of(
                    "work", EasyMap.properties("number", "0208 3123 456"),
                    "home", EasyMap.properties("number", "01234 567890"),
                    "mobile", EasyMap.properties("number", "07771 234567")));
    
    private final BeanReader<Person> reader = DeepBeanReader.forClass(Person.class);
    private final BeanWriter<Person> writer = DeepBeanWriter.forClass(Person.class);
    
    @Test public void
    reader_reads_bean_properties() {
        assertThat(reader.apply(person), equalTo(personProperties));
    }
    
    @Test public void
    writer_writes_bean() {
        assertThat(writer.apply(personProperties), equalTo(person));
    }
    
    @Test public void
    cloner_clones_bean() {
        Person shallowClone = BeanCloner.shallowClone(person);
        Person deepClone = BeanCloner.deepClone(person);
        
        assertThat(shallowClone, equalTo(person));
        assertThat(shallowClone.getPhoneNumbers(), sameInstance(person.getPhoneNumbers()));
        
        assertThat(deepClone, equalTo(person));
        assertThat(deepClone.getPhoneNumbers(), not(sameInstance(person.getPhoneNumbers())));
    }
}
