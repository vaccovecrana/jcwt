plugins { id("io.vacco.oss") version "1.0.0" }

group = "io.vacco.jcwt"
version = "0.1.0"

configure<io.vacco.oss.CbPluginProfileExtension> {
  addJ8Spec()
  addPmd()
  addClasspathHell()
  sharedLibrary(true, false)
}

configure<JavaPluginExtension> {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}
