import org.gradle.api.tasks.wrapper.*
import org.gradle.script.lang.kotlin.*

apply<ApplicationPlugin>()

configure<ApplicationPluginConvention> {
    mainClassName = "com.github.jdanekrh.CoreSenderReceiver"
}

repositories {
    flatDir {
        it.dir("apache-artemis-1.3.0/lib")
    }
}

dependencies {
    compile(":artemis-commons:1.3.0")
    compile(":artemis-core-client:1.3.0")
    compile(":geronimo-jms_2.0_spec:1.0-alpha-2")
    runtime(":artemis-jms-client:1.3.0")
    runtime(":commons-beanutils:1.9.2")
    runtime(":commons-logging:1.2")
    runtime(":jboss-logging:3.3.0.Final")
    runtime(":commons-collections:3.2.2")
    runtime(":netty-all:4.0.32.Final")

    runtime(":artemis-selector:1.3.0")
}
