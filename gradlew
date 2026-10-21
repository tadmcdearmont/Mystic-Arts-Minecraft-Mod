#!/bin/sh
##############################################################################
# Gradle start up script for UN*X / Linux / macOS / GitHub Actions
##############################################################################

# Attempt to set APP_HOME
APP_HOME=$(cd "`dirname "$0"`" && pwd -P)
APP_NAME="Gradle"

warn () {
    echo "$*"
}

die () {
    echo
    echo "$*"
    echo
    exit 1
}

# Determine the Java command to use
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        JAVACMD="$JAVA_HOME/jre/sh/java"
    else
        JAVACMD="$JAVA_HOME/bin/java"
    fi
else
    JAVACMD="java"
    which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH."
fi

# Try global gradle if available
if command -v gradle >/dev/null 2>&1; then
    echo "Found local Gradle installation. Executing build..."
    exec gradle "$@"
fi

# Run wrapper jar if present
WRAPPER_JAR="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"
if [ -f "$WRAPPER_JAR" ]; then
    exec "$JAVACMD" -jar "$WRAPPER_JAR" "$@"
fi

echo "====================================================================="
echo " Building Mystic Arts Fabric Mod 1.20.1"
echo "====================================================================="
echo "Notice: No global 'gradle' binary was found in your local PATH."
echo ""
echo "RECOMMENDED FAST BUILD (No installation needed):"
echo "  1. Push this folder to a free GitHub repository."
echo "  2. Go to the 'Actions' tab -> 'Build Mystic Arts Fabric Mod'."
echo "  3. The JAR is automatically compiled in the cloud in 60s and"
echo "     available under 'Artifacts'!"
echo ""
echo "LOCAL BUILD REQUIREMENT:"
echo "  Please install Java 17 JDK and run with a local Gradle tool or IDE."
echo "====================================================================="
exit 1
