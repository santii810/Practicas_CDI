rem Crece el numero de columnas
@ECHO OFF
for /L %%x IN (6,1,99) DO (
	java MiProblema 10000 %%x 2 8 >> ColumnasCrecientes.dat
)
for /L %%x IN (100,10,1000) DO (
	java MiProblema 10000 %%x 2 8 >> ColumnasCrecientes.dat
)
for /L %%x IN (1000,100,10000) DO (
	java MiProblema 10000 %%x 2 8 >> ColumnasCrecientes.dat
)

rem Crece el numero de filas
@ECHO OFF
for /L %%x IN (6,1,99) DO (
	java MiProblema %%x 10000 2 8 >> FilasCrecientes.dat
)
for /L %%x IN (100,10,1000) DO (
	java MiProblema %%x 10000 2 8 >> FilasCrecientes.dat
)
for /L %%x IN (1000,100,10000) DO (
	java MiProblema %%x 10000 2 8 >> FilasCrecientes.dat
)

rem Crece el numero de hilos
@ECHO OFF
for /L %%x IN (6,1,99) DO (
	java MiProblema 10000 10000 2 %%x >> HilosCrecientes.dat
)
for /L %%x IN (100,10,1000) DO (
	java MiProblema 10000 10000 2 %%x >> HilosCrecientes.dat
)
for /L %%x IN (1000,100,10000) DO (
	java MiProblema 10000 10000 2 %%x >> HilosCrecientes.dat
)

rem Crece el filtro
@ECHO OFF
for /L %%x IN (1,1,99) DO (
	java MiProblema 10000 10000 %%x 8 >> FiltroCreciente.dat
)
for /L %%x IN (100,10,1000) DO (
	java MiProblema 10000 10000 %%x 8 >> FiltroCreciente.dat
)

