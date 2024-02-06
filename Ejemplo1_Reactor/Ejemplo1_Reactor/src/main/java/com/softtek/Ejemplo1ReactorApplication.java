package com.softtek;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.softtek.models.Producto;
import com.softtek.models.ProductoTiendas;
import com.softtek.models.Tiendas;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@SpringBootApplication
public class Ejemplo1ReactorApplication implements CommandLineRunner{
	
	List<Producto> lista = Arrays.asList(
			new Producto(1, "Pantalla", 129.50),
			new Producto(2, "Raton", 32.50),
			new Producto(3, "Teclado", 57.95));

	public static void main(String[] args) {
		SpringApplication.run(Ejemplo1ReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ejercicio_1();
	}
	
	public void ejercicio_1() {
		// crear un Flujo con numeros del 1 al 10
		// crear un Flujo con numeros del 20 al 30
		
		// Combinar ambos flujos devolviendo la suma 1 + 21, 2 + 22
		Flux<Integer> numeros1 = Flux.range(1, 10);
		Flux<Integer> numeros2 = Flux.range(21, 10);
		
		// Version 1 con zipWith
		numeros1.zipWith(numeros2, (n1, n2) -> new Integer(n1 + n2))
			.subscribe(item -> System.out.println(item));
		
		// Version 2 con zipWith
		numeros1.zipWith(numeros2)
			.map(tupla -> {
				Integer n1 = tupla.getT1();
				Integer n2 = tupla.getT2();
				return new Integer(n1 + n2);
			})
			.subscribe(item -> System.out.println(item));
		
		// Version 3 con flatMap	
		numeros1.flatMap(it -> numeros2 
					.skip(it - 1) 
					.take(1) 
					.map(it2 -> it + it2) 
				) 
				.subscribe(System.out::println);
		
	}
	
	public void operador_range() {
		Flux<Integer> numeros = Flux.range(1, 5);  // empieza en 1 y 5 elementos
		numeros.subscribe(System.out::println);
	}
	
	public void combinar_flujos_zipWith(){
		// Creamos un Mono con un producto
		Mono<Producto> productoMono = Mono.just(new Producto(1, "Pantalla", 129.50));
		
		// Crear un Mono de tiendas
		Mono<Tiendas> tiendasProductoMono = Mono.fromCallable( () -> {
			Tiendas tiendas = new Tiendas();
			tiendas.addTienda("PC Components");
			tiendas.addTienda("Amazon");
			tiendas.addTienda("Media Mark");
			return tiendas;
		});
		
		// Combinar ambos flujos para obtener un tercero
		Mono<ProductoTiendas> mono = productoMono.zipWith(tiendasProductoMono, (prod, tiendas) -> new ProductoTiendas(prod, tiendas));
		mono.subscribe(resultado -> System.out.println(resultado));
		
		// Otra forma de hacerlo
		Mono<Tuple2<Producto,Tiendas>> monoTupla =  productoMono.zipWith(tiendasProductoMono);
		monoTupla.map(tupla -> {
				Producto prod = tupla.getT1();
				Tiendas tiendas = tupla.getT2();
				return new ProductoTiendas(prod, tiendas);
			})
		.subscribe(resultado -> System.out.println(resultado));
		
	}
	
	public void combinar_flujos_flatMap(){
		// Creamos un Mono con un producto
		Mono<Producto> productoMono = Mono.just(new Producto(1, "Pantalla", 129.50));
		
		// Crear un Mono de tiendas
		Mono<Tiendas> tiendasProductoMono = Mono.fromCallable( () -> {
			Tiendas tiendas = new Tiendas();
			tiendas.addTienda("PC Components");
			tiendas.addTienda("Amazon");
			tiendas.addTienda("Media Mark");
			return tiendas;
		});
		
		// Combinar ambos flujos para obtener un tercero
		productoMono.flatMap(prod -> tiendasProductoMono.map(tienda -> new ProductoTiendas(prod, tienda)))
			.subscribe(resultado -> System.out.println(resultado));
		
	}
	
	public void convertir_flux_mono() {
		// De esta forma mostramos toda la lista como un solo objeto
		Flux.fromIterable(lista)
			.collectList()   // retorna Mono<List<Producto>>
			.subscribe(listaProd -> System.out.println(listaProd));
		
		// Mostramos los productos de uno en uno
		Flux.fromIterable(lista)
			.collectList()   // retorna Mono<List<Producto>>
			.subscribe(listaProd -> {
				listaProd.forEach(p -> System.out.println(p));
			});
	}
	
	public void operador_flatMap() {
		// El operador map modifica un elemento y lo devuelve al stream pero no es reactivo,
		// flatMap modifica el elemento y lo devuelve como reactivo, un observable Flux o Mono		
		Flux.fromIterable(lista)
			.flatMap(prod -> {
				if (prod.getDescripcion().equals("Raton")) {
					return Mono.just(prod);
				} else {
					return Mono.empty();
				}
			})
			.subscribe(prod -> System.out.println(prod));
		
	}
	
	public void operador_map_filter() {
		// Crear el flujo a partir de un iterable
		// OJO!!! los arrays no son iterables
		List<Producto> lista = new ArrayList<>();
		lista.add(new Producto(1, "Pantalla", 129.50));
		lista.add(new Producto(2, "Raton", 32.50));
		lista.add(new Producto(3, "Teclado", 57.95));
		
		Flux.fromIterable(lista)
				// filter Filtra elementos en base a una condicion
				.filter(p -> p.getPrecio() > 50)
				// map Modifica un elemento y lo devuelve al flujo
				.map(prod -> {
					prod.setDescripcion(prod.getDescripcion().toUpperCase());
					return prod;
				})
				.doOnNext(System.out::println)
				.subscribe();
	}
	
	public void complete() {
		// Msotramos los datos en consola y un mensaje final
		Flux<String> datos = Flux.just("uno", "dos", "tres", "cuatro");
		
		// Si quiero meter un Runnable necesito tener 2 consumer:
		// 1.- muestra el dato por consola
		// 2.- es null
		datos.subscribe(dato -> System.out.println(dato), null, new Runnable() {
			
			// Este metodo run se ejecuta al terminar de procesar todo el flujo
			@Override
			public void run() {
				System.out.println("--- FIN ---");
			}
		});
		
		// Otra forma
		Flux<String> datos2 = Flux.just("uno", "dos", "tres", "cuatro")
				.doOnNext(dato -> System.out.println(dato))
				.doOnComplete(new Runnable() {
					
					// Este metodo run se ejecuta al terminar de procesar todo el flujo
					@Override
					public void run() {
						System.out.println("--- FIN ---");
					}
				});
		datos2.subscribe();
	}
	
	public void crear_Flujo() {
		// Crear un flujo de datos y mostrar cada uno en consola
//		Flux<String> datos = Flux.just("uno", "dos", "tres", "cuatro")
//				.doOnNext(dato -> System.out.println(dato));
		
		// Con Java 8
//		Flux<String> datos = Flux.just("uno", "dos", "tres", "cuatro")
//				.doOnNext(System.out::println);
		
		// Manejar excepciones
		Flux<String> datos = Flux.just("uno", "dos", "", "tres", "cuatro")
				.doOnNext(dato -> {
					if (dato.isEmpty()) {
						throw new RuntimeException("Dato vacio");
					}
					System.out.println(dato);
				});
		
		// Hasta que no se subscribe al flujo, no hace nada
		datos.subscribe();
	}

}
