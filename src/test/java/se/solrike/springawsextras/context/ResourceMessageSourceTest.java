package se.solrike.springawsextras.context;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;

class ResourceMessageSourceTest {

  private ResourceMessageSource mMessageSource;

  @BeforeEach
  void setUp() {
    DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
    mMessageSource = new ResourceMessageSource(resourceLoader);
    mMessageSource.setBasename("classpath:i18n/messages");
  }

  @Test
  void testGetMessageFromClasspathResource() {
    String message = mMessageSource.getMessage("key1", null, new Locale("sv"));
    assertEquals("nyckel ett", message);
  }

  @Test
  void testGetDefaultMessageFromClasspathResource() {
    // "en" does not exists instead it will fallback to default messages.properties
    // but then the default resource bundle will be cached under "en" locale
    String messageEn = mMessageSource.getMessage("key1", null, new Locale("en"));
    assertEquals("key one", messageEn);
  }

}
