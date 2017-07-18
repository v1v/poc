[ -e build ] && rm -rf build
mkdir -p build
cd build
git clone ssh://${USER}@mpd-gerrit.cambridge.arm.com:29418/gerrit/plugins/ti2 .
cp ../Dockerfile .
docker build -t ti2-gerrit-plugin .
docker run -it --name ti2-gerrit-plugin ti2-gerrit-plugin mvn package
docker cp ti2-gerrit-plugin:/usr/src/app/target/ti2-plugin-2.13.jar .
docker rm -f ti2-gerrit-plugin