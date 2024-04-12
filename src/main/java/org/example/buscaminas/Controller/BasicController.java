package org.example.buscaminas.Controller;

import org.example.buscaminas.Entity.Bloque;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class BasicController {

    @GetMapping("/buscaminas")
    public String inicio(){
        return "main";
    }

    @GetMapping("/juegoPlantilla")
    public String juego(Model model,@RequestParam(name="cantidadFilas",required = false) Integer cantidadFilas,
                        @RequestParam(name = "cantidadColumnas",required = false) Integer cantidadColumnas,
                        @RequestParam("esPrimeraPartida")Boolean esPrimeraPartida,
                        @RequestParam("matrizActual")Bloque[][] matrizActual,
                        @RequestParam("filaEscogida") Integer filaEscogida,
                        @RequestParam("columnaEscogida") Integer columnaEscogida){
        Bloque[][] matriz=new Bloque[cantidadFilas][cantidadColumnas];
        if(esPrimeraPartida){
            matriz=new Bloque[cantidadFilas][cantidadColumnas];
            for (int i=0;i<cantidadFilas;i++){
                for(int j=0;j<cantidadColumnas;j++){
                    matrizActual[i][j].setEstado("sinExplorar");
                    Integer numeroRandom1=new Random().nextInt(cantidadFilas*cantidadColumnas);
                    if (numeroRandom1<cantidadColumnas*cantidadFilas*0.05){
                        matrizActual[i][j].setTieneBomba(true);
                    }
                }
            }
        }else {
            matriz=matrizActual;
        }
        Boolean bombaEncontrada=false;
        for(int i=0;i<cantidadFilas;i++){
            for(int j=0;j<cantidadColumnas;j++){
                if(i==filaEscogida&&j==columnaEscogida){
                    if(matriz[i][j].isTieneBomba()&&matriz[i][j].getEstado().equals("sinExplorar")){
                        matriz[i][j].setEstado("descubierto");
                        bombaEncontrada=true;
                    }else {
                        buscarBloquesSinBomba(i,j,matriz,cantidadFilas,cantidadColumnas);
                    }
                }
            }
        }
        Bloque[][] nuevaMatriz=new Bloque[cantidadFilas][cantidadColumnas];
        Integer cantidadBombasAlrededor=0;
        for (int i=0;i<cantidadFilas;i++){
            for(int j=0;j<cantidadColumnas;j++){
                if(j==0){
                    if(i==0){
                        if(matriz[i+1][j].isTieneBomba()){
                            cantidadBombasAlrededor++;
                        }
                        if(matriz[i+1][j+1].isTieneBomba()){
                            cantidadBombasAlrededor++;
                        }
                        if(matriz[i][j+1].isTieneBomba()){
                            cantidadBombasAlrededor++;
                        }
                    }else if(i==cantidadFilas-1){
                        if(matriz[i-1][j].isTieneBomba()){
                            cantidadBombasAlrededor++;
                        }
                        if(matriz[i-1][j+1].isTieneBomba()){
                            cantidadBombasAlrededor++;
                        }
                        if(matriz[i][j+1].isTieneBomba()){
                            cantidadBombasAlrededor++;
                        }
                    }else {
                        if(matriz[i-1][j].isTieneBomba()){
                            cantidadBombasAlrededor++;
                        }
                        if(matriz[i+1][j].isTieneBomba()){
                            cantidadBombasAlrededor++;
                        }
                        if(matriz[i-1][j+1].isTieneBomba()){
                            cantidadBombasAlrededor++;
                        }
                        if(matriz[i][j+1].isTieneBomba()){
                            cantidadBombasAlrededor++;
                        }
                        if(matriz[i+1][j+1].isTieneBomba()){
                            cantidadBombasAlrededor++;
                        }
                    }
                }else if(j==cantidadColumnas-1){
                    if(i==0){
                        if(matriz[i+1][j].isTieneBomba()){
                            cantidadBombasAlrededor++;
                        }
                        if(matriz[i+1][j-1].isTieneBomba()){
                            cantidadBombasAlrededor++;
                        }
                        if(matriz[i][j-1].isTieneBomba()){
                            cantidadBombasAlrededor++;
                        }
                    }else if(i==cantidadFilas-1){
                        if(matriz[i-1][j].isTieneBomba()){
                            cantidadBombasAlrededor++;
                        }
                        if(matriz[i-1][j-1].isTieneBomba()){
                            cantidadBombasAlrededor++;
                        }
                        if(matriz[i][j-1].isTieneBomba()){
                            cantidadBombasAlrededor++;
                        }
                    }else {
                        if(matriz[i-1][j].isTieneBomba()){
                            cantidadBombasAlrededor++;
                        }
                        if (matriz[i+1][j].isTieneBomba()){
                            cantidadBombasAlrededor++;
                        }
                        if(matriz[i-1][j-1].isTieneBomba()){
                            cantidadBombasAlrededor++;
                        }
                        if(matriz[i][j-1].isTieneBomba()){
                            cantidadBombasAlrededor++;
                        }
                        if (matriz[i+1][j-1].isTieneBomba()){
                            cantidadBombasAlrededor++;
                        }
                    }
                }else {
                    if(matriz[i+1][j].isTieneBomba()){
                        cantidadBombasAlrededor++;
                    }
                    if(matriz[i+1][j+1].isTieneBomba()){
                        cantidadBombasAlrededor++;
                    }
                    if (matriz[i+1][j-1].isTieneBomba()){
                        cantidadBombasAlrededor++;
                    }
                    if(matriz[i-1][j].isTieneBomba()){
                        cantidadBombasAlrededor++;
                    }
                    if(matriz[i-1][j+1].isTieneBomba()){
                        cantidadBombasAlrededor++;
                    }
                    if(matriz[i-1][j-1].isTieneBomba()){
                        cantidadBombasAlrededor++;
                    }
                    if(matriz[i][j+1].isTieneBomba()){
                        cantidadBombasAlrededor++;
                    }
                    if(matriz[i][j-1].isTieneBomba()){
                        cantidadBombasAlrededor++;
                    }
                }
                matriz[i][j].setNumero(cantidadBombasAlrededor);
                cantidadBombasAlrededor=0;
            }
        }

        model.addAttribute("matrizBloques",matriz);
        return "main";
    }

    @GetMapping("/juego")
    public String juegoMolde(){
        return "juegoMolde";
    }

    private void buscarBloquesSinBomba(int fila,int columna,Bloque[][] matriz,int cantidadFilasMatriz,int cantidadColumnasMatriz){

    }
}
