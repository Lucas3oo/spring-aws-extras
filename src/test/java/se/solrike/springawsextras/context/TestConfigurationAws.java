package se.solrike.springawsextras.context;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class TestConfigurationAws {

  @Bean
  AmazonS3 amazonS3() {
    // this only works if the test is run with -Daws.region=us-east-1 or some other region
    System.setProperty(SDKGlobalConfiguration.AWS_REGION_SYSTEM_PROPERTY, "us-east-1");
    AmazonS3 amazonS3 = Mockito.spy(AmazonS3ClientBuilder.defaultClient());
    return amazonS3;
  }
}
