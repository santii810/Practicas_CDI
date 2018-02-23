@ECHO OFF
for /L %%x IN (1,1,10) DO (
	java P3 %%x >> datos.txt	
)
for /L %%x IN (5,5,500) DO (
	java P3 %%x >> datos.txt	
)