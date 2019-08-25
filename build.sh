./gradlew clean shadowJar
cp build/libs/cfg-1.0-SNAPSHOT-all.jar bundle/app.jar

(cd golang && ./build.sh)
cp golang/libjson2hcl.so ./bundle/jre/lib/amd64/
./warp-packer --arch linux-x64 --input_dir bundle --exec run.sh --output mrcfg
