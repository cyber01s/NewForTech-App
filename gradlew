#!/usr/bin/env sh

# Determine the directory of the script
APP_HOME=$(dirname "$0")

# Determine Java command
if [ -n "$JAVA_HOME" ]; then
    JAVA_CMD="$JAVA_HOME/bin/java"
else
    JAVA_CMD="java"
fi

# Determine the wrapper JAR path relative to the script location
GRADLE_JAR="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

# Execute the Java command
exec "$JAVA_CMD" -jar "$GRADLE_JAR" "$@"
