@echo off
	for /L %%x IN (10000,1000,100000) DO (
		java MiProblema %%x >> Sync3.dat
	)
	for /L %%x IN (100000,10000,1000000) DO (
		java MiProblema %%x  >> Sync3.dat
	)
	for /L %%x IN (10000,1000,100000) DO (
		java MiProblema %%x >> Sync2.dat
	)
	for /L %%x IN (100000,10000,1000000) DO (
		java MiProblema %%x  >> Sync2.dat
	)
		for /L %%x IN (10000,1000,100000) DO (
		java MiProblema %%x >> Sync1.dat
	)
	for /L %%x IN (100000,10000,1000000) DO (
		java MiProblema %%x  >> Sync1.dat
	)