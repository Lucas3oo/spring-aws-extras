package se.solrike.springawsextras.context;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ProtocolResolver;

import com.amazonaws.services.s3.AmazonS3;

import io.awspring.cloud.core.io.s3.SimpleStorageProtocolResolver;

/**
 * Adds support for s3 URLs (e.g. s3://&lt;my_bucket&gt;/&lt;my_object&gt;) using the
 * {@link org.springframework.core.io.ResourceLoader} class.
 * <p>
 * Only created if the {@link AwsRegionCondition} is true.
 * <p>
 * If the order is important then depend on the bean created in this configuration.
 *
 * @author Lucas Persson
 *
 */
@Configuration
@Conditional(AwsRegionCondition.class)
public class S3ProtocolResolverConfiguration {

  public static final String BEAN_NAME = "simpleStorageProtocolResolver";

  /**
   * The bean is not really to be used. Created so that the side effect to add the resolver to
   * the context is triggered.
   *
   * @param context
   *          Spring application context
   * @param amazonS3
   *          AWS SDK client for S3
   * @return ProtocolResolver for S3 URLs
   */
  @Bean(BEAN_NAME)
  public ProtocolResolver protocolResolver(ConfigurableApplicationContext context, AmazonS3 amazonS3) {
    ProtocolResolver resolver = new SimpleStorageProtocolResolver(amazonS3);
    context.addProtocolResolver(resolver);
    return resolver;
  }
}
