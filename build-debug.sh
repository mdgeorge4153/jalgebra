javac -cp bin/processor.jar \
      -J-Xdebug             \
      -J-Xrunjdwp:transport=dt_socket,address=6666,server=y,suspend=y \
      -d bin/algebra        \
      -sourcepath src       \
      $(find src -name "*.java" )
