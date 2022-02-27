if [ -f deps ]
then
    rm -f deps
fi
touch deps

(jar cvf depstesting.jar *.class) > /dev/null
jdeps -v depstesting.jar | grep "depstesting.jar" | cut -b 4- | awk '{if (NR!=1) {print}}' | sed 's/depstesting\.jar//g' | sed 's/ \+/ /g' | sed '/\$/d' > dependencies.txt
counter=0
while [ $(cat dependencies.txt | wc -l) -ne "0" -a $counter -lt "50" ]
do
    cat dependencies.txt | cut -d ' ' -f 1 > current.txt
    ls *.java | cut -d '.' -f 1 > files.txt
    cat deps >> current.txt

    while read -r line 
    do
        cat files.txt | sed "/\b$line\b/d" > newfiles.txt
        cat newfiles.txt > files.txt
        rm -f newfiles.txt
    done < current.txt
    cat files.txt >> deps
    while read -r line
    do
        cat dependencies.txt | sed "/\b$line\b/d" > ndependencies.txt
        cat ndependencies.txt > dependencies.txt
        rm -f ndependencies.txt
    done < files.txt
    counter=$((counter+1))
done
if [ -f current.txt ]
then
    rm current.txt
fi
ls *.java | cut -d '.' -f 1 > files.txt
cat files.txt | grep -vf deps >> deps
rm depstesting.jar files.txt dependencies.txt
cat deps | sed 's/$/.java/g' | tr '\n' ' '


