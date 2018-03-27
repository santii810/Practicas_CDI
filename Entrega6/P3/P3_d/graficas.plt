#fijo la extension de l salida de los plot
set term png
# Tiempo en eje X para todas las graficas
unset logscale

set ylabel 'Tiempo (ms)'
set xlabel 'Num de ejecuciones'
#set logscale x 2
#set logscale y 2


set title 'Fuerza bruta'
set output 'Fuerza bruta.png'
plot  'ModoFuerzaBruta.dat' w l lw 2

set title 'Modo explicito'
set output 'Modo explicito.png'
plot  'ModoExplicito.dat' w l lw 2

set title 'Comparativa'
set output 'Comparativa.png'
plot  'ModoFuerzaBruta.dat' w l lw 2, 'ModoExplicito.dat' w l lw 2
