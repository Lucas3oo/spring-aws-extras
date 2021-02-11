
package se.solrike.springawsextras.context;

import java.io.InputStream;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * Spring Message source that uses Spring's {@link ResourceLoader} to load properties files as
 * resource bundles.
 *
 * Only resource bundles with language are supported. That is no country or variant/script is
 * supported.
 *
 * @author Lucas Persson
 *
 */
public class ResourceMessageSource extends ResourceBundleMessageSource {

  private final ResourceLoader mResourceLoader;

  public ResourceMessageSource(ResourceLoader resourceLoader) {
    super();
    mResourceLoader = resourceLoader;
  }

  /**
   * The basename can be a fully qualified URLs, e.g. "file:C:/folder/test" or a classpath
   * pseudo-URLs, e.g. "classpath:folder/test".
   *
   * If the ResourceLoader supports other URLs like "s3://folder/test" then that can be use here
   * also.
   *
   *
   * @see ResourceLoader#getResource(String)
   *
   * @param basename
   *          the basename as an URL or pseudo-URL
   */
  @Override
  @SuppressWarnings("all") // Suppress sonarLint warning
  public void setBasename(String basename) {
    super.setBasename(basename);
  }

  /**
   * Obtain the resource bundle for the given basename and Locale.
   *
   * @param basename
   *          the basename to look for
   * @param locale
   *          the Locale to look for
   * @return the corresponding ResourceBundle
   * @throws MissingResourceException
   *           if no matching bundle could be found
   *
   */
  @Override
  protected ResourceBundle doGetBundle(String basename, Locale locale) throws MissingResourceException {
    String resourceName = basename + "_" + locale.getLanguage() + ".properties";
    Resource resource = null;
    resource = mResourceLoader.getResource(resourceName);
    try (InputStream inputStream = resource.getInputStream()) {
      return loadBundle(inputStream);
    }
    catch (Exception e) {
      // fallback to root properties file
      resource = mResourceLoader.getResource(basename + ".properties");
      try (InputStream inputStream = resource.getInputStream()) {
        return loadBundle(inputStream);
      }
      catch (Exception e2) {
        throw new MissingResourceException("Could not load resource bundle for basename: " + basename + ", Locale: "
            + locale + ". Reason: " + e.getMessage() + ", " + e2.getMessage(), resource.getFilename(), "");
      }
    }
  }

  @Override
  public String toString() {
    return "basenames=" + getBasenameSet();
  }

}
