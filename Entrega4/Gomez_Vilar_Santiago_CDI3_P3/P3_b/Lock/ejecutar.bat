@echo off
	for /L %%x IN (10000,1000,100000) DO (
		java MiProblema %%x >> Lock.dat
	)
	for /L %%x IN (100000,10000,1000000) DO (
		java MiProblema %%x  >> Lock.dat
	)