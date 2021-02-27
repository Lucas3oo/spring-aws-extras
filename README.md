# spring-aws-extras
Util classes to glue Spring and AWS together.

For instance you can use Spring's ResourceLoader to load Java ResourceBundles located in an AWS S3 bucket.

To use it from Gradle depend on:

	implementation 'se.solrike.spring-aws-extras:spring-aws-extras:0.0.3'


Code example on add S3 URL support to Spring's ResourceLoader.
Import the S3ProtocolResolverConfiguration Spring configuration that will add an S3 protocol resolver
to the application context. S3ProtocolResolverConfiguration only does it thing if AwsRegionCondition is true and
it needs an AmazonS3 object.

```
@Configuration
@Import({ S3ProtocolResolverConfiguration.class })
public class TestConfigForS3Support {

  @Bean(name = "amazonS3")
  @Conditional(AwsRegionCondition.class)
  public AmazonS3 amazonS3() {
    return AmazonS3ClientBuilder.defaultClient();
  }

  /**
   * Call to this method must be after the S3 support has been added to the context/resource
   * loader
   */
  @Bean("someClass")
  @Primary
  @ConditionalOnBean(name = "SimpleStorageProtocolResolver")
  @DependsOn("SimpleStorageProtocolResolver")
  public SomeClass someClassWithS3(ResourceLoader resourceLoaderWithS3) {
    return new SomeClass(resourceLoaderWithS3, true);
  }

  /**
   * Fallback if S3 is not available. I.e. we are not running with AWS config to access S3.
   */
  @Bean("someClass")
  public SomeClass someClassWithoutS3(ResourceLoader resourceLoader) {
    return new SomeClass(resourceLoader, false);
  }

}


```
