#fijo la extension de l salida de los plot
set term png
# Tiempo en eje X para todas las graficas
unset logscale

set ylabel 'Tiempo (microsegundos)'
set xlabel 'Nº de incrementos (x1000)'
#set logscale x 2
#set logscale y 2

set title 'Pruebas'
set output 'Locks.png'
plot  'Lock1.dat' w l lw 2, 'Lock2.dat' w l lw 2, 'Lock3.dat' w l lw 2

