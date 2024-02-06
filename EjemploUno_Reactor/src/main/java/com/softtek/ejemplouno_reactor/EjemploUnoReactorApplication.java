package com.softtek.ejemplouno_reactor;

import com.softtek.ejemplouno_reactor.model.Producto;
import com.softtek.ejemplouno_reactor.model.Tiendas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootApplication
public class EjemploUnoReactorApplication implements CommandLineRunner {
	List<Producto> lista = new ArrayList<>();
	
	public static void main(String[] args) {
		SpringApplication.run(EjemploUnoReactorApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		lista.add(new Producto(1, "Pantalla", 130.5));
		lista.add(new Producto(2, "Mouse", 28.5));
		lista.add(new Producto(3, "Teclado", 200.0));
		
		convertirFLuxMono();
	
	//		Flux<String> datos = Flux.just("uno", "dos", "tres", "cuatro", "cinco").doOnNext(log :: info)
		//				                     .doOnComplete(() -> log.info("---FIN---"));
	}
	
	//	public void complete() {
	//		Flux<String> datos = Flux.just("uno", "dos", "tres", "cuatro", "cinco");
	//		datos.subscribe(p -> log.info(p), null, new Runnable() {
	//			@Override
	//			public void run() {
	//				log.info("---FIN---");
	//			}
	//		});
	//	}
	
	//		public void crearFlujo() {
	//		Flux<String> datos = Flux.just("uno", "dos", "tres", "cuatro", "cinco").doOnNext(p -> {
	//			if (p.isEmpty()) {
	//				throw new RuntimeException("Dato vacío");
	//			}
	//			log.info(p);
	//		});
	//	}
	
	public void operatorMapFilter() {
		List<Producto> lista = new ArrayList<>();
		lista.add(new Producto(1, "Pantalla", 130.5));
		lista.add(new Producto(2, "Mouse", 28.5));
		lista.add(new Producto(3, "Teclado", 200.0));
		
		Flux.fromIterable(lista).filter(p -> p.getPrecio() > 50)
				// Modifica un elemento y lo devuelve al flujo.
				
				.map(p -> {
					p.setDescripcion(p.getDescripcion().toUpperCase());
					return p;
				}).doOnNext(System.out :: println).subscribe();
	}
	
	public void operadorFlatMap() {
		List<Producto> lista = new ArrayList<>();
		lista.add(new Producto(1, "Pantalla", 130.5));
		lista.add(new Producto(2, "Mouse", 28.5));
		lista.add(new Producto(3, "Teclado", 200.0));
		
		// Modifica un elemento y los devuelve como reactivos.
		Flux.fromIterable(lista).flatMap(p -> {
			if (p.getDescripcion().equals("Mouse")) {
				return Mono.just(p);
			} else {
				return Mono.empty();
			}
		}).subscribe(System.out :: println);
	}
	
	public void convertirFLuxMono() {
		// Imprimo toda la lista.
		Flux.fromIterable(lista).collectList().subscribe(System.out :: println);
		
		// Imprimo cada elemento por separado.
		Flux.fromIterable(lista).collectList().subscribe(p -> p.forEach(System.out :: println));
	}
	
	public void combinarFlujosFaltMap(){
		Mono<Producto> productoMono = Mono.just(new Producto(1, "Pantalla", 129.50));
		Mono<Tiendas> tiendasMono = Mono.fromCallable(()->{
			Tiendas tiendas = new Tiendas();
			tiendas.add;
		});
	}
}