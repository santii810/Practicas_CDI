@echo off
	for /L %%x IN (10,10,1000) DO (
		java ModoFuerzaBruta 8 %%x >> ModoFuerzaBruta.dat
	)