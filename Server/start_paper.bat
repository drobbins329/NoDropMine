TITLE Paper 1.16.1
:start
java -Xms1024M -Xmx4G -agentlib:jdwp=transport=dt_socket,server=n,suspend=n,address=25565 -jar paper.jar
GOTO start