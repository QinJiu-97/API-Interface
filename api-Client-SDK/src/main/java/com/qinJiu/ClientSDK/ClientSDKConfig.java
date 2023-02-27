package com.qinJiu.ClientSDK;

import com.qinJiu.ClientSDK.client.ApiClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author QinJiu
 * @Date 2022/11/27
 */
@Configuration
@ConfigurationProperties("qinjiu.client")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ComponentScan
public class ClientSDKConfig {
    private String accessKey;
    private String secretKey;

    @Bean
    public ApiClient qinClient() {
        return new ApiClient(accessKey, secretKey);
    }
}
