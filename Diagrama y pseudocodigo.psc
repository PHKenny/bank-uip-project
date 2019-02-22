Algoritmo app
	salida = Falso
	Repetir
		Escribir "Iniciar sesion: como banco 1, como cliente 2"
		Leer iniciarComo
		
		Escribir "Nombre de usuario"
		Leer usuario
		
		Escribir "Contrase–a"
		Leer clave
		
		Si iniciarComo = 1
			Escribir "Menu banco: Crear usuario 1, Ver usuarios 2, Registrar transacciones 3, salir otro numero"
			Leer opcion
			Si opcion = 1
				Escribir "Nombre de usuario"
				Leer usuario
				
				Escribir "Contrase–a"
				Leer clave
				
				Escribir "Balance inicial"
				Leer balance
				
				Si balance >= 1500
					balance<-balance
				SiNo
					Escribir "No puede ser registrado"
				FinSi
			Sino
				Si opcion = 2
					Escribir "Muestra los usuarios del sistema"
				SiNo
					Si opcion = 3
						Escribir "Ingrese el usuario"
						Leer usuario
						
						Escribir "Ingrese monto de la transaccion"
						Leer monto
						
						balance <- balance + monto
					Sino
						Escribir "Volver al menu principal"
					FinSi
				FinSi
			FinSi
		SiNo
			Si ultimoInicio = ""
				Escribir "Nueva contrase–a"
				Leer claveNueva
				
				clave <- claveNueva
				ultimoInicio <- "Fecha actual"
			SiNo
				Escribir "Menu cliente: Ver balance 1, Solcitar credito 2, Pagar credito 3, Cambiar contrase–a 4, salir otro numero"
				Leer opcion
				Si opcion = 1
					Escribir "Muestra el balance del cliente"
				Sino
					Si opcion = 2
						Escribir "Calcula el monto del prestamo y agrega el monto al usuario logueado y lo descuenta del usuario con mayor monto disponible"
						balanceUsuario1 <- balanceUsuario1 + (montoPrestamo * 1.3)
						balanceUsuario2 <- balanceUsuario2 - montoPrestamo
					SiNo
						Si opcion = 3
							Escribir "Ingresa el saldo a pagar"
							Leer monto							
							
							Escribir "Calculara lo que le corresponde al usuario que presto el monto y la ganancia del banco"
							
							balanceUsuario2 <- balanceUsuario2 + monto
							balanceUsuario1 <- balanceUsuario1 - monto
						Sino
							Si opcion = 4
								Escribir "Ingresa una nueva contrase–a"
								Leer claveNueva
								
								clave <- claveNueva
							Sino
								Escribir "Volver al menu principal"
							FinSi
						FinSi
					FinSi
				FinSi
			FinSi
		FinSi
	Hasta Que salida = Verdadero
FinAlgoritmo