./gradlew assemblerelease
mv app/build/outputs/apk/release/WeatherClient.apk WeatherClientUnsigned.apk
java -jar uber-apk-signer-1.0.0.jar -a . --out WeatherClient
rm *Unsigned*
mv WeatherClient/* . && rm -rf WeatherClient
mv *apk WeatherClient.apk
echo 'Successfully built and signed WeatherClient.apk'
