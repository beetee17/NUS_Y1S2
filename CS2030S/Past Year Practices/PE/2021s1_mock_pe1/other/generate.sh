#!/bin/bash
cd ../solutions/
echo "Compiling solutions..."
javac -d . *.java
echo "Archiving solutions..."
if [ ! -d actual_solutions/ ]; then
    mkdir actual_solutions/
fi
echo "Removing package statements for jshell testing..."
cp --verbose *.java actual_solutions/
for f in *.java
do
    cat $f | sed "/package/d" > $f.temp
    cat $f.temp > $f
    rm $f.temp
done
main=`grep "public static void main" *.java | head -1 | cut -f1 -d\.`
ls ../input/ | sed "/README.md/d" > 1nfn230f2vn1203r91vnwcnwe2ritrptwpfkv2r3rg820.txt

echo "Getting dependency tree..."
deps=$(../other/get_dependencies.sh)
rm deps
echo "Generating output files..."
while read -r line
do
    echo "Generating output for $line..."
    fileName=`echo $line | cut -f1 -d\.`
    if echo $line | cut -f2 -d\. | grep "jsh" > /dev/null 2>&1
    then
        jshell -q $deps < ../input/$line 2>&1 | sed "s/^\$[1-9][0-9]*/\$\.\./g" > ../output/$fileName.out
    else
        java $main $(cat ../input/$line) > ../output/$fileName.out
    fi
done < 1nfn230f2vn1203r91vnwcnwe2ritrptwpfkv2r3rg820.txt


echo "Amending test cases in html file"
cp ../index_working.html ../index_temp.html

cd ../input/
ls | sed "/README.md/d" > FILES.txt
while read -r line
do
    cat ../index_temp.html | sed "s/<<<$line>>>/$(sed -e 's/[\&/]/\\&/g' -e 's/$/\\n/' $line | tr -d '\n')/" > ../index.html
cp ../index.html ../index_temp.html
done < FILES.txt
rm FILES.txt


cd ../output/
ls | sed "/README.md/d" > FILES.txt
while read -r line
do
    cat ../index_temp.html | sed "s/<<<$line>>>/$(sed -e 's/[\&/]/\\&/g' -e 's/$/\\n/' $line | tr -d '\n')/" > ../index.html
    cp ../index.html ../index_temp.html
done < FILES.txt
rm FILES.txt
cd ../solutions
rm ../index_temp.html

echo "Cleaning up..."
cp --verbose actual_solutions/*.java .
rm 1nfn230f2vn1203r91vnwcnwe2ritrptwpfkv2r3rg820.txt
rm -rf actual_solutions
find . -type f -name "*.class" -exec rm {} +
rmdir * &> /dev/null

cd ..

echo "Zipping files for CodeCrunch..."
zip -r generated_files.zip ./input/ ./output ./index.html ./other -x "*/README.md" -x "*/generate.sh"

