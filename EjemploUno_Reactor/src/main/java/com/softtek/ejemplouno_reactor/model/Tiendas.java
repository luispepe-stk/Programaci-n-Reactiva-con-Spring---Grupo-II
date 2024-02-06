package com.softtek.ejemplouno_reactor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Tiendas {
	private List<String> tiendas = new ArrayList<>();
}
