#fijo la extension de l salida de los plot
set term png
# Tiempo en eje X para todas las graficas
unset logscale

set ylabel 'Tiempo (microsegundos)'
set xlabel 'Num de incrementos (x1000)'
#set logscale x 2
#set logscale y 2


set title 'Pruebas'
set output 'Syncs.png'
plot  'Sync1.dat' w l lw 2, 'Sync2.dat' w l lw 2, 'Sync3.dat' w l lw 2

