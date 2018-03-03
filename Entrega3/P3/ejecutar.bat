@ECHO OFF
for /L %%x IN (6,1,99) DO (
	java MiProblema 10000 %%x 2 8 >> datos.dat
)
for /L %%x IN (100,10,1000) DO (
	java MiProblema 10000 %%x 2 8 >> datos.dat
)
