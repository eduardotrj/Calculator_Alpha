package Main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Calculadora {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		Ventana CalcMarc=new Ventana(); //Crea objeto ventana
		
		CalcMarc.setVisible(true);	//Vuelve visible
		
		CalcMarc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//Permite su cierre
		
	}

}//calculadora class

class Ventana extends JFrame{
	
	public Ventana() {
		
		setTitle("Calculadora Alpha");	//Titulo ventana
		
		setBounds(750,300,756,500);		//Tamano y posicion de la ventana
		
		LaminaDisplay milamina=new LaminaDisplay();
		
		add(milamina);
		
		//pack();//adapta el tamano de los botones al tamano de los elemmentos
		
	}
}//ventana class

class LaminaDisplay extends JPanel implements ActionListener{
	
	public LaminaDisplay() {
		
		principio=true;	//resetea el valor de pantalla al introducir un caracter.
		
		setLayout(new BorderLayout());
		
		pantalla=new JButton("0"); //crea boton como pantalla
		
		pantalla.setPreferredSize(new Dimension(40, 80)); //modifica tamano pantalla
		
		pantalla.setFont(new Font("Arial", Font.PLAIN, 40)); 
		
		pantalla.setEnabled(false); //Desabilita animacion de boton.
		
		add(pantalla,BorderLayout.NORTH);
		
		LaminaBotones=new JPanel();
		
		LaminaBotones.setLayout(new GridLayout(4,4));
		
		agregarBotones();  //Llama al metodo
		
		add(LaminaBotones,BorderLayout.CENTER);
	}
	
	
	
	private void agregarBotones() {
	
	  String texto_Botones = "789/456*123-0.=+"; // El orden de caracteres en un string
	  
	  String caracter_Boton;	//variable temporal
	  for (int i = 0; i < texto_Botones.length(); i++ ) { //Para recorrer el string de caracteres
		    	
	      caracter_Boton = Character.toString(texto_Botones.charAt(i));//Asigna a CaracterB, un caract "n(i)" de textoB
	      
	      boton = new JButton(caracter_Boton); //Crea un boton con ese caracter de texto.
	      
	      boton.setFont(new Font("Arial", Font.PLAIN, 40)); 
	      
	      boton.addActionListener(this);  //Agrega un listener al boton creado
	      
	      LaminaBotones.add(boton);	//anade a lamina el boton
	      
	   //   texto_Botones = texto_Botones.substring(1); //Quita el primer caracter de la cadena
	      
	  }
	  
	}//agregarBotones
	public void actionPerformed(ActionEvent e) { //metodo de eventos pulsados
		
		String entrada=e.getActionCommand(); //Recoge el obj. evento y lo traduce a un simbolo
		
		if (entrada.equals(".")) {//detecta si lleva un punto.
			
			if (llevaPunto) {//si ya hay un punto, anula esa entrada
				entrada="";
			} else { //si no, activa llevaPunto
			
			llevaPunto=true;
			}
		}

		
		
	//	System.out.println(ultimoDigito);
		
		
			
		//resetea el valor de la pantalla a 0. Requiere anadir variables a resetear!!!
		if(principio) {
			
			pantalla.setText("");
			
			principio=false;
		}
		
		
		if (entrada.equals("=")) { //Si tiene un igual, calcula el resultado.
			
			resultado(pantalla.getText()); //coge texto de pantalla
			
			//resultado("56+36/6-5*4/-3");
			
			entrada="";
			
			principio=true; //para resetear la pantalla
		}
		
		
		/*captura texto de pantalla, comprobar su longitud, si tiene mas de 2 caracteres
		 * extrae el ultimo para comprobar si es un signo, cual y evitar repetirlo
		 * si no,evita introducir signos :D
		 */
		
		String textoPantalla = pantalla.getText(); 
		int longitudPantalla=textoPantalla.length();
		if (longitudPantalla>0) {
			String ultimoCaracter=textoPantalla.substring(longitudPantalla-1,longitudPantalla);
			
			/*Comprobar si el simbolo introducido es un -, en tal caso, desabilitar si ya hay un + o otro - introducido.
			 * Si el simbolo es +, *, /, comprobar de que si existe otro simbolo, deshabilitar la entrada.
			 */
			if (entrada.equals("-")) {
				if (ultimoCaracter.equals("+") || ultimoCaracter.equals("-") || ultimoCaracter.equals(".")){
					entrada="";
				}
			}else if (entrada.equals("+") || 
					entrada.equals("/") || 
					entrada.equals("*") ||
					entrada.equals(".")) {
				
				if (ultimoCaracter.equals("+") || 
						ultimoCaracter.equals("/") || 
						ultimoCaracter.equals("*") ||
						ultimoCaracter.equals(".") ||
						ultimoCaracter.equals("-")) {
				
								entrada="";
					}
				
			}
			
			
			//System.out.println("El ultimo elemento introducido es un: " +ultimoCaracter);
		}
		pantalla.setText(pantalla.getText()+ entrada);//suma la entrada al final de texto de pantalla.
		
	}//fin metodo actionperformed
	
	//________________________ METODO PARA CALCULAR EL STRING__________________
	private void resultado (String operacion) {
		/*recoger el strin y recorrerlo hasta el siguiente simbolo 
		 * detectar el primer simbolo y separarlo hasta ahi
		guardarlo en temporal1
		comprobar si el simbolo es * o /,
			si tiene, guardar la siguiente cantidad en temporal2
			efectuar calculo con temporal1 y guardarlo en temporal1
			
		pasar temporal a una cadena
		signos + y - pasar a cadena
		
		*/
		String temporal1;
		
		String temporal2;
		
		Double resultado=0.0;
		
		String cadena="";
		
		String simbol="";
		
		int posicionSimbol; //en que posicion se encuentra el simbolo
		
	/*Comprueba si tiene / o *. Despues determina cual es mas proximo.
	 * Una vez localizado, separa todo el string en base a ese signo. y otros adyacentes
	 * Cadena + temporal1 + signo + temporal2 + operacion
	 */
	
	//___________________ Quiza se pueda reducir____________________________	
		while(operacion.contains("/") || operacion.contains("*")){ //ejecuta si contiene simbolos * / while!!
			
			if(operacion.contains("/") && operacion.contains("*")){ //si tiene ambos
				int division = operacion.indexOf("/");
				int multiplicacion = operacion.indexOf("*");
				
				if(division<multiplicacion) { //determina cual es mas proximo 
					
					posicionSimbol=division;  //y lo asigna
				} else {
					
					posicionSimbol=multiplicacion;
				}
				
			}else if(operacion.contains("/")) { //si no, determina cual de ellos si tiene
				posicionSimbol = operacion.indexOf("/"); //y lo asigna
			}
			else {
				posicionSimbol = operacion.indexOf("*");
			}
	//________________________________________________________________________		
			temporal1 = operacion.substring(0,posicionSimbol); //extrae a parte de la cadena hasta el simbolo / o *.
			operacion=operacion.substring(posicionSimbol);  //guarda la parte restante en operacion
			
			simbol = operacion.substring(0,1); //extrae el simbolo.
			operacion=operacion.substring(1);  //guarda la parte restante en operacion
			
			
			//determina si en esa parte hay + o - en temporal1
			if(temporal1.contains("+") || temporal1.contains("-")) { 
				posicionSimbol=temporal1.lastIndexOf("+");
				if (posicionSimbol<temporal1.lastIndexOf("-")){
				posicionSimbol=temporal1.lastIndexOf("-");
				}
				
				cadena = cadena.concat(temporal1.substring(0,posicionSimbol));
				temporal1=temporal1.substring(posicionSimbol);
			}
			
			//determina si quedan mas simbolos para definir temporal2
			//comprueba si la parte restante tiene simbolos, si los tiene, determina cual tiene (para evitar -1 de contains)
			//y anade la posicion del caracter mas cercano.
			if(operacion.contains("+") || operacion.contains("-") || operacion.contains("/") || operacion.contains("*")) { 
				posicionSimbol=32000; //Para resetear la variable
				if(operacion.contains("*")) {
				posicionSimbol=operacion.indexOf("*");
				}
				if(operacion.contains("/")) {
					if (posicionSimbol>operacion.indexOf("/")){
						posicionSimbol=operacion.indexOf("/");
						}
				}
				//comprueba que la posicion de + o - no sea la posicion 0.
				if(operacion.contains("-") && 0<operacion.indexOf("-", 1)) {
					if (posicionSimbol>operacion.indexOf("-", 1)){
						posicionSimbol=operacion.indexOf("-"); ///
						}
				}
				if(operacion.contains("+")&& 0<operacion.indexOf("+", 1)) {
					if (posicionSimbol>operacion.indexOf("+", 1)){
						posicionSimbol=operacion.indexOf("+");
						} 
				} 
				//Se encarga de asegurar las alternativas en las que el resultado de posicion sea 0
				// sea el valor por defecto 32000.
				if (posicionSimbol==0 || posicionSimbol==32000){ 
					posicionSimbol=operacion.length();
				}
				
				temporal2 = operacion.substring(0,posicionSimbol);//extrae de operacion, operador 2
				operacion=operacion.substring(posicionSimbol);
				
			} else {
				temporal2=operacion;
				operacion="";//limpia operacion para quitar temporal2 al extraerlo
			}
			
			temporal1=calcular(temporal1,temporal2, simbol);//guarda el resultado obtenido
					
			operacion=temporal1.concat(operacion); //devuelve a la parte cadena 
			
			
		/*	System.out.println("Esta es la cadena si hay " + cadena);
			System.out.println("Esta es temporal1 " +temporal1);
			System.out.println("Esta es temporal2 " +temporal2);
			System.out.println("Este es el simbolo " + simbol);
			System.out.println("Esta la operacion restante 1: " + operacion); */
			
			
		}//fin while de zona multiplicacion y division
		
		operacion=cadena.concat(operacion); //une todo lo restante
		System.out.println("La operacion restante es de: " + operacion);
		
		cadena=operacion.substring(1);//Hace una lectura para excluir cualquier signo inicial
		
		while(cadena.contains("+") || cadena.contains("-")){	//?????????????????????????????????????????????????
			posicionSimbol=32000;	
			if (cadena.contains("-")){
				posicionSimbol = cadena.indexOf("-"); ///

			}
			if (cadena.contains("+")) {
				if (posicionSimbol > cadena.indexOf("+")){
					posicionSimbol = cadena.indexOf("+");
				}
			}
			posicionSimbol=posicionSimbol+1;	//resetea valor cadena(-1)
			
			temporal1 = operacion.substring(0,posicionSimbol); //extrae a parte de la cadena hasta la operacion.
			operacion=operacion.substring(posicionSimbol);///////////////
			
			simbol = operacion.substring(0,1); //extrae el simbolo.
			operacion=operacion.substring(1);  //guarda la parte restante en operacion
			
			cadena=operacion.substring(1);
			//_____________________________________________________________________
			
			if(cadena.contains("+") || cadena.contains("-")) { 
				posicionSimbol=32000; //Para resetear la variable
				if(cadena.contains("-")) {
				posicionSimbol=cadena.indexOf("-");
				}
				if(cadena.contains("+")) {
					if (posicionSimbol>cadena.indexOf("+")){
						posicionSimbol=cadena.indexOf("+");
						}
				}
				//Se encarga de asegurar las alternativas en las que el resultado de posicion sea 0
				// sea el valor por defecto 32000.
				if (posicionSimbol==0 || posicionSimbol==32000){ 
					posicionSimbol=operacion.length();
				}
				posicionSimbol=posicionSimbol+1;
				temporal2 = operacion.substring(0,posicionSimbol);//extrae de operacion, operador 2
				operacion=operacion.substring(posicionSimbol);
				
			} else {
				temporal2=operacion;
				operacion="";//limpia operacion para quitar temporal2 al extraerlo
			}
			
			//____________________________________________________________________
			temporal1=calcular(temporal1,temporal2, simbol);//guarda el resultado obtenido
			
			operacion=temporal1.concat(operacion); //devuelve a la parte cadena 
			
			System.out.println("La operacion **FINAL** es de: " + operacion);
			cadena=operacion.substring(1);
				
			}//fin while suma y resta
		//muestra el resultado final en pantalla
		pantalla.setText("El resultado es = "+ operacion);
		
		
		}//fin metodo resultado
	
	private String calcular (String cifra1,String cifra2, String operador) {
		
		double resultado=0;
		double a= Double.parseDouble(cifra1);//pasa string a numeros
		double b= Double.parseDouble(cifra2);
		
		switch(operador) {
		  case "*":
			  resultado=a*b;
		    break;
		  case "/":
			  resultado=a/b;
		    break;
		  case "-":
			  resultado=a-b;
		    break;
		  default:  //+
			  resultado=a+b;
		}
		
		//System.out.println("El  resultado troc " + resultado);
		
		String numeroObtenido= String.valueOf(resultado);
		
		if (resultado>0) {//anade un + si el resultado es positivo
			numeroObtenido="+".concat(numeroObtenido); 
		}
		return(numeroObtenido);
	}//fin metodo calcular
	
	//anadir boton reset.
	
	  private JPanel LaminaBotones;
	  
	  private JButton pantalla;
	  
	  private JButton boton;
	  
	  private boolean principio;
	  
	  //debe resetearse
	  private boolean llevaPunto = false; //para activar si se ha introducido ya un punto
	 // private String ultimoDigito;  //guarda cada digito para comprobar si hay un punto o un igual.
	  
}//LaminaDisplay