package se.solrike.springawsextras.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.core.io.s3.SimpleStorageProtocolResolver;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ProtocolResolver;

import com.amazonaws.services.s3.AmazonS3;

/**
 * Adds support for s3 URLs (e.g. s3://&lt;my_bucket&gt;/&lt;my_object&gt;) using the
 * {@link org.springframework.core.io.ResourceLoader} class.
 *
 */
@Configuration
@Conditional(AwsRegionCondition.class)
public class S3ProtocolResolverConfigurator {

  @Autowired
  public S3ProtocolResolverConfigurator(ConfigurableApplicationContext context, AmazonS3 amazonS3) {
    ProtocolResolver resolver = new SimpleStorageProtocolResolver(amazonS3);
    context.addProtocolResolver(resolver);
  }
}
