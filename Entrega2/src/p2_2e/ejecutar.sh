#/bin/bash
for i in `seq 1 1 19`
do
    java P3 $i >> datos.dat
done
for i in `seq 20 5 99`
do
    java P3 $i >> datos.dat
done
for i in `seq 100 10 499`
do
    java P3 $i >> datos.dat
done
for i in `seq 500 50 1000`
do
    java P3 $i >> datos.dat
done
