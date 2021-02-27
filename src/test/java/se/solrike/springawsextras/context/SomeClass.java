package se.solrike.springawsextras.context;

import org.springframework.core.io.ResourceLoader;

public class SomeClass {

  private ResourceLoader mResourceLoader;
  private boolean mS3Supported;

  public SomeClass(ResourceLoader resourceLoader, boolean s3Supported) {
    mResourceLoader = resourceLoader;
    mS3Supported = s3Supported;
  }

  public ResourceLoader getResourceLoader() {
    return mResourceLoader;
  }

  public boolean isS3Supported() {
    return mS3Supported;
  }

}
