package se.solrike.springawsextras.context;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;

/**
 *
 * Simulate the usage of S3 support in ResourceLoader. If there is a bean that needs to make use
 * to the S3 support in the ResourceLoader then it must depend on something. But on the other
 * hand the S3 support can be optional depending if the code is running in AWS or not.
 *
 *
 */
@Configuration
@Import({ S3ProtocolResolverConfiguration.class })
public class TestConfigForS3Support {

  /**
   * Call to this method must be after the S3 support has been added to the context/resource
   * loader
   */
  @Bean("someClass")
  @Primary
  @ConditionalOnBean(name = S3ProtocolResolverConfiguration.BEAN_NAME)
  @DependsOn(S3ProtocolResolverConfiguration.BEAN_NAME)
  public SomeClass someClassWithS3(ResourceLoader resourceLoader) {
    return new SomeClass(resourceLoader, true);
  }

  /**
   * Fallback if S3 is not available. I.e. we are not running with AWS config to access S3.
   */
  @Bean("someClass")
  public SomeClass someClassWithoutS3(ResourceLoader resourceLoader) {
    return new SomeClass(resourceLoader, false);
  }

}
