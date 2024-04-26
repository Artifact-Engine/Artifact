cd ..
cd ArtifactTestProject

./gradlew jar
cd build
cd libs

java -jar ArtifactTestProject-1.0-SNAPSHOT.jar