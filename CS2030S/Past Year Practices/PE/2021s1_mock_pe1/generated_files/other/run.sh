if [ -f a.out ]
then
    ./a.out < $1
else
    if echo $1 | cut -f2 -d\. | grep "jsh" > /dev/null 2>&1
    then
        if [ ! -d /tmp/home/.java ]; then
            mkdir /tmp/home/.java
        fi

        if [ ! -d /tmp/home/.java/.userPrefs ]; then
            mkdir /tmp/home/.java/.userPrefs
        fi
        deps=$(sh ../other/get_dependencies.sh)
        rm deps
        jshell -q $deps < $1 2>&1 | sed "s/^\$[1-9][0-9]*/\$\.\./g"
    else
        main=`grep "public static void main" *.java | head -1 | cut -f1 -d\.`
        if [ ! -z "$main" ];
        then
            java $main $(cat $1)
        else
            java Main $(cat $1)
        fi
    fi
fi

