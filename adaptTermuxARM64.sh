#!/bin/bash
cd

if ! [ `command -v wget` ]
then
	echo 'Please install wget first !!'
	exit
fi

# install request libs
mkdir "libs"

cd libs
libs_base_url="https://github.com/VcotyQin/TermuxWithGradle/raw/master/libs/"
libs=("lib7z.so" "liblog.so.0" "libaapt.so" "libpng16.so.16" "libaapt.so.0" "libprotobuf-lite.so.17" "libandroidfw.so.0" "libunwind.so.0" "libbacktrace.so.0" "libutils.so.0" "libbase.so.0" "libziparchive.so.0" "libcutils.so.0")

for lib in ${libs[@]}
do
	wget ${libs_base_url}${lib}

	if [ -e "$lib" ]
	then
		mv $lib /usr/lib
	fi
done

cd ..
rmdir libs
