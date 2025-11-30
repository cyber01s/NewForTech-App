#!/usr/bin/env sh
exec java -jar `"`$APP_HOME/gradle/wrapper/gradle-wrapper.jar`" `"`$@`"
"@
Set-Content -Path "gradlew" -Value $gradlewContent -Encoding UTF8
