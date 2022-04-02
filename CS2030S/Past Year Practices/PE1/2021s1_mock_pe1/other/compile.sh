if [ -f $src.c ]
then
    g++ -lm $1
else
    if [ -f Dorms.java ]
    then
        rm Dorms.java
    fi
    if [ -f PersonDatabase.java ]
    then
        rm PersonDatabase.java
    fi
    if [ -f LocationDatabase.java ]
    then
        rm LocationDatabase.java
    fi
    if [ -f RandomNumberGenerator.java ]
    then
        rm RandomNumberGenerator.java
    fi
    if [ -f Main.java ]
    then
        rm Main.java
    fi
    if [ -f SafeEntry.java ]
    then
        rm SafeEntry.java
    fi
    if [ -f TraceTogether.java ]
    then
        rm TraceTogether.java
    fi
    if [ -f SimulationParameters.java ]
    then
        rm SimulationParameters.java
    fi

    cp ../others/*.java .

    javac -d . *.java
fi

