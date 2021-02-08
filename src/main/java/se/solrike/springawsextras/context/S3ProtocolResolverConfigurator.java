package se.solrike.springawsextras.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.core.io.s3.SimpleStorageProtocolResolver;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ProtocolResolver;

import com.amazonaws.services.s3.AmazonS3;

/**
 * Adds support for s3 URLs (e.g. s3://<my_bucket>/<my_object>) using the
 * {@link org.springframework.core.io.ResourceLoader} class.
 *
 */
@Configuration
public class S3ProtocolResolverConfigurator {

  @Autowired
  @SuppressWarnings("all")
  public S3ProtocolResolverConfigurator(ConfigurableApplicationContext context, AmazonS3 amazonS3) {
    ProtocolResolver resolver = new SimpleStorageProtocolResolver(amazonS3);
    context.addProtocolResolver(resolver);
  }
}
