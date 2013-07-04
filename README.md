SAPSSO wrapper
==============

This tiny project creates a JAR with a class that can be used for SAP SSO authentication. It wraps a SAP native library: `sapsecu.dll` or `libsapsecu.so`.

This class can be put in Tomcat's shared lib dir and then be used from multiple web applications. Per-application deployment should not be used, unless you are running only 1 application, because native libs can only be loaded once per JVM.

The source code was provided by SAP. The package name and class name should not be changed, since the native library contains the same JNI exports.
