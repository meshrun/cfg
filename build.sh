./gradlew clean shadowJar
cp build/libs/cfg-1.0-SNAPSHOT-all.jar bundle/app.jar
./warp-packer --arch linux-x64 --input_dir bundle --exec run.sh --output mrcfg
