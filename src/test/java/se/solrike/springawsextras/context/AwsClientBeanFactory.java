package se.solrike.springawsextras.context;

import static org.mockito.Mockito.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

/**
 *
 * Spring configuration to optionally create an AWS SDK client if a region is configured.
 *
 * @author Lucas Persson
 *
 */
@Configuration
class AwsClientBeanFactory {

  @Bean(name = "amazonS3")
  @Conditional(AwsRegionCondition.class)
  @Primary
  public AmazonS3 amazonS3() {
    return AmazonS3ClientBuilder.defaultClient();
  }

  @Bean(name = "amazonS3")
  public AmazonS3 amazonS3Mock() {
    return mock(AmazonS3.class);
  }

}
