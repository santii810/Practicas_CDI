@ECHO OFF
for /L %%x IN (1,1,19) DO (
	java P3 %%x >> datos.dat
)
for /L %%x IN (20,5,99) DO (
	java P3 %%x >> datos.dat
)
for /L %%x IN (100,10,499) DO (
	java P3 %%x >> datos.dat
)
for /L %%x IN (500,50,1000) DO (
	java P3 %%x >> datos.dat
)

