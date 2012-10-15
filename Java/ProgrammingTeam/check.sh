
J=../judgedata/texassummers/data/
P=$1
javac $P.java

for F in secret/*.in
do
    echo -n `basename $F` " "
    B=`basename $F`
    /usr/bin/time -f '%Uuser %Ssystem %Eelapsed %PCPU' java $P < $F > $B.myout
    diff $B.myout secret/`basename $F .in`.ans
    rm *.in*
    #./compare.py $F `dirname $F`/`basename $F .in`.ans $B.myout
done

