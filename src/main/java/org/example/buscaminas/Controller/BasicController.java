package org.example.buscaminas.Controller;

import org.example.buscaminas.Entity.Bloque;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Integer.parseInt;

@Controller
public class BasicController {


    private Bloque[][]matriz;
    private Integer numeroIntentos;
    private Integer bloquesDescubiertos;
    private Integer filas;
    private Integer columnas;
    private Integer cantidadBombas;
    @GetMapping("/buscaminas")
    public String inicio(){
        return "main";
    }

    @GetMapping("/jugar")
    public String juego(Model model,@RequestParam(name="filas") Integer cantidadFilas,
                        @RequestParam(name = "columnas") Integer cantidadColumnas,
                        @RequestParam(name = "intentos")Integer numero,
                        @RequestParam(name = "posiciones") String posicionesBombas){
            matriz=new Bloque[cantidadFilas][cantidadColumnas];
            numeroIntentos=numero;
            bloquesDescubiertos=0;
            columnas=cantidadColumnas;
            filas=cantidadFilas;
            String[] auxPosiciones=posicionesBombas.split(" ");
            cantidadBombas=auxPosiciones.length;
            ArrayList<Integer[]>posicionBomba=new ArrayList<>();
            for(int i=0;i<cantidadBombas;i++){
                Integer[] aux={Integer.parseInt(auxPosiciones[i].split(",")[0].split("\\(")[1])-1,Integer.parseInt(auxPosiciones[i].split(",")[1].split("\\)")[0])-1};
                posicionBomba.add(aux);
            }
            for (int i=0;i<cantidadFilas;i++){
                for(int j=0;j<cantidadColumnas;j++){
                    matriz[i][j]=new Bloque();
                    for(int k=0;k<posicionBomba.size();k++){
                        if(i==posicionBomba.get(k)[0]&&j==posicionBomba.get(k)[1]){
                            matriz[i][j].setTieneBomba(true);
                        }
                    }
                }
            }
        for(int i=0;i<filas;i++) {
            for (int j = 0; j < columnas; j++) {
                if (i > 0 && j > 0) {
                    if (matriz[i - 1][j - 1].isTieneBomba()) {
                        matriz[i][j].setNumero(matriz[i][j].getNumero() + 1);
                    }
                }
                if (i > 0) {
                    if (matriz[i - 1][j].isTieneBomba()) {
                        matriz[i][j].setNumero(matriz[i][j].getNumero() + 1);
                    }
                }
                if (i > 0 && j < columnas - 1) {
                    if (matriz[i - 1][j + 1].isTieneBomba()) {
                        matriz[i][j].setNumero(matriz[i][j].getNumero() + 1);
                    }
                }
                if (j > 0) {
                    if (matriz[i][j - 1].isTieneBomba()) {
                        matriz[i][j].setNumero(matriz[i][j].getNumero() + 1);
                    }
                }
                if (j < columnas - 1) {
                    if (matriz[i][j + 1].isTieneBomba()) {
                        matriz[i][j].setNumero(matriz[i][j].getNumero() + 1);
                    }
                }
                if (j > 0 && i < filas - 1) {
                    if (matriz[i + 1][j - 1].isTieneBomba()) {
                        matriz[i][j].setNumero(matriz[i][j].getNumero() + 1);
                    }
                }
                if (i < filas - 1) {
                    if (matriz[i + 1][j].isTieneBomba()) {
                        matriz[i][j].setNumero(matriz[i][j].getNumero() + 1);
                    }
                }
                if (j < columnas - 1 && i < filas - 1) {
                    if (matriz[i + 1][j + 1].isTieneBomba()) {
                        matriz[i][j].setNumero(matriz[i][j].getNumero() + 1);
                    }
                }
            }
        }
        model.addAttribute("bombaEncontrada",false);
        model.addAttribute("gano",false);
        model.addAttribute("perdio",false);
        model.addAttribute("matriz", matriz);
        return "juego";
    }

    @GetMapping("/minar")
    public String minar(Model model,
                        @RequestParam(value = "coordenada")String coordenadaAux){
        Integer[]coordenada={Integer.parseInt(coordenadaAux.split(" ")[0])-1,Integer.parseInt(coordenadaAux.split(" ")[1])-1};
        Boolean bombaEncontrada=false;
        if(matriz[coordenada[0]][coordenada[1]].getEstado().equals("sinExplorar")){
            if(matriz[coordenada[0]][coordenada[1]].isTieneBomba()){
                matriz[coordenada[0]][coordenada[1]].setEstado("descubiertoConBomba");
                numeroIntentos--;
                bombaEncontrada=true;
                model.addAttribute("bombaEncontrada",bombaEncontrada);
            }else {
                if(matriz[coordenada[0]][coordenada[1]].getNumero()!=0){
                    explotarMina(coordenada[0],coordenada[1],false);
                }else {
                    explotarMina(coordenada[0],coordenada[1],true);
                }

            }
        }
        model.addAttribute("bombaEncontrada",bombaEncontrada);
        if(numeroIntentos==0){
            model.addAttribute("perdio",true);
        }else{
            model.addAttribute("perdio",false);
        }
        if(bloquesDescubiertos==filas*columnas-cantidadBombas){
            model.addAttribute("gano",true);
        }else {
            model.addAttribute("gano",false);
        }
        model.addAttribute("matriz",matriz);
        model.addAttribute("intentos",numeroIntentos);
        return "juego";
    }


    private void explotarMina(Integer x,Integer y,Boolean aux){

                matriz[x][y].setEstado("descubiertoSinBomba");
                bloquesDescubiertos++;
                if(x>0){
                    Bloque bloque=matriz[x-1][y];
                    if(!bloque.isTieneBomba()&bloque.getEstado().equals("sinExplorar")){
                        if(bloque.getNumero()!=0){
                            if(aux){
                                explotarMina(x-1,y,false);
                            }
                        }else {
                            explotarMina(x-1,y,true);
                        }
                    }
                }
                if(y<columnas-1){
                    Bloque bloque=matriz[x][y+1];
                    if(!bloque.isTieneBomba()&&bloque.getEstado().equals("sinExplorar")){
                        if(bloque.getNumero()!=0){
                            if(aux){
                                explotarMina(x,y+1,false);
                            }
                        }else {
                            explotarMina(x,y+1,true);
                        }

                    }
                }
                if(y>0){
                    Bloque bloque=matriz[x][y-1];
                    if(!bloque.isTieneBomba()&&matriz[x][y-1].getEstado().equals("sinExplorar")){
                        if(bloque.getNumero()!=0){
                            if(aux){
                                explotarMina(x,y-1,false);
                            }
                        }else {
                            explotarMina(x,y-1,true);
                        }
                    }
                }
                if(x<filas-1){
                    Bloque bloque=matriz[x+1][y];
                    if(!bloque.isTieneBomba()&&bloque.getEstado().equals("sinExplorar")){
                        if(bloque.getNumero()!=0){
                            if(aux){
                                explotarMina(x+1,y,false);
                            }
                        }else {
                            explotarMina(x+1,y,true);
                        }
                    }
                }

    }
}
