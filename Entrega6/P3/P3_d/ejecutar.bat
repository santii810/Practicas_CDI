@echo off
rm .\ModoExplicito.dat
rm .\ModoFuerzaBruta.dat

	for /L %%x IN (10,10,2000) DO (
		java ModoFuerzaBruta 8 %%x >> ModoFuerzaBruta.dat
		java ModoExplicito 8 %%x >> ModoExplicito.dat
	)