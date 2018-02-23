@ECHO OFF
for /L %%x IN (1,1,100) DO (
	java P3 %%x >> datos.dat
)
