package com.escaperoom.tests;

import com.escaperoom.config.DatabaseConfig;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.assertj.core.api.Assertions.assertThat;

class ConnectionTestTest {

    @Test
    void shouldConnectToDatabase() throws Exception {
        try (Connection con = DatabaseConfig.getInstance().getConnection()) {
            assertThat(con).isNotNull();
            System.out.println("✅ Database connection test successful.");
        }
    }
}
