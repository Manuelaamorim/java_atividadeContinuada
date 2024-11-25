package br.com.cesarschool.poo.titulos.utils;

public class Ordenador {
    public static void ordenar(Comparavel[] array) {
        ordenar(array, null);
    }

    public static void ordenar(Comparavel[] array, Comparador comparador) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                int resultado;
                if (comparador == null) {
                    resultado = array[j].comparar(array[j + 1]);
                } else {
                    resultado = comparador.comparar(array[j], array[j + 1]);
                }
                if (resultado > 0) {
                    Comparavel temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
}
