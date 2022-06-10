package com.techartworks.helloworld.integTest;

import com.techartworks.helloworld.library.util.BearerTokenRequestPostProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SecurityIntegrationTest {

    String noScopesToken = "eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiJmNjViNDEyMi00NzQxLTRkNmYtOWRhNy05N2Y1NTI0Y2IxYmQiLCJleHAiOjIwODY2NTE0MTUsIm5iZiI6MCwiaWF0IjoxNjU0NjUxNDE1LCJpc3MiOiJodHRwOi8vMC4wLjAuMDo4MDgwL2F1dGgvcmVhbG1zL21hc3RlciIsImF1ZCI6InRlc3QtY2xpZW50Iiwic3ViIjoiODg2MmM4ODgtMTZjNS00NDUyLTllOTMtMjI4NDBiMzkyYTViIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoidGVzdC1jbGllbnQiLCJzZXNzaW9uX3N0YXRlIjoiYmM3NjZiMDktOTJlMS00MzY2LWJiYjEtZWViNmQxZjY3ODEyIiwiY2xpZW50X3Nlc3Npb24iOiJiNWE2ZWRhYi03OTE3LTRiMmYtOTk4MS03ODIxNThiOTE2YjUiLCJhbGxvd2VkLW9yaWdpbnMiOlsiKiJdLCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInByZWZlcnJlZF91c2VybmFtZSI6InRlc3QiLCJjbGllbnRfaWQiOiJ0ZXN0LWNsaWVudCJ9.g_F7zbuvp5QU-KNAAB4tud8I8LhLbsPqOPLVeHkvzpmBc7EdL2Fee5pPaKJvw0dayaTG0OBq8GPLQPwZd4QPbbkaiJiWdlA39iQpfURAbSU_JJsR59BRoxVgg1CJH7IlnRXH68vU8GFFBWFs0LOT-XkN1vGMHynFnpAe3gflMHBQ2362WLFz5gL1z4VXH3bYYOGkZHVJpWbY-1VLO-sjV5Qvq0xjTa1Wuoy-RpK-HpIlcCOhOB3RY8P2E6QqkC19sGCWbdDfE-GcJqD6V5BIQW8-em81tEpCt8duyNVBXTJk8iIrsXrrRg0X_PvbkxsY6wX4Ev5vArx08-fElcrq_A";

    String messageReadToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzdWJqZWN0IiwiZXhwIjoyMTY0MjQ1NjQ4LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiY2I1ZGMwNDYtMDkyMi00ZGJmLWE5MzAtOGI2M2FhZTYzZjk2IiwiY2xpZW50X2lkIjoicmVhZGVyIiwic2NvcGUiOlsibWVzc2FnZTpyZWFkIl19.Pre2ksnMiOGYWQtuIgHB0i3uTnNzD0SMFM34iyQJHK5RLlSjge08s9qHdx6uv5cZ4gZm_cB1D6f4-fLx76bCblK6mVcabbR74w_eCdSBXNXuqG-HNrOYYmmx5iJtdwx5fXPmF8TyVzsq_LvRm_LN4lWNYquT4y36Tox6ZD3feYxXvHQ3XyZn9mVKnlzv-GCwkBohCR3yPow5uVmr04qh_al52VIwKMrvJBr44igr4fTZmzwRAZmQw5rZeyep0b4nsCjadNcndHtMtYKNVuG5zbDLsB7GGvilcI9TDDnUXtwthB_3iq32DAd9x8wJmJ5K8gmX6GjZFtYzKk_zEboXoQ";

    String messageWriteToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzdWJqZWN0IiwiZXhwIjoyMTY0MjQzOTA0LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiZGI4ZjgwMzQtM2VlNy00NjBjLTk3NTEtMDJiMDA1OWI5NzA4IiwiY2xpZW50X2lkIjoid3JpdGVyIiwic2NvcGUiOlsibWVzc2FnZTp3cml0ZSJdfQ.USvpx_ntKXtchLmc93auJq0qSav6vLm4B7ItPzhrDH2xmogBP35eKeklwXK5GCb7ck1aKJV5SpguBlTCz0bZC1zAWKB6gyFIqedALPAran5QR-8WpGfl0wFqds7d8Jw3xmpUUBduRLab9hkeAhgoVgxevc8d6ITM7kRnHo5wT3VzvBU8DquedVXm5fbBnRPgG4_jOWJKbqYpqaR2z2TnZRWh3CqL82Orh1Ww1dJYF_fae1dTVV4tvN5iSndYcGxMoBaiw3kRRi6EyNxnXnt1pFtZqc1f6D9x4AHiri8_vpBp2vwG5OfQD5-rrleP_XlIB3rNQT7tu3fiqu4vUzQaEg";

    @Autowired
    MockMvc mvc;

    @Test
    void performWhenValidBearerTokenThenAllows() throws Exception {

        this.mvc.perform(get("/hello").with(bearerToken(this.noScopesToken)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello world")));
    }

    private static BearerTokenRequestPostProcessor bearerToken(String token) {
        return new BearerTokenRequestPostProcessor(token);
    }

}
