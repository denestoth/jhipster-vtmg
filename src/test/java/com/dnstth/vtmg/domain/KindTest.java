package com.dnstth.vtmg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.dnstth.vtmg.web.rest.TestUtil;

public class KindTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kind.class);
        Kind kind1 = new Kind();
        kind1.setId("id1");
        Kind kind2 = new Kind();
        kind2.setId(kind1.getId());
        assertThat(kind1).isEqualTo(kind2);
        kind2.setId("id2");
        assertThat(kind1).isNotEqualTo(kind2);
        kind1.setId(null);
        assertThat(kind1).isNotEqualTo(kind2);
    }
}
