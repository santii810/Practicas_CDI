rem @echo off
del .\ModoExplicito.dat
del .\ModoFuerzaBruta.dat

	for /L %%x IN (0,200,10000) DO (
		java ModoFuerzaBruta 8 %%x >> ModoFuerzaBruta.dat
		java ModoExplicito 8 %%x >> ModoExplicito.dat
	)