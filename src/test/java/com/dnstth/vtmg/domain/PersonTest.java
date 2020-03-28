package com.dnstth.vtmg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.dnstth.vtmg.web.rest.TestUtil;

public class PersonTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Person.class);
        Person person1 = new Person();
        person1.setId("id1");
        Person person2 = new Person();
        person2.setId(person1.getId());
        assertThat(person1).isEqualTo(person2);
        person2.setId("id2");
        assertThat(person1).isNotEqualTo(person2);
        person1.setId(null);
        assertThat(person1).isNotEqualTo(person2);
    }
}
