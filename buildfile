require "buildr/bnd"
require 'buildr/java/cobertura'
require 'buildr/checkstyle'
require 'buildr/findbugs'
require 'buildr/javancss'
require 'buildr/jdepend'
require 'buildr/pmd'
require 'buildr/scala'

repositories.remote << Buildr::Bnd.remote_repository

desc "list utils"
define "russellstreet" do

  project.version = `git describe --tags --always`.strip
  project.group = "org.russell.street"

  compile.options.source = '1.6'
  compile.options.target = '1.6'
  compile.options.lint = 'all'

  compile.with :testng,
               :mockito

  test.with :mockito
  test.using :testng

  package(:jar)

  javancss.enabled = true
  javancss.config_directory = _('etc/javancss')

  checkstyle.config_directory = _('etc/checkstyle')

  jdepend.enabled = true
  jdepend.config_directory = _('etc/jdepend')

  findbugs.enabled = true
  findbugs.config_directory = _('etc/findbugs')

end
